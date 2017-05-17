package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit.CopyAction;
import javax.swing.text.DefaultEditorKit.CutAction;
import javax.swing.text.DefaultEditorKit.PasteAction;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAppAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAction;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;
	private JMenuBar menuBar;
	private HashMap<String, Action> actions;

	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(20, 20, 900, 600);
		setTitle("JNotepad++");

		actions = new HashMap<>();
		initGUI();
	}

	private void initGUI() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane();
		container.add(tabbedPane, BorderLayout.CENTER);
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		createActions();
		addActions();
		addCloseOperationAction();
	}

	private void createActions() {
		actions.put("openFile",
				new OpenFileAction(this, "Open", "control O", KeyEvent.VK_O, "Used to open a document from disk."));
		actions.put("saveFile",
				new SaveFileAction(this, "Save", "control S", KeyEvent.VK_S, "Used to save a document to disk."));
		actions.put("saveAs", new SaveAsAction(this, "Save As", "control alt S", KeyEvent.VK_A,
				"Used to save new document to disk."));
		actions.put("newFile", new NewFileAction(this, "New file", "control N", KeyEvent.VK_N,
				"Used to create new document on disk."));
		actions.put("closeFile",
				new CloseFileAction(this, "Close file", "control W", KeyEvent.VK_L, "Used to close current document."));
		actions.put("stats", new FileStatsAction(this, "File stats", "control shift S", KeyEvent.VK_T,
				"Show current file statistics."));
		actions.put("exitApp",
				new ExitAppAction(this, "Exit", "control alt X", KeyEvent.VK_X, "Used to exit application."));
		actions.put("cut",
				editPremadeAction(new CutAction(), "Cut", "Used to cut selected text.", KeyEvent.VK_U, "control X"));
		actions.put("copy",
				editPremadeAction(new CopyAction(), "Copy", "Used to copy selected text.", KeyEvent.VK_Y, "control C"));
		actions.put("paste", 
				editPremadeAction(new PasteAction(), "Paste", "Used to paste copied/cut text.", KeyEvent.VK_P, "control V"));
	}

	private Action editPremadeAction(Action action, String name, String description, int mnemonic, String keyStroke) {
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
		action.putValue(Action.NAME, name);
		action.putValue(Action.SHORT_DESCRIPTION, description);
		action.putValue(Action.MNEMONIC_KEY, mnemonic);

		return action;
	}
	
	private void addActions() {
		JToolBar toolBar = new JToolBar("Alatna traka");
		getContentPane().add(toolBar, BorderLayout.PAGE_START);

		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu toolsMenu = new JMenu("Tools");
		JMenu languagesMenu = new JMenu("Languages");
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolsMenu);
		menuBar.add(languagesMenu);

		addToolbarAndMenuItem(actions.get("newFile"), fileMenu, toolBar, "icons/new_file.png");
		addToolbarAndMenuItem(actions.get("openFile"), fileMenu, toolBar, "icons/open_file.png");
		addToolbarAndMenuItem(actions.get("saveFile"), fileMenu, toolBar, "icons/save_blue.png");
		addToolbarAndMenuItem(actions.get("saveAs"), fileMenu, toolBar, "icons/saveAs.png");
		addToolbarAndMenuItem(actions.get("closeFile"), fileMenu, toolBar, "icons/close_file.png");
		fileMenu.addSeparator();
		addToolbarAndMenuItem(actions.get("stats"), fileMenu, toolBar, "icons/stats.png");
		addToolbarAndMenuItem(actions.get("exitApp"), fileMenu, toolBar, "icons/exit_app.png");

		toolBar.addSeparator();
		addToolbarAndMenuItem(actions.get("copy"), editMenu, toolBar, "icons/copy.png");
		addToolbarAndMenuItem(actions.get("cut"), editMenu, toolBar, "icons/cut.png");
		addToolbarAndMenuItem(actions.get("paste"), editMenu, toolBar, "icons/paste.png");
	}
	
	private void addToolbarAndMenuItem(Action action, JMenu menu , JToolBar toolBar, String imagePath){
		menu.add(new JMenuItem(action));
		toolBar.add(createToolbarButton(imagePath, action));
	}

	private JButton createToolbarButton(String imagePath, Action action) {
		JButton button = new JButton();

		button.setAction(action);
		button.setHideActionText(true);
		button.setIcon(MyAction.loadIconFrom(imagePath));

		return button;
	}

	private void addCloseOperationAction() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ExitAppAction action = (ExitAppAction) actions.get("exitApp");
				action.exitIfPossible();
			}
		});
	}

	// private void createStatusBar() {
	// }

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public HashMap<String, Action> getActions() {
		return actions;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}