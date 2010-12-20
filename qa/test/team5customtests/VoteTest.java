package team5customtests;

import static org.junit.Assert.*;
import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;
import models.impl.*;

import org.junit.Before;
import org.junit.*;

public class VoteTest extends UnitTest{
	IUser use;
	Vote vote;

	@Before
	public void setUp() throws Exception {
		use = new User("Hans","");
		vote = new Vote(1,use);
	}
	
	@Test
	public void shouldReturnVoteUser(){
		assertEquals(use,vote.getUser());
	}
	
	@Test
	public void shouldReturnValuePlusOne(){
		assertEquals(1,vote.getValue());
	}
	
	@Test
	public void shouldProperlySetValue(){
		vote.setValue(-1);
		assertEquals(-1,vote.getValue());
	}
	
	@Test
	public void shouldGetIdGreaterThanOne(){
		assertTrue(vote.getId()>1);
	}

}
