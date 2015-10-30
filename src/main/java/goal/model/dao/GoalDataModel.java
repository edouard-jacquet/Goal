package goal.model.dao;

import goal.model.bean.Preference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class GoalDataModel {
	
	private static final GoalDataModel INSTANCE = new GoalDataModel();
	private DataModel model;
	private PreferenceDAO preferenceDAO = new PreferenceDAO();
	
	private GoalDataModel(){
		this.model = new InMemoryDataModel();
		List<Preference> rList = preferenceDAO.getAllPreferences();
		Iterator<Preference> it = rList.iterator();
		while(it.hasNext()){
			Preference p = it.next();
			try {
				this.model.setPreference(p.getUser().getId(), p.getWord().getId(), p.getValue());
			} catch (TasteException e) {}
		}
	}
	
	public static GoalDataModel getInstance(){
		return INSTANCE;
	}
	
	public void setPreference(long user, long word, float value){
		try {
			this.model.setPreference(user, word, value);
		} catch (TasteException e) {}
	}
	
	public UserBasedRecommender getRecommender() throws TasteException{
		UserSimilarity similarity = new PearsonCorrelationSimilarity(this.model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, this.model);
		return new GenericUserBasedRecommender(this.model, neighborhood, similarity);
	}
	
	public Float getValue(long user, long word) throws TasteException{
		return this.model.getPreferenceValue(user, word);
	}
	
	private class InMemoryDataModel implements DataModel{
		
		private static final long serialVersionUID = 3358406492944012801L;
		private HashMap<Long,HashMap<Long,Pref>> PrefListByUser = new HashMap<Long,HashMap<Long,Pref>>();
		private HashMap<Long,HashMap<Long,Pref>> PrefListByItem = new HashMap<Long,HashMap<Long,Pref>>();

		@Override
		public void refresh(Collection<Refreshable> alreadyRefreshed) {
			System.out.println("InMemoryDataModel call : refresh");
		}

		@Override
		public LongPrimitiveIterator getUserIDs() throws TasteException {
			return this.new Iter(this.PrefListByUser);
		}

		@Override
		public PreferenceArray getPreferencesFromUser(long userID) throws TasteException {
			ArrayList<org.apache.mahout.cf.taste.model.Preference> prefs = new ArrayList<org.apache.mahout.cf.taste.model.Preference>();
			if(this.PrefListByUser.size() == 0)
				throw new NoSuchUserException(userID);
			HashMap<Long,Pref> items = this.PrefListByUser.get(userID);
			if(items == null)
				throw new NoSuchUserException(userID);
			Iterator<Entry<Long, Pref>> it = items.entrySet().iterator();
			while(it.hasNext()){
				Entry<Long, Pref> p = it.next();
				prefs.add(new GenericPreference(userID, p.getKey(), p.getValue().value));
			}
			PreferenceArray ret = new GenericUserPreferenceArray(prefs);
			ret.sortByItem();
			return ret;
		}

		@Override
		public FastIDSet getItemIDsFromUser(long userID) throws TasteException {
			if(this.PrefListByUser.size() == 0)
				throw new NoSuchUserException(userID);
			HashMap<Long,Pref> items = this.PrefListByUser.get(userID);
			if(items == null)
				throw new NoSuchUserException(userID);
			Iterator<Long> Ids = this.PrefListByUser.get(userID).keySet().iterator();
			long[] ids = new long[this.PrefListByUser.get(userID).size()];
			for(int i = 0; i < ids.length; i++)
				ids[i] = Ids.next();
			return new FastIDSet(ids);
		}

		@Override
		public LongPrimitiveIterator getItemIDs() throws TasteException {
			return this.new Iter(this.PrefListByItem);
		}

		@Override
		public PreferenceArray getPreferencesForItem(long itemID) throws TasteException {
			System.out.println("InMemoryDataModel call : getPreferencesForItem");
			throw new SecurityException();
			//return null;
		}

		@Override
		public Float getPreferenceValue(long userID, long itemID) throws TasteException {
			if(this.PrefListByUser.size() > 0){
				HashMap<Long,Pref> items = this.PrefListByUser.get(userID);
				if(items != null){
					if(items.size() > 0){
						Pref p = items.get(itemID);
						if(p != null){
							return p.value;
						}else
							return null;
					}else
						return null;
				}else
					return null;
				
			}else
				return null;
		}

		@Override
		public Long getPreferenceTime(long userID, long itemID) throws TasteException {
			return null;
		}

		@Override
		public int getNumItems() throws TasteException {
			return this.PrefListByItem.size();
		}

		@Override
		public int getNumUsers() throws TasteException {
			return this.PrefListByUser.size();
		}

		@Override
		public int getNumUsersWithPreferenceFor(long itemID) throws TasteException {
			System.out.println("InMemoryDataModel call : getNumUsersWithPreferenceFor");
			throw new SecurityException();
			//return 0;
		}

		@Override
		public int getNumUsersWithPreferenceFor(long itemID1, long itemID2) throws TasteException {
			System.out.println("InMemoryDataModel call : getNumUsersWithPreferenceFor");
			throw new SecurityException();
			//return 0;
		}

		@Override
		public void setPreference(long userID, long itemID, float value) throws TasteException {
			HashMap<Long,Pref> items = this.PrefListByUser.get(userID);
			if(items == null){
				items = new HashMap<Long,Pref>();
				this.PrefListByUser.put(userID, items);
			}
			HashMap<Long,Pref> users = this.PrefListByItem.get(itemID);
			if(users == null){
				users = new HashMap<Long,Pref>();
				this.PrefListByItem.put(itemID, users);
			}
			Pref p = items.get(itemID);
			if(p == null || p != users.get(userID)){
				p = new Pref(value);
				items.put(itemID, p);
				users.put(userID, p);
			}else
				p.value = value;
		}

		@Override
		public void removePreference(long userID, long itemID) throws TasteException {
			System.out.println("InMemoryDataModel call : removePreference");
			throw new SecurityException();
		}

		@Override
		public boolean hasPreferenceValues() {
			return true;
		}

		@Override
		public float getMaxPreference() {
			return Float.MAX_VALUE;
		}

		@Override
		public float getMinPreference() {
			return 0;
		}
		
		private class Iter implements LongPrimitiveIterator{

			private Iterator<Entry<Long, HashMap<Long, Pref>>> it;
			
			public Iter(HashMap<Long,HashMap<Long,Pref>> list){
				this.it = list.entrySet().iterator();
			}
			
			@Override
			public void skip(int n) {
				System.out.println("InMemoryDataModel.Iter call : skip");
				throw new SecurityException();
			}

			@Override
			public boolean hasNext() {
				return this.it.hasNext();
			}

			@Override
			public Long next() {
				return this.it.next().getKey();
			}

			@Override
			public long nextLong() {
				return this.next();
			}

			@Override
			public long peek() {
				System.out.println("InMemoryDataModel.Iter call : peek");
				throw new SecurityException();
				//return 0;
			}
			
		}
		
		private class Pref{
			public float value = 0;
			public Pref(float v){this.value = v;}
		}
	}
}
