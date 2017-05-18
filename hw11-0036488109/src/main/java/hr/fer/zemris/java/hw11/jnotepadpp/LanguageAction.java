package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

public class LanguageAction extends MyAction {

	private static final long serialVersionUID = 1L;
	private String language;

	public LanguageAction(String language, JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
		
		this.language = language;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (LocalizationProvider.getInstance().getLanguage().equals(language)) {
			return;
		}
		
		LocalizationProvider.getInstance().setLanguage(language);
	}
}
