package models.mock;

import java.util.Date;
import java.util.List;

import models.*;
import models.impl.Post;
import models.impl.User;

/**
 * The Class MockUser used for tests.
 */
public class MockUser implements IUser {

	/** The username. */
	private String username;

	/**
	 * Instantiates a new mock user.
	 *
	 * @param username the username
	 */
	public MockUser(String username) {
		this.username = new String(username);
	}

	public void registerPost(Post post) {
	}
	
  public void addNotification(INotification notif) {}

	
  public void delete() {}

	
  public Date getBirthDate() {
	  return null;
  }
	
  public String getEmail() {	 
	  return null;
  }
	
  public String getHomepage() {	  
	  return null;
  }
  public long getId() {	  
	  return 0;
  }
  public String getName() {	  
	  return null;
  }	
  public List<INotification> getNotifications() {	  
	  return null;
  }	
  public String getUsername() {	  
	  return null;
  }	
  public void setBirthDate(Date date) {}

	
  public void setEmail(String mail) {}
	
  public void setHomepage(String homepage) {}

  public void setUsername(String username) {}
	
  public void unregister(Post post) {}

	
  public void delNotification(INotification notif) {}

	
  public void addSubscription(IQuestion quest) {}

  public List<IQuestion> getSubscriptions() {
	  return null;
  }	
  public void remSubscription(IQuestion quest) {}

  
  public String getPassword() {
    return "dummyPassword";
  }
	
  public boolean isAdmin() {
	  // TODO Auto-generated method stub
	  return false;
  }
  
  public void setAdmin(boolean isAdmin){  	
  }

	public boolean canBlockUser() {
		// TODO Auto-generated method stub
		return false;
	}

	public void blockUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public boolean isBlocked() {
		// TODO Auto-generated method stub
		return false;
	}

}
