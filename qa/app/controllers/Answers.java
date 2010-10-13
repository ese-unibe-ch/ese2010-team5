package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import models.Answer;
import models.Question;
import models.User;
import play.Logger;
import play.data.validation.Required;

public class Answers extends Posts {
	
	
	public static void edit(long id){
				
		Answer a = Answer.findById(id);		
		if(a == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		
		render(a);
		
	}
	
	public static void setContent(long id, String content){
		Logger.debug("Setting new content: \""+content+"\"");
		
		Answer a = Answer.findById(id);		
		if(a == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		a.setContent(content);
		flash.put("info","Content of answer "+id+" changed");
		
		Question q = a.getQuestion();
		//sends a redirect
		Questions.view(q.getId());
		
	}
	
	
}
