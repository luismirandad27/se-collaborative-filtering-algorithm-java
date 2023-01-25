/**
 * 
 * Java Class: User
 * 
 * Author: Luis Miguel Miranda
 * 
 * Date: 24/01/2023
 * 
 * Description: This a class that will simulate a User class. In future projects, we can add more attributes
 * 
 * Version: 1.0
 * 
 **/

public class User {

	private int userId;
	private String userName;
	
	public User() {
		
	}
	
	public User(int userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
