package controllers;

import play.Logger;
import utils.QaDB;
import utils.QaMarkdown;
import models.*;

/**
 * The Class Posts.
 */
public class Posts extends Auth {
 
	
	/**
	 * Markdown preview.
	 *
	 * @param input the input
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
	 * Rate the post.
	 *
	 * @param id the id
	 * @param up the up
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