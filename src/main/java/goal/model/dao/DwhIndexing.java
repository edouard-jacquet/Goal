package goal.model.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import goal.controller.service.analyzer.TextAnalyzer;
import goal.controller.service.indexer.TextIndexer;
import goal.model.bean.DwhResult;
import goal.model.bean.FileResult;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class DwhIndexing {
	
	private static final DwhIndexing DWHINDEXING = new DwhIndexing();
	private Directory indexDirectory;
	private TextIndexer textIndexer;
	private TextAnalyzer textAnalyzer;
	
	private DwhIndexing() {
		indexDirectory = new RAMDirectory();
		textAnalyzer = new TextAnalyzer();
		textIndexer = new TextIndexer(indexDirectory, textAnalyzer);
		this.rebuildIndexes();
	}
	
	public static DwhIndexing getInstance() {
		return DWHINDEXING;
	}
	
	public Directory getIndexes() {
		return indexDirectory;
	}
	
	public boolean rebuildIndexes() {
		try {
			DwhDAO dwhDAO = new DwhDAO();
			List<DwhResult> results = dwhDAO.getAll();
			if(results != null){
				for(DwhResult result : results){
					textIndexer.index(result.getTitle(), result.getSummarize());
				}
			}
			textIndexer.closeIndexWriter();
			return true;
		}
		catch(IOException e) {
			return false;
		}
	}

}
