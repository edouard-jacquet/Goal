package goal.model.dao;

import java.util.List;

import goal.model.bean.DwhResult;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class DwhDAO {
	
	private EntityManager entityManager = Connection.getInstance().getConnection();
	
	public List<DwhResult> getAll() {
		try {
			return (List<DwhResult>)entityManager.createQuery("SELECT d FROM DwhResult d").getResultList();
		}
		catch(NoResultException e) {
			return null;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public DwhResult getById(long id) {
		try {
			return (DwhResult)entityManager.createQuery("SELECT d FROM DwhResult d WHERE d.id = :id")
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
	
	public DwhResult getByTitle(String title) {
		try {
			return (DwhResult)entityManager.createQuery("SELECT d FROM DwhResult d WHERE d.title = :title")
			.setParameter("title", title)
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
