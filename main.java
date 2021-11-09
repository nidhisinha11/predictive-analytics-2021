import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		for (int i = 1; i < 9; i++) {
			String s = "unknown/article0" + i + ".txt";
			List<String> temp = preProcessingTasks.preProcess(s);
			documentTermMatrix.allTerms(temp);
		}
		String s = "unknown/article10.txt";
		List<String> temp = preProcessingTasks.preProcess(s);
		documentTermMatrix.allTerms(temp);
		//create the document term matrix, which will call the preprocessing tasks from that class
		List<String> realMasterTerms = documentTermMatrix.createMatrix();
		
		//transform the matrix using the TFIDF values 
		List<List<Double>> mat = documentTermMatrix.transformMatrix(realMasterTerms);
		
		//generate keywords from the TFIDF matrix for each document
		List<String> topics = documentTermMatrix.generateKeywords(mat, realMasterTerms);
		
		//write the keywords for each document into a new file
		FileWriter out = new FileWriter("topics.txt"); 
		for (int i = 1; i < topics.size(); i++) {
			out.write("Document " + i + " Topic: " + topics.get(i));
		}
		out.close();
		
		List<List<Double>> predictions = new ArrayList<List<Double>>();
		
		//perform K-NN for each document in the matrix to find its nearest neighbors
		for (int i = 0; i < mat.size(); i++) {
			predictions = clustering.kNN(mat, mat.get(i), 3);
		}
		
		//create the actual clusters in order to evaluate the model 
		List<List<Double>> actual = new ArrayList<List<Double>>();
		
		List<List<Double>> cluster1 = new ArrayList<List<Double>>();
		for(int i = 0; i < 3; i++) {
			cluster1.add(mat.get(i));
		}
		List<List<Double>> cluster2 = new ArrayList<List<Double>>();
		for(int i = 3; i < 6; i++) {
			cluster2.add(mat.get(i));
		}
		List<List<Double>> cluster3 = new ArrayList<List<Double>>();
		for(int i = 6; i < mat.size(); i++) {
			cluster3.add(mat.get(i));
		}
		actual.addAll(cluster1);
		actual.addAll(cluster2);
		actual.addAll(cluster3);
		
		//evaluate the model performance
		evaluation.confusionMatrix(predictions, actual);
	}
	

}
