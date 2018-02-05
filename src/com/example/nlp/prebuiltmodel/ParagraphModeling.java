package com.example.nlp.prebuiltmodel;

import java.io.FileInputStream;
import java.io.IOException;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;

public class ParagraphModeling {

	private static final String TRAINED_SENTENCE_MODEL = "D:\\install\\apache-opennlp-1.8.3\\pre-trained-models\\en-sent.bin";
	private SentenceDetectorME detector;

	public ParagraphModeling() throws IOException {
		// Loading sentence detector model (Its trained to detect sentences in a
		// given raw text)
		// Prerequisite 1: make sure the input text is decoded correctly
		// Prerequisite 1: language is same.
		SentenceModel model = new SentenceModel(new FileInputStream(TRAINED_SENTENCE_MODEL));
		// split the raw text into sentences using Maximum Entropy model
		detector = new SentenceDetectorME(model);
	}

	public String[] breakSentence(String paragraph) {
		String sentences[] = detector.sentDetect(paragraph);
		Span[] spans = detector.sentPosDetect(paragraph);
		for (Span span : spans)
			System.out.println(paragraph.substring(span.getStart(), span.getEnd()) + "" + span);

		// Getting the probabilities of the last decoded sequence
		double[] probs = detector.getSentenceProbabilities();
		for (int i = 0; i < probs.length; i++)
			System.out.println(probs[i]);
		return sentences;

		// OR
		// String[] splitString = (sentence.split("[.?!]"));
		// for (String string : splitString) System.out.println(string);
	}
}
