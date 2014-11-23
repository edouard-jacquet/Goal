package goal.controller.service.indexer;

import goal.model.bean.FileResult;
import goal.model.dao.FileDAO;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

public class FileIndexer {
	
	private Analyzer analyzer;
	private Directory indexDir;
	private IndexWriter indexWriter = null;
	
	public FileIndexer(Directory indexDir, Analyzer analyzer) {
		this.analyzer = analyzer;
		this.indexDir = indexDir;
	}
	
	public IndexWriter getIndexWriter(boolean create) throws IOException {
		if (indexWriter == null) {
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, analyzer);
			indexWriter = new IndexWriter(indexDir, config);
		}
		return indexWriter;
	}
	
	public void closeIndexWriter() throws IOException {
		if (indexWriter != null) {
			indexWriter.close();
		}
	}
	
	public void indexFile(FileResult file) throws IOException {
		IndexWriter writer = getIndexWriter(false);
		Document doc = new Document();
		doc.add(new TextField("title", file.getTitle(), Field.Store.YES));
		doc.add(new TextField("content", file.getContent(), Field.Store.NO));
		writer.addDocument(doc);
	}
	
	public void rebuildIndexes() throws IOException {
		getIndexWriter(true);
		FileDAO fileDAO = new FileDAO();
		File folder = new File(fileDAO.getFolder());
		if(folder.list() != null){
			for(String file : folder.list()){
				if(file.endsWith(".txt") || file.endsWith(".TXT")){
					this.indexFile(fileDAO.getFile(file.substring(0, (file.length() - 4))));
				}
			}
		}
		// Else ?
		closeIndexWriter();
	}
	
}
