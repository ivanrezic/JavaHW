package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyAction;

public class FileStatsAction extends MyAction {

	private static final long serialVersionUID = 1L;

	public FileStatsAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		if (panel == null) return;
		int[] stats = panel.getStats();

		String stat = String.format("This file has:%n%d characters%n%d characters that are not blank%n%d lines",
				stats[0], stats[1], stats[2]);

		JOptionPane.showMessageDialog(container, stat);
	}

}
