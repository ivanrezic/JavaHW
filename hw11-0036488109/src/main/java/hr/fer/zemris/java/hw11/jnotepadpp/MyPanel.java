package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Path;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyAction;

public class MyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private File file;
	private boolean edited = false;

	public MyPanel(File file) {
		this.file = file;
		
		setLayout(new BorderLayout());
	}
	
	private void initTextArea(String text) {
		this.textArea = new JTextArea(text);
		JScrollPane scroll = new JScrollPane(textArea);
		add(scroll, BorderLayout.CENTER);
	}

	public void setTextAreaListener(JTabbedPane tabbedPane, Icon icon) {
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

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setText(String text) {
		initTextArea(text);
		
		JTabbedPane tabbedPane = (JTabbedPane)this.getParent();
		setTextAreaListener(tabbedPane, MyAction.loadIconFrom("icons/save_red.png"));
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
	
	public int[] getStats(){
		int[] stats = new int[]{0,0,0};
		String text = textArea.getText();
		
		if ("".equals(text)) {
			stats[2] = 0;
		}else {			
			stats[2] = textArea.getLineCount();
		}	
		stats[0] = text.replaceAll("\\n", "").length();
		stats[1] = text.replaceAll("\\s*", "").length();
		
		return stats;
	}
}
