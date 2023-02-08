/**
 * 
 * Main Function: RecommendationSystem
 * 
 * Author: Luis Miguel Miranda
 * 
 * Date: 24/01/2023
 * 
 * Description: This will make the simulation of the process of predict the ratings.
 * 
 * Version: 1.0
 * 
 **/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationSystem {
	
	/*
	 * method: main
	 * params:
	 * {String[] args} -> arguments
	 * return:
	 * {} -> main function
	 */
	public static void main(String[] args) {
		
		//Creating a set Users
		User user1 = new User(1,"Luis");
		User user2 = new User(2,"Gianella");
		User user3 = new User(3,"Victor");
		User user4 = new User(4,"Marc");
		User user5 = new User(5,"Regal");
		
		//Creating a set Movies
		Movie movie1 = new Movie(1,"Avengers: Endgame");
		Movie movie2 = new Movie(2,"The Conjuring");
		Movie movie3 = new Movie(3,"Midnight in Paris");
		Movie movie4 = new Movie(4,"The Pianist");
		Movie movie5 = new Movie(5,"SpiderMan No Way Home");
		Movie movie6 = new Movie(6,"Evil Dead");
		
		List<Movie> allMovies = new ArrayList<>();
		allMovies.add(movie1);
		allMovies.add(movie2);
		allMovies.add(movie3);
		allMovies.add(movie4);
		allMovies.add(movie5);
		allMovies.add(movie6);
		
		//Add ratings: Let's suppose that the data comes from a List<UserMovie>
		List<UserMovie> totalRatings = new ArrayList<>();
		totalRatings.add(new UserMovie(user1,movie1,4));
		totalRatings.add(new UserMovie(user1,movie3,5));
		totalRatings.add(new UserMovie(user1,movie5,5));
		
		totalRatings.add(new UserMovie(user2,movie1,5));
		totalRatings.add(new UserMovie(user2,movie2,1));
		totalRatings.add(new UserMovie(user2,movie3,4));
		totalRatings.add(new UserMovie(user2,movie4,2));
		totalRatings.add(new UserMovie(user2,movie5,5));
		totalRatings.add(new UserMovie(user2,movie6,1));
		
		
		totalRatings.add(new UserMovie(user3,movie2,2));
		totalRatings.add(new UserMovie(user3,movie3,2));
		totalRatings.add(new UserMovie(user3,movie5,4));
		totalRatings.add(new UserMovie(user3,movie6,3));
		
		totalRatings.add(new UserMovie(user4,movie1,4));
		totalRatings.add(new UserMovie(user4,movie2,3));
		totalRatings.add(new UserMovie(user4,movie3,1));
		totalRatings.add(new UserMovie(user4,movie4,4));
		totalRatings.add(new UserMovie(user4,movie5,5));
		
		
		totalRatings.add(new UserMovie(user5,movie1,4));
		totalRatings.add(new UserMovie(user5,movie4,5));
		totalRatings.add(new UserMovie(user5,movie5,5));
		
		
		HashMap<Integer, HashMap<Integer, Double>> ratings = convertListToHashMap(totalRatings);
		
		//Testing if everything was stored correctly
		//printHashMap(ratings);
		
		//Creating our recommender object
		Recommender rec = new Recommender(ratings);
		
		//Let's test this for the userId 1
		int userId = 1;
		
		//Calculate the predictions for the movies with no ratings
		HashMap<Integer,Double> allPredictions = rec.rateAllMoviesForUser(allMovies, userId);
		
		for(Map.Entry<Integer, Double> m: allPredictions.entrySet()) {
			
			System.out.println("Prediction for movie "+ allMovies.get(m.getKey()-1).getMovieName()+" is: "+m.getValue());
			
		}
		
	}
	
	/*
	 * method: printHashMap
	 * params:
	 * {HashMap<Integer, HashMap<Integer,Double>> m} -> hashMap
	 * return:
	 * {} -> print the elements of the HashMap
	 */
	public static void printHashMap(HashMap<Integer, HashMap<Integer,Double>> m) {
		for(Map.Entry<Integer, HashMap<Integer,Double>> e: m.entrySet()) {
			
			System.out.println("User name: "+e.getKey());
			
			for(Map.Entry<Integer, Double> f: e.getValue().entrySet()) {
				
				System.out.println("Movie name: "+ f.getKey()+" rating: "+f.getValue());
				
			}
			
		}
	}
	
	/*
	 * method: convertListToHashMap
	 * params:
	 * {List<UserMovie> totalRatings} -> total ratings made by the users
	 * return:
	 * {HashMap<Integer, HashMap<Integer,Double>>} -> total ratings converted into a HashMap structure
	 */
	public static HashMap<Integer, HashMap<Integer,Double>> convertListToHashMap(List<UserMovie> totalRatings){
		
		HashMap<Integer, HashMap<Integer, Double>> ratings = new HashMap<>();
		
		int prevUser = 1;
		int currentUser;
		HashMap<Integer, Double> ratingsPerUser = new HashMap<>();
		for(int i=0; i<totalRatings.size();i++) {
			
			currentUser = totalRatings.get(i).getUser().getUserId();
			
			if(prevUser != currentUser) {
				
				ratings.put(prevUser, ratingsPerUser);
				
				ratingsPerUser = new HashMap<>();
				
				ratingsPerUser.put(totalRatings.get(i).getMovie().getMovieId(), 
						totalRatings.get(i).getRating());
				
				
			}else {
				
				ratingsPerUser.put(totalRatings.get(i).getMovie().getMovieId(), 
						totalRatings.get(i).getRating());
				
			}
			
			prevUser = currentUser;
			
		}
		
		ratings.put(prevUser, ratingsPerUser);
		
		return ratings;
	}

}
