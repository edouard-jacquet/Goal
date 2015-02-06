package goal.model.dao;

import goal.model.bean.Preference;
import goal.model.bean.User;
import goal.model.bean.Word;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

public class PreferenceDAO {

	private EntityManager em = Connection.getInstance().getConnection();
	
	public List<Preference> getAllPreferences(){
		List<Preference> ret = new ArrayList<Preference>();
		try{
			@SuppressWarnings("unchecked")
			List<Preference> res = em.createQuery("SELECT p FROM Preference p").getResultList();
			ret = res;
		}catch(Exception e){}
		return ret;
	}
	
	public void putValue(User user, Word word , float value){
		Preference preference = null;
		try{
			preference = (Preference) em.createQuery("SELECT p FROM Preference p WHERE p.user = :user AND p.word = :word").setParameter("user", user).setParameter("word", word).getSingleResult();
		}catch(Exception e){}
		if(preference == null){
			preference = new Preference();
			preference.setUser(user);
			preference.setWord(word);
			preference.setValue(0);
			try {
				em.getTransaction().begin();
				em.persist(preference);
				em.getTransaction().commit();
			}
			catch(Exception e) {
				System.out.println(e);
				em.getTransaction().begin();
				em.getTransaction().rollback();
			}
		}
		
		try {
			em.getTransaction().begin();
			preference.setValue(value);
			em.getTransaction().commit();
		}
		catch(Exception e) {
			em.getTransaction().begin();
			em.getTransaction().rollback();
		}
	}
}
