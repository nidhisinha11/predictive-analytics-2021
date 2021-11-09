import java.util.ArrayList;
import java.util.List;

public class evaluation {
	public static void confusionMatrix(List<List<Double>> predictions, List<List<Double>> actual) {
		List<Integer> truePositives = new ArrayList<Integer>();
		List<Integer> falsePositives = new ArrayList<Integer>();
		List<Integer> trueNegatives = new ArrayList<Integer>();
		List<Integer> falseNegatives = new ArrayList<Integer>();
		
		//if the actual cluster and the predicted cluster contain the same document, add to the true positives 
		for (List<Double> a : actual) {
			int i = 0;
			int num = 0;
			for (List<Double> p : predictions) {
				for (int j = 0; j < p.size(); j++) {
					if(a.contains(p.get(j))) {
						num++;
					}
				}
				truePositives.set(i, num);
				i++;
			}
		}
		
		//if the predicted cluster contains a document that is  NOT in the actual cluster, add to the false positives  
		for (List<Double> a : actual) {
			int i = 0;
			int num = 0;
			for (List<Double> p : predictions) {
				for (int j = 0; j < p.size(); j++) {
					if(!a.contains(p.get(j))) {
						num++;
					}
				}
				falsePositives.set(i, num);
				i++;
			}
		}
		
		//if the predicted cluster does NOT contain a document that IS in the actual cluster, add to the false negatives  
		for (List<Double> p : predictions) {
			int i = 0;
			int num = 0;
			for (List<Double> a : actual) {
				for (int j = 0; j < a.size(); j++) {
					if(!p.contains(a.get(j))) {
						num++;
					}
				}
				falseNegatives.set(i, num);
				i++;
			}
		}
		
		//if the predicted cluster and the actual cluster both do NOT have a document, add to the true negatives
		for (List<Double> a : actual) {
			for (List<Double> p : predictions) {
				int i = 0; 
				int num = 0; 
				for (int j = 0; j < actual.size(); j++) {
					if (!a.contains(actual.get(j)) && !p.contains(actual.get(j))) {
						num++;
					}
				}
				trueNegatives.set(i, num);
			}
		}
		
		//print out confusion matrix 
		System.out.println("Confusion Matrix");
		for (int i = 0; i < truePositives.size(); i++) {
			System.out.println("True Positives for Cluster " + i + " : " + truePositives.get(i));
			System.out.println("True Negatives for Cluster " + i + " : " + trueNegatives.get(i));
			System.out.println("False Positives for Cluster " + i + " : " + falsePositives.get(i));
			System.out.println("False Negatives for Cluster " + i + " : " + falseNegatives.get(i));
		}
		
		//calculate Precision, Recall, F-1 Score
		double precision = 0.0;
		double recall = 0.0;
		double f1score = 0.0;
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		
		for (int i = 0; i < truePositives.size(); i++){
			sum1 += truePositives.get(i);
		}
		for (int i = 0; i < falseNegatives.size(); i++){
			sum2 += falseNegatives.get(i);
		}
		for (int i = 0; i < falsePositives.size(); i++){
			sum3 += falsePositives.get(i);
		}
		if (sum1 == 0 || sum2 == 0 || sum3 == 0) {
			recall = 0;
			precision = 0;
			f1score = 0; 
		}
		else {
		recall = sum1 / (sum1 + sum2);
		precision = sum1 / (sum1 + sum3);
		f1score = 2*((precision*recall)/(precision + recall));
		}
		
		//print out metrics
		System.out.println("Precision: " + precision);
		System.out.println("Recall: " + recall); 
		System.out.println("F1 Score: " + f1score); 
	}

}
