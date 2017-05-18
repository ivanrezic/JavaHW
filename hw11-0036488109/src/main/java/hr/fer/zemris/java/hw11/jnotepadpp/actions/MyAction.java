package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

public abstract class MyAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private static final int MAX_ICON_SIZE = 3000;
	private static int newFiles = 0;
	protected JNotepadPP container;
	protected JTabbedPane tabbedPane;

	public MyAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent, String shortDescription) {
		this.container = container;
		this.tabbedPane = container.getTabbedPane();

		this.putValue(Action.NAME, container.getFlp().getString(actionName));
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
		this.putValue(Action.MNEMONIC_KEY, keyEvent);
		this.putValue(Action.SHORT_DESCRIPTION, container.getFlp().getString(shortDescription));
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

	protected boolean addTab(File file, String tooltip) {
		if (alreadyOpened(file))
			return false;

		MyTextArea panel = new MyTextArea(file,container);

		String title = "New " + ++newFiles;
		if (file != null) {
			title = file.getName();
		}

		
		tabbedPane.addTab(title, loadIconFrom("icons/save_green.png"), panel, tooltip);
		tabbedPane.setSelectedComponent(panel);
		return true;
	}

	public static Icon loadIconFrom(String imageLocation) {
		InputStream is = JNotepadPP.class.getResourceAsStream(imageLocation);
		if (is == null) {
			System.err.println("Missing icon with provided path or icon size > " + MAX_ICON_SIZE + " bytes.");
		}

		byte[] bytes = new byte[MAX_ICON_SIZE];
		try (BufferedInputStream reader = new BufferedInputStream(is)) {
			reader.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ImageIcon(bytes);
	}

	private boolean alreadyOpened(File file) {
		if(file == null) return false;
		boolean flag = false;
		int count = tabbedPane.getTabCount();

		for (int i = 0; i < count; i++) {
			flag = file.getName().equals(tabbedPane.getTitleAt(i));
			if (flag)
				break;
		}

		return flag;
	}

	protected void setCurrentTabIcon(Icon icon) {
		tabbedPane.setIconAt(tabbedPane.getSelectedIndex(), icon);
	}
	
	protected void toolAction(UnaryOperator<String> action){
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		if(panel == null) return;
		JTextArea textArea = panel.getTextArea();
		
		int start = textArea.getSelectionStart();
		int end = textArea.getSelectionEnd();
		StringBuilder strBuilder = new StringBuilder(textArea.getText());
		
		String newText = textArea.getSelectedText();
		if(newText == null) return;
		strBuilder.replace(start, end, action.apply(newText));
		textArea.setText(strBuilder.toString());
	}
	
	protected void toolAction2(Function<List<String>, List<String>> action){
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		if(panel == null) return;
		JTextArea textArea = panel.getTextArea();
		Document doc = textArea.getDocument();
		
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		int offset = len != 0 ? Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark()) : doc.getLength();
		
		try {
			offset = textArea.getLineStartOffset(textArea.getLineOfOffset(offset));
			len = textArea.getLineEndOffset(textArea.getLineOfOffset(len + offset));
			
			String text = doc.getText(offset, len - offset);
			List<String> list = action.apply(Arrays.asList(text.split("\\r?\\n")));
			
			doc.remove(offset, len - offset);
			for (String string : list) {
				doc.insertString(offset, string + "\n", null);
				offset += string.length() + 1;
			}
		} catch (BadLocationException ignorable) {
		}
	}
}
