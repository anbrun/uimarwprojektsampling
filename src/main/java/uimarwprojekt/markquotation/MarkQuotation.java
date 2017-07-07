package uimarwprojekt.markquotation;

import java.util.*;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import uimarwprojekt.types.Quotation;

public class MarkQuotation extends JCasAnnotator_ImplBase {
	
	public static String openingQuot = "»";
	public static String closingQuot = "«";
	
	public static final String PARAMDEBUG = "Debugging-Informationen ausgeben"; 
	@ConfigurationParameter(name=PARAMDEBUG, defaultValue = "false", description = "Output debugging info")
	private Boolean debug;
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		Iterator<Token> tokiter = JCasUtil.iterator(aJCas, Token.class);
		
		
		Token quotStart = null;
		while (tokiter.hasNext()) {
			Token tok = tokiter.next();
			if (debug) {
				System.out.println("currTok: " + tok.getCoveredText());
			}
			
			if (tok.getCoveredText().equals(MarkQuotation.openingQuot)) {
				quotStart = tok;
				if (debug) {
					System.out.println("Opening Quote found: " + tok.getCoveredText() + " at " + tok.getBegin());
				}
			}
			else {
				if (tok.getCoveredText().equals(MarkQuotation.closingQuot)) {
					if (debug) {
						System.out.println("Closing Quote found: " + tok.getCoveredText() + " at " + tok.getBegin());
					}
					if (quotStart != null) {
						this.addAnnotation(aJCas, quotStart.getBegin(), tok.getEnd());
						quotStart = null;
					}
				}
			}
		
		
		}
		
	}
	

		
	
	private void addAnnotation(JCas aJCas, int start, int end) {
		// generate a new annotation of type Indirect
		Quotation quot = new Quotation(aJCas, start, end);
		quot.addToIndexes(aJCas);
	}

}
