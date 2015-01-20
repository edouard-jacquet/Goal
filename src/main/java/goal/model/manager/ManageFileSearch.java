package goal.model.manager;

import goal.controller.service.analyzer.TextAnalyzer;
import goal.controller.service.searcher.TextSearcher;
import goal.model.bean.FileResult;
import goal.model.dao.FileDAO;
import goal.model.dao.FileIndexing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;

public class ManageFileSearch {
	
	private List<FileResult> results = new ArrayList<FileResult>();
	
	public boolean search(String query) {
		try {
			TextAnalyzer textAnalyzer = new TextAnalyzer();
			FileIndexing fileIndexing = FileIndexing.getInstance();
			TextSearcher textSearcher = new TextSearcher(fileIndexing.getIndexes(), textAnalyzer);
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
    	FileDAO fileDAO = new FileDAO();
    	for(ScoreDoc scoreDoc : scoreDocList) {
			docId = scoreDoc.doc;
			document = searcher.getDoc(docId);
			FileResult result = fileDAO.getFile(document.get("title"));
			result.setScore(scoreDoc.score);
			results.add(result);
    	}
    }
	
	public List<FileResult> getResults() {
		return results;
	}

}
