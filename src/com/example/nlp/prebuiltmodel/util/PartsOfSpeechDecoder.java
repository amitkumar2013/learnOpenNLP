package com.example.nlp.prebuiltmodel.util;

public class PartsOfSpeechDecoder {

	public static String decodePOSCode(String code) {
		String pos = null;
		switch (code) {
		case "NN":
			pos = "Noun";
			break;
		case "DT":
			pos = "Determiner";
			break;
		case "VB":
			pos = "Verb";
			break;
		case "VBD":
			pos = "Past Tense Verb";
			break;
		case "VBZ":
			pos = "Present Tense Verb";
			break;
		case "IN":
			pos = "Preposition";
			break;
		case "NNP":
			pos = "Proper Noun";
			break;
		case "TO":
			pos = "to";
			break;
		case "JJ":
			pos = "Adjective";
			break;
		default:
			pos = "???";
		}
		return pos;
	}
}