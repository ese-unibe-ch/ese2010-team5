package models.impl;

import play.*;
import play.db.jpa.*;
import utils.QaDB;

import javax.persistence.*;

import models.IComment;
import models.IUser;

import java.util.*;

/**
 * The Class Comment.
 */
public class Comment extends Post implements IComment{
	
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
			/*remove me from my post(answer or question)*/
			post.delComment(this);
	}
    
}
