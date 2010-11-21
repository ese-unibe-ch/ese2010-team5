package controllers;

import models.IComment;
import models.IQuestion;
import models.impl.Answer;
import models.impl.Comment;
import models.impl.Post;
import models.impl.Question;
import play.Logger;
import utils.QaDB;

/**
 * The Class Comments.
 */
public class Comments extends Posts {

	/**
	 * Edits the comment.
	 * 
	 * @param id
	 *            the id
	 */
	public static void edit(long id) {

		IComment c = QaDB.findCommentById(id);

		if (c == null) {
			flash("error", "could not find Comment with id " + id);
			redirect("/");
		}
		render(c);

	}

	/**
	 * Sets the content.
	 * 
	 * @param id
	 *            the id
	 * @param content
	 *            the content
	 */
	public static void setContent(long id, String content) {
		Logger.debug("Setting new content: \"" + content + "\"");

		Comment cA = QaDB.findCommentById(id);

		if (cA == null) {
			flash("error", "could not find answer with id " + id);
			redirect("/");
		}
		cA.setContent(content);
		flash.put("info", "Content of answer " + id + " changed");

		Post a = cA.getPost();

		if (a instanceof Question)
			Questions.view(a.getId());
		else if (a instanceof Answer) {
			Answer b = (Answer) a;
			IQuestion q = b.getQuestion();
			Questions.view(q.getId());
		}

	}

	/**
	 * @author simon
	 */
	public static void LikeOrUnlike(long id, boolean likesComment) {
		Comment comment = QaDB.findCommentById(id);
		if (likesComment) {
			comment.like(user);
		} else {
			comment.dislike(user);
		}
		renderText(comment.getLikes());
	}

}
