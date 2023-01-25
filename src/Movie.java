/**
 * 
 * Java Class: Movie
 * 
 * Author: Luis Miguel Miranda
 * 
 * Date: 24/01/2023
 * 
 * Description: This a class that will simulate a Movie class. In future projects, we can add more attributes
 * 
 * Version: 1.0
 * 
 **/

public class Movie {
	
	private int movieId;
	private String movieName;
	
	public Movie() {
		
	}
	
	public Movie(int movieId, String movieName) {
		this.movieId = movieId;
		this.movieName = movieName;
		
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

}
