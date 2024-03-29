package hr.fer.zemris.bf.demo;

import hr.fer.zemris.bf.lexer.Lexer;
import hr.fer.zemris.bf.lexer.LexerException;
import hr.fer.zemris.bf.lexer.Token;
import hr.fer.zemris.bf.lexer.TokenType;

/**
 * <code>Izrazi1</code> is demonstration class. It demonstrates how each expression is disjointed into tokens.
 * 
 *
 * @author Ivan Rezic
 */
public class Izrazi1 {

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		String[] expressions = new String[] {
			"0",
			"tRue",
			"Not a",
			"A aNd b",
			"a or b",
			"a xoR b",
			"A and b * c",
			"a or b or c",
			"a xor b :+: c",
			"not not a",
			"a or b xor c and d",
			"a or b xor c or d",
			"a xor b or c xor d",
			"(a + b) xor (c or d)",
			"(d or b) xor not (a or c)",
			"(c or d) mor not (a or b)",
			"not a not b",
			"a and (b or",
			"a and (b or c",
			"a and 10"
		};
		
		for(String expr : expressions) {
			System.out.println("==================================");
			System.out.println("Izraz: " + expr);
			System.out.println("==================================");
			
			try {
				System.out.println("Tokenizacija:");
				Lexer lexer = new Lexer(expr);
				Token token = null;
				do {
					token = lexer.nextToken();
					System.out.println(token);
				} while(token.getTokenType()!=TokenType.EOF);
			} catch(LexerException ex) {
				System.out.println("Iznimka: " + ex.getClass()+" - " + ex.getMessage());
			}
			System.out.println();
		}
	}
}
