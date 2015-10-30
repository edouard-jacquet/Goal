package goal.model.dao;

import goal.model.bean.Word;

import javax.persistence.EntityManager;

public class WordDAO {

	private EntityManager em = Connection.getInstance().getConnection();
	
	public Word getOrCreate(String caption){
		Word ret = null;
		try{
			ret = (Word) em.createQuery("SELECT w FROM Word w WHERE w.caption = :caption").setParameter("caption", caption).getSingleResult();
		}catch(Exception e){}
		if(ret == null){
			ret = new Word();
			ret.setCaption(caption);
			try {
				em.getTransaction().begin();
				em.persist(ret);
				em.getTransaction().commit();
			}
			catch(Exception e) {
				em.getTransaction().begin();
				em.getTransaction().rollback();
			}
		}
		return ret;
	}
	
	public Word getByID(long id){
		Word ret = null;
		try{
			ret = (Word) em.createQuery("SELECT w FROM Word w WHERE w.id = :id").setParameter("id", id).getSingleResult();
		}catch(Exception e){}
		return ret;
	}
	
}
