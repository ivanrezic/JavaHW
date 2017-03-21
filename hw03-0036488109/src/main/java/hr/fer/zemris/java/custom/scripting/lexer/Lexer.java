package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

public class Lexer {
	public static final String TAG_NAME = "(^[=]|^([a-zA-Z]\\w*))";
	public static final String FUNCTION = "(@([a-zA-Z]\\w*))";
	public static final String OPERATORS = "([+]|\\-(?!\\d)|[*]|[\\/]|[\\^])";
	public static final String VARIABLE = "([a-zA-Z]\\w*)";
	public static final String NUMBER = "(-?\\d+(?:\\.\\d+)?)";
	public static final String STRING = "\\\"(.*?)\\\"";
	public static final String END_TAG = "(\\$})";

	private String[] document;
	private ArrayIndexedCollection helperCollector;
	private ArrayIndexedCollection tagBodyParts;
	private Token[] tokens;
	private int currentIndex;

	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text shouldnt be null!");
		}
		this.document = text.split("(?<!\\\\)(?=(\\{\\$))|(?<=(\\$}))");
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
		if (stringOrTextRegexChecker(part, "(?<!\\\\)[{]")) {
			throw new LexerException("There should be no { without escape preceding");
		}
		if (stringOrTextRegexChecker(part, "(?<!\\\\)(\\\\[^\\\\{])")) {
			throw new LexerException("In string after \\ there should be no characters except { and \".");
		}

		part = part.replace("\\\\", "\\").replace("\\{", "{");

		helperCollector.add(new Token(TokenType.TEXT, part));
	}

	public void processTag(String part) {
		String tagBody = part.replace("{$", "");
		helperCollector.add(new Token(TokenType.TAGSTART, "{$"));
		regexMatcher(tagBody);
		tagBodyPartsTokenExtractor();
	}

	private void regexMatcher(String tagBody) {
		Pattern pattern = Pattern.compile(TAG_NAME + "|" + FUNCTION + "|" + OPERATORS + "|" + VARIABLE + "|" + NUMBER
				+ "|" + STRING + "|" + END_TAG);
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
		} else if (string.matches(OPERATORS)) {
			token = new Token(TokenType.OPERATOR, string);
		} else if (string.matches(END_TAG)) {
			token = new Token(TokenType.TAGEND, string);
		} else if (string.matches(NUMBER)) {
			token = new Token(TokenType.NUMBER, string);
		} else if (string.matches(VARIABLE)) {
			token = new Token(TokenType.VARIABLE, string);
		} else if (string.matches(STRING)) {
			string = string.substring(1, string.length() - 1);
			string = correctString(string);
			token = new Token(TokenType.STRING, string);
		}

		helperCollector.add(token);
	}

	private String correctString(String string) {
		if (stringOrTextRegexChecker(string, "(?<!\\\\)(\\\\[^\\\\\"])")) {
			throw new LexerException("In string after \\ there should be no characters except \\ and \".");
		}

		return string.replace("\\\\", "\\").replace("\\\"", "\"");
	}

	public boolean stringOrTextRegexChecker(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);

		return matcher.find();
	}

	private void helperCollectorToArray() {
		tokens = new Token[helperCollector.size() + 1];

		for (int i = 0, n = helperCollector.size(); i < n; i++) {
			Token temporary = (Token) helperCollector.get(i);

			tokens[i] = new Token(temporary.getType(), temporary.getValue());
		}

		tokens[tokens.length - 1] = new Token(TokenType.EOF, null);
		helperCollector.clear();
	}

	public Token nextToken() {
		return tokens[currentIndex++];
	}

//	public static void main(String[] args) {
//		Lexer lexer = new Lexer(
//				"This is sample text.  {$ FOR i 1 10 1 $} This is {$= i $}-th time this message is generated. {$END$} {$FOR i 0 10 2 $}sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $} {$END$}");
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//		System.out.println(lexer.nextToken());
//	}

}
