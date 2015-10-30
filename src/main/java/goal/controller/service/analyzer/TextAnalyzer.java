package goal.controller.service.analyzer;

import goal.Constante;

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
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter.Side;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

public class TextAnalyzer extends Analyzer {
	
	private static final List<String> STOP_WORDS = Arrays.asList(
		"a", "an", "and", "are", "as", "at", "be", "bu", "by",
		"for", "if", "in", "into", "is", "it", 
		"no", "not", "of", "on", "or", "such", 
		"that", "the", "their", "then", "there", "these", 
		"they", "this", "to", "was", "will", "with",
		"does", "from"
	);
	private Version version = Constante.LUCENE_VERSION;
	
	public TextAnalyzer() {
		super();
	}

	@Override
	protected TokenStreamComponents createComponents(String field, Reader reader) {
		Tokenizer source = new StandardTokenizer(version, reader);
		TokenStream filter = source;		
		// Suppression des majuscules
		filter = new LowerCaseFilter(version, filter);
		// Suppression des accents
		filter = new ASCIIFoldingFilter(filter);
		// Suppression des mots inutiles
		filter = new StopFilter(version, filter, new CharArraySet(version, STOP_WORDS, true)); 		
		// Filtre pour les pluriels anglais
		filter = new EnglishMinimalStemFilter(filter);	
		// Racination anglais (stemming)
		filter = new PorterStemFilter(filter);
		// Autocomplete
		filter = new EdgeNGramTokenFilter(version, filter, Side.FRONT, 3, 20);
		return new TokenStreamComponents(source, filter);
	}
	
}
