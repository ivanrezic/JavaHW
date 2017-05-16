package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyPanel;

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
		int count = tabbedPane.getComponentCount();

		for (int i = 0; i < count; i++) {
			MyPanel panel = (MyPanel) tabbedPane.getComponentAt(i);
			if (panel.isEdited()) {
				int choice = JOptionPane.showConfirmDialog(container, "There are unsaved files, proceed?");
				if (choice != JOptionPane.YES_OPTION) {
					return;
				} else {
					break;
				}
			}
		}

		System.exit(0);
	}

}
