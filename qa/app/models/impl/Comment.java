package models.impl;

import java.util.ArrayList;
import java.util.List;

import utils.QaDB;

import models.IComment;
import models.IUser;

/**
 * The Class Comment.
 */

public class Comment extends Post implements IComment {

	/** The comment belongs to a Post (either a Question or an Answer). */
	private Post post;
	
	/** The like list. */
	private List<IUser> likeList;

	/**
	 * Instantiates a new comment.
	 * 
	 * @param owner
	 * @param content
	 * @param quest
	 *            the post that corresponds to this comment
	 */
	public Comment(IUser owner, String content, Post quest) {
		super(owner, content);
		this.post = quest;
		this.post.addComment(this);
		this.likeList = new ArrayList<IUser>();
	}

	/**
	 * Returns the corresponding post to this comment.
	 * 
	 * @return the post
	 */
	public Post getPost() {
		return post;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner the new owner
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	protected void doDelete() {
		/* remove me from my post(answer or question) */
		post.delComment(this);
	}

	/**
	 * Gets the likes.
	 *
	 * @return the likes
	 */
	public String getLikes() {
		if (likeList.size() == 1) {
			return likeList.size() + " user likes this comment.";
		} else {
			return likeList.size() + " users like this comment.";
		}
	}

	/**
	 * Checks if is in like list.
	 *
	 * @param user the user
	 * @return true, if is in like list
	 */
	public boolean isInLikeList(IUser user) {
		return likeList.contains(user);
	}

	/**
	 * Add a user to the like list.
	 *
	 * @param user the user
	 */
	public void like(IUser user) {
		if (!(likeList.contains(user) || this.getOwner().equals(user))) {
			likeList.add(user);
		}
	}

	/**
	 * Add a user to the dislike list.
	 *
	 * @param user the user
	 */
	public void dislike(IUser user) {
		if (likeList.contains(user)) {
			likeList.remove(user);
		}
	}
	
	public boolean save(){
		QaDB.addComment(this);
		return true;
	}

}
