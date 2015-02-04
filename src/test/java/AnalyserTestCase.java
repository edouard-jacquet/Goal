import goal.controller.service.analyzer.TextAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.junit.Before;
import org.junit.Test;


public class AnalyserTestCase {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAnalyserWithManyWords() throws Exception {
		List<String> tokens = tokenize("zlatan ibra");
		Assert.assertEquals(6, tokens.size());
		Assert.assertEquals(Arrays.asList("zla", "zlat", "zlata"
				, "zlatan", "ibr", "ibra"), tokens);
	}
	
	@Test
	public void testAnalyserWithSmallWord() throws Exception {
		List<String> tokens = tokenize("cup");
		Assert.assertEquals(1, tokens.size());
		Assert.assertEquals(Arrays.asList("cup"), tokens);
	}
	
	private List<String> tokenize(String text) throws IOException {
		TextAnalyzer analyzer = new TextAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(text));
		tokenStream.addAttribute(OffsetAttribute.class);
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

		tokenStream.reset();
		List<String> tokens = new ArrayList<String>();
		while (tokenStream.incrementToken()) {
		    tokens.add(charTermAttribute.toString());
		}
		
		analyzer.close();
		
		return tokens;
	}

}
