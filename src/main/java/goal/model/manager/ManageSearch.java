package goal.model.manager;

import goal.Constante;
import goal.controller.service.analyzer.TextAnalyzer;
import goal.controller.service.suggestion.TextSuggestion;
import goal.model.bean.Result;
import goal.model.bean.User;
import goal.model.bean.Word;
import goal.model.dao.Connection;
import goal.model.dao.GoalDataModel;
import goal.model.dao.PreferenceDAO;
import goal.model.dao.WordDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;

public class ManageSearch {
	
	private List<Result> results = new ArrayList<Result>();
	private List<String> suggestions;
	private WordDAO wordDAO = new WordDAO();
	private PreferenceDAO preferenceDAO = new PreferenceDAO();

	public boolean search(String query, User user) {
		try {
			TextSuggestion textSuggestion = new TextSuggestion();	
			suggestions = textSuggestion.suggest(query);
			
List<Result> suggestedResults = null;
			
			
			if(user != null){
				ArrayList<String> suggestedTalkens = new ArrayList<String>();
				String suggestedQuery = "";
				TextAnalyzer textAnalyzer = new TextAnalyzer();
				QueryParser parser = new QueryParser(Constante.LUCENE_VERSION, "content", textAnalyzer);
				Query talkenQuery = parser.parse(query);
				Set<Term> terms = new TreeSet<Term>();
				talkenQuery.extractTerms(terms);
				Iterator<Term> it1 = terms.iterator();
				System.out.println("liste des tockens : ");
				while(it1.hasNext()){
					System.out.println("tocken " + it1.next().text());
				}
				it1 = terms.iterator();
				while(it1.hasNext()){
					Word word = wordDAO.getOrCreate(it1.next().text());
					Float value = null;
					try{
						value = GoalDataModel.getInstance().getValue(user.getId(), word.getId());
					}catch(Exception e){}
					if(value != null)
						value = (float) Math.log10( Math.pow((double) 10, (double) value) + (double) 1);
					else
						value = (float) 0;
					GoalDataModel.getInstance().setPreference(user.getId(), word.getId(), value);
					preferenceDAO.putValue(user, word, value);
				}
				UserBasedRecommender recommender = GoalDataModel.getInstance().getRecommender();
				List<RecommendedItem> recommendations = recommender.recommend(user.getId(), 3);
				Iterator<RecommendedItem> it2 = recommendations.iterator();
				while(it2.hasNext()){
					suggestedTalkens.add(wordDAO.getByID(it2.next().getItemID()).getCaption());
				}
				for(int i = 0; i < suggestedTalkens.size(); i++)
					suggestedQuery = suggestedQuery + " " + suggestedTalkens.get(i);
				
				
				if(suggestedTalkens.size() > 0){
					System.out.println("suggested Query : " + suggestedQuery);
					suggestedResults = new ArrayList<Result>();
					ManageFileSearch manageFileSearch2 = new ManageFileSearch();
					if(manageFileSearch2.search(suggestedQuery)) {
						for(Result result : manageFileSearch2.getResults()) {
							suggestedResults.add(result);
						}
					}
					
					
					ManageWebSearch manageWebSearch2 = new ManageWebSearch();
					if(manageWebSearch2.search(suggestedQuery)) {
						for(Result result : manageWebSearch2.getResults()) {
							suggestedResults.add(result);
						}
					}
					
				}else{
					System.out.println("no recomendations !!");
				}
			}
			
			ManageFileSearch manageFileSearch = new ManageFileSearch();
			if(manageFileSearch.search(query)) {
				for(Result result : manageFileSearch.getResults()) {
					results.add(result);
				}
			}
			
			ManageWebSearch manageWebSearch = new ManageWebSearch();
			if(manageWebSearch.search(query)) {
				for(Result result : manageWebSearch.getResults()) {
					results.add(result);
				}
			}
			
			ManageDwhSearch manageDwhSearch = new ManageDwhSearch();
			if(manageDwhSearch.search(query)) {
				for(Result result : manageDwhSearch.getResults()) {
					results.add(result);
				}
			}
			
			if(user != null && suggestedResults != null){
				for(int i = 0; i < suggestedResults.size(); i++){
					for(int j = 0; j < results.size(); j++){
						if(suggestedResults.get(i).sameDocumentAs(results.get(j))){
							double p1 = results.get(j).getScore();
							double p2 = suggestedResults.get(i).getScore();
							results.get(j).setScore(((1-p1)*p2)+p1);
							break;
						}
					}
				}
			}
			
			Collections.sort(results, Collections.reverseOrder());
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Result> getResults() {
		return results;
	}
	
	public List<String> getSuggestions() {
		return suggestions;
	}
	
}
