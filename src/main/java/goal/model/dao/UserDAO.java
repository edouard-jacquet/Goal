package goal.model.dao;

import goal.model.bean.User;

public class UserDAO {
	
	public boolean addUser(String login, String password) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		return true;
	}
	
	public User getUser(String login, String password) {
		User user = new User();
		user.setLogin("admin");
		user.setPassword("admin");
		return user;
	}

}
