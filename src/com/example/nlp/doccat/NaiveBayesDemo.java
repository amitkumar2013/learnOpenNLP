package com.example.nlp.doccat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.ml.AbstractTrainer;
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class NaiveBayesDemo {

	private static NaiveBayesDemo ref = new NaiveBayesDemo();

	public static void main(String[] args) throws IOException {
		ObjectStream<DocumentSample> sampleStream = ref.readData();
		DoccatModel model = ref.createModel(sampleStream);
		ref.persistModel(model);
		// Test
		DocumentCategorizer doccat = new DocumentCategorizerME(model);
		String[] docWords = ("Afterwards Stuart and Charlie notice Kate in the photos Stuart"
				+ " took at Leopolds ball and realise that her destiny must be to go back and"
				+ " be with Leopold That night while Kate is accepting her promotion at a company"
				+ " banquet he and Charlie race to meet her and show her the pictures Kate initially"
				+ " rejects their overtures and goes on to give her acceptance speech but it is"
				+ " there that she sees Stuarts picture and realises that she truly wants to be with Leopold")
						.replaceAll("[^A-Za-z]", " ").split(" ");
		double[] aProbs = doccat.categorize(docWords);
		for (int i = 0; i < doccat.getNumberOfCategories(); i++) {
			System.out.println(doccat.getCategory(i) + " : " + aProbs[i]);
		}
		System.out.println(
				"\n" + doccat.getBestCategory(aProbs) + " : is the predicted category for the given sentence.");

	}

	public DoccatModel createModel(ObjectStream<DocumentSample> sampleStream) throws IOException {
		TrainingParameters params = new TrainingParameters();
		params.put(TrainingParameters.ITERATIONS_PARAM, 10 + "");
		params.put(TrainingParameters.CUTOFF_PARAM, 0 + "");
		params.put(AbstractTrainer.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE);
		// train the model
		DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
		return model;
	}

	private void persistModel(DoccatModel model) throws IOException {
		BufferedOutputStream modelOut = new BufferedOutputStream(
				new FileOutputStream("model\\en-movie-classifier-naive-bayes.bin"));
		model.serialize(modelOut);
	}

	private ObjectStream<DocumentSample> readData() throws IOException {
		InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("input\\en-movie-category.train"));
		ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
		ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
		return sampleStream;
	}

}
