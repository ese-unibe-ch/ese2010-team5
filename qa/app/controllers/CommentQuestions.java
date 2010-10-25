package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import models.Answer;
import models.Comment;
import models.Post;
import models.Question;
import models.User;
import models.CommentQuestion;
import models.CommentAnswer;
import play.Logger;
import play.data.validation.Required;
import utils.QaDB;

public class CommentQuestions extends Posts {
	
	
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
		
		Comment c = QaDB.findCommentById(id);		
		if(c == null){
			flash("error", "could not find answer with id "+id);
			redirect("/");
		}
		c.setContent(content);
		flash.put("info","Content of answer "+id+" changed");
		
		Post q = c.getPost();
		//sends a redirect
		Questions.view(q.getId());
		
	}
	
	
}
