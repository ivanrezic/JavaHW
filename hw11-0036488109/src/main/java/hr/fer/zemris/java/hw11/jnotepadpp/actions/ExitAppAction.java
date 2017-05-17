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
		int count = tabbedPane.getComponentCount() - 1;

		while (count >= 0) {
			MyPanel panel = (MyPanel) tabbedPane.getComponentAt(count);
			if (panel.isEdited()) {
				tabbedPane.setSelectedComponent(panel);
				int choice = JOptionPane.showConfirmDialog(container, "Do you want to save this file?",
						"File is not saved yet!", JOptionPane.YES_NO_CANCEL_OPTION);

				if (choice == JOptionPane.YES_OPTION) {
					if (panel.isFileUnsaved()) {
						SaveAsAction saveAs = (SaveAsAction) container.getActions().get("saveAs");
						saveAs.saveFileAs(panel);
					} else {
						SaveFileAction save = (SaveFileAction) container.getActions().get("saveFile");
						save.saveFile(panel);
					}
				} else if (choice == JOptionPane.CANCEL_OPTION) {
					return;
				}
			}

			tabbedPane.remove(count--);
		}

		System.exit(0);
	}

}
