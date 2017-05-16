package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyPanel;

public class SaveAsAction extends MyAction {

	private static final long serialVersionUID = 1L;

	public SaveAsAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MyPanel panel = (MyPanel) tabbedPane.getSelectedComponent();
		if (panel == null)
			return;

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save As file");
		if (fc.showSaveDialog(container) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(container, "Saving canceled.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (fc.getSelectedFile() != null) {
			int choose = JOptionPane.showConfirmDialog(container, "Do you want to overwrite existing file?");
			if (choose == JOptionPane.NO_OPTION || choose == JOptionPane.CANCEL_OPTION) return;
		}
		
		Path openedFilePath = fc.getSelectedFile().toPath();
		try {
			Files.write(openedFilePath, panel.getTextArea().getText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(container, "Saving aborted! File status not clear.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(container, "File saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
		changeTabInfo(openedFilePath);
	}

	private void changeTabInfo(Path openedFilePath) {
		MyPanel panel = (MyPanel) tabbedPane.getSelectedComponent();
		panel.setFile(openedFilePath);
		
		int index = tabbedPane.getSelectedIndex();
		tabbedPane.setTitleAt(index, openedFilePath.getFileName().toString());
		tabbedPane.setToolTipTextAt(index, openedFilePath.toString());
	}

}
