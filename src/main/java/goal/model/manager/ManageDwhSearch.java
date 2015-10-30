package goal.model.manager;

import goal.controller.service.analyzer.TextAnalyzer;
import goal.controller.service.searcher.TextSearcher;
import goal.model.bean.DwhResult;
import goal.model.dao.DwhDAO;
import goal.model.dao.DwhIndexing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;

public class ManageDwhSearch {
	
	private List<DwhResult> results = new ArrayList<DwhResult>();
	
	public boolean search(String query) {
		try {
			TextAnalyzer textAnalyzer = new TextAnalyzer();
			DwhIndexing dwhIndexing = DwhIndexing.getInstance();
			TextSearcher textSearcher = new TextSearcher(dwhIndexing.getIndexes(), textAnalyzer);
			this.scoring(textSearcher, query);
			return true;
		}
		catch (IOException | ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void scoring(TextSearcher searcher, String query) 
    throws IOException, ParseException {
    	List<ScoreDoc> scoreDocList = Arrays.asList(searcher.performSearch(query));
    	int docId;
    	Document document;
    	DwhDAO dwhDAO = new DwhDAO();
    	for(ScoreDoc scoreDoc : scoreDocList) {
			docId = scoreDoc.doc;
			document = searcher.getDoc(docId);
			DwhResult result = dwhDAO.getByTitle(document.get("title"));
			result.setScore(scoreDoc.score);
			results.add(result);
    	}
    }
	
	public List<DwhResult> getResults() {
		return results;
	}

}
