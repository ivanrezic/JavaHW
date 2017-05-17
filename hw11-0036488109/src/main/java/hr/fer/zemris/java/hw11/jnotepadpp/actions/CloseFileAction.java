package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyPanel;

public class CloseFileAction extends MyAction {

	private static final long serialVersionUID = 1L;

	public CloseFileAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MyPanel panel = (MyPanel) tabbedPane.getSelectedComponent();
		if (panel == null) return;

		if (panel.isEdited()) {
			JOptionPane.showMessageDialog(container, "Please save file before closing it.");
		} else {
			tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
		}
	}
}
