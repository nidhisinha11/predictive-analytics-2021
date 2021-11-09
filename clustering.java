import java.util.ArrayList;
import java.util.List;

public class clustering {
	
	
	public static List<List<Double>> kNN(List<List<Double>> matrix, List<Double> testDoc, int k){
		List<Double> distances = new ArrayList<Double>(); 
		//calculate the distance for each doc and the test document 
		for (int i = 0; i < matrix.size(); i++) {
			distances.add(euclideanDist(testDoc, matrix.get(i)));
		}
		//find the smallest 3 distances and the corresponding documents
		int[] docNums = new int[k];
		double smallest = 1000000.0; 
		//find the SMALLEST distance and the corresponding document number
		for (int i = 0; i < distances.size(); i++) {
			if (distances.get(i) < smallest) {
				smallest = distances.get(i);
				docNums[0] = i;
			}
		}
		//find the SECOND smallest distance
		double smallest2 = 1000000.0;
		for (int i = 0; i < distances.size(); i++) {
			if (smallest < distances.get(i) && distances.get(i) < smallest2) {
				smallest2 = distances.get(i);
				docNums[1] = i;
			}
		}
		//find the THIRD smallest distance 
		double smallest3 = 1000000.0;
		for (int i = 0; i < distances.size(); i++) {
			if (smallest2 < distances.get(i) && distances.get(i) < smallest3) {
				smallest3 = distances.get(i);
				docNums[2] = i;
			}
		}
		
		//create a list of the test document and the nearest neighbors 
		List<List<Double>> ans = new ArrayList<List<Double>>();
		ans.add(testDoc);
		for (int i = 0; i < docNums.length; i++) {
			ans.add(matrix.get(docNums[i]));
		}
		
		return ans; 
	}
	
	
	//calculate euclidean distance
	public static double euclideanDist(List<Double> v1, List<Double> v2) {
		return Math.sqrt((v2.get(1) - v1.get(1)) * (v2.get(1) - v1.get(1)) + (v2.get(0) - v1.get(0)) * (v2.get(0) - v1.get(0)));
	}
	
}
