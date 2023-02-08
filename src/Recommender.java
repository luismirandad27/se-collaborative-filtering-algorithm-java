import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Java Class: Recommender
 * 
 * Author: Luis Miguel Miranda
 * 
 * Date: 24/01/2023
 * 
 * Description: This class consists on a list of methods that will calculate the prediction of a movie rating
 * by using the User-based Collaborative Filtering Algorithm
 * 
 * Version: 1.0
 **/

public class Recommender {

	private static final int NUM_NEIGHBORS = 2;
	private HashMap<Integer, HashMap<Integer,Double>> ratings;
	
	
	public Recommender() {
		
	}
	
	public Recommender(HashMap<Integer, HashMap<Integer,Double>> ratings) {
		this.ratings = ratings;
	}
	
	/*
	 * method: getCommonRatedMoviesList
	 * params:
	 * {int a} -> userId
	 * {int b} -> userId
	 * return:
	 * {List<Integer>} -> list of MovieIds that both users rated.
	 */
	public List<Integer> getCommonRatedMoviesList (int a, int b) {
		
		List<Integer> commonRatedMovies = new ArrayList<>();
		
		HashMap<Integer,Double> moviesRatedA = ratings.get(a);
		HashMap<Integer,Double> moviesRatedB = ratings.get(b);
		
		for(Map.Entry<Integer, Double> entryA: moviesRatedA.entrySet()) {
			if (moviesRatedB.containsKey(entryA.getKey())) {
				commonRatedMovies.add(entryA.getKey());
			}
		}
		
		return commonRatedMovies;
		
	}
	
	/*
	 * method: calculateAverageRating
	 * params:
	 * {int a} -> userId
	 * return:
	 * {double} -> the average rating for user with userId 'a'
	 */
	public double calculateAverageRating(int a) {
		
		double average = 0.0;
		
		HashMap<Integer,Double> moviesRatedA = ratings.get(a);
		
		for(Map.Entry<Integer,Double> entry : moviesRatedA.entrySet()) {
			
			average += entry.getValue();
			
		}
		
		average = average / (double)moviesRatedA.size();
		
		return average;
		
	}
	
	/*
	 * method: calculateSimilarity
	 * params:
	 * {int a} -> userId
	 * {int b} -> userId
	 * {List<Integer> crm} -> crm = common rated movies
	 * return:
	 * {double} -> the similarity between users 'a' and 'b' with a list of common rated movies
	 */
	public double calculateSimilarity(int a, int b, List<Integer> crm) {
		
		double similarity = 0.0;
		
		double avgRa = calculateAverageRating(a);
		double avgRb = calculateAverageRating(b);
		
		double numerator = 0.0;
		double denom1 = 0.0;
		double denom2 = 0.0;
		
		HashMap<Integer,Double> moviesRatedA = ratings.get(a);
		HashMap<Integer,Double> moviesRatedB = ratings.get(b);
		
		for(int i=0; i< crm.size(); i++) {
			
			numerator += (moviesRatedA.get(crm.get(i)))*(moviesRatedB.get(crm.get(i)));
			
			denom1	  += Math.pow(moviesRatedA.get(crm.get(i)), 2);
			denom2	  += Math.pow(moviesRatedB.get(crm.get(i)), 2);
			
		}
		
		denom1 = Math.sqrt(denom1);
		denom2 = Math.sqrt(denom2);
		
		if (crm.size()> 1 && numerator !=0 && denom1 != 0 && denom2 != 0) {
			return numerator / (denom1 * denom2);
		}
		return -100;
		
	}
	
