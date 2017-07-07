package uimarwprojekt.markstwwords;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import uimarwprojekt.types.STWWord;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * MarkSTWWords 
 * UIMA component to annotate speech, thought and writing words
 * Created: Dec 2014
 * 
 * @author Annelen Brunner, brunner@ids-mannheim.de
 */
public class MarkSTWWords extends JCasAnnotator_ImplBase {

	public static final String PARAM_DEBUG = "Debugging-Informationen ausgeben";
	@ConfigurationParameter(name=PARAM_DEBUG, defaultValue="false", description = "Output debugging info")
	private Boolean debug;
	
	// This file has to be UTF-8 *without* BOM
	public static final String PARAM_STWORDS_FILE = "STW File";
	@ConfigurationParameter(name=PARAM_STWORDS_FILE, description="", mandatory=true, defaultValue="resources/mstwwords.csv")
	File fileSTWWords;
	
	public static final String PARAM_PENALTYLEVEL = "Maximaler Penalty-Wert, den das Wiedergabewort haben darf";
	@ConfigurationParameter(name = PARAM_PENALTYLEVEL, defaultValue = "5", description = "Ignore all frame words with the marker 'rep'")
	private int penaltyLevel;
	
	// collection of stwwords
	private ArrayList<String> stwWords;
	// additional infos for the stwwords
	private ArrayList<String> stwWordsCat = new ArrayList<String>();
	private ArrayList<String> stwWordsSource = new ArrayList<String>();
	private ArrayList<Integer> stwWordsPenalty = new ArrayList<Integer>();
	private ArrayList<String> stwWordsMark = new ArrayList<String>();
	private ArrayList<Integer> stwWordsFreq = new ArrayList<Integer>();

	private URL rwWordsURL;
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);

		if ( this.fileSTWWords != null ) {
			try {
				rwWordsURL = this.fileSTWWords.toURI().toURL();
			} catch (MalformedURLException e1) {
				throw new ResourceInitializationException(e1);
			}
		}
		else {
			throw new ResourceInitializationException();
		}

		try {
			this.readWordList();
		} catch (IOException e) {
			throw new ResourceInitializationException(e);			
		}
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		AnnotationIndex<Annotation> ai = aJCas.getAnnotationIndex(Token.type);
		for (Annotation annotation : ai) {
			Token tok = (Token) annotation;
			
			if (this.debug) {
				System.out.println("Curr Token: " + tok.getLemma().getValue());
			}

			if ( stwWords.contains(tok.getLemma().getValue())) {
				STWWord stwWord = new STWWord(aJCas, tok.getBegin(), tok.getEnd());
				
				if (this.debug) {
					System.out.println("This Token: " + tok.getLemma().getValue() + " annotated");
				}
				
				// set the attributes
				String lemmaStr = tok.getLemma().getValue();
				int pos = stwWords.indexOf(lemmaStr);
				stwWord.setLemma(lemmaStr);
				stwWord.setSource(this.stwWordsSource.get(pos));
				stwWord.setPenalty(this.stwWordsPenalty.get(pos));
				stwWord.setCategory(this.stwWordsCat.get(pos));
				stwWord.setMarker(this.stwWordsMark.get(pos));
				stwWord.setFrequency(this.stwWordsFreq.get(pos));
				
				stwWord.addToIndexes(aJCas);
			}
		}

	}

	private void readWordList() throws IOException {

		try {
			// Open a reader for the rw word file so we can load
			// the list of rw word
			Path path = fileSTWWords.toPath();
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

			if (this.debug) {
				System.out.println("VerbUrl: " + rwWordsURL.toString());
			}
			this.stwWords = new ArrayList<String>();
			

			for (String line : lines) {
				// while there is still data in the file...
				
				String[] linefields = line.trim().split("\\s+");			
				// when there are 5 elements they should be: 1:lemma. 2:source,
				// 3:penalty, 4:cat; 5: marker
				if (linefields.length >= 5) {
					// if a penalty value is defined, penalty level
					// need to be checked
					Integer penalty = Integer.parseInt(linefields[2]);
					if (penalty <= this.penaltyLevel) {
					
					this.stwWords.add(linefields[0]);
					this.stwWordsSource.add(linefields[1]);
					this.stwWordsPenalty.add(penalty);
					this.stwWordsCat.add(linefields[3]);
					this.stwWordsMark.add(linefields[4]);
					this.stwWordsFreq.add(Integer.parseInt(linefields[5]));
					}
				}
				// otherwise assume that the first element is the lemma and
				// ignore the rest
				else {
					stwWords.add(linefields[0]);
					// all other fields get default values
					this.stwWordsSource.add("");
					this.stwWordsPenalty.add(0);
					this.stwWordsCat.add("");
					this.stwWordsMark.add("");
					this.stwWordsFreq.add(0);
				}
				
			}
			if (this.debug) {
				System.out.println("rwWords read: " + stwWords);
			}
		}

		catch (Exception e) {
			// if an error occurred then throw an exception so that the user
			// knows
			throw new IOException(
					"Unable to read the stwword file: "
							+ e.getMessage());
		}

	}
}

