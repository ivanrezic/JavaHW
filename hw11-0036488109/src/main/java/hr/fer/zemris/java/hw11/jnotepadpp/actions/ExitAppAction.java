package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

public class ExitAppAction extends MyAction {

	private static final long serialVersionUID = 1L;

	public ExitAppAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		exitIfPossible();
	}

	public void exitIfPossible() {
		int count = tabbedPane.getComponentCount() - 1;

		while (count >= 0) {
			MyTextArea panel = (MyTextArea) tabbedPane.getComponentAt(count);
			CloseFileAction action = (CloseFileAction)container.getActions().get("closeFile");
			
			action.closeTab(panel);
			count--;
		}

		System.exit(0);
	}

}
