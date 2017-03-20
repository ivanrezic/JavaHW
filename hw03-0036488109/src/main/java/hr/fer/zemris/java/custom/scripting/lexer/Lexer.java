package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

public class Lexer {
	private String[] document;
	private ArrayIndexedCollection helperCollector;
	private ArrayIndexedCollection tagBodyParts;
	private Token[] tokens;
	private int currentIndex;

	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text shouldnt be null!");
		}
		this.document = text.split("(?=(\\{\\$))|(?<=(\\$\\}))");
		helperCollector = new ArrayIndexedCollection();
		tagBodyParts = new ArrayIndexedCollection();
		tokenizer();
	}

	private void tokenizer() {
		for (String part : document) {
			if (part.startsWith("{")) {
				processTag(part);
				tagBodyParts.clear();
			} else {
				processText(part);
			}
		}

		helperCollectorToArray();
	}

	public void processText(String part) {
		char[] charParts = part.toCharArray();
		String accuratePart;

		for (int i = 0; i < charParts.length; i++) {
			if (charParts[i] == '\\' && (charParts[i + 1] != '{' || charParts[i + 1] != '\\')) {
				throw new LexerException("After \\ there should be no characters except { and \\.");
			}
		}

		accuratePart = new String(charParts).replace("\\", "\\\\").replace("{", "\\{");
		helperCollector.add(new Token(TokenType.TEXT, accuratePart));
	}

	public void processTag(String part) {
		String tagBody = part.replace("{$", "");
		helperCollector.add(new Token(TokenType.TAG, "{$"));
		regexMatcher(tagBody);
		tagBodyPartsTokenExtractor();
	}

	private void regexMatcher(String tagBody) {
		String tagName = "(^[=]|^([a-zA-Z]\\w*))";
		String functionName = "(@([a-zA-Z]\\w*))";
		String operators = "([+]|\\-(?!\\d)|[*]|[\\/]|[\\^])";
		String variable = "([a-zA-Z]\\w*)";
		String digit = "(-?\\d+)";
		String string = "(\\\"([^]]+)\\\")";
		String endTag = "(\\$})";

		Pattern pattern = Pattern.compile(tagName + "|" + functionName + "|" + operators + "|" + variable + "|" + digit
				+ "|" + string + "|" + endTag);
		Matcher matcher = pattern.matcher(tagBody);

		while (matcher.find()) {
			tagBodyParts.add(matcher.group());
		}
	}

	private void tagBodyPartsTokenExtractor() {
		helperCollector.add(new Token(TokenType.TAGNAME, tagBodyParts.get(0)));

		for (int i = 1, n = tagBodyParts.size(); i < n; i++) {
			checkTokenType((String) tagBodyParts.get(i));
		}
	}

	private void checkTokenType(String string) {
		Token token = null;

		if (string.startsWith("@")) {
			token = new Token(TokenType.FUNCTION, string.substring(1));
		} else if (string.matches("([+]|\\-(?!\\d)|[*]|[\\/]|[\\^])")) {
			token = new Token(TokenType.OPERATOR, string);
		} else if (string.matches("(\\$})")) {
			token = new Token(TokenType.TAG, string);
		} else if (string.matches("(-?\\d+)")) {
			token = new Token(TokenType.NUMBER, string);
		} else if (string.matches("([a-zA-Z]\\w*)")) {
			token = new Token(TokenType.VARIABLE, string);
		} else if (string.matches("(\\\"([^]]+)\\\")")) {
			token = new Token(TokenType.STRING, string);
		}

		helperCollector.add(token);
	}

	private void helperCollectorToArray() {
		tokens = new Token[helperCollector.size() + 1];

		for (int i = 0, n = helperCollector.size(); i < n; i++) {
			Token temporary = (Token) helperCollector.get(i);
			tokens[i] = new Token(temporary.getType(), temporary.getValue());
		}

		tokens[tokens.length-1] = new Token(TokenType.EOF, null);
		helperCollector.clear();
	}

	public Token nextToken() {
		return tokens[currentIndex++];
	}

	public static void main(String[] args) {
		Lexer lexer = new Lexer(
				"This is sample text.  {$ FOR i 1 10 1 $} This is {$= i $}-th time this message is generated. {$END$} {$FOR i 0 10 2 $}sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $} {$END$}");
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
	}

}
