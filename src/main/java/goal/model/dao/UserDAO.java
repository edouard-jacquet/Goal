package goal.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import goal.model.bean.User;

public class UserDAO {
	
	private EntityManager entityManager = Connection.getInstance().getConnection();
	
	public boolean addUser(User user) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(user);
			entityManager.getTransaction().commit();
			return true;
		}
		catch(Exception e) {
			entityManager.getTransaction().begin();
			entityManager.getTransaction().rollback();
			return false;
		}
	}
	
	public boolean UserNotExist(String login) {
		try {
			entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login")
			.setParameter("login", login)
			.getSingleResult();
			return false;
		}
		catch(NoResultException e) {
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public User getUserByConnection(String login, String password) {
		try {
			return (User)entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password = :password")
			.setParameter("login", login)
			.setParameter("password", password)
			.getSingleResult();
		}
		catch(NoResultException e) {
			return null;
		}
		catch(Exception e) {
			return null;
		}
	}

}
