package models;

import java.util.LinkedList;
import java.util.List;

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

	public int count() {
		return this.relatedQuestions.size();
	}

	public void registerQuestion(Question question) {
		this.relatedQuestions.add(question);
	}

	public void unregisterQuestion(Question question) {
		this.relatedQuestions.remove(question);
	}

}
