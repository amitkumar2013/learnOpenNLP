package com.example.nlp.prebuiltmodel;

import java.io.FileInputStream;
import java.io.IOException;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.Span;

/**
 * en-nerdate.bn, en-ner-organization.bin,and en-ner-time.bin
 */
public class PartsOfSpeechFinder {

	private POSTaggerME posFinder;
	private ChunkerME chunkerME;
	private DictionaryLemmatizer lemmatizer;
	private static final String TRAINED_POS_MODEL = "D:\\install\\apache-opennlp-1.8.3\\pre-trained-models\\en-pos-maxent.bin";
	private static final String TRAINED_CHUNKER_MODEL = "D:\\install\\apache-opennlp-1.8.3\\pre-trained-models\\en-chunker.bin";
	private static final String DICTIONARY = "D:\\javaProj\\learnOpenNLP\\model\\en-lemmatizer.dict";

	public PartsOfSpeechFinder() throws IOException {
		POSModel posModel = new POSModel(new FileInputStream(TRAINED_POS_MODEL));
		// OR POSModel model = new POSModelLoader().load(TRAINED_POS_MODEL);
		posFinder = new POSTaggerME(posModel);

		ChunkerModel chunkerModel = new ChunkerModel(new FileInputStream(TRAINED_CHUNKER_MODEL));
		chunkerME = new ChunkerME(chunkerModel);

		// loading the lemmatizer with dictionary/input stream
		lemmatizer = new DictionaryLemmatizer(new FileInputStream(DICTIONARY));

	}

	public void findPOS(String[] tokens) {
		String tags[] = posFinder.tag(tokens);
		for (String tag : tags)
			System.out.println(tag);
		POSSample sample = new POSSample(tokens, tags);
		System.out.println(sample.toString());

		// Probabilities for each tag of the last tagged sentence.
		double[] probs = posFinder.probs();
		for (int i = 0; i < probs.length; i++)
			System.out.println(probs[i]);

		// Monitoring the performance of POS tagger
		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
		perfMon.start();
		perfMon.incrementCounter();
		perfMon.stopAndPrintFinalResult();
	}

	// Chunking a sentences refers to breaking/dividing a sentence into parts of
	// words such as word groups and verb groups.
	public void findChunks(String[] tokens) {
		String tags[] = posFinder.tag(tokens);
		//
		String result[] = chunkerME.chunk(tokens, tags);
		for (String s : result)
			System.out.println(s);

		//
		double[] probs = chunkerME.probs();
		for (int i = 0; i < probs.length; i++)
			System.out.println(probs[i]);

		//
		Span[] span = chunkerME.chunkAsSpans(tokens, tags);
		for (Span s : span)
			System.out.println(s.toString());

	}

	public void findLemmas(String[] tokens) {
		String tags[] = posFinder.tag(tokens);
		//
		String[] lemmas = lemmatizer.lemmatize(tokens, tags);
		for (int i = 0; i < tokens.length; i++) {
			System.out.println(tokens[i] + " -" + tags[i] + " : " + lemmas[i]);
		}

	}

}
