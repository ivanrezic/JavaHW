package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Util;

public class OpenFileAction extends MyAction {

	public OpenFileAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		Map<Integer, Path> openedFilePaths = container.getOpenedFilePaths();
		Map<Integer, JTextArea> openedEditors = container.getOpenedEditors();
		JFileChooser fc = new JFileChooser();
		
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(container) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		if(openedFilePaths.containsValue(filePath)) return;
		JTextArea editor = Util.addTab(container, fileName.getName(), fileName.getPath());

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

		editor.setText(text);
		int index = container.getTabbedPane().getSelectedIndex();
		openedEditors.put(index, editor);
		openedFilePaths.put(index, filePath);
	}

}
