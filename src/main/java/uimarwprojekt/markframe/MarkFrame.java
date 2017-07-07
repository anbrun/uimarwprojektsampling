package uimarwprojekt.markframe;

import java.util.*;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Lemma;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import uimarwprojekt.types.STWWord;
import uimarwprojekt.types.Frame;

/**
 * MarkFrame 
 * UIMA component to annotate the frame word for direct speech, thought and writing representation
 * Created: Dec 2014
 * 
 * @author Annelen Brunner, brunner@ids-mannheim.de
 */
public class MarkFrame extends JCasAnnotator_ImplBase {

	@ConfigurationParameter(defaultValue = "true", description = "Remove the frame word when processing")
	private Boolean removeSTWWord;

	public static final String PARAM_DEBUG = "Debugging-Informationen ausgeben";
	@ConfigurationParameter(name=PARAM_DEBUG, defaultValue = "false", description = "Output debugging info")
	private Boolean debug;

	public static final String PARAM_PENALTYLEVEL = "Maximaler Penalty-Wert, den das Wiedergabewort haben darf";
	@ConfigurationParameter(name = PARAM_PENALTYLEVEL, defaultValue = "5", description = "Ignore all frame words with the marker 'rep'")
	private int penaltyLevel;

	Set<STWWord> toRemove = new HashSet<>();

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// toRemove needs to be empty whenever a process task starts
		// otherwise there will be problems when processing multiple documents
		toRemove = new HashSet<>();
		Iterator<STWWord> stwworditer = JCasUtil.iterator(aJCas, STWWord.class);

		while (stwworditer.hasNext()) {
			STWWord stwWord = stwworditer.next();
			if (this.debug) {
				System.out.println("sTWWord: " + stwWord.getCoveredText());
			}
			boolean marked = this.leadingFrame(aJCas, stwWord);
			// only try to mark a following frame if no leading frame was marked
			if (!marked) {
				marked = this.followingFrameQuote(aJCas, stwWord);
				if (!marked) {
					this.followingFrame(aJCas, stwWord);
				}
			}
		}

		// the STWWords need to be removed after the iteration, because
		// otherwise the loop breaks down
		if (this.removeSTWWord) {
			for (STWWord stwWord : this.toRemove) {
				if (this.debug) {
					System.out.println("Remove FrameWord: "
							+ stwWord.getCoveredText());
				}
				stwWord.removeFromIndexes(aJCas);
			}
		}


	}

	/**
	 * Marks a frame that precedes a quotation Only one annotation is done and
	 * the patterns are tested in the order below: 1) und entgegnete : (")
	 * Mittags komme ich 2) und sagte ihm/Peter : (") Du hast es versprochen
	 * returns true, if an annotation was made
	 * 
	 * @param aJCas
	 * @param stwWord
	 * @return
	 */
	private boolean leadingFrame(JCas aJCas, STWWord stwWord) {
		List<Token> toklist = JCasUtil.selectFollowing(aJCas, Token.class,
				stwWord, 2);

		// 1) und entgegnete : (") Mittags komme ich
		if (toklist.get(0).getCoveredText().equals(":")) {
			this.addAnnotation(aJCas, stwWord.getBegin(), stwWord
					.getEnd(), stwWord);
			return true;
		}
		// 2) und sagte ihm/Peter : (") Du hast es versprochen
		else if ((toklist.get(0).getPos().getPosValue().equals("NE") || toklist
				.get(0).getPos().getPosValue().equals("PPER"))
				&& toklist.get(1).getCoveredText().equals(":")) {
			this.addAnnotation(aJCas, stwWord.getBegin(), stwWord.getEnd(), stwWord);
			return true;
		} else
			return false;
	}

	/**
	 * marks a frame that follows the quotation Only one annotation is done and
	 * the patterns are tested in the order below:
	 *  3) " , sagte 
	 *  4) " sagte
	 * 
	 * returns true if an annotation was made
	 * 
	 * @param aJCas
	 * @param stwWord
	 * @return
	 */
	private boolean followingFrameQuote(JCas aJCas, STWWord stwWord) {
	
		List<Token> tokBefore = JCasUtil.selectPreceding(aJCas, Token.class,
				stwWord, 2);
		// 1) " , sagte
		if (tokBefore.get(0).getPos().getPosValue().equals("$(")
					&& tokBefore.get(1).getPos().getPosValue().equals("$,")) {
				this.addAnnotation(aJCas, stwWord.getBegin(),
						stwWord.getEnd(), stwWord);
				return true;
			}
			// 2) " sagte
		else if (tokBefore.get(1).getPos().getPosValue().equals("$(")) {
				this.addAnnotation(aJCas, stwWord.getBegin(),
						stwWord.getEnd(), stwWord);
				return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	 * marks a frame that follows the quotation Only one annotation is done and
	 * the patterns are tested in the order below: 1) , sagte er/Peter 2) , sagte der Mann
	 * returns true if an annotation was made
	 * 
	 * @param aJCas
	 * @param stwWord
	 * @return
	 */
	private boolean followingFrame(JCas aJCas, STWWord stwWord) {
		List<Token> tokAfter = JCasUtil.selectFollowing(aJCas, Token.class,
				stwWord, 2);
		List<Token> tokBefore = JCasUtil.selectPreceding(aJCas, Token.class,
				stwWord, 2);

		if (tokBefore.get(1).getCoveredText().equals(",")) {
			// 1) , sagte er/Peter
			if (tokAfter.get(0).getPos().getPosValue().equals("NE")
					|| tokAfter.get(0).getPos().getPosValue().equals("NN")
					|| tokAfter.get(0).getPos().getPosValue().equals("PPER")) {
				this.addAnnotation(aJCas, stwWord.getBegin(), stwWord
						.getEnd(), stwWord);
				return true;
			}
			// 2) , sagte der Mann
			else if (tokAfter.get(0).getPos().getPosValue().equals("ART")
					&& tokAfter.get(1).getPos().getPosValue().equals("NN")) {
				this.addAnnotation(aJCas, stwWord.getBegin(), stwWord
						.getEnd(), stwWord);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
		
	}

	private void addAnnotation(JCas aJCas, int start, int end, STWWord stwWord) {

		// generate a new annotation of type Indirect
		Frame frame = new Frame(aJCas, start, end);
		frame.addToIndexes(aJCas);

		// if removeFrameWord is true, schedule the STWWord to be removed
		// (cannot be removed immediately, because then the iteration
		// in 'process' breaks down :-/)
		if (this.removeSTWWord) {
			this.toRemove.add(stwWord);
		}

	}

}
