package controllers;

import java.util.Collection;

import models.Question;
import models.Tag;

import utils.QaDB;

public class Tags extends Auth {

    public static void list() {
    	Collection<Tag> tags = QaDB.findAllTags();
        render(tags);
    }
    
    public static void listQuestionsTaggedWith(Tag tag){
    	Collection<Question> questions = QaDB.findAllQuestionsTaggedWith(tag);
    	render(questions);
    }

}
