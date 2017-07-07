package uimarwprojekt.markindirect;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import uimarwprojekt.types.STWWord;
import uimarwprojekt.types.Indirect;
import uimarwprojekt.types.RWToken;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class MarkIndirectRFTagger extends JCasAnnotator_ImplBase {

	public static final String PARAMCONJVERBURL = "Konjunktivverb-Liste";
	@ConfigurationParameter(name=PARAMCONJVERBURL, defaultValue = "C:/Users/Annelen/Documents/eclipse-workspace/uimarwprojekt/src/main/resources/conj_verbs.list", description = "URL of the list of verbs in conjunctive mode")
	private File conjVerbsURL;

	@ConfigurationParameter(defaultValue = "C:/Users/Annelen/Documents/eclipse-workspace/uimarwprojekt/src/main/resources/conjunctions.list", description = "URL of the list of conjunctions")
	private File conjURL;

	@ConfigurationParameter(defaultValue = "C:/Users/Annelen/Documents/eclipse-workspace/uimarwprojekt/src/main/resources/quotation_marks.list", description = "URL of the list of quotation marks")
	private File quotMarksURL;

	// @Optional
	// @RunTime
	@ConfigurationParameter(defaultValue = "true", description = "Output debugging info")
	private Boolean debug;

	// @Optional
	// @RunTime
	@ConfigurationParameter(defaultValue = "true", description = "Remove the frame word when processing")
	private Boolean removeSTWWord;

	// @Optional
	// @RunTime
	@ConfigurationParameter(defaultValue = "true", description = "Ignore all frame words with the marker 'rep'")
	private Boolean ignoreRepWords;

	// @Optional
	// @RunTime
	@ConfigurationParameter(defaultValue = "true", description = "Use RFTagger in addition to TreeTagger")
	private Boolean useRFTagger;

	// @Optional
	// @RunTime
	@ConfigurationParameter(defaultValue = "true", description = "Use a list of conjunctive forms for identification.")
	private Boolean useConjVerbList;

	Set<STWWord> toRemove = new HashSet<>();
	
	private ArrayList<String> conj;
	private ArrayList<String> conjVerbs;
	private ArrayList<String> quotMarks;
	// private ArrayList<String> forbiddenConj;

	// penalty will be incremented, if the case is unsure
	private int penalty = 0;

	// store the type of the recognized indirect representation
	private String irType = new String();

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);

		if (conjURL == null) {
			throw new ResourceInitializationException(
					"Conjunctions-URL must be specified", null);
		}

		if (conjVerbsURL == null) {
			throw new ResourceInitializationException(
					"Conjunctive verbs URL must be specified", null);
		}

		if (quotMarksURL == null) {
			throw new ResourceInitializationException(
					"Quotation marks URL must be specified", null);
		}

		// Read conjunctions list
		try {
			// Open a reader over the conj file so we can load
			// the list of conjunctions

			URL conjURLPath = conjURL.toURI().toURL();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conjURLPath.openStream(), "UTF8"));

			// read in the first line of the file
			String line = in.readLine();

			this.conj = new ArrayList<String>();

			while (line != null) {
				// while there is still data in the file...

				conj.add(line.trim());

				// get the next line from the data file
				line = in.readLine();
			}

			// close the data file now we have finished with it
			in.close();
			System.out.println("conjunctions read: " + conj);
		} catch (Exception e) {
			// if an error occurred then throw an exception so that the user
			// knows
			// throw new
			// ResourceInitializationException("Unable to correctly read in the conjunctions file: "
			// + e.getMessage(), null);
			throw new ResourceInitializationException(e);

		}

		// read verbs in subjunctive mode
		try {
			// Open a reader over the conjVerbs file so we can load
			// the list of verbs in subjunctive mode
			URL conjVerbsURLPath = conjVerbsURL.toURI().toURL();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					conjVerbsURLPath.openStream(), "UTF8"));

			// read in the first line of the file
			String line = in.readLine();

			this.conjVerbs = new ArrayList<String>();

			while (line != null) {
				// while there is still data in the file...

				this.conjVerbs.add(line.trim());

				// get the next line from the data file
				line = in.readLine();
			}

			// close the data file now we have finished with it
			in.close();
			System.out.println("conjunctive verbs read: " + this.conjVerbs);
		} catch (Exception e) {
			// if an error occurred then throw an exception so that the user
			// knows
			// throw new
			// ResourceInitializationException("Unable to correctly read in the conjunctive verbs file: "
			// + e.getMessage(), null);
			throw new ResourceInitializationException(e);
		}

		// read quotation marks

		try {
			// Open a reader over the quotation marks file so we can load
			// the list of quotation marks

			URL quotMarksURLPath = quotMarksURL.toURI().toURL();
			;
			BufferedReader in = new BufferedReader(new InputStreamReader(
					quotMarksURLPath.openStream(), "UTF8"));

			// read in the first line of the file
			String line = in.readLine();

			this.quotMarks = new ArrayList<String>();

			while (line != null) {
				// while there is still data in the file...

				this.quotMarks.add(line.trim());

				// get the next line from the data file
				line = in.readLine();
			}

			// close the data file now we have finished with it
			in.close();
			System.out.println("quotation marks read: " + this.quotMarks);
		} catch (Exception e) {
			// if an error occurred then throw an exception so that the user
			// knows
			// throw new
			// ResourceInitializationException("Unable to correctly read in the quotation marks file: "
			// + e.getMessage(), null);
			throw new ResourceInitializationException(e);
		}


	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {

		// get the sentences containing STWWords
		Map<Sentence, Collection<STWWord>> sentsWithSTWWords = JCasUtil
				.indexCovered(aJCas, Sentence.class, STWWord.class);
		// get the Tokens for each sentence
		// TODO ineffizient! Besser nur die Tokens für die Sätze mit STWWords
		Map<Sentence, Collection<RWToken>> sentsWithToks = JCasUtil.indexCovered(
				aJCas, Sentence.class, RWToken.class);

		// look at each sentence that contains at least one STWWord
		for (Annotation sent : sentsWithSTWWords.keySet()) {

			// get all STWWords in the sentence
			Collection<STWWord> stwWords = sentsWithSTWWords.get(sent);

			// if ignoreRepWords == true,
			// remove all STWWords with "rep" marker from the list
			if (this.ignoreRepWords) {
				
				for (STWWord stwWord : stwWords) {
					if (stwWord.getMarker().equals("rep")) {
						stwWords.remove(stwWord);
					}
				}
			}

			// for each stwWord in the sentence:
			for (STWWord currSTWWord : stwWords) {
				
				// get the Tokens of this sentence
				Collection<RWToken> toks = sentsWithToks.get(sent);

				// get the STWWord Token
				// and a list of all Tokens follwing it
				List<RWToken> tokList = new ArrayList<>();
				RWToken startTok = null;
				for (RWToken tok : toks) {
					if (currSTWWord.getBegin() == tok.getBegin()) {
						startTok = tok;
					}
					else if (currSTWWord.getBegin() < tok.getBegin()) {
						tokList.add(tok);
					}
				}

				// these variables are for remembering certain positions later
				RWToken commaTok = null; // Position of the comma (must be before conjunction)
				RWToken zuTok = null; // Position of "zu" (must be before infinitive verb)

				// start in stage 1 for each STWWord
				int stageStatus = 1;
				// starting point is the Token after the current STWWord
				for (RWToken tok : tokList) {

					if (this.debug) {
						System.out.println("Index: " + tok.getBegin() + " Wort: "
								+ tok.getCoveredText());
					}

					// *********** STAGE 1 ******************** //

					if (stageStatus == 1) {

						// If rfTagger is available:
						// first word after stw word is a coordination
						// conjunction --> break
						if (this.useRFTagger &&
						 (JCasUtil.selectFollowing(RWToken.class, startTok, 1).get(0).equals(tok)) &&
						 (tok.getRfpos().matches(".*Coord.*")))
						 {
							 if (this.debug) {
								 System.out.println("Abbruch stage 1: Koordination nach frame-Wort");
								 }
							 break;
						 }
						 
						// Colon found (can replace comma) --> Stage 2
						// (must be checked first, because : has the same
						// category as sentence
						// end)
						if (tok.getLemma().equals(":")) {
							stageStatus = 2;
							commaTok = tok;
							if (this.debug) {
								System.out
										.println("Doppelpunkt gefunden: stage 2");
							}

						}
						// Sentence end found --> break
						else if (tok.getPos().equals("$.")) {
							//stageStatus = 0;
							if (this.debug) {
								System.out.println("Abbruch stage 1: Satzende");
							}
							break;
						}

						// Quotation mark found --> break
						else if (this.quotMarks.contains((String) tok.getCoveredText())) {
							//stageStatus = 0;
							if (this.debug) {
								System.out
										.println("Abbruch stage 1: AnfÃ¼hrungszeichen");
							}
							break;
						}

						// comma found --> stage 2
						else if (tok.getPos().equals("$,")) {
							stageStatus = 2;
							commaTok = tok;
							if (this.debug) {
								System.out.println("Komma gefunden: stage 2");
							}
						}

						// "zu" found --> stage 3 (and wait for infinitive verb)
						else if (tok.getLemma().equals("zu")) {
							if (this.debug) {
								System.out.println("\"zu\" gefunden");
							}
							stageStatus = 3;
							zuTok = tok;
						}

						// infinitive with merged "zu" found --> success, add
						// annotation
						else if (tok.getPos().equals("VVIZU")) {
							if (this.debug) {
								System.out.println("eingebettes zu gefunden");
							}
							//stageStatus = 0;
							this.irType = "zu";

							addAnnotation(aJCas, startTok, tok, currSTWWord);

							if (this.debug) {
								System.out.println("Erkennung abgeschlossen");
							}
							break;

						}

					}

					// *********** STAGE 2 ******************** //
					// Stage 2: Comma or Colon was found

					else if (stageStatus == 2) {
						// sentence end found --> break
						if (tok.getPos().equals("$.")) {
							//stageStatus = 0;
							if (this.debug) {
								System.out.println("Satzende: Abbruch stage 2");
							}
							break;
						}

						// Quotation mark found: break
						else if (this.quotMarks.contains((String) tok.getCoveredText())) {
							//stageStatus = 0;
							if (this.debug) {
								System.out
										.println("Abbruch stage 2: Anfuehrungszeichen");
							}
							break;
						}

						// additional comma found --> break
						// (problematic as subordinate clauses may sometimes be
						// possible here,
						// but often
						// prevents wrong annotations)
						else if (tok.getPos().equals("$,")) {
							if (this.debug) {
								 System.out.println("weiteres Komma: Abbruch stage 2");
								//System.out.println("weiteres Komma: penalty");
							}
							//stageStatus = 0;
							break;

						}
						// "zu" found --> stage 3 (wait for verb in infinitive)
						else if (tok.getLemma().equals("zu")) {
							if (this.debug) {
								System.out.println("zu gefunden");
							}
							stageStatus = 3;
							zuTok = tok;
						}
						// infinitive with merged "zu" found --> success, add
						// annotation
						else if (tok.getPos().equals("VVIZU")) {
							if (this.debug) {
								System.out.println("eingebettes zu gefunden");
							}
							//stageStatus = 0;
							this.irType = "zu";

							this.addAnnotation(aJCas, startTok, tok,
									currSTWWord);
							if (this.debug) {
								System.out.println("Erkennung abgeschlossen");
							}
							break;

						}

						// TODO
						// "conjunctive verb" found (with RF-Tagger) -->
						// success, add annotation
						// else if (
						// this.useRFTagger &&
						// ((String)tok.getFeatures().get("featsRF")).matches(".*\\.Subj")
						// )
						// {
						// if (this.debug) {
						// System.out.println("conjunction verb found");
						// }
						// stageStatus = 0;
						// this.irType = "conjVerb";
						// this.addAnnotation(outputAS, inputAS, tokens, start,
						// pIndex,
						// currSTWWord);
						// if (this.debug) {
						// System.out.println("Erkennung abgeschlossen");
						// }
						// }
						//
						// "conjunctive verb" found (with conjunctive verb list)
						// --> success, add
						// annotation
						else if (this.useConjVerbList
								&& this.conjVerbs.contains((String) tok.getCoveredText())) {
							if (this.debug) {
								System.out.println("conjunction verb found");
							}
							//stageStatus = 0;
							this.irType = "conjVerb";
							this.addAnnotation(aJCas, startTok, tok,
									currSTWWord);
							if (this.debug) {
								System.out.println("Erkennung abgeschlossen");
							}
							break;
						}

						// position directly behind comma / colon
						else if (commaTok != null &&
								tokList.indexOf(tok) == tokList.indexOf(commaTok) + 1) {
							if (this.debug) {
								System.out.println("Check Pos hinter Komma: "
										+ tok.getCoveredText());
							}
							// conjunction found (with conj list) --> stage 4
							if (this.conj.contains((String) tok
									.getCoveredText())) {
								if (this.debug) {
									System.out.println("Konjunktion gefunden: "
											+ tok
													.getCoveredText());
								}
								stageStatus = 4;
								irType = "konj";
							}
							// Konjunktion gefunden, die nicht in der Liste ist
							// -> Abbruch
							else if (tok.getPos()
									.equals("KOUS")
									|| tok.getPos()
											.equals("KOUI") //
									|| tok.getPos()
											.equals("KOKOM") //
									// Vergleichspartikel
									|| tok.getPos()
											.equals("KON") //
							// Nebenordnende Konj
							) {
								if (this.debug) {
									System.out
											.println("Falsche Konjunktion: Abbruch stage 2");
								}
								//stageStatus = 0;
								break;
							}
						}

					}

					// *********** STAGE 3 ******************** //

					// Stage 3: "zu" found
					else if (stageStatus == 3) {
						if (this.debug) {
							System.out.println("Stage 3, Token:"
									+ tok.getPos());
						}
						// too far (verb may be just one position behind "zu")
						// --> break
						if (zuTok != null &&
							tokList.indexOf(tok) > tokList.indexOf(zuTok) + 1) {
							if (this.debug) {
								System.out.println("zu weit: Abbruch stage 3");
							}
							//stageStatus = 0;
							zuTok = null;
							break;
						}
						// infinitive found --> success, add annotation
						else if (tok.getPos().equals("VVINF")
								|| tok.getPos().equals("VAINF")
								|| tok.getPos().equals("VMINF")) {
							if (this.debug) {
								System.out.println("VINF gefunden: "
										+ tok.getLemma());
							}
							//stageStatus = 0;
							irType = "zu";
							this.addAnnotation(aJCas, startTok, tok,
									currSTWWord);
							if (this.debug) {
								System.out.println("Erkennung abgeschlossen");
							}
							break;

						}
						// sentence end found : break
						else if (tok.getPos().equals("$.")) {
							if (this.debug) {
								System.out.println("Abbruch stage 3: Satzende");
							}
							//stageStatus = 0;
							break;
						}
					}

					// *********** STAGE 4 ******************** //

					// Stage 4: conjunction found
					else if (stageStatus == 4) {
						if (this.debug) {
							System.out.println("in Stage 4");
						}
						// finite verb found --> success, add annotation
						if (tok.getPos().equals("VVFIN")
								|| tok.getPos().equals("VAFIN")
								|| tok.getPos().equals("VMFIN")) {
							//stageStatus = 0;
							this.addAnnotation(aJCas, startTok, tok,
									currSTWWord);
							if (this.debug) {
								System.out.println("Erkennung abgeschlossen");
							}
							break;
						}
						// sentence end found --> success (but with penalty),
						// add annotation
						// (annotation may end here, because it is highly likely
						// that this is an
						// indirect
						// representation and there was just a mistake - e.g.
						// the finite verb is
						// not tagged
						// correctly -, but annotation is penalized)
						else if (tok.getPos().equals("$.")) {
							// penalty is incremented
							this.penalty += 1;
							//stageStatus = 0;
							this.addAnnotation(aJCas, startTok, tok,
									currSTWWord);
							if (this.debug) {
								System.out
										.println("Erkennung abgeschlossen (ohne finites Verb)");
							}
							break;
						}

					}
				}
			}
		}
		
		// if removeRWWords = true
		// the STWWords need to be removed after the iteration, because
				// otherwise the loop breaks down
				if (this.removeSTWWord) {
					for (STWWord stwWord : this.toRemove) {
						if (this.debug) {
							System.out.println("Remove FrameWord: " + stwWord.getCoveredText());
						}
						stwWord.removeFromIndexes(aJCas);
					}
				}
	}

	private void addAnnotation(JCas aJCas, RWToken start, RWToken end, STWWord rwWord) {
		// TODO: copy features from the stw word - do we need this?
		// params.putAll(rwWord.getFeatures());

		System.out.println("start: " + start + " end: " + end);
		
		// generate a new annotation of type Indirect
		Indirect ind = new Indirect(aJCas, start.getBegin(), end.getEnd());

		// add type of dependent sentence
		ind.setIndtype(this.irType);
		ind.setPenalty(this.penalty);
		ind.addToIndexes(aJCas);

		if (this.debug) {
			System.out.println("Add Indirect annotation: " + ind);
		}

		// if removeFrameWord is true, remove the STWWord annotation
		if (this.removeSTWWord) {
			if (this.debug) {
				System.out.println("Remove FrameWord");
			}
			this.toRemove.add(rwWord);
		}

	}

}
