package uimarwprojekt.markreported;

import java.util.Iterator;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import uimarwprojekt.types.STWWord;
import uimarwprojekt.types.Reported;

/**
 * MarkReported 
 * UIMA component to annotate reported speech, thought and writing representation
 * Created: Dec 2014
 * 
 * @author Annelen Brunner, brunner@ids-mannheim.de
 */
public class MarkReported extends JCasAnnotator_ImplBase {

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Iterator<STWWord> stwwordIter = JCasUtil.iterator(aJCas, STWWord.class);
		
		while (stwwordIter.hasNext()) {
			STWWord stwWord = stwwordIter.next();
			
			Reported rep = new Reported(aJCas, stwWord.getBegin(), stwWord.getEnd());
			rep.setLemma(stwWord.getLemma());
			rep.setPenalty(stwWord.getPenalty());
			rep.addToIndexes(aJCas);
		}
		
	}

}
