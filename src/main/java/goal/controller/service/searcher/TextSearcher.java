package goal.controller.service.searcher;

import goal.Constante;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;

public class TextSearcher {

	private IndexSearcher searcher = null;
	private QueryParser parser = null;
	int hitsPerPage = 10;

	public TextSearcher(Directory indexDir, Analyzer analyzer) throws IOException {
		IndexReader reader = DirectoryReader.open(indexDir);
		searcher = new IndexSearcher(reader);
		parser = new QueryParser(Constante.LUCENE_VERSION, "content", analyzer);
	}

	public ScoreDoc[] performSearch(String queryString)
	throws IOException, ParseException {
		Query query = parser.parse(queryString);        
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		return hits;
	}

	public Document getDoc (int docId)
		throws IOException {
		Document doc = searcher.doc(docId);
		return doc;
	}
	
}
