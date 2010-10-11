package models;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Answer extends Post {
	
	private static List<Answer> answerList = new LinkedList<Answer>();
		

	public Answer(User owner, String content) {
		super(owner,content);		
		answerList.add(this);
	}	
	
	public  String toString(){
		return "rating: "+getVotation()+", "+getContent();
	}	

	
	public static Collection<Answer> findAll(){
		return answerList;
	}
	
  protected void doDelete() {	  
  	answerList.remove(this);	  
  }	

}
