package models;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Question extends Post {
	
		
	private static List<Question> questionList = new LinkedList<Question>();
	private List<Answer> answers= new LinkedList<Answer>();
	

	public Question(IUser user, String content) {
		super(user, content);
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void addAnswer(Answer newAnswer) {
		this.answers.add(newAnswer);
	}
	
			
	protected void doDelete() {
		/* do somthing when im getting deleted*/	  	  
	}

}
