package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

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
	private HashMap<String, MyAction> actions;

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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ExitAppAction action = (ExitAppAction) actions.get("exitApp");
				action.exitIfPossible();
			}
		});
	}

	private void createActions() {
		actions.put("openFile",
				new OpenFileAction(this, "Open", "control O", KeyEvent.VK_O, "Used to open a document from disk."));
		actions.put("saveFile",
				new SaveFileAction(this, "Save", "control S", KeyEvent.VK_S, "Used to save a document to disk."));
		actions.put("saveAs",
				new SaveAsAction(this, "Save As", "control alt S", KeyEvent.VK_A, "Used to save new document to disk."));
		actions.put("newFile",
				new NewFileAction(this, "New file", "control N", KeyEvent.VK_N, "Used to create new document on disk."));
		actions.put("closeFile",
				new CloseFileAction(this, "Close file", "control W", KeyEvent.VK_L, "Used to close current document."));
		actions.put("exitApp",
				new ExitAppAction(this, "Exit", "control X", KeyEvent.VK_X, "Used exit application."));
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

		fileMenu.add(new JMenuItem(actions.get("openFile")));
		toolBar.add(createToolbarButton(toolBar, "icons/open_file.png", "openFile"));
		fileMenu.add(new JMenuItem(actions.get("saveFile")));
		toolBar.add(createToolbarButton(toolBar, "icons/save_blue.png", "saveFile"));
		fileMenu.add(new JMenuItem(actions.get("saveAs")));
		toolBar.add(createToolbarButton(toolBar, "icons/saveAs.png", "saveAs"));
		fileMenu.add(new JMenuItem(actions.get("newFile")));
		toolBar.add(createToolbarButton(toolBar, "icons/new_file.png", "newFile"));
		fileMenu.add(new JMenuItem(actions.get("closeFile")));
		toolBar.add(createToolbarButton(toolBar, "icons/close_file.png", "closeFile"));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(actions.get("exitApp")));
		toolBar.add(createToolbarButton(toolBar, "icons/exit_app.png", "exitApp"));

	}

	private JButton createToolbarButton(Container container, String imagePath, String actionName) {
		JButton button = new JButton();

		button.setAction(actions.get(actionName));
		button.setHideActionText(true);
		button.setIcon(MyAction.loadIconFrom(imagePath));

		return button;
	}

	// private void createStatusBar() {
	// }

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}