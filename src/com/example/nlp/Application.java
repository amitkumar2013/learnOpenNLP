package com.example.nlp;

import java.io.IOException;

import com.example.nlp.prebuiltmodel.NameFinder;
import com.example.nlp.prebuiltmodel.ParagraphModeling;
import com.example.nlp.prebuiltmodel.PartsOfSpeechFinder;
import com.example.nlp.prebuiltmodel.SentenceModeling;

public class Application {

	public static void main(String args[]) throws IOException {
		String paragraph = "Hi. How are you? Welcome Mike. Yo! Mike and Smith are good friends located in Noida.";
		ParagraphModeling ref = new ParagraphModeling();

		System.out.println("\n....Working on paragraph => " + paragraph);
		String[] sentences = ref.breakSentence(paragraph);

		SentenceModeling sentenceModel = new SentenceModeling();
		NameFinder finderModel = new NameFinder();
		PartsOfSpeechFinder posFinder = new PartsOfSpeechFinder();
		for (String sentence : sentences) {
			System.out.println("\n....Working on sentence => " + sentence);
			String[] tokens = sentenceModel.tokenizerModel(sentence, false);
			System.out.println("\n....Working on names => " + sentence);
			finderModel.findNames(tokens);
			System.out.println("\n....Working on locations => " + sentence);
			finderModel.findLocations(tokens);
			System.out.println("\n....Working on pos => " + sentence);
			posFinder.findPOS(sentenceModel.tokenizerModel(sentence, true));
			System.out.println("\n....Working on pos-chunks => " + sentence);
			posFinder.findChunks(tokens);
			System.out.println("\n....Working on lemmas => " + sentence);
			posFinder.findLemmas(tokens);
		}
	}
}
