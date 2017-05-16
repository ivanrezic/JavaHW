package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Path;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	private File file;

	public MyPanel(File file) {
		this.file = file;
		
		setLayout(new BorderLayout());
		textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(textArea);
		add(scroll, BorderLayout.CENTER);
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setText(String text) {
		textArea.setText(text);
	}

	public Path getOpenedFilePath() {
		return file.toPath();
	}
	
	public boolean hasFile(File file){
		return this.file.equals(file);
	}
	
	public void setFile(Path file) {
		this.file = file.toFile();
	}
}
