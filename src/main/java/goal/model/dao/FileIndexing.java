package goal.model.dao;

import java.io.File;
import java.io.IOException;

import goal.controller.service.analyzer.TextAnalyzer;
import goal.controller.service.indexer.TextIndexer;
import goal.model.bean.FileResult;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class FileIndexing {
	
	private static final FileIndexing FILEINDEXING = new FileIndexing();
	private Directory indexDirectory;
	private TextIndexer textIndexer;
	private TextAnalyzer textAnalyzer;
	
	private FileIndexing() {
		indexDirectory = new RAMDirectory();
		textAnalyzer = new TextAnalyzer();
		textIndexer = new TextIndexer(indexDirectory, textAnalyzer);
		this.rebuildIndexes();
	}
	
	public static FileIndexing getInstance() {
		return FILEINDEXING;
	}
	
	public Directory getIndexes() {
		return indexDirectory;
	}
	
	public boolean rebuildIndexes() {
		try {
			FileDAO fileDAO = new FileDAO();
			File folder = new File(fileDAO.getFolder());
			if(folder.list() != null){
				for(String file : folder.list()){
					if(file.endsWith(".txt") || file.endsWith(".TXT")){
						FileResult result = fileDAO.getFile(file.substring(0, (file.length() - 4)));
						textIndexer.index(result.getTitle(), result.getContent());
					}
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
