package hr.fer.zemris.bf.lexer;

public class Token {
	
	private TokenType tokenType;
	private Object tokenValue;

	public Token(TokenType tokenType, Object tokenValue) {
		if (tokenType == null) {
			throw new LexerException("Token type can not be null");
		}
		
		this.tokenType = tokenType;
		this.tokenValue = tokenValue;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public Object getTokenValue() {
		return tokenValue;
	}

	public String toString() {
		StringBuilder token = new StringBuilder();
		
		token.append("Type: ").append(tokenType);
		token.append(", Value: ").append(tokenValue);
		if (tokenValue != null) {
			token.append(", Value is instance of: ").append(tokenValue.getClass().getCanonicalName());
		}
		
		return token.toString();
	}
	
	public static void main(String[] args) {
		char[] bzvz = new char[]{'a','b','c',':','+',':','o','p'};
		System.out.println(new String(bzvz, 6-3, 3));
		
		if (Boolean.parseBoolean("true")) {
			System.out.println("saasd");
		}
		
		String help = new String(bzvz, 7 - 0, 1).toLowerCase();
		System.err.println(help);
	}

}
