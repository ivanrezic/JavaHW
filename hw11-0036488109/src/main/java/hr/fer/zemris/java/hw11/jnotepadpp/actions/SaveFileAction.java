package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

public class SaveFileAction extends MyAction {

	private static final long serialVersionUID = 1L;

	public SaveFileAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int index = container.getTabbedPane().getSelectedIndex();
		Path openedFilePath = container.getOpenedFilePaths().get(index);
		JTextArea editor = container.getOpenedEditors().get(index);

		if (openedFilePath == null) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save file");
			if (fc.showSaveDialog(container) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(container, "Saving aborted.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			openedFilePath = fc.getSelectedFile().toPath();
		}

		try {
			Files.write(openedFilePath, editor.getText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(container, "Saving aborted! File status not clear.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(container, "File saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
		return;
	}
}
