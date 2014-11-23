package goal.controller.service.analyzer;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishMinimalStemFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public class TextAnalyzer extends Analyzer {
	
	private static final List<String> STOP_WORDS = Arrays.asList(
		"a", "an", "and", "are", "as", "at", "be", "bu", "by",
		"for", "if", "in", "into", "is", "it", 
		"no", "not", "of", "on", "or", "such", 
		"that", "the", "their", "then", "there", "these", 
		"they", "this", "to", "was", "will", "with"
	);
	private Version version;
	
	public TextAnalyzer(Version version) {
		super();
		this.version = version;
	}

	@Override
	protected TokenStreamComponents createComponents(String field, Reader reader) {
		Tokenizer source = new StandardTokenizer(version, reader);
		TokenStream filter = new EnglishMinimalStemFilter(source);
		filter = new LowerCaseFilter(version, filter);
		filter = new ASCIIFoldingFilter(filter);
		filter = new StopFilter(version, filter, new CharArraySet(version, STOP_WORDS, false));
		filter = new PorterStemFilter(filter);
		return new TokenStreamComponents(source, filter);
	}
	
}
