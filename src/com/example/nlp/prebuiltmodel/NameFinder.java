package com.example.nlp.prebuiltmodel;

import java.io.FileInputStream;
import java.io.IOException;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 * en-nerdate.bn, en-ner-location.bin, en-ner-organization.bin,
 * en-ner-person.bin, and en-ner-time.bin
 * 
 */
public class NameFinder {

	private NameFinderME nameFinder, locationFinder;
	private static final String TRAINED_PERSON_MODEL = "D:\\install\\apache-opennlp-1.8.3\\pre-trained-models\\en-ner-person.bin";
	private static final String TRAINED_LOCATION_MODEL = "D:\\install\\apache-opennlp-1.8.3\\pre-trained-models\\en-ner-location.bin";

	public NameFinder() throws IOException {
		TokenNameFinderModel nameModel = new TokenNameFinderModel(new FileInputStream(TRAINED_PERSON_MODEL));
		TokenNameFinderModel locationModel = new TokenNameFinderModel(new FileInputStream(TRAINED_LOCATION_MODEL));
		nameFinder = new NameFinderME(nameModel);
		locationFinder = new NameFinderME(locationModel);
	}

	// Finding the names in the sentence
	public void findNames(String[] tokens) {
		Span nameSpans[] = nameFinder.find(tokens);
		for (Span s : nameSpans)
			System.out.println(s.toString() + "  " + tokens[s.getStart()]);
	}

	// Finding the location in the sentence
	public void findLocations(String[] tokens) {
		Span nameSpans[] = locationFinder.find(tokens);
		for (Span s : nameSpans)
			System.out.println(s.toString() + "  " + tokens[s.getStart()]);
	}
}
