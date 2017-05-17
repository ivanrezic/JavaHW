package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

public class ToolsAction extends MyAction {

	private static final long serialVersionUID = 1L;
	private String tool;
	
	public ToolsAction(String tool, JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
		this.tool = tool;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (tool) {
		case "uppercase":
			toolAction(String::toUpperCase);
			break;
		case "lowercase":
			toolAction(String::toLowerCase);
			break;
		case "invert":
			toolAction((text) -> invertText(text));
			break;			
		case "descending":
			toolAction2((list) -> sort("desc" , list));
			break;			
		case "ascending":
			toolAction2((list) -> sort("asc" , list));
			break;			
		case "unique":
			toolAction2((list) -> unique(list));
			break;			
		}
	}

	private String invertText(String text) {
		StringBuilder sb = new StringBuilder(text.length());
		for (char c : text.toCharArray()) {
			if (Character.isUpperCase(c)) {
				sb.append(Character.toLowerCase(c));
			} else if (Character.isLowerCase(c)) {
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	private List<String> sort(String order , List<String> list){
		Locale hrLocale = new Locale("hr");
		Collator hrCollator = Collator.getInstance(hrLocale);
		
		StringBuilder stringBuilder = new StringBuilder();
		if("desc".equals(order)){
			Collections.sort(list, hrCollator.reversed());
		}else {
			Collections.sort(list, hrCollator);
		}
		list.forEach((e) -> stringBuilder.append(e));
		
		return list;
	}
	
	private List<String> unique(List<String> list){
		Set<String> set = new LinkedHashSet<>(list);
		
		return new ArrayList<>(set);
	}
}
