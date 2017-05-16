package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

public abstract class MyAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	protected JNotepadPP container;

	public MyAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent, String shortDescription) {
		this.container = container;

		this.putValue(Action.NAME, actionName);
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
		this.putValue(Action.MNEMONIC_KEY, keyEvent);
		this.putValue(Action.SHORT_DESCRIPTION, shortDescription);
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

}
