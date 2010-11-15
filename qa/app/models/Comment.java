package models;

import play.*;
import play.db.jpa.*;
import utils.QaDB;

import javax.persistence.*;
import java.util.*;

/**
 * The Class Comment.
 */
public class Comment extends Post{
	
	/** The comment belongs to a Post (either a Question or an Answer). */
	private Post post;
	
	/**
	 * Instantiates a new comment.
	 *
	 * @param owner the owner
	 * @param content the content
	 * @param quest the post that corresponds to this comment
	 */
	public Comment(IUser owner, String content, Post quest) {
		super(owner, content);
		this.post = quest;
		this.post.addComment(this);
	}

	/**
	 * Returns the corresponding post to this comment.
	 *
	 * @return the post
	 */
	public Post getPost() {
		return post;
	}

	protected void doDelete() {
				
	}
    
}
