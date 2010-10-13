package models;

public class Vote {
	
	//either +1 or -1
	private int value;
	private User user;
	private long id;
	
	private static long idCounter = 1;
	
	public Vote(int value,User user){
		this.value=value;
		this.user=user;
		this.id = idCounter++;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setValue(int inValue){
		this.value = inValue;
	}
	
	
	public long getId(){
		return id;
	}

}
