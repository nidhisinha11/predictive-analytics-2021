import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import org.apache.commons.lang3.ArrayUtils;

public class preProcessingTasks {
	
	//this function performs all of the preprocessing tasks  
	public static List<String> preProcess(String f) throws IOException {
		//remove all stopwords 
		List<String> stopwords = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		stopwords = Files.readAllLines(Paths.get("stopwords.txt"));
		temp = Files.readAllLines(Paths.get(f));
		for (int i = 0; i < temp.size(); i++) {
			String s = temp.get(i);
			String[] t = s.split(" ");
			for (String s1 : stopwords) {
				for (int j = 0; j < t.length; j++) {
					if (t[j].equals(s1)) {
						String[] tcopy = java.util.Arrays.copyOfRange(t, 0, j);   
						String[] tcopy1 = java.util.Arrays.copyOfRange(t, j + 1, t.length);
						String[] both = ArrayUtils.addAll(tcopy, tcopy1);
						String b = "";
						for (int k = 0; k < both.length; k++) {
							b = b + both[k] + " ";
						}
						temp.set(i, b);
					}
				}
			}
		}
		//perform tokenization, lemmatization and find the ner tags
		Document d = new Document(temp.toString());
		List<String> tokens = new ArrayList<String>();
		List<String> lemmas = new ArrayList<String>();
		List<String> ner = new ArrayList<String>();
		for (Sentence sent : d.sentences()) {
		tokens.addAll(sent.words()); 
		lemmas.addAll(sent.lemmas());
		ner.addAll(sent.nerTags());
		}
		//find the ngrams and merge them into one list 
		List<String> twograms = slidingWindow(ner, 2);
		List<String> trigrams = slidingWindow(ner, 3);
		List<String> ngrams = new ArrayList<String>();
		ngrams.addAll(twograms);
		ngrams.addAll(trigrams);
		List<String> finalString = new ArrayList<String>();
		//return a list of tokens, ner, and ngrams 
		finalString.addAll(tokens);
		finalString.addAll(ner);
		finalString.addAll(ngrams);
		return finalString;
	}
	
	//this function does the sliding window algorithm
	public static List<String> slidingWindow(List<String> phrases, int n){
		List<String> possiblengrams = new ArrayList<String>();
		List<String> ngrams = new ArrayList<String>();
		for (String str : phrases) {
			for (int i = 0; i < str.length() - n; i++) {
				possiblengrams.add(str.substring(i, i + n));
			}
		}
		
		for (int i = 0; i < possiblengrams.size(); i++) {
			int counter = 0;
			for(int j = i + 1; j < possiblengrams.size(); j++) {
				String temp = possiblengrams.get(i);
				if (temp == possiblengrams.get(j)) {
					counter++;
				}
			}
			if (counter >= 3) {
				ngrams.add(possiblengrams.get(i));
			}
		}
		
		List<String> ngrams1 = new ArrayList<String>(); 
		
		for (int i= 0; i < ngrams.size(); i++) {
			String ngram = ""; 
			for (int j = 0; j < ngrams.get(i).length(); j++) {
				ngram.concat(ngrams.get(i));
				ngram.concat("-");
			}
			ngram.substring(0, ngram.length() - 1);
			ngrams1.add(ngram);
		}
		
		return ngrams1; 
	}
	
}
