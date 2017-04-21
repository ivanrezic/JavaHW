package hr.fer.zemris.bf.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexerTest {

	@Test(expected = LexerException.class)
	public void nullExpression() {
		new Lexer(null);
	}

	@Test(expected = LexerException.class)
	public void illegalCharInExpression() {
		new Lexer("?").nextToken();
	}
	
	@Test(expected = LexerException.class)
	public void incopleteXorInExpression() {
		new Lexer(":+").nextToken();
	}
	
	@Test(expected = LexerException.class)
	public void incopleteIdentificatorInExpression() {
		new Lexer("5").nextToken();
	}
	
	@Test
	public void twoOperatorsOneAfterOther() throws Exception {
		Lexer lexer = new Lexer("**");
		assertEquals("and", lexer.nextToken().getTokenValue());
		assertEquals("and", lexer.nextToken().getTokenValue());
		
		Lexer lexer2 = new Lexer(":+::+:");
		assertEquals("xor", lexer2.nextToken().getTokenValue());
		assertEquals("xor", lexer2.nextToken().getTokenValue());
		assertEquals(null, lexer2.nextToken().getTokenValue());
	}
	
	@Test
	public void identificatorConcatenatedWithOperator() throws Exception {
		Lexer lexer = new Lexer("*true");
		assertEquals("and", lexer.nextToken().getTokenValue());
		assertEquals(true, lexer.nextToken().getTokenValue());
		
		Lexer lexer2 = new Lexer("orfalse");
		assertEquals("ORFALSE", lexer2.nextToken().getTokenValue());
		
		Lexer lexer3 = new Lexer(":+:jane_doe");
		assertEquals("xor", lexer3.nextToken().getTokenValue());
		assertEquals("JANE_DOE", lexer3.nextToken().getTokenValue());
	}
	
	@Test
	public void valiedIdentificators() throws Exception {
		Lexer lexer = new Lexer("And xOR Or NoT tRUE fALSE b42 ana_22");
		
		assertEquals("and", lexer.nextToken().getTokenValue());
		assertEquals("xor", lexer.nextToken().getTokenValue());
		assertEquals("or", lexer.nextToken().getTokenValue());
		assertEquals("not", lexer.nextToken().getTokenValue());
		assertEquals(true, lexer.nextToken().getTokenValue());
		assertEquals(false, lexer.nextToken().getTokenValue());
		assertEquals("B42", lexer.nextToken().getTokenValue());
		assertEquals("ANA_22", lexer.nextToken().getTokenValue());
	}
	
	@Test
	public void emtyExpression() throws Exception {
		Lexer lexer = new Lexer("     ");
		assertEquals("EOF", lexer.nextToken().getTokenType().toString());
	}
}
