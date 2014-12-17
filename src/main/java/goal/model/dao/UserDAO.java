package goal.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import goal.model.bean.User;

public class UserDAO {
	
	private EntityManager entityManager = Connection.getInstance().getConnection();
	
	public boolean add(User user) {
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
	
	public boolean notExist(String login) {
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
	
	public User getByConnection(String login, String password) {
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
	
	public User getById(long id) {
		try {
			return (User)entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id")
			.setParameter("id", id)
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
