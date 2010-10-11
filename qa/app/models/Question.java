package models;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Question extends Post {

	
	private List<Answer> answers= new LinkedList<Answer>();	
	
	private static List<Question> questionList = new LinkedList<Question>();
	
	private static Comparator<Post> ratingSorter = new Comparator<Post>() {		
    public int compare(Post post1, Post post2) {
	    // TODO Auto-generated method stub
    	if(post1.getVotation() > post2.getVotation())
    		return -1;
    	else if(post1.getVotation() < post2.getVotation())
    		return 1;
    	    	
	    return 0;
    }	
	};
		
  
	
	
	public Question(User user, String content) {
		super(user, content);		
		questionList.add(this);
		
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void addAnswer(Answer newAnswer) {
		this.answers.add(newAnswer);
	}	

	
	public static Collection<Question> findAll(){						
		Collections.sort(questionList, ratingSorter);	
		
		return questionList;
	}
	
	public static Collection<Question> findByUser(User inUser){
		
		Collection<Question> result = new LinkedList<Question>();
		
		for(Question q : questionList){
			if(q.getOwner().equals(inUser))
				result.add(q);
		}
		
		return result;
		
	}

	@Override
  protected void doDelete() {
	  questionList.remove(this);	  
  }

}
