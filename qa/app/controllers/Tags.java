package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import models.IQuestion;
import models.impl.Question;
import models.impl.Tag;

import utils.NoLogin;
import utils.QaDB;

/**
 * The Class Tags.
 */
public class Tags extends Auth {

	/**
	 * List the tags.
	 */
	@NoLogin
	public static void list() {
		Collection<Tag> tags = QaDB.findAllTags();
		render(tags);
	}

	/**
	 * List questions tagged with.
	 * 
	 * @param tag
	 */
	@NoLogin
	public static void listQuestionsTaggedWith(String tag) {
		Collection<Question> questions = QaDB.findAllQuestionsTaggedWith(tag);
		render(questions);
	}

}
