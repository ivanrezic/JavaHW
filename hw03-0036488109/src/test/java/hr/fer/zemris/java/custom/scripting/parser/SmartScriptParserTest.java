package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.hw03.SmartScriptTester;

public class SmartScriptParserTest {
	SmartScriptParser parser;

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

	@Test
	public void testDoc1Parsing() throws Exception {
		parser = new SmartScriptParser(loader("doc1.txt"));
		String first = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		parser = new SmartScriptParser(first);
		String second = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		
		assertEquals(first, second);
	}
	
	@Test
	public void testDoc2Parsing() throws Exception {
		parser = new SmartScriptParser(loader("doc2.txt"));
		String first = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		parser = new SmartScriptParser(first);
		String second = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		
		assertEquals(first, second);
	}
	
	@Test
	public void testDoc3Parsing() throws Exception {
		parser = new SmartScriptParser(loader("doc3.txt"));
		String first = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		parser = new SmartScriptParser(first);
		String second = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		
		assertEquals(first, second);
	}
	
	@Test
	public void testDoc4Parsing() throws Exception {
		parser = new SmartScriptParser(loader("doc4.txt"));
		String first = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		parser = new SmartScriptParser(first);
		String second = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		
		assertEquals(first, second);
	}
	
	@Test
	public void testDoc5Parsing() throws Exception {
		parser = new SmartScriptParser(loader("doc5.txt"));
		String first = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		parser = new SmartScriptParser(first);
		String second = SmartScriptTester.createOriginalDocumentBody(parser.getDocumentNode());
		
		assertEquals(first, second);
	}
	
	@Test(expected = LexerException.class)
	public void escapingNonAllowedCharsInText() throws Exception {
		parser = new SmartScriptParser(loader("doc6.txt"));
	}
	
	@Test(expected = LexerException.class)
	public void escapingNonAllowedCharsInString() throws Exception {
		parser = new SmartScriptParser(loader("doc7.txt"));
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void parserConstrutorPassingNull() throws Exception {
		parser = new SmartScriptParser(null);
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void invalidTagName() throws Exception {
		parser = new SmartScriptParser(loader("doc8.txt"));
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void moreEndTagsThanOpenedTags() throws Exception {
		parser = new SmartScriptParser(loader("doc9.txt"));
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void emptyStackAfterRemovingEndTag() throws Exception {
		parser = new SmartScriptParser(loader("doc10.txt"));
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void illegalTokenAfterForToken() throws Exception {
		parser = new SmartScriptParser(loader("doc11.txt"));
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void illegalTagAsStarExpression() throws Exception {
		parser = new SmartScriptParser(loader("doc12.txt"));
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void illegalTagAsEndExpression() throws Exception {
		parser = new SmartScriptParser(loader("doc13.txt"));
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void illegalTagAsStepExpression() throws Exception {
		parser = new SmartScriptParser(loader("doc14.txt"));
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void invalidNumberOfForLoopNoodChilderns() throws Exception {
		parser = new SmartScriptParser(loader("doc15.txt"));
	}
}
