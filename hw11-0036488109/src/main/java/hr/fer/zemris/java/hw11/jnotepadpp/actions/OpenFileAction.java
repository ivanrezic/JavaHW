package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyPanel;

public class OpenFileAction extends MyAction {

	public OpenFileAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(container) != JFileChooser.APPROVE_OPTION) return;
		File file = fc.getSelectedFile();
		Path filePath = file.toPath();
		addTab(file, file.getPath());

		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(container, "File" + filePath + "is not readable!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		byte[] data = null;
		try {
			data = Files.readAllBytes(filePath);
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(container, "Error while reading" + filePath + ".",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String text = new String(data, StandardCharsets.UTF_8);

		MyPanel panel = (MyPanel) tabbedPane.getSelectedComponent();//provjera je li null?
		panel.setText(text);
	}

}
