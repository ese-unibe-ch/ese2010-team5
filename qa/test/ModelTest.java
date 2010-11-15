import org.junit.*;
import java.util.*;
import play.test.*;
import utils.QaDB;
import models.*;
import models.mock.MockUser;

public class ModelTest extends UnitTest {

    @Test
    public void testAllModelContructors() {
    	
    	IUser u = new MockUser("tester");    	
    	assertNotNull(u);    	
    	
    	Question q = new Question(u, "test"); 
    	assertNotNull(q);
    	assertNotNull(new Answer(u,"testanswer",q));
    	assertNotNull(new Vote(1, u));   	
    	
    }
    
    @Test
    public void testStaticFindByMethods(){
       	
    	
    	//user
    	User u = QaDB.findUserByName("user-1");
    	assertNotNull(u);
    	assertEquals(QaDB.findUserById(u.getId()),u);
    	
    	//answer
    	
    	
    	
    }
    
    
    @Test
    public void testRateFuntions(){
    	
    	IUser user1 = QaDB.findUserByName("user-1");
    	assertNotNull(user1);
    	IUser user2 = QaDB.findUserByName("user-2");
    	assertNotNull(user2);
    	
    	Post post = new Question(user1, "test");
    	
    	assertEquals(0, post.getVotation());
    	//owner can not rate
    	post.rateUp(user1);
    	assertEquals(0, post.getVotation());
    	//other user
    	post.rateUp(user2);
    	assertEquals(1, post.getVotation());
    	//only once
    	post.rateUp(user2);
    	assertEquals(1, post.getVotation());
    	post.rateDown(user2);
    	assertEquals(-1, post.getVotation());
    	
    	
    }
    
    

}
