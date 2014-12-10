package goal.model.dao;

import java.io.IOException;

import goal.controller.service.analyzer.TextAnalyzer;
import goal.controller.service.indexer.FileIndexer;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class FileIndexing {
	
	private static final FileIndexing FILEINDEXING = new FileIndexing();
	private Directory indexDirectory;
	private FileIndexer fileIndexer;
	private TextAnalyzer textAnalyzer;
	
	private FileIndexing() {
		indexDirectory = new RAMDirectory();
		textAnalyzer = new TextAnalyzer(Version.LUCENE_41);
		fileIndexer = new FileIndexer(indexDirectory, textAnalyzer);
		try {
			fileIndexer.rebuildIndexes();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static FileIndexing getInstance() {
		return FILEINDEXING;
	}
	
	public Directory getIndexes() {
		return indexDirectory;
	}
	
	public boolean rebuildIndexes() {
		try {
			fileIndexer.rebuildIndexes();
			return true;
		}
		catch(IOException e) {
			return false;
		}
	}

}
