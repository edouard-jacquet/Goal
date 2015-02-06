package goal.controller.service.suggestion;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import goal.Constante;
import goal.controller.service.indexer.TextIndexer;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.LuceneLevenshteinDistance;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class TextSuggestion {
	
	public static final String DIRECTORY_SUGGESTION = "storage/resource/suggestion/";
	private SpellChecker spellChecker;
	
	public TextSuggestion() throws IOException {
		String path = TextIndexer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(0, path.indexOf("WEB-INF"));
		File dir = new File(path + DIRECTORY_SUGGESTION + "spellchecker");
		Directory directory = FSDirectory.open(dir);
		spellChecker = new SpellChecker(directory, new LuceneLevenshteinDistance());
		spellChecker.setAccuracy(0.8f);
		PlainTextDictionary dictionary = new PlainTextDictionary(new File(path + DIRECTORY_SUGGESTION + "dictionary.txt"));
		IndexWriterConfig config = new IndexWriterConfig(Constante.LUCENE_VERSION, null);
		spellChecker.indexDictionary(dictionary, config, false);
	}
	
	public List<String> suggest(String query) throws IOException {
		return Arrays.asList(spellChecker.suggestSimilar(query, 5));
	}

}