	/*
	 * method: sortingHashMap
	 * params:
	 * {HashMap<Integer,Double> unsortedHashMap} -> unsorted HashMap
	 * return:
	 * {HashMap<Integer,Double>} -> sorted HashMap by Value in descending order
	 */
	public HashMap<Integer, Double> sortingHashMap(HashMap<Integer,Double> unsortedHashMap){
		
		List<Map.Entry<Integer, Double>> list = new LinkedList<>(unsortedHashMap.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<Integer,Double>>(){
			
			@Override
			public int compare(Map.Entry<Integer, Double> o1,
							   Map.Entry<Integer, Double> o2) {
				
				return (o2.getValue()).compareTo(o1.getValue());
			}
			
		});
		
		HashMap<Integer,Double> sortedHashMap = new LinkedHashMap<>();
		for (Map.Entry<Integer, Double> entry: list) {
			
			sortedHashMap.put(entry.getKey(), entry.getValue());
			
		}
		
		
		return sortedHashMap;
		
	}
	
	/*
	 * method: getUserSimilarities
	 * params:
	 * {int a} -> userId
	 * return:
	 * {HashMap<Integer,Double>} -> HashMap which Key is the other userId we are comparing with and the Value is the similarity value
	 */
	public HashMap<Integer, Double > getUsersSimilarities(int a){
		
		HashMap<Integer,Double> userSimilarities = new HashMap<>();
		
		for(Map.Entry<Integer, HashMap<Integer,Double>> entry: ratings.entrySet()) {
			
			if (a != entry.getKey()) {
			
				List<Integer> crm = getCommonRatedMoviesList(a,entry.getKey());
				
				double similarity = calculateSimilarity(a,entry.getKey(),crm);
				
				userSimilarities.put(entry.getKey(), similarity);
				
			}
			
		}
		
		userSimilarities = sortingHashMap(userSimilarities);
		
		return userSimilarities;
		
	}
	
	/*
	 * method: calculatePrediction
	 * params:
	 * {int a} -> userId
	 * {int movieIdx} -> movie we want to predict its rating.
	 * return:
	 * {double} -> predicted rating
	 */
	public double calculatePrediction(int a, int movieIdx) {
		
		//First we are going to calculate the similarities and let's set a limit of Neighbors
		HashMap<Integer, Double > similarUsers = getUsersSimilarities(a);
		
		int numUsers = Math.min(similarUsers.size(), NUM_NEIGHBORS);
		
		if (numUsers == 0) {
			return 0;
		}
		
		double rating; 
		
		double numerator = 0.0;
		double denom = 0.0;
		
		int j=0;
		
		for(Map.Entry<Integer, Double> user: similarUsers.entrySet()) {
			
			if (j<numUsers) {
				
				double avgRj = calculateAverageRating(user.getKey());
				
				double similarityWithJ = user.getValue();
				
				double ratingFromUser = ratings.get(user.getKey()).get(movieIdx) == null ? 0 : ratings.get(user.getKey()).get(movieIdx) ;
				
				if (ratingFromUser > 0) {
				
					numerator += similarityWithJ * (ratingFromUser  - avgRj);
					
					denom += Math.abs(similarityWithJ);
					
					j++;
					
				}
				
			}else {
				
				break;
				
			}			
		
		}
		
		double avgRa = calculateAverageRating(a);
		
		
		rating = avgRa + (numerator / denom);
		
		return rating;
		
	}
	
	/*
	 * method: rateAllMoviesForUser
	 * params:
	 * {List<Movie>} -> list of all movies available
	 * {int a} -> userId
	 * return:
	 * {HashMap<Integer,Double>} -> all the movies that the user didn't rate that include the predicted rating.
	 */
	public HashMap<Integer,Double> rateAllMoviesForUser(List<Movie> allMovies ,int a) {
		
		//Before making the predictions, first we need to get the users with the highest similarities
		
		HashMap<Integer,Double> predictedRatings = new HashMap<>();
		
		for (int i = 0; i < allMovies.size() ; i++) {
			
			Movie movie = allMovies.get(i);
			
			if (ratings.get(a).get(movie.getMovieId()) == null) {
				
				predictedRatings.put(movie.getMovieId(), calculatePrediction(a,movie.getMovieId()));

			}
			
		}
		
		return predictedRatings;
		
	}
	
}
