package goal.model.manager;

import goal.controller.service.analyzer.TextAnalyzer;
import goal.controller.service.http.Http;
import goal.controller.service.indexer.TextIndexer;
import goal.controller.service.searcher.TextSearcher;
import goal.model.bean.WebResult;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.json.JSONArray;
import org.json.JSONObject;

public class ManageWebSearch {

	//Constantes
	private final String HTTP_HOST = "http://dbpedia.org/sparql";
	private final String HTTP_URI = "default-graph-uri=http://dbpedia.org";
	private final String HTTP_QUERY_REQUEST = "prefix dbpedia-owl: <http://dbpedia.org/ontology/> SELECT * WHERE {";
	private final String[] HTTP_QUERY_CLASS = {"SoccerLeague", "SoccerClub", "SoccerPlayer", "SoccerManager", "SoccerTournament", "SoccerClubSeason", "SoccerLeagueSeason"};
	private final String HTTP_QUERY_FILTER = " FILTER(langMatches(lang(?title), 'EN') && langMatches(lang(?abstract), 'EN'))";
	private final String HTTP_QUERY_LIMIT = " OFFSET 0 LIMIT 100";
	// HTML=text/html ; json=application/sparql-results+json
	private final String HTTP_FORMAT = "application/sparql-results+json";
	private final String HTTP_TIMEOUT = "5000";
	private final String HTTP_DEBUG = "on";
	//Resultats
	private List<WebResult> resources = new ArrayList<WebResult>();
	private List<WebResult> results = new ArrayList<WebResult>();
	
	public boolean search(String query) {
		//Preparation de la requete sparql
		String prepared = query.replace("\'", "\\'").replace("\"", "\\\"");
		String QUERY = HTTP_QUERY_REQUEST;
		for(int i = 0 ; i < HTTP_QUERY_CLASS.length ; i++) {
			QUERY += "{SELECT distinct ?title, ?abstract, ?resource WHERE {?resource rdf:type <http://dbpedia.org/ontology/" + HTTP_QUERY_CLASS[i] + "> . ?resource dbpedia-owl:abstract ?abstract . ?resource rdfs:label ?title}}";
			if((i+1) < HTTP_QUERY_CLASS.length) {
				QUERY += " UNION ";
			}
		}
		QUERY += " FILTER(contains(lcase(?title), '" + prepared + "') || contains(lcase(?abstract), '" + prepared + "'))";
		QUERY += HTTP_QUERY_FILTER + "}" + HTTP_QUERY_LIMIT;
		
		try {
			//Envoi de la requete
			Http http = new Http();
			String response = http.executeGET(
				this.HTTP_HOST + "?" + this.HTTP_URI
				+ "&query=" + URLEncoder.encode(QUERY, "UTF-8")
				+ "&format=" + URLEncoder.encode(this.HTTP_FORMAT, "UTF-8")
				+ "&timeout=" + URLEncoder.encode(this.HTTP_TIMEOUT, "UTF-8")
				+ "&debug=" + URLEncoder.encode(this.HTTP_DEBUG, "UTF-8")
			);
			
			//Preparation de la reponse
			this.parseResponse(response);
			if(resources.size() > 0) {
				Directory indexDirectory = new RAMDirectory();
				TextAnalyzer textAnalyzer = new TextAnalyzer();
				TextIndexer textIndexer = new TextIndexer(indexDirectory, textAnalyzer);
				for(WebResult resource : resources) {
					textIndexer.index(resource.getTitle(), resource.getSummarize());
				}
				textIndexer.closeIndexWriter();
				TextSearcher textSearcher = new TextSearcher(indexDirectory, textAnalyzer);
				this.scoring(textSearcher, query);
			}
			return true;
		}
		catch (IOException | ParseException | NullPointerException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void parseResponse(String response) {
		JSONObject json = new JSONObject(response);
		JSONArray resources = json.getJSONObject("results").getJSONArray("bindings");
		
		for(int i = 0 ; i < resources.length() ; i++) {
			WebResult result = new WebResult();
			result.setTitle(resources.getJSONObject(i).getJSONObject("title").getString("value"));
			result.setSummarize(resources.getJSONObject(i).getJSONObject("abstract").getString("value"));
			result.setLocation("resource?type=web&name=" + result.getTitle() + "&url=" + resources.getJSONObject(i).getJSONObject("resource").getString("value"));
			this.resources.add(result);
		}
	}
	
	public void scoring(TextSearcher searcher, String query) 
    throws IOException, ParseException {
    	List<ScoreDoc> scoreDocList = Arrays.asList(searcher.performSearch(query));
    	int docId;
    	Document document;
    	for(ScoreDoc scoreDoc : scoreDocList) {
			docId = scoreDoc.doc;
			document = searcher.getDoc(docId);
			for(WebResult resource : resources) {
				if(resource.getTitle().equals(document.get("title"))) {
					resource.setScore(scoreDoc.score);
					results.add(resource);
					break;
				}
			}
			
    	}
    }
	
	public List<WebResult> getResults() {
		return results;
	}
	
}
