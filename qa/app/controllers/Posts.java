package controllers;

import java.awt.List;
import java.util.Collection;

import play.Logger;
import play.mvc.With;
import play.mvc.Http.Header;
import utils.NoLogin;
import utils.QaDB;
import utils.QaMarkdown;
import models.*;
import models.impl.Post;
import models.impl.Question;

/**
 * The Class Posts.
 */
public class Posts extends Auth {
 
	/**
	 * Markdown preview.
	 *
	 * @param input
	 */
	public static void markdownPreview(String input){
		if(input != null){
			String preview = QaMarkdown.toHtml(input);			
			renderText(preview);			
		}else{
			renderText("input is null");
		}
	}
	
	/**
	 * Delete a post.
	 * Redirect to referer
	 * 
	 * @param id
	 */
	public static void delete(long id){
		
		Post p = QaDB.findPostById(id);
		Header refererHeader = request.headers.get("referer");
		String referer = "/"; /*fallback*/
		
		if(p instanceof Question && refererHeader != null){
			referer = "/";
		}
		else if (refererHeader != null) {
			referer = refererHeader.value();
		}
		
		if(p == null){
			flash.error("could not find Post with id "+id);			
		}else{
			if(QaDB.delPost(p))
				flash.put("info","Post "+p.getId()+" deleted");
			else
				flash.put("error","Could not delete "+p.getId());
		}			
		redirect(referer);
	}
	
	/**
	 * Rate the post.
	 *
	 * @param id
	 * @param up boolean value to determine if user is voted up or down
	 */
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
	
}                      
