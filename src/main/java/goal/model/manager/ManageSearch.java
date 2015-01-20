package goal.model.manager;

import goal.model.bean.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageSearch {
	
	private List<Result> results = new ArrayList<Result>();
	
	public boolean search(String query) {
		try {
			ManageFileSearch manageFileSearch = new ManageFileSearch();
			manageFileSearch.search(query);
			for(Result result : manageFileSearch.getResults()) {
				results.add(result);
			}
			ManageWebSearch manageWebSearch = new ManageWebSearch();
			manageWebSearch.search(query);
			for(Result result : manageWebSearch.getResults()) {
				results.add(result);
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
	
}
