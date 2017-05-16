package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Util {

	public static JTextArea addTab(JNotepadPP container, String fileName, String tooltip) {
		JTabbedPane tabbedPane = container.getTabbedPane();
		JTextArea textArea = new JTextArea();

		tabbedPane.addTab(fileName, loadIconFrom(container, "icons/save_green.png"), new JScrollPane(textArea),
				tooltip);
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				changeIcon();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changeIcon();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				changeIcon();
			}

			private void changeIcon() {
				tabbedPane.setIconAt(tabbedPane.getSelectedIndex(), loadIconFrom(container, "icons/save_red.png"));
			}
		});

		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
		return textArea;
	}

	public static Icon loadIconFrom(JNotepadPP container, String string) {
		InputStream is = container.getClass().getResourceAsStream(string);
		if (is == null)
			System.err.println("Missing icon with provided path.");

		byte[] bytes = new byte[10000];
		try (BufferedInputStream reader = new BufferedInputStream(is)) {
			reader.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ImageIcon(bytes);
	}

}
