package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import models.Answer;
import models.Comment;
import models.IQuestion;
import models.Post;
import models.Question;
import models.User;


import play.Logger;
import play.data.validation.Required;
import utils.QaDB;

public class Comments extends Posts {
	
	
	public static void edit(long id){
				
		Comment c = QaDB.findCommentById(id);
		
		if(c == null){
			flash("error", "could not find Comment with id "+id);
			redirect("/");
		}
		
		render(c);
		
	}
	
	public static void setContent(long id, String content){
		Logger.debug("Setting new content: \""+content+"\"");
		
		Comment cA = QaDB.findCommentById(id);		
		
		
		if(cA == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		cA.setContent(content);
		flash.put("info","Content of answer "+id+" changed");
		
		Post a = cA.getPost();
		
		if(a instanceof Question)
			Questions.view(a.getId());
		else if (a instanceof Answer){
			Answer b = (Answer) a;
			IQuestion q = b.getQuestion();
			Questions.view(q.getId());
		}
		
		
	}
	
	
}
