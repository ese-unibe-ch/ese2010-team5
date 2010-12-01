package models.impl;

import java.util.LinkedList;
import java.util.List;

import utils.QaDB;

/**
 * The Class Tag.
 */
public class Tag {

	/** The name. */
	private String name;

	/** The related questions. */
	private List<Question> relatedQuestions = new LinkedList<Question>();

	/** The id. */
	private long id;

	/** The id counter. */
	private static long idCounter = 1;

	/**
	 * Instantiates a new tag.
	 * 
	 * @param name
	 *            the name
	 * @param quest
	 *            the related question
	 */
	public Tag(String name, Question quest) {
		this.name = name;
		this.relatedQuestions.add(quest);
		this.id = idCounter++;
	}

	public String toString() {
		return this.name;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the tag count
	 * 
	 * @return the number of questions tagged with this Tag.
	 */
	public int count() {
		return this.relatedQuestions.size();
	}
	
	/**
	 * Register the question as tagged with this Tag.
	 * 
	 * @param the question tagged with Tag
	 */
	public void registerQuestion(Question question) {
		this.relatedQuestions.add(question);
	}
	
	/**
	 * Unregisters the question from being tagged with Tag.
	 * Used when deleting a question.
	 * 
	 * @param the question from which the Tag is removed
	 */
	public void unregisterQuestion(Question question) {
		this.relatedQuestions.remove(question);
	}

	public static Tag findOrCreateTagByName(String tagName, Question question) {
		Tag tag = QaDB.findTagByName(tagName);
		if (tag == null){
			tag = new Tag(tagName, question);
			QaDB.addTag(tag);
		}
		else{
			tag.registerQuestion(question);
		}
		return tag;
	}
}
