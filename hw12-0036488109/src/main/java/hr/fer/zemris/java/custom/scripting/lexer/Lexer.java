package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Lexer represents object that processes lexic analysis and groups input text
 * into tokens. There are ten types of token which are later forwarded to
 * parser.
 * 
 * @author Ivan
 */
public class Lexer {

	/**
	 * The Constant TAG_NAME which represent regular expression for tag name.
	 */
	public static final String TAG_NAME = "(^[=]|^([a-zA-Z]\\w*))";

	/**
	 * The Constant FUNCTION which represent regular expression for function.
	 */
	public static final String FUNCTION = "(@([a-zA-Z]\\w*))";

	/**
	 * The Constant OPERATORS which represent regular expression for operators.
	 */
	public static final String OPERATORS = "([+]|\\-(?!\\d)|[*]|[\\/]|[\\^])";

	/**
	 * The Constant VARIABLE which represent regular expression for variable.
	 */
	public static final String VARIABLE = "([a-zA-Z]\\w*)";

	/** The Constant NUMBER which represent regular expression for numbers. */
	public static final String NUMBER = "(-?\\d+(?:\\.\\d+)?)";

	/** The Constant STRING which represent regular expression string. */
	public static final String STRING = "(?<!\\\\)\\\"(.*?)(?<!\\\\)\\\"";

	/** The Constant END_TAG which represent regular expression for end tag. */
	public static final String END_TAG = "(\\$})";

	/** Input document splited into text and tokens. */
	private String[] document;

	/** The helper collector which stores tokens sequentially. */
	private ArrayIndexedCollection helperCollector;

	/** Collection which stores each tag elements. */
	private ArrayIndexedCollection tagBodyParts;

	/** Tokens array made out of {@link #helperCollector}. */
	private Token[] tokens;

	/**
	 * The current index. Used in {@link #nextToken()} for sequentially sending
	 * tokens.
	 */
	private int currentIndex;

	/**
	 * Constructor that instantiates a new lexer.
	 *
	 * @param text
	 *            input document
	 * @throws IllegalArgumentException
	 *             if given document is null
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text shouldnt be null!");
		}
		this.document = text.split("(?<!\\\\)(?=(\\{\\$))|(?<=(\\$}))");
		helperCollector = new ArrayIndexedCollection();
		tagBodyParts = new ArrayIndexedCollection();
		tokenizer();
	}

	/**
	 * Helper method which takes part by part from splited text stored in
	 * {@link #document}, and delegates it based on part type.
	 */
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

	/**
	 * Helper method which checks if given input has correctly esacped chars,
	 * and later groups that text into text token.
	 *
	 * @param part
	 *            part of document
	 * @throws LexerException
	 *             if given part doesnt have escapes for \ or {.
	 */
	public void processText(String part) {
		if (stringOrTextRegexChecker(part, "(?<!\\\\)[{]")) {
			throw new LexerException("There should be no { without escape preceding");
		}
		if (stringOrTextRegexChecker(part, "(?<!\\\\)(\\\\[^\\\\{])")) {
			throw new LexerException("In text after \\ there should be no characters except { and \\.");
		}

		part = part.replace("\\\\", "\\").replace("\\{", "{");

		helperCollector.add(new Token(TokenType.TEXT, part));
	}

	/**
	 * Helper method which delegates tag parts to {@link #regexMatcher(String)}
	 * and {@link #tagBodyPartsTokenExtractor()}
	 * 
	 * @param part
	 *            the part
	 */
	public void processTag(String part) {
		String tagBody = part.replace("{$", "");
		helperCollector.add(new Token(TokenType.TAGSTART, "{$"));
		regexMatcher(tagBody);
		tagBodyPartsTokenExtractor();
	}

	/**
	 * Regex matcher is helper fucntion which given tag body part checks against
	 * regex pattern. And each finding stores in tagBodyParts array.
	 *
	 * @param tagBody
	 *            the tag body
	 */
	private void regexMatcher(String tagBody) {
		Pattern pattern = Pattern.compile(TAG_NAME + "|" + FUNCTION + "|" + OPERATORS + "|" + VARIABLE + "|" + NUMBER
				+ "|" + STRING + "|" + END_TAG);
		Matcher matcher = pattern.matcher(tagBody);

		while (matcher.find()) {
			tagBodyParts.add(matcher.group());
		}
	}

	/**
	 * Helper method which stores each processed token from tag into
	 * {@link #helperCollector}. It uses {@link #checkTokenType(String)} to find
	 * which type should be assigned to given tag part.
	 */
	private void tagBodyPartsTokenExtractor() {
		helperCollector.add(new Token(TokenType.TAGNAME, tagBodyParts.get(0)));

		for (int i = 1, n = tagBodyParts.size(); i < n; i++) {
			checkTokenType((String) tagBodyParts.get(i));
		}
	}

	/**
	 * Check token type is used to match defined regex types with given string,
	 * and based on match creating new token which are later stored in helper
	 * collector.
	 *
	 * @param string
	 *            tag body part
	 */
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

	/**
	 * Correct string is helper method which checks if given string contains
	 * forbiden escapes.
	 *
	 * @param string
	 *            the string
	 * @return the string with added escapes where needed
	 * @throws LexerException
	 *             if there are escapes on any character except \r, \n, \\ and
	 *             \"
	 */
	private String correctString(String string) {
		if (stringOrTextRegexChecker(string, "(?<!\\\\)(\\\\[^\\\\\"\\\\r\\\\n])")) {
			throw new LexerException("In string after \\ there should be no characters except \\ and \"");
		}

		return string.replace("\\\\", "\\").replace("\\\"", "\"");
	}

	/**
	 * String or text regex checker is used for simple regex matching.
	 *
	 * @param string
	 *            the string
	 * @param regex
	 *            the regex
	 * @return true, if successful false otherwise
	 */
	public boolean stringOrTextRegexChecker(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);

		return matcher.find();
	}

	/**
	 * Helper method which ArrayIndexeCollection transforms into tokens array.
	 */
	private void helperCollectorToArray() {
		tokens = new Token[helperCollector.size() + 1];

		for (int i = 0, n = helperCollector.size(); i < n; i++) {
			Token temporary = (Token) helperCollector.get(i);

			tokens[i] = new Token(temporary.getType(), temporary.getValue());
		}

		tokens[tokens.length - 1] = new Token(TokenType.EOF, null);
		helperCollector.clear();
	}

	/**
	 * Method which forwards tokens sequentially when needed.
	 *
	 * @return the token
	 */
	public Token nextToken() {
		return tokens[currentIndex++];
	}

}
