package com.example.nlp.prebuiltmodel;

import java.io.FileInputStream;
import java.io.IOException;

import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.Span;

public class SentenceModeling {

	private static final String TRAINED_TOKENIZER_MODEL = "D:\\install\\apache-opennlp-1.8.3\\pre-trained-models\\en-token.bin";
	// AbstractTokenizer has 3 variants
	private TokenizerME tokenizer1 = null;
	private SimpleTokenizer tokenizer2 = SimpleTokenizer.INSTANCE;
	private WhitespaceTokenizer tokenizer3 = WhitespaceTokenizer.INSTANCE;

	public SentenceModeling() throws IOException {
		TokenizerModel tokenModel = new TokenizerModel(new FileInputStream(TRAINED_TOKENIZER_MODEL));
		tokenizer1 = new TokenizerME(tokenModel);
		tokenizer2 = SimpleTokenizer.INSTANCE;
		tokenizer3 = WhitespaceTokenizer.INSTANCE;
	}

	public String[] tokenizerModel(String sentence, boolean useWhiteSpaceTokenizer) {
		String[] tokens = new String[0];
		boolean variant = true;
		if (useWhiteSpaceTokenizer) {
			tokens = tokenizer3.tokenize(sentence);
		} else if (variant) {
			tokens = tokenizeModel(sentence);
		} else {
			tokens = tokenizer2.tokenize(sentence);
		}

		for (String token : tokens)
			System.out.println(token);
		return tokens;
	}

	private String[] tokenizeModel(String sentence) {
		String[] tokens;
		tokens = tokenizer1.tokenize(sentence);
		Span tokenSpans[] = tokenizer1.tokenizePos(sentence);

		for (Span span : tokenSpans) {
			String token = sentence.substring(span.getStart(), span.getEnd());
			System.out.println(span + " " + token);
		}

		double[] probs = tokenizer1.getTokenProbabilities();
		for (int i = 0; i < probs.length; i++)
			System.out.println(probs[i]);
		return tokens;
	}
}
