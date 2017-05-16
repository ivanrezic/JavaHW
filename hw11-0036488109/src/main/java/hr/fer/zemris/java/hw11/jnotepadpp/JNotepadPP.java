package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAction;

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;
	private JMenuBar menuBar;
	private HashMap<String, MyAction> actions;
	private Map<Integer,Path> openedFilePaths;
	private Map<Integer,JTextArea> openedEditors;

	public JNotepadPP() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(20, 20, 900, 600);
		setTitle("JNotepad++");

		actions = new HashMap<>();
		openedFilePaths = new HashMap<>();
		openedEditors = new HashMap<>();
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
	}

	private void createActions() {
		actions.put("openFile",
				new OpenFileAction(this, "Open", "control O", KeyEvent.VK_O, "Used to open a document from disk."));
		actions.put("saveFile",
				new SaveFileAction(this, "Save", "control S", KeyEvent.VK_S, "Used to save a document to disk."));
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

	}

	private JButton createToolbarButton(Container container, String imagePath, String action) {
		JButton button = new JButton();

		button.setAction(actions.get(action));
		button.setHideActionText(true);
		button.setIcon(Util.loadIconFrom(this, imagePath));

		return button;
	}

	// private void createStatusBar() {
	// }

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	public Map<Integer, Path> getOpenedFilePaths() {
		return openedFilePaths;
	}
	
	public Map<Integer, JTextArea> getOpenedEditors() {
		return openedEditors;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}
}