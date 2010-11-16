package models.impl;

import models.IUser;

/**
 * The Class Vote.
 */
public class Vote{
	
	/** The value (either +1 or -1). */
	private int value;
	
	/** The user. */
	private IUser user;
	
	/** The id. */
	private long id;
	
	/** The id counter. */
	private static long idCounter = 1;
	
	/**
	 * Instantiates a new vote.
	 *
	 * @param value the value
	 * @param user the user
	 */
	public Vote(int value,IUser user){
		this.value=value;
		this.user=user;
		this.id = idCounter++;
	}
	
	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public IUser getUser(){
		return this.user;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue(){
		return this.value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param inValue the new value
	 */
	public void setValue(int inValue){
		this.value = inValue;
	}
	
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId(){
		return id;
	}

}
