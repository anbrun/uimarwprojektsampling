package uimarwprojekt.markfreeindirect;

import java.util.*;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import uimarwprojekt.types.FreeIndirect;
import uimarwprojekt.types.RWToken;

import java.io.*;

/**
 * MarkFreeIndirect 
 * UIMA component to annotate free indirect speech, thought and writing representation
 * Created: Dec 2014
 * 
 * @author Annelen Brunner, brunner@ids-mannheim.de
 */
public class MarkFreeIndirect extends JCasAnnotator_ImplBase {

	public static final String PARAM_DEBUG = "Debugging-Informationen ausgeben";
	@ConfigurationParameter(name = PARAM_DEBUG, defaultValue = "false", description = "Output debugging info")
	private Boolean debug;

	public static final String PARAM_LIMIT = "Grenze zur Annotation";
	@ConfigurationParameter(name = PARAM_LIMIT, defaultValue = "2", description = "Threshold must have at least this value.")
	private Integer limit;

	public static final String PARAM_USE_ADVINDICATOR = "ADV-Wert als Indikator verwenden";
	@ConfigurationParameter(name = PARAM_USE_ADVINDICATOR, defaultValue = "true", description = "Use ADV as indicator.")
	private Boolean useAdvIndicator;

	public static final String PARAM_FIWORDSURL = "Liste der FI-Wörter";
	@ConfigurationParameter(name = PARAM_FIWORDSURL, defaultValue = "", description = "URL specifying the list of fi words.")
	private String fiWordsURL;

	private Set<String> puncSpec = new HashSet<String>();
	private Set<String> fiWords = new HashSet<String>();

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);

		this.puncSpec.add("?");
		this.puncSpec.add("-");
		this.puncSpec.add("!");
		try {
			this.readWordList();
		} catch (IOException e) {
			System.out.println("Error when parsing FI word list.");
			e.printStackTrace();
		}
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Map<Sentence, Collection<Token>> sentsWithToks = JCasUtil.indexCovered(
				aJCas, Sentence.class, Token.class);

		int counter = 0;
		for (Sentence sent : sentsWithToks.keySet()) {

			if (this.debug) {
				System.out.println("Sentence: " + counter);
			}

			List<Token> tokList = new ArrayList<>(sentsWithToks.get(sent));

			int indicatorCounter = 0;
			int advCounter = 0;
			boolean blocked = false;

			// Negative indicator:
			// if sentence length is above average: decrement indicatorCounter
			// (20 is about average for the Erzaehltextkorpus)
//			if (tokList.size() > 20) {
//				indicatorCounter = indicatorCounter - 4;
//				if (debug) {
//					System.out.println("long sent (-4): " + indicatorCounter);
//				}
//			} else if (tokList.size() > 10) {
//				indicatorCounter = indicatorCounter - 2;
//				if (debug) {
//					System.out.println("long sent (-2): " + indicatorCounter);
//				}
//			}

			// Loop through all the tokens in the current sentence
			for (Token token : tokList) {

				// get the corresponding RWToken
				RWToken currentRWTok = null;
				List<RWToken> rwTokList = JCasUtil.selectCovered(RWToken.class,
						token);
				if (rwTokList.isEmpty()) {
					if (debug) {
						System.out
								.println("Fehler: RW-Token-Annotation nicht vorhanden");
					}
					throw new AnalysisEngineProcessException();
				} else {
					currentRWTok = rwTokList.get(0);
				}

				// tokList.add(token);

				// collect indicators from the POS markup
				String ttFeat = (String) token.getPos().getPosValue();

				// Negative indicators:
				//
				// if the sentence contains a 1st or 2nd person pronoun:
				// reset indicatorCounter and break the loop
				if (currentRWTok.getRfpos().matches(
						"PRO\\.Pers\\.Subst\\.[12]\\..*")) {
					indicatorCounter = 0;
					blocked = true;
					if (debug) {
						System.out.println("1st/2nd Person: blocked");
					}
					break;
				}
				// if there is a quotation mark: reset the counter and break the
				// loop
				else if (ttFeat.equals("$(")) {
					indicatorCounter = 0;
					blocked = true;
					if (debug) {
						System.out.println("$(: blocked");
					}
					break;
				}
				// Positive indicators:
				// if the token is in the puncSpec list: +1
				else if (this.puncSpec
						.contains((String) token.getCoveredText())) {
					indicatorCounter = indicatorCounter + 1;
					if (debug) {
						System.out
								.println("PuncSpec (+1): " + indicatorCounter);
					}
				}
				// if the token is in the fiWords list: + 1
				else if (this.fiWords.contains((String) token.getLemma()
						.getValue())) {
					indicatorCounter = indicatorCounter + 1;
					if (debug) {
						System.out.println("FiWord (+1): " + indicatorCounter);
					}
				} else if (token.getCoveredText().matches("würde(n)*")) {
					indicatorCounter = indicatorCounter + 1;
					if (debug) {
						System.out.println("würde (+1): " + indicatorCounter);
					}
				}
				// if the token is ITJ: +2
				else if (ttFeat.equals("ITJ")) {
					indicatorCounter = indicatorCounter + 2;
					if (debug) {
						System.out.println("ITJ (+2): " + indicatorCounter);
					}
				}

				if (this.useAdvIndicator) {
					// if the token is ADV: add +1 to advCounter
					if (ttFeat.equals("ADV")) {
						advCounter++;
					}
				}

			}

			if (!blocked) {
				// calculate how much to add from the advCounter:
				// if ADV is between 5 and 15% of tokens -> add 1
				// if ADV is between 15 and 25% of tokens -> add 2 etc.
				Float relAdv = Float.valueOf(advCounter)
						/ Float.valueOf(tokList.size()) * 10;
				if (debug) {
					System.out.println("relAdv: " + relAdv);
					System.out.println("--- " + Math.round(relAdv));
					System.out.println("--- " + relAdv.intValue());
				}
				indicatorCounter = indicatorCounter + relAdv.intValue();
				if (debug) {
					System.out.println("ADV.rel (+ " + relAdv + "): "
							+ indicatorCounter);
				}
			}

			
			// if debug is true, show the score for each sentence
			if (debug) {
				System.out.println("Sentence: " + sent.getCoveredText() + "\\n score: " + indicatorCounter + " blocked: " + blocked);
			}
			
			// if indicator > limit, add an annotation
			if (indicatorCounter >= this.limit) {
				FreeIndirect free_ind = new FreeIndirect(aJCas,
						sent.getBegin(), sent.getEnd());
				free_ind.setWeight(indicatorCounter);
				free_ind.addToIndexes(aJCas);
			}

			counter++;

		}
	}

	private void readWordList() throws IOException {

		try {
			// Open a reader over the file so we can load
			// the fi words
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fiWordsURL), "UTF8"));
			if (this.debug) {
				System.out.println("FiWordsUrl: " + fiWordsURL.toString());
			}
			// read in the first line of the file
			String line = in.readLine();

			this.fiWords = new HashSet<String>();

			while (line != null) {
				// while there is still data in the file...
				String[] linefields = line.trim().split("\\s+");
				fiWords.add(linefields[0]);

				// get the next line from the data file
				line = in.readLine();
			}

			// close the data file now we have finished with it
			in.close();
			if (this.debug) {
				System.out.println("fiWords read: " + fiWords);
			}
		}

		catch (Exception e) {
			// if an error occurred then throw an exception so that the user
			// knows
			throw new IOException(
					"Unable to correctly read in the rw verbs file: "
							+ e.getMessage());
		}

	}
}
