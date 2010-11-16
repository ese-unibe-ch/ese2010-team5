package models;

import java.util.Date;
import java.util.List;

import models.impl.Post;

/**
 * The Interface IUser.
 */
public interface IUser {

	
	
	public void delete();
	public void addNotification(INotification notif);
	public void delNotification(INotification notif);
	public Date getBirthDate();
	public String getEmail();
	public String getHomepage();
	public long getId();
	public String getName();
	public List<INotification> getNotifications();
	public String getUsername();	
	public void registerPost(Post post);
	public void setBirthDate(Date date);
	public void setEmail(String mail);
	public void setHomepage(String homepage);
	public void setUsername(String username);
	public void unregister(Post post);
	
	 
	
	
	

}