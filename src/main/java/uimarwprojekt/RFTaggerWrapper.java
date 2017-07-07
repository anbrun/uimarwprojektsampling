package uimarwprojekt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
// from the rfTagger java wrapper
import de.sfb833.a4.RFTagger.Model;
import de.sfb833.a4.RFTagger.RFTagger;
import de.sfb833.a4.RFTagger.tagsetconv.ConverterFactory;
import de.sfb833.a4.RFTagger.tagsetconv.TagsetConverter;
import de.sfb833.a4.RFTagger.tagcorrector.*;
import de.sfb833.a4.RFTagger.lemmatizer.*;
import uimarwprojekt.types.RWToken;

/**
 * RFTaggerWrapper Wrapper for the RFTagger Java interface
 * (http://sifnos.sfs.uni-tuebingen.de/resource/A4/rftj) Stores the RFTagger
 * info in RWToken
 * 
 * Created: Dec 2014
 * 
 * @author Annelen Brunner, brunner@ids-mannheim.de
 */
public class RFTaggerWrapper extends JCasAnnotator_ImplBase {
	public static final String COMPONENT_ID = "RFTagger";
	public static final String RFTAGGER_CONFIG_FILE = "RFTaggerConfig";

	public static final String PARAM_PROPERTIESFILENAME = "Properties file for RFTagger";
	@ConfigurationParameter(name = PARAM_PROPERTIESFILENAME, defaultValue = "", description = "Properties file for RFTagger")
	private String propertiesFileName;
	
	private RFTagger rftagger;
	private Properties rfproperties;

	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);

		try {
			// load the properties files
			rfproperties = new Properties();
			BufferedInputStream stream = new BufferedInputStream(
					new FileInputStream(propertiesFileName));
			rfproperties.load(stream);
			stream.close();

			// intialize the RFTagger
			Model model = new Model(new File(
					rfproperties.getProperty("rftagger_par32")), new File(
					rfproperties.getProperty("rftagger_par64")));

			this.rftagger = new RFTagger(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceInitializationException();
		}
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// build a List of all Tokens
		List<String> strtokens = new ArrayList<>();
		List<Token> tokens = new ArrayList<>();
		Iterator<Token> tokiter = JCasUtil.iterator(aJCas, Token.class);
		while (tokiter.hasNext()) {
			Token tok = tokiter.next();
			strtokens.add(tok.getCoveredText());
			tokens.add(tok);
		}
		// get the tags for each token
		List<String> rftags = this.rftagger.getTags(strtokens);

		try {
			// correct the tags (necessary esp for lemmatization)
			TagCorrector corrector = TagCorrectorFactory
					.getTagCorrector("german");

			// get the stts tags for each token
			TagsetConverter conv = ConverterFactory.getConverter("stts");

			// get the lemmata
			// this loads the lexicon and takes a while...
			Lemmatizer lem = LemmatizerFactory.getLemmatizer("german",
					new File(rfproperties.getProperty("rftagger_lex")));

			int counter = 0;
			for (String tok : strtokens) {
				Token fullToken = tokens.get(counter);

				String corTag = corrector.correctTag(rftags.get(counter));
				String sttsTag = conv.rftag2tag(corTag);
				String lemma = lem.getLemma(tok, corTag);
				if (lemma == null) {
					lemma = "";
				}

				RWToken newTok = new RWToken(aJCas, fullToken.getBegin(),
						fullToken.getEnd());
				newTok.setRfpos(corTag);
				newTok.setPos(sttsTag);
				newTok.setLemma(lemma);
				newTok.addToIndexes(aJCas);

				counter++;
			}

		} catch (Exception e) {
			throw new AnalysisEngineProcessException();
		}
	}
}
