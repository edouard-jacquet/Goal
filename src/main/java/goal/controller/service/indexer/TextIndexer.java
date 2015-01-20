package goal.controller.service.indexer;

import goal.Constante;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

public class TextIndexer {

	private Analyzer analyzer;
	private Directory indexDir;
	private IndexWriter indexWriter = null;
	
	public TextIndexer(Directory indexDir, Analyzer analyzer) {
		this.analyzer = analyzer;
		this.indexDir = indexDir;
	}
	
	public void index(String title, String content) throws IOException {
		IndexWriter writer = this.getIndexWriter();
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("content", content, Field.Store.NO));
		writer.addDocument(doc);
	}
	
	public void closeIndexWriter() throws IOException {
		if (indexWriter != null) {
			indexWriter.close();
		}
	}
	
	private IndexWriter getIndexWriter() throws IOException {
		if (indexWriter == null) {
			IndexWriterConfig config = new IndexWriterConfig(Constante.LUCENE_VERSION, analyzer);
			indexWriter = new IndexWriter(indexDir, config);
		}
		return indexWriter;
	}
	
}
