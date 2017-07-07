package uimarwprojekt;

import java.io.*;
import java.util.*;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import uimarwprojekt.types.Sample;

/**
 * Sampler UIMA component to sample random snippets from a text
 * 
 * @author Annelen Brunner, brunner@ids-mannheim.de
 */
public class Sampler extends JCasAnnotator_ImplBase {
	private static int TRYLIMIT = 20;

	public static final String PARAM_SAMPLENR = "Number of samples to draw from this text";
	@ConfigurationParameter(name = PARAM_SAMPLENR, defaultValue = "1", description = "Number of samples to draw from this text")
	private int samplenr;

	public static final String PARAM_SAMPLELEN = "Length of samples to draw from this text";
	@ConfigurationParameter(name = PARAM_SAMPLELEN, defaultValue = "1000", description = "Length of samples to draw from this text")
	private int samplelen;

	public static final String PARAM_OUTPUTDIR = "Output directcory for samples";
	@ConfigurationParameter(name = PARAM_OUTPUTDIR, defaultValue = "samples", description = "Output directcory for samples")
	private String outputdir;
	
	private Random randomGenerator = new Random();

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {

		Map<Sentence, Collection<Token>> sentsWithToks = JCasUtil.indexCovered(
				aJCas, Sentence.class, Token.class);

		// TEST
		// AnnotationIndex<Annotation> idx = aJCas.getAnnotationIndex();

		int samples = 0;
		int tries = 0;
		while (samples < this.samplenr) {
			// break the loop if there have been TRYLIMIT more tries than
			// samples
			System.out.println("Find sample: Try " + tries);
			if (tries > this.samplenr + TRYLIMIT) {
				Object[] params = new Object[0];
				//throw new AnalysisEngineProcessException("Too many tries: "
				//		+ tries, params);
				System.err.println("Too many tries: " + tries);
				break;
			}
			boolean success = this.getSample(aJCas, sentsWithToks, samples);
			if (success) {
				samples++;
			}
			tries++;
		}

		try {
			Iterator<Token> toks = JCasUtil.iterator(aJCas, Token.class);
			String docStart = new String();
			int counter = 0;
			while (counter < 5 && toks.hasNext()) {
				docStart = docStart + toks.next().getCoveredText();
				counter++;
			}
			docStart = docStart.replaceAll("[<\\?!/\\(\\)>\\.=*]+", "");
			docStart = docStart.replaceAll("\\\\", "");
			System.out.println("DocName: " + docStart);
			
			this.writeSamplesToFiles(aJCas, docStart);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void writeSamplesToFiles(JCas aJCas, String doc)
			throws Exception {
		Iterator<Sample> samples = JCasUtil.iterator(aJCas, Sample.class);
		while (samples.hasNext()) {
			Sample s = samples.next();

			String filename = this.outputdir + File.separator + doc + "_"
					+ s.getId() + ".txt";
			this.writeFile(filename, s.getCoveredText());
		}
	}

	private void writeFile(String filename, String content) throws Exception {
		System.out.println("filename: " + filename);
		Writer writer = null;
		try {
			File file = new File(filename);
			file.getParentFile().mkdirs();
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filename), "utf-8"));
			writer.write(content);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				// ignore ex
			}
		}

	}

	private boolean getSample(JCas aJCas,
			Map<Sentence, Collection<Token>> sentTokMap, int sampleID) {
		boolean success = false;

		// // check if there are enough tokens left to generate a sample of the
		// appropriate length
		// Token startTok = sentTokMap.get(startSent).iterator().next();

		Map<Sentence, Collection<Sample>> sentSampMap = JCasUtil.indexCovering(
				aJCas, Sentence.class, Sample.class);

		Token startTok = null;
		int tries = 0;
		while (tries < TRYLIMIT) {
			startTok = this.pickStartToken(aJCas, sentTokMap, sentSampMap);
			if (startTok != null) {
				break;
			}
			tries++;
		}

		if (startTok != null) {

			List<Token> tokSample = JCasUtil.selectFollowing(Token.class,
					startTok, this.samplelen);

			if (tokSample.size() == this.samplelen) {
				// determine endTok --> always expand the Anno to the next
				// Sentence end
				Token sampleTokEnd = tokSample.get(tokSample.size() - 1);
				List<Sentence> coveringSents = JCasUtil.selectCovering(
						Sentence.class, sampleTokEnd);
				Sentence endSent = coveringSents.get(0);
				// only add annotation if the endSent is not covered by a
				// SampleAnno either
				System.out.println("Endsent not Sample? "
						+ sentSampMap.get(endSent).isEmpty());
				if (sentSampMap.get(endSent).isEmpty()) {

					// annotation only successfully added if it does not overlap
					// with another sample
					success = this.addAnnotation(aJCas, startTok.getBegin(),
							coveringSents.get(0).getEnd(), sampleID);
				}
			}
		}
		System.out.println("Success: " + success);
		return success;
	}

	private Token pickStartToken(JCas aJCas,
			Map<Sentence, Collection<Token>> sentTokMap,
			Map<Sentence, Collection<Sample>> sentSampMap) {
		Token startTok = null;
		List<Sentence> sentList = new ArrayList<>(sentTokMap.keySet());
		int index = this.randomGenerator.nextInt(sentList.size());

		Sentence startSent = sentList.get(index);

		System.out.println("Startsent not Sample? "
				+ sentSampMap.get(startSent).isEmpty());
		// check whether the startSent is not covered by a Sample Annot
		if (sentSampMap.get(startSent).isEmpty()) {
			startTok = sentTokMap.get(startSent).iterator().next();
		}
		return startTok;
	}

	private boolean addAnnotation(JCas aJCas, int start, int end, int id) {
		boolean success = false;
		// generate a new annotation of type Indirect
		Sample s = new Sample(aJCas, start, end);
		s.setId(id);
		s.addToIndexes(aJCas);
		success = true;
		//
		// Map<Sample, Collection<Sample>> sampleMap = JCasUtil.indexCovered(
		// aJCas, Sample.class, Sample.class);
		// if (!sampleMap.get(s).isEmpty()) {
		// s.removeFromIndexes();
		// success = false;
		// }
		return success;
	}

}
