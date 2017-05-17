package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

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
			toolAction(ToolsAction::invertText);
			break;			
		default:
			break;
		}
	}

	private static String invertText(String text) {
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
}
