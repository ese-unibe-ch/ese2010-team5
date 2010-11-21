package models.impl;

import java.util.ArrayList;
import java.util.List;

import models.IComment;
import models.IUser;

/**
 * The Class Comment.
 */

public class Comment extends Post implements IComment {

	/** The comment belongs to a Post (either a Question or an Answer). */
	private Post post;
	private int likes, unlikes;
	private List<IUser> likeList;

	/**
	 * Instantiates a new comment.
	 * 
	 * @param owner
	 *            the owner
	 * @param content
	 *            the content
	 * @param quest
	 *            the post that corresponds to this comment
	 */
	public Comment(IUser owner, String content, Post quest) {
		super(owner, content);
		this.post = quest;
		this.post.addComment(this);
		this.likes = 0;
		this.unlikes = 0;
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

	public void setOwner(User owner) {
		this.owner = owner;
	}

	protected void doDelete() {
		/* remove me from my post(answer or question) */
		post.delComment(this);
	}

	/**
	 * @author simon
	 */
	public String getLikes() {
		return likes + " users like this comment and " + unlikes + " do not";
	}

	public boolean isInLikeList(User user) {
		return likeList.contains(user);
	}

	public void like(User user) {
		if (!(likeList.contains(user) || this.getOwner().equals(user))) {
			likes++;
			likeList.add(user);
		}
	}

	public void dislike(User user) {
		if (!(likeList.contains(user) || this.getOwner().equals(user))) {
			unlikes++;
			likeList.add(user);
		}
	}

}
