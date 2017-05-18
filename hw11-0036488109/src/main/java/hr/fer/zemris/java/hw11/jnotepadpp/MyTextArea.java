package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Path;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyAction;

public class MyTextArea extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;

	private boolean edited = false;
	private JTextArea textArea;
	private MyStatusBar statusBar;
	private File file;

	public MyTextArea(File file, JNotepadPP container) {
		this.file = file;
		this.tabbedPane = container.getTabbedPane();
		this.statusBar = container.getStatusBar();
		
		statusBar.setDefaultValues();
		setLayout(new BorderLayout());
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setText(String text) {
		initTextArea(text);

		setTextAreaListener(MyAction.loadIconFrom("icons/save_red.png"));
		setCaretListener();
	}

	private void initTextArea(String text) {
		this.textArea = new JTextArea(text);
		JScrollPane scroll = new JScrollPane(textArea);
		add(scroll, BorderLayout.CENTER);
	}

	public void setTextAreaListener(Icon icon) {
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
				tabbedPane.setIconAt(tabbedPane.getSelectedIndex(), icon);
				edited = true;
			}
		});
	}

	private void setCaretListener() {
		textArea.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				String text = textArea.getText();
				int caretPosition = textArea.getCaretPosition();
				try {
					int line = textArea.getLineOfOffset(caretPosition);
					int column = caretPosition - textArea.getLineStartOffset(line);
					int selection = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

					statusBar.setCaretPositionText(
							(String.format("Ln: %d   Col: %d   Sel: %d", line + 1, column, selection)));
				} catch (BadLocationException ignorable) {
				}

				statusBar.setLengthText(String.format(" length: %d", text.replaceAll("\\r?\n", "").length()));
			}
		});
	}

	public boolean isFileUnsaved() {
		return file == null;
	}

	public Path getOpenedFilePath() {
		return file.toPath();
	}

	public boolean hasFile(File file) {
		if (this.file == null) {
			return false;
		}

		return this.file.equals(file);
	}

	public void setFile(Path file) {
		this.file = file.toFile();
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public int[] getStats() {
		int[] stats = new int[] { 0, 0, 0 };
		String text = textArea.getText();

		if ("".equals(text)) {
			stats[2] = 0;
		} else {
			stats[2] = textArea.getLineCount();
		}
		stats[0] = text.replaceAll("\\n", "").length();
		stats[1] = text.replaceAll("\\s*", "").length();

		return stats;
	}
}
