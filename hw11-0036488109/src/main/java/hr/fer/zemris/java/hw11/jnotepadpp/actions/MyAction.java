package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.UnaryOperator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

public abstract class MyAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private static final int MAX_ICON_SIZE = 3000;
	protected JNotepadPP container;
	protected JTabbedPane tabbedPane;

	public MyAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent, String shortDescription) {
		this.container = container;
		this.tabbedPane = container.getTabbedPane();

		this.putValue(Action.NAME, actionName);
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
		this.putValue(Action.MNEMONIC_KEY, keyEvent);
		this.putValue(Action.SHORT_DESCRIPTION, shortDescription);
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

	protected void addTab(File file, String tooltip) {
		if (alreadyOpened(file))
			return;

		MyTextArea panel = new MyTextArea(file,container);

		String title = "New";
		if (file != null) {
			title = file.getName();
		}

		
		tabbedPane.addTab(title, loadIconFrom("icons/save_green.png"), panel, tooltip);
		tabbedPane.setSelectedComponent(panel);
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
		boolean flag = false;
		int count = tabbedPane.getTabCount();

		for (int i = 0; i < count; i++) {
			MyTextArea panel = (MyTextArea) tabbedPane.getComponentAt(i);

			flag = panel.hasFile(file);
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
		JTextArea text = panel.getTextArea();
		
		int start = text.getSelectionStart();
		int end = text.getSelectionEnd();
		StringBuilder strBuilder = new StringBuilder(text.getText());
		strBuilder.replace(start, end, action.apply(text.getSelectedText()));
		text.setText(strBuilder.toString());
	}
}
