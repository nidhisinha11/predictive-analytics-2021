import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator; 

public class documentTermMatrix {
	public static Set<String> masterTerms = new HashSet<String>();
	public static List<List<Integer>> matrix = new ArrayList<List<Integer>>();
	
	public static void allTerms(List<String> terms){
		//add to a list of every unique term from every document to create the matrix 
		for (String t : terms) {
			String[] t1 = t.split(" ");
			for (int i = 0; i < t1.length; i++) {
				masterTerms.add(t1[i]);
			}
		}
	}
	
	//create the initial matrix of term frequencies
	public static List<String> createMatrix() throws IOException {
		List<String> realMasterTerms = new ArrayList<String>(masterTerms);
		//add the frequency values for each document to the matrix
		for (int i = 1; i < 9; i++) {
			String s = "dataset_3/data/C1/article0" + i + ".txt";
			List<Integer> termFreq = new ArrayList<Integer>(); 
			List<String> temp = new ArrayList<String>();
			temp = preProcessingTasks.preProcess(s);
			//count the frequency for each term in the document against the masterlist of terms
			for (String m : realMasterTerms) {
				int count = 0;
				for (String t : temp) {
					String[] t1 = t.split(" ");
					for (int i1 = 0; i1 < t1.length; i1++) {
						if (t1[i1].equals(m)) {
							count++;
						}
					}
				}
				termFreq.add(count);
			}
			//add each frequency to the matrix 
			matrix.add(termFreq);
		}
		for (int i = 1; i < 9; i++) {
			String s = "dataset_3/data/C4/article0" + i + ".txt";
			List<Integer> termFreq = new ArrayList<Integer>(); 
			List<String> temp = new ArrayList<String>();
			temp = preProcessingTasks.preProcess(s);
			//count the frequency for each term in the document against the masterlist of terms
			for (String m : realMasterTerms) {
				int count = 0;
				for (String t : temp) {
					String[] t1 = t.split(" ");
					for (int i1 = 0; i1 < t1.length; i1++) {
						if (t1[i1].equals(m)) {
							count++;
						}
					}
				}
				termFreq.add(count);
			}
			//add each frequency to the matrix 
			//System.out.println(termFreq);
			matrix.add(termFreq);
			}
		for (int i = 1; i < 9; i++) {
			String s = "dataset_3/data/C7/article0" + i + ".txt";
			List<Integer> termFreq = new ArrayList<Integer>(); 
			List<String> temp = new ArrayList<String>();
			temp = preProcessingTasks.preProcess(s);
			//count the frequency for each term in the document against the masterlist of terms
			for (String m : realMasterTerms) {
				int count = 0;
				for (String t : temp) {
					String[] t1 = t.split(" ");
					for (int i1 = 0; i1 < t1.length; i1++) {
						if (t1[i1].equals(m)) {
							count++;
						}
					}
				}
				termFreq.add(count);
			}
			//add each frequency to the matrix 
			//System.out.println(termFreq);
			matrix.add(termFreq);
			}
		return realMasterTerms; 
	}
	
	//calculates occurrence of term t in document doc 
	public static double tf(String t, int docNo, List<String> realMasterTerms) throws IOException {
		int count = 0;
		int length = matrix.get(docNo).size();
		int col = 0; 
		//find what column the term is in
		for (int i = 0; i < realMasterTerms.size(); i++) {
			if (realMasterTerms.get(i).equals(t)) {
				col = i; 
			}
		}
		//find the term freq at that column for that document 
		count = matrix.get(docNo).get(col);
		double tf = count/length;
		return tf; 
	}

	//calculate the inverse document frequency for each term t
	public static double idf(String t, List<String> realMasterTerms) {
		int docNum = 24; 
		int count = 0;
		int col = 0;
		//find what column the term is in 
		for (int i = 0; i < realMasterTerms.size(); i++) {
			if (realMasterTerms.get(i).equals(t)) {
				col = i; 
			}
		}
		//if the termFreq > 0 then that doc has that term
		for (int i = 0; i < matrix.size(); i++) {
			if (matrix.get(i).get(col) > 0){
				count++;
			}
		}
		double idf = Math.log(docNum/count);
		return idf;
	}
	
	//calculate tfidf
	public static double tfidf(double tf, double idf) {
		return tf*idf;
	}
	
	//transform the matrix using tfidf 
	public static List<List<Double>> transformMatrix(List<String> realMasterTerms) throws IOException {
		List<List<Double>> finalMatrix = new ArrayList<List<Double>>();
		List<Double> docTfIdf = new ArrayList<Double>();
		//go through each possible term and calculate the tfidf for each row (document) in the matrix 
		for (int i = 0; i < matrix.size(); i++) {
			for (String m : realMasterTerms) {
				double tf = tf(m, i, realMasterTerms);
				double idf = idf(m, realMasterTerms);
				double tfidf = tfidf(tf, idf);
				docTfIdf.add(tfidf);
			}
			//add the tfidf for each term in each document into a final matrix
			finalMatrix.add(docTfIdf);
		}
		return finalMatrix;
	}
	
	//generate the keywords for each document based on the largest TFIDF values
	public static List<String> generateKeywords(List<List<Double>> finalMat, List<String> realMasterTerms){
		List<String> topics = new ArrayList<String>();
		int largestIndex = 0;
		//iterate through the matrix through each document 
		for (int i = 0; i < finalMat.size(); i++) {
			//inside the list<double> of the doc, find the largest TFIDF value and its index which correlates to a term on the masterlist 
			for (int j = 0; j < finalMat.get(i).size(); j++) {
				List<Double> temp = finalMat.get(i);
				if (temp.get(j) >= largestIndex) {
					largestIndex = j;
				}
			}
			topics.add(realMasterTerms.get(largestIndex));
		}
		return topics;
	}
}
