package goal.model.manager;

import goal.Constante;
import goal.controller.service.indexer.TextIndexer;
import goal.model.bean.Result;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class ManageSearch {
	
	private List<Result> results = new ArrayList<Result>();
	private SpellChecker spellChecker;
	private List<String> suggestions;
	
	public static final String DIRECTORY_SUGGESTION = "storage/resource/suggestion/";
	
	public ManageSearch() throws IOException {
		String path = TextIndexer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(0, path.indexOf("WEB-INF"));
		
		File dir = new File(path + DIRECTORY_SUGGESTION + "spellchecker");
		Directory directory = FSDirectory.open(dir);
		spellChecker = new SpellChecker(directory);
		PlainTextDictionary dictionary = new PlainTextDictionary(new File(path + DIRECTORY_SUGGESTION + "dictionary.txt"));
		IndexWriterConfig config = new IndexWriterConfig(Constante.LUCENE_VERSION, null);
		spellChecker.indexDictionary(dictionary, config, false);
	}
	
	public boolean search(String query) {
		try {
			ManageFileSearch manageFileSearch = new ManageFileSearch();
			manageFileSearch.search(query);
			for(Result result : manageFileSearch.getResults()) {
				results.add(result);
			}
			ManageWebSearch manageWebSearch = new ManageWebSearch();
			manageWebSearch.search(query);
			for(Result result : manageWebSearch.getResults()) {
				results.add(result);
			}
			Collections.sort(results, Collections.reverseOrder());
			suggestions = Arrays.asList(spellChecker.suggestSimilar(query, 5));
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Result> getResults() {
		return results;
	}
	
	public List<String> getSuggestions() {
		return suggestions;
	}
	
}
