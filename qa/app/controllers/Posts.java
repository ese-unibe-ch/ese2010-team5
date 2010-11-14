package controllers;

import play.Logger;
import play.mvc.Http.Request;
import utils.QaDB;
import utils.QaMarkdown;
import models.Answer;
import models.Post;
import models.Question;

public class Posts extends Auth {
 
	
	public static void markdownPreview(String input){
		
		if(input != null){
			String preview = QaMarkdown.toHtml(input);			
			renderText(preview);			
		}else{
			renderText("input is null");
		}
		
		
	}
	
	
	
	public static void rate(long id, boolean up){
		
		Post post = QaDB.findPostById(id);
		
		if(post == null){
			error("could not find post with id: "+id);
			return;
		}		
		
		if(up)
			post.rateUp(user);
		else
			post.rateDown(user);
		
		//render new voting
		renderText(post.getVotation());
		
	}	
	
	public static void delete(long id){
		
		Post p = QaDB.findPostById(id);
		
		if(p == null){
			flash.error("could not find Question with id "+id);
		}else{
			if(QaDB.delPost(p))
				flash.put("info","Question "+p.getId()+" deleted");
			else
				flash.put("error","Could not delete "+p.getId());
		}			
		Logger.debug("request url: %s", request.url);
		Questions.listAll();		
		
	}
	
}                      