package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

public class SmartScriptParserTest {
	String document1;
	String document2;
	String document3;
	String document4;
	String document5;
	SmartScriptParser parser1;
	SmartScriptParser parser2;
	SmartScriptParser parser3;
	SmartScriptParser parser4;
	SmartScriptParser parser5;

	@Before
	public void initialize() {
		document1 = loader("doc1.text");
		document2 = loader("doc2.text");
		document3 = loader("doc3.text");
		document4 = loader("doc4.text");
		document5 = loader("doc5.text");
		
		
		parser2 = new SmartScriptParser(document2);
		parser3 = new SmartScriptParser(document3);
		parser4 = new SmartScriptParser(document4);
		parser5 = new SmartScriptParser(document5);
	}

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
	public void testName() throws Exception {
		parser1 = new SmartScriptParser(document1);
		String first = parser1.getDocumentNode().toString();
		parser1 = new SmartScriptParser(first);
		String second = parser1.getDocumentNode().toString();
		
		assertEquals(first, second);
	}
}
