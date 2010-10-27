package models;

public class Vote{
	
	//either +1 or -1
	private int value;
	private IUser user;
	private long id;
	
	private static long idCounter = 1;
	
	public Vote(int value,IUser user){
		this.value=value;
		this.user=user;
		this.id = idCounter++;
	}
	
	public IUser getUser(){
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
