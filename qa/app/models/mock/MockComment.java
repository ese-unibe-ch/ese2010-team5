package models.mock;

import models.*;
import models.impl.Post;

/**
 * The Class MockComment used for tests.
 */
public class MockComment extends Post implements IComment {
	
	/** The mockcomment belongs to a Post (either a Question or an Answer). */
	private Post post;
	
	/**
	 * Instantiates a new comment.
	 *
	 * @param owner the owner
	 * @param content the content
	 * @param quest the post that corresponds to this comment
	 */
	public MockComment(IUser owner, String content, Post quest) {
		super(owner, content);
		this.post = quest;
		this.post.addComment(this);
	}

	protected void doDelete() {
	}
}
