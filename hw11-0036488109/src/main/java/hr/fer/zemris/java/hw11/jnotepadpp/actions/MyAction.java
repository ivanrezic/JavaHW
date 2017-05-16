package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyPanel;

public abstract class MyAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
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
		if (alreadyOpened(file)) return;
		MyPanel panel = new MyPanel(file);
		
		String title = "New";
		if(file != null){
			title = file.getName();
		}

		tabbedPane.addTab(title, loadIconFrom("icons/save_green.png"), panel, tooltip);
		panel.getTextArea().getDocument().addDocumentListener(new DocumentListener() {

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
				tabbedPane.setIconAt(tabbedPane.getSelectedIndex(), loadIconFrom("icons/save_red.png"));
			}
		});

		tabbedPane.setSelectedComponent(panel);
	}

	public Icon loadIconFrom(String string) {
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

	private boolean alreadyOpened(File file) {
		boolean flag = false;
		int count = tabbedPane.getTabCount();

		for (int i = 0; i < count; i++) {
			MyPanel panel = (MyPanel) tabbedPane.getComponentAt(i);
			
			flag = panel.hasFile(file);
			if (flag) break;
		}

		return flag;
	}
}
