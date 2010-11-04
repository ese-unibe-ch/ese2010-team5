package models;

import java.util.LinkedList;
import java.util.List;

public class Tag {
	
	private String name;
	private List<Question> relatedQuestions = new LinkedList<Question>();
	private long id;
	private static long idCounter = 1;
	
	public Tag(String name, Question relatedQuestion){
		this.name = name;
		this.relatedQuestions.add(relatedQuestion);
		this.id = idCounter++;
		
	}
	
	public String toString(){
		return this.name;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

}
