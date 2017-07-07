package uimarwprojekt;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.*;
import static org.apache.uima.fit.factory.CollectionReaderFactory.*;
import static org.apache.uima.fit.pipeline.SimplePipeline.*;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasDumpWriter;

import uimarwprojekt.markframe.MarkFrame;
import uimarwprojekt.markfreeindirect.MarkFreeIndirect;
import uimarwprojekt.markindirect.MarkIndirect;
import uimarwprojekt.markquotation.MarkQuotation;
import uimarwprojekt.markreported.MarkReported;
import uimarwprojekt.markstwwords.MarkSTWWords;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosTagger;


public class Runner {

	public static void main(String[] args) throws UIMAException, IOException {
		CollectionReaderDescription cr = createReaderDescription(
				TextReader.class, TextReader.PARAM_SOURCE_LOCATION,
				"src/test/resources/docs/*.txt", 
				TextReader.PARAM_LANGUAGE, "de");
	

		AnalysisEngineDescription seg = createEngineDescription(BreakIteratorSegmenter.class);

		AnalysisEngineDescription tagger = createEngineDescription(
				TreeTaggerPosTagger.class,
				TreeTaggerPosTagger.PARAM_EXECUTABLE_PATH,
				"resources/tree-tagger-windows-3.2/TreeTagger/bin/tree-tagger.exe",
				TreeTaggerPosTagger.PARAM_MODEL_LOCATION,
				"resources/tree-tagger-windows-3.2/TreeTagger/lib/german-utf8.par",	
				TreeTaggerPosTagger.PARAM_MODEL_ENCODING, "UTF-8");
	
		AnalysisEngineDescription rfTagger = createEngineDescription(
				RFTaggerWrapper.class,
				RFTaggerWrapper.PARAM_PROPERTIESFILENAME, 
				"resources/RFTagger.properties");
		
		AnalysisEngineDescription markSTWWords = createEngineDescription(
				MarkSTWWords.class, 
				MarkSTWWords.PARAM_DEBUG, false,
				MarkSTWWords.PARAM_STWORDS_FILE, "resources/mstwwords.csv",
				MarkSTWWords.PARAM_PENALTYLEVEL, 2);
		
		AnalysisEngineDescription markFrame = createEngineDescription(
				MarkFrame.class,
				MarkFrame.PARAM_DEBUG, false);

		AnalysisEngineDescription markIndirect = createEngineDescription(
				MarkIndirect.class, 
				MarkIndirect.PARAM_DEBUG, false,
				MarkIndirect.PARAM_USE_RFTAGGER, true,
				MarkIndirect.PARAM_REMOVE_STWWORD, true,
				MarkIndirect.PARAM_IGNORE_REPWORDS, true,
				MarkIndirect.PARAM_PENALTYLEVEL, 2
				);

		AnalysisEngineDescription markReported = createEngineDescription(MarkReported.class);
		
		AnalysisEngineDescription markFreeIndirect = createEngineDescription(MarkFreeIndirect.class,
				MarkFreeIndirect.PARAM_FIWORDSURL, "resources/fiWords.txt",
				MarkFreeIndirect.PARAM_DEBUG, false);

		AnalysisEngineDescription markQuotation = createEngineDescription(MarkQuotation.class);

		AnalysisEngineDescription expandRWAnno = createEngineDescription(ExpandRWAnnotation.class);
		
		
		AnalysisEngineDescription sampler = createEngineDescription(Sampler.class,
				Sampler.PARAM_SAMPLELEN, 500,
				Sampler.PARAM_SAMPLENR, 1,
				Sampler.PARAM_OUTPUTDIR, "samples");

		AnalysisEngineDescription xmiWriter = createEngineDescription(
				XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION, "target");

		runPipeline(cr, seg, sampler, xmiWriter);
		//runPipeline(cr, seg, tagger, rfTagger, markSTWWords, markIndirect, xmiWriter);
		

	}
}
