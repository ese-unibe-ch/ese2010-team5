package models;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Answer extends Post {
	
	private static Map<Long,Answer> instanceMap = new HashMap();
	
	

	public Answer(User owner, String content) {
		super(owner,content);
		instanceMap.put(getId(),this);
		
	}	
	
	public  String toString(){
		return "rating: "+getVotation()+", "+getContent();
	}
	
	public static Answer findById(long inId){
		return instanceMap.get(inId);
	}
	
	public static Collection<Answer> findAll(){
		return instanceMap.values();
	}

	

}
