package jobs;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import models.Answer;
import models.IQuestion;
import models.IUser;
import models.Post;
import models.Question;
import models.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import play.cache.Cache;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import utils.QaDB;

@OnApplicationStart
public class BootStrap extends Job {
    
		private static final Log log = LogFactory.getLog(BootStrap.class);
		private static Random random = new Random();
		
		private static void randomVote(Post post, IUser user){
			
			boolean up = (random.nextInt(2) == 0 ? true : false);
  		if(up)
  			post.rateUp(user);
  		else
  			post.rateDown(user); 
			
			
		}
	
		
    public void doJob() {
    	
    	QaDB.addUser(QaDB.ANONYMOUS);
    	
    	log.info("fill Model with test-data");
    	
    	int users = 5;
    	int questionsPerUser = 5;
    	
    	//create some users
    	for(int i=1;  i <= users ; i++){
    		User u = QaDB.addUser(new User("user-"+i));
    		//5 questions per user
    		for(int j = 1; j <= questionsPerUser; j++){

    			String[] tags = {"Tag"+i, "Tag"+(i+1)};

        		IQuestion q = QaDB.addQuestion(new Question(u, "Title of question "+j+
        				", from "+u.getName(), "content of question "+j+", from "+u.getName(), tags));

        	}	
    	}
    	
    	Collection<Question> questions = QaDB.findAllQuestions();
    	
    	//an answer for every second question, from a random user
    	int i = 0;
    	for(Question q : questions){    		    		
    		
    		if( i++ % 2 == 0){
    			IUser u = QaDB.findUserById((random.nextInt(users)+1));
    			Answer a = QaDB.addAnswer(new Answer(u, "Answer to question "+q.getId(),q));    			
    			randomVote(a, u);
    			randomVote(q, u);
    		}
    	}
        
    }
    
}
