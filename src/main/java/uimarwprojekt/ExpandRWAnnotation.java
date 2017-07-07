package uimarwprojekt;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import uimarwprojekt.types.Indirect;
import uimarwprojekt.types.Reported;
import uimarwprojekt.types.Quotation;
import uimarwprojekt.types.Frame;
import uimarwprojekt.types.FreeIndirect;
import uimarwprojekt.types.Dir;
import uimarwprojekt.types.Ind;
import uimarwprojekt.types.Rep;
import uimarwprojekt.types.Fi;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;

/**
 * ExpandRWAnnotation 
 * UIMA component to expand ST&WR annotation to sentence length
 * 
 * Frame, Quotation --> Dir 
 * FreeIndirect --> Fi
 * Indirect --> Ind 
 * Reported --> Rep
 * 
 * no matter how many instances of the same type are contained in a sentence
 * only one sentence annoation is added (meaning: sentence contains stw type)
 * Created: Dec 2014
 * 
 * @author Annelen Brunner, brunner@ids-mannheim.de
 */
public class ExpandRWAnnotation extends JCasAnnotator_ImplBase {

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {

		// INDIRECT
		Map<Sentence, Collection<Indirect>> sentsWithIndirect = JCasUtil
				.indexCovered(aJCas, Sentence.class, Indirect.class);
		for (Sentence sent : sentsWithIndirect.keySet()) {
			// generate a new annotation of type Indirect
			Ind ind = new Ind(aJCas, sent.getBegin(), sent.getEnd());
			ind.addToIndexes(aJCas);
		}

		// FREE INDIRECT
		Map<Sentence, Collection<FreeIndirect>> sentsWithFreeIndirect = JCasUtil
				.indexCovered(aJCas, Sentence.class, FreeIndirect.class);
		for (Sentence sent : sentsWithFreeIndirect.keySet()) {
			// generate a new annotation of type Indirect
			Fi fi = new Fi(aJCas, sent.getBegin(), sent.getEnd());
			fi.addToIndexes(aJCas);
		}

		// REPORTED
		Map<Sentence, Collection<Reported>> sentsWithReported = JCasUtil
				.indexCovered(aJCas, Sentence.class, Reported.class);
		for (Sentence sent : sentsWithReported.keySet()) {
			// generate a new annotation of type Indirect
			Rep rep = new Rep(aJCas, sent.getBegin(), sent.getEnd());
			rep.addToIndexes(aJCas);
		}

		// DIRECT (combine quotation or frame)
		Set<Sentence> sentsWithQuotation = JCasUtil.indexCovered(aJCas,
				Sentence.class, Quotation.class).keySet();
		Set<Sentence> sentsWithFrame = JCasUtil.indexCovered(aJCas,
				Sentence.class, Frame.class).keySet();
		Set<Sentence> allDir = new HashSet<Sentence>();
		allDir.addAll(sentsWithQuotation);
		allDir.addAll(sentsWithFrame);
		
		for(Sentence sent : allDir) {
			Dir dir = new Dir(aJCas, sent.getBegin(), sent.getEnd());
			dir.addToIndexes(aJCas);
		}
		
//		for (Sentence sent : sentsWithQuotation) {
//			// generate a new annotation of type Indirect
//			Dir dir = new Dir(aJCas, sent.getBegin(), sent.getEnd());
//			dir.addToIndexes(aJCas);
//			sentsWithFrame.remove(sent);
//		}
//		for (Sentence sent : sentsWithFrame) {
//			// generate a new annotation of type Indirect
//			Dir dir = new Dir(aJCas, sent.getBegin(), sent.getEnd());
//			dir.addToIndexes(aJCas);
//		}

	}

}