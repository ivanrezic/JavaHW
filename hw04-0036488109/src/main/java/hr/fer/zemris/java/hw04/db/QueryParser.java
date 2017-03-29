package hr.fer.zemris.java.hw04.db;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {
	private String querry;
	private List<ConditionalExpression> conditionals = new LinkedList<>();

	public QueryParser(String querry) {
		this.querry = querry.trim();
		parse();
	}

	boolean isDirectQuery(){
		return querry.matches("jmbag\\s*=\\s*\".+\"");
	}
	
	String getQueriedJMBAG(){
		if (!isDirectQuery()) {
			throw new IllegalStateException("Querry is not direct one.");
		}
		return regexMathcer(querry,"(?<=\").+(?=\")").get(0);
	}
	

	private List<String> regexMathcer(String querry, String regex) {
		List<String> parts = new LinkedList<>();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(querry);
		
		while (matcher.find()) {
			parts.add(matcher.group().trim());
		}
		
		return parts;
	}

	List<ConditionalExpression> getQuery(){
		return conditionals;
	}
	
	private void parse() {
		if (isDirectQuery()) {
			conditionals.add(new ConditionalExpression(FieldValueGetters.JMBAG, getQueriedJMBAG(), ComparisonOperators.EQUALS));
			return;
		}
		
		String[] querryParts = querry.split("\\s*(?i)AND\\s*");
		for (String part : querryParts) {
			regexMathcer(part, "ifdsoaidfhdihfoidauhfaodifdaiuhfads");
		}
	}
}
