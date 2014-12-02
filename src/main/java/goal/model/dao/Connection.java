package goal.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connection {
	
	private static final Connection INSTANCE = new Connection();
	private EntityManager entityManager;
	
	private Connection() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DB");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	public static Connection getInstance() {
		return INSTANCE;
	}
	
	public EntityManager getConnection() {
		return entityManager;
	}
	
}
