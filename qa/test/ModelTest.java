import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class ModelTest extends UnitTest {

    @Test
    public void testAllModelContructors() {
    	
    	User u = new User("tester");    	
    	assertNotNull(u);    	
    	
    	assertNotNull(new Question(u, "test"));
    	assertNotNull(new Answer(u,"testanswer"));
    	assertNotNull(new Vote(1, u));   	
    	
    }
    
    @Test
    public void testStaticFindByMethods(){
       	
    	
    	//user
    	User u = User.findByName("user-1");
    	assertNotNull(u);
    	assertEquals(User.findById(u.getId()),u);
    	
    	//answer
    	
    	
    	
    }
    
    
    @Test
    public void testRateFuntions(){
    	
    	User user1 = User.findByName("user-1");
    	assertNotNull(user1);
    	User user2 = User.findByName("user-2");
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
