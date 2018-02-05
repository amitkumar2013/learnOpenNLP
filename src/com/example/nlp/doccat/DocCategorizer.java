package com.example.nlp.doccat;

import java.io.File;
import java.io.IOException;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 * Usage of MaxEnt uses multinomial logistic regression to determine the right
 * category for a given text
 */
public class DocCategorizer {

	private static DocumentCategorizerME myCategorizer;

	public static void main(String[] args) {
		DocCategorizer twitterCategorizer = new DocCategorizer();
		DoccatModel model = twitterCategorizer.trainModel();
		myCategorizer = new DocumentCategorizerME(model);
		twitterCategorizer.classifyNewTweet("Lets not kill some bad animal".replaceAll("[^A-Za-z]", " ").split(" "));
	}

	public DoccatModel trainModel() {
		DoccatModel model = null;
		try {
			InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("input/tweets.txt"));
			ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
			// Specifies the minimum number of times a feature must be seen
			TrainingParameters params = TrainingParameters.defaultParams();
			params.put(TrainingParameters.CUTOFF_PARAM, 2);
			params.put(TrainingParameters.ITERATIONS_PARAM, 30);
			model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return model;
	}

	public void classifyNewTweet(String[] tweet) {
		double[] outcomes = myCategorizer.categorize(tweet);
		// Look at them
		for (int i = 0; i < myCategorizer.getNumberOfCategories(); i++) {
			System.out.println(myCategorizer.getCategory(i) + " : " + outcomes[i]);
		}

		String category = myCategorizer.getBestCategory(outcomes);
		System.out.println("The tweet is " + (category.equalsIgnoreCase("1") ? "positive :) " : "negative :( "));
	}
}
