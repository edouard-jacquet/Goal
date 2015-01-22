package goal.model.manager;

import goal.controller.service.suggestion.TextSuggestion;
import goal.model.bean.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageSearch {
	
	private List<Result> results = new ArrayList<Result>();
	private List<String> suggestions;

	public boolean search(String query) {
		try {
			TextSuggestion textSuggestion = new TextSuggestion();	
			suggestions = textSuggestion.suggest(query);
			
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
