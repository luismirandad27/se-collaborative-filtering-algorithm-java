/**
 * 
 * Java Class: UserMovie
 * 
 * Author: Luis Miguel Miranda
 * 
 * Date: 24/01/2023
 * 
 * Description: This a class that will simulate a UserxMovie class. In future projects, we can add more attributes
 * This class refers on the combination between a User with a Movie and its rating.
 * 
 * Version: 1.0
 * 
 **/

public class UserMovie {

	private User user;
	private Movie movie;
	private double rating;
	
	public UserMovie() {
		
	}
	
	public UserMovie(User user, Movie movie, double rating) {
		
		this.user = user;
		this.movie = movie;
		this.rating = rating;
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}


	
}
