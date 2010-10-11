package controllers;

import java.util.Collection;

import models.Answer;
import models.Post;
import models.Question;

import play.Logger;
import play.mvc.Controller;
import play.mvc.With;

public class Questions extends Posts {	
	
	
	public static void list(){
		
		Collection<Question> questions = Question.findAll();
		render(questions);
	}
	
	public static void delete(long id){
		
		Post p = Post.findById(id);
		
		if(p == null){
			flash.error("could not find Question with id "+id);
		}else{
			flash.put("info","Question "+p.getId()+" deleted");
		}	
				
		
		list();		
		
	}
	
	
	public static void create(String content){
		
		Logger.debug("Create Question with content: "+content);		
		Question q = new Question(user, content);	
		
		flash.put("info", "Question "+q.getId()+" created");
		
		list();
		
	}
	
	public static void addAnswer(String answer, long qId){
		Question q = Question.findById(qId);		
		
		
		if(q == null){
			flash.error("could not find question q: "+qId);			
			redirect("/");
		}
		Answer newAnswer = new Answer(user, answer);
		q.addAnswer(newAnswer);
				
		flash.put("info", "new Answer created");		
		view(qId);		
			
	}
	
	
	public static void view(long id){
		Logger.debug("Show question: "+id);
		
		Question q = Question.findById(id);		
		if(q == null){
			flash("error", "could not find question with id "+id);
			redirect("/");
		}
		
		render(q);
		
	}
	
}
