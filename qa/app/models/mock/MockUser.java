package models.mock;

import models.*;

/**
 * The Class MockUser used for tests.
 */
public class MockUser implements IUser {

	/** The username. */
	private String username;

	/**
	 * Instantiates a new mock user.
	 *
	 * @param username the username
	 */
	public MockUser(String username) {
		this.username = new String(username);
	}

	public void registerPost(Post post) {
	}

}
