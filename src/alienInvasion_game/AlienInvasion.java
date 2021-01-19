package alienInvasion_game;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class AlienInvasion extends JFrame implements ActionListener {

	private static final long serialVersionUID = 3805945535904189775L;
	private final GamePanel panel;
	private JFrame preferences, devPane, commandLine;
	private JMenuBar menubar;
	private JMenu fileMenu, viewMenu, helpMenu, currentLevelMenu, developerGameStateMenu;
	private JMenuItem exitMenuItem, preferencesMenuItem, hideStatsItem, helpWithControlsItem, infoItem, githubItem,
			newGameItem, pauseGameItem, easyMenuItem, mediumMenuItem, hardMenuItem, toggleDevToolsItem, fullScreenItem,
			writeStatsToFItem, openDevPaneItem, commandLineItem;
	private JMenuItem[] devStates = new JMenuItem[6];
	private JCheckBox spawnPowerfulAliensCxb, spawnAsteroidsCxb, spawnPowerupsCxb;
	boolean isSaved = false;
	public static int WIDTH = 800;
	public static int HEIGHT = 800;
	private JTextField sizeX, sizeY, cmdtxtfld;
	private JTextField alienSpawnRate;
	String cmd;
	private JButton saveAll, resetAll, closePref, backDev, forwDev, endGameDev, enterCmdB;
	private JLabel cmdResult;
	static boolean onMac = false;

	public static void main(String[] args) {
		onMac = System.getProperty("os.name").toLowerCase().startsWith("mac os");
		if (onMac) {
			System.setProperty("apple.awt.applicationName", "Alien Invasion");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}
		new AlienInvasion();
	}

	public AlienInvasion() {
		super.setTitle("Alien Invasion");
		panel = new GamePanel();
		menubar = new JMenuBar();
		fileMenu = new JMenu("File");
		exitMenuItem = new JMenuItem();
		cmdResult = new JLabel();
		commandLine = new JFrame();
		cmdtxtfld = new JTextField();
		commandLineItem = new JMenuItem("Command Line");
		enterCmdB = new JButton("Enter Command");
		backDev = new JButton("Back [");
		forwDev = new JButton("Forward ]");
		endGameDev = new JButton("End Game (end)");
		devPane = new JFrame("Developer Panel");
		infoItem = new JMenuItem("Project Info");
		if (onMac) {
			exitMenuItem.setText("Close Window");
		} else {
			exitMenuItem.setText("Exit");
		}
		devStates[0] = new JMenuItem("Menu");
		devStates[1] = new JMenuItem("Game Phase 1");
		devStates[2] = new JMenuItem("Game Phase 2");
		devStates[3] = new JMenuItem("Game Phase 3");
		devStates[4] = new JMenuItem("End");
		devStates[5] = new JMenuItem("Won");
		developerGameStateMenu = new JMenu("Game States");
		openDevPaneItem = new JMenuItem("Open Developer Panel");
		spawnPowerfulAliensCxb = new JCheckBox("Spawn Powerful Aliens");
		spawnAsteroidsCxb = new JCheckBox("Spawn Asteroids");
		spawnPowerupsCxb = new JCheckBox("Spawn Power-ups");
		toggleDevToolsItem = new JMenuItem("Toggle Developer Shortcuts");
		currentLevelMenu = new JMenu("Current Mode");
		fullScreenItem = new JMenuItem("Zoom");
		easyMenuItem = new JMenuItem("Easy");
		mediumMenuItem = new JMenuItem("Medium");
		hardMenuItem = new JMenuItem("Hard");
		writeStatsToFItem = new JMenuItem("Copy Stats");
		viewMenu = new JMenu("View");
		helpMenu = new JMenu("Info");
		newGameItem = new JMenuItem("New Game");
		pauseGameItem = new JMenuItem("Pause Game");
		githubItem = new JMenuItem("View Source Code");
		helpWithControlsItem = new JMenuItem("Controls");
		hideStatsItem = new JMenuItem("Hide/Show Stats");
		panel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		preferencesMenuItem = new JMenuItem("Preferences");
		this.registerForMacOSXEvents();
		super.setVisible(true);
		super.setSize(new Dimension(WIDTH, HEIGHT));
		super.setResizable(false);
		super.add(panel);
		super.setJMenuBar(menubar);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.addKeyListener(panel);
		super.setLocationRelativeTo(null);
		spawnPowerfulAliensCxb.setSelected(true);
		spawnAsteroidsCxb.setSelected(true);
		spawnPowerupsCxb.setSelected(true);
		setShortcuts();
		addMenubar();
	}

	public void registerForMacOSXEvents() {
		if (onMac) {
			try {
				// Generate and register the OSXAdapter, passing it a hash of all the methods we
				// wish to
				// use as delegates for various com.apple.eawt.ApplicationListener methods
				OSXAdapter.setQuitHandler(this, getClass().getDeclaredMethod("quit", (Class[]) null));
				OSXAdapter.setAboutHandler(this, getClass().getDeclaredMethod("about", (Class[]) null));
				OSXAdapter.setPreferencesHandler(this, getClass().getDeclaredMethod("openPreferences", (Class[]) null));
			} catch (Exception e) {
				System.err.println("Error while loading the OSXAdapter:");
				e.printStackTrace();
			}
		}
	}

	public void quit() {
		System.exit(0);
	}

	public void about() {
		JOptionPane.showMessageDialog(this,
				"This Java project is designed on/for macOS. You might experience minor issues when playing on a different OS.\nDeveloped by Sameer Prakash 2020-21");
	}

	void openCommandLine() {
		JPanel ppanel = new JPanel();
		commandLine.setVisible(true);
		ppanel.add(cmdtxtfld);
		ppanel.add(enterCmdB);
		ppanel.add(cmdResult);
		cmdtxtfld.setPreferredSize(new Dimension(400, 30));
		commandLine.setPreferredSize(new Dimension(600, 100));
		commandLine.add(ppanel);
		commandLine.pack();
		cmdtxtfld.addActionListener(this);
		enterCmdB.addActionListener(this);
	}

	void setShortcuts() {
		pauseGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		hideStatsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		int commandKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		if (onMac) {
			fullScreenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, commandKey));
			toggleDevToolsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, commandKey | KeyEvent.ALT_MASK));
			openDevPaneItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, commandKey | KeyEvent.SHIFT_MASK));
			easyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, commandKey | KeyEvent.CTRL_MASK));
			mediumMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, commandKey | KeyEvent.CTRL_MASK));
			hardMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, commandKey | KeyEvent.CTRL_MASK));
			newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, commandKey | KeyEvent.SHIFT_MASK));
			helpWithControlsItem
					.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, commandKey | KeyEvent.SHIFT_MASK));
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, commandKey));
			commandLineItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, commandKey | KeyEvent.SHIFT_MASK));
			int counter = 49;
			for (int i = 0; i < devStates.length; i++) {
				devStates[i].setAccelerator(
						KeyStroke.getKeyStroke(counter, commandKey | KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK));
				counter++;
			}
		} else {
			fullScreenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.VK_CONTROL));
			toggleDevToolsItem
					.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.SHIFT_MASK | KeyEvent.CTRL_MASK));
			easyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK));
			mediumMenuItem
					.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK));
			hardMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK));
			newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.VK_CONTROL));
			preferencesMenuItem
					.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK));
			helpWithControlsItem
					.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK));

			commandLineItem
					.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_MASK | KeyEvent.ALT_MASK));
			int counter = 49;
			for (int i = 0; i < devStates.length; i++) {
				devStates[i].setAccelerator(KeyStroke.getKeyStroke(counter, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));
				counter++;
			}
		}
	}

	public void devPane() {
		JPanel ppanel = new JPanel();
		ppanel.add(endGameDev);
		ppanel.add(backDev);
		ppanel.add(forwDev);
		devPane.add(ppanel);
		devPane.setVisible(true);
		devPane.pack();
		endGameDev.addActionListener(this);
		backDev.addActionListener(this);
		forwDev.addActionListener(this);
	}

	void addMenubar() {
		fileMenu.add(currentLevelMenu);
		fileMenu.addSeparator();
		fileMenu.add(newGameItem);
		if (!onMac) {
			fileMenu.add(preferencesMenuItem);
		}
		fileMenu.addSeparator();
		fileMenu.add(toggleDevToolsItem);
		fileMenu.addSeparator();
		fileMenu.add(writeStatsToFItem);
		fileMenu.add(exitMenuItem);
		viewMenu.add(hideStatsItem);
		viewMenu.add(pauseGameItem);
		viewMenu.addSeparator();
		viewMenu.add(commandLineItem);
		viewMenu.addSeparator();
		viewMenu.add(fullScreenItem);
		if (!onMac) {
			helpMenu.add(infoItem);
		}
		helpMenu.add(githubItem);
		helpMenu.addSeparator();
		helpMenu.add(helpWithControlsItem);
		menubar.add(fileMenu);
		menubar.add(viewMenu);
		menubar.add(helpMenu);
		currentLevelMenu.add(easyMenuItem);
		currentLevelMenu.add(mediumMenuItem);
		currentLevelMenu.add(hardMenuItem);
		for (JMenuItem menuItem : devStates) {
			menuItem.addActionListener(this);
			developerGameStateMenu.add(menuItem);
		}
		exitMenuItem.addActionListener(this);
		preferencesMenuItem.addActionListener(this);
		hideStatsItem.addActionListener(this);
		helpWithControlsItem.addActionListener(this);
		infoItem.addActionListener(this);
		githubItem.addActionListener(this);
		pauseGameItem.addActionListener(this);
		newGameItem.addActionListener(this);
		easyMenuItem.addActionListener(this);
		mediumMenuItem.addActionListener(this);
		hardMenuItem.addActionListener(this);
		toggleDevToolsItem.addActionListener(this);
		fullScreenItem.addActionListener(this);
		writeStatsToFItem.addActionListener(this);
		openDevPaneItem.addActionListener(this);
		commandLineItem.addActionListener(this);
	}

	void openPreferences() {
		sizeX = new JTextField("width");
		sizeY = new JTextField("height");
		saveAll = new JButton("Save");
		resetAll = new JButton("Reset");
		alienSpawnRate = new JTextField("seconds");
		closePref = new JButton("Close");
		preferences = new JFrame("Preferences");
		JPanel ppanel = new JPanel();
		JLabel textLabel = new JLabel("Size");
		JLabel label2 = new JLabel("Alien spawn rate");
		ppanel.add(textLabel);
		ppanel.add(sizeX);
		ppanel.add(sizeY);
		ppanel.add(label2);
		ppanel.add(alienSpawnRate);
		ppanel.add(spawnPowerfulAliensCxb);
		ppanel.add(spawnAsteroidsCxb);
		ppanel.add(spawnPowerupsCxb);
		ppanel.add(saveAll);
		ppanel.add(resetAll);
		ppanel.add(closePref);
		sizeX.addActionListener(this);
		sizeY.addActionListener(this);
		saveAll.addActionListener(this);
		sizeX.setPreferredSize(new Dimension(230, 30));
		sizeY.setPreferredSize(new Dimension(230, 30));
		alienSpawnRate.setPreferredSize(new Dimension(390, 30));
		spawnPowerfulAliensCxb.setPreferredSize(new Dimension(175, 30));
		spawnAsteroidsCxb.setPreferredSize(new Dimension(175, 30));
		spawnPowerupsCxb.setPreferredSize(new Dimension(175, 30));
		alienSpawnRate.addActionListener(this);
		closePref.addActionListener(this);
		resetAll.addActionListener(this);
		preferences.add(ppanel);
		preferences.setVisible(true);
		preferences.setPreferredSize(new Dimension(560, 180));
		preferences.pack();
		spawnPowerfulAliensCxb.addActionListener(this);
		spawnAsteroidsCxb.addActionListener(this);
		spawnPowerupsCxb.addActionListener(this);
	}

	private static void openWebpage(String urlString) {
		try {
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void saveChanges() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		try {
			WIDTH = Integer.parseInt(sizeX.getText());
			HEIGHT = Integer.parseInt(sizeY.getText());
			if (WIDTH <= width && HEIGHT <= HEIGHT) {
				super.setSize(WIDTH, HEIGHT);
			} else {
				JOptionPane.showMessageDialog(preferences,
						"The height or width written above is larger than your screen size.");
			}
		} catch (Exception e) {

		}
		try {
			GamePanel.setAlienSpawnRate(Double.parseDouble(alienSpawnRate.getText()));
		} catch (Exception e) {

		}
		ObjectManagerP1.spawnPowerfulAliens = spawnPowerfulAliensCxb.isSelected();
		ObjectManagerP1.spawnAsteroids = spawnAsteroidsCxb.isSelected();
		ObjectManagerP1.spawnPowerups = spawnPowerupsCxb.isSelected();
		ObjectManagerP2.spawnPowerups = spawnPowerupsCxb.isSelected();
		isSaved = true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == exitMenuItem) {
			if (onMac) {
				super.dispose();
			} else {
				System.exit(0);
			}
		}
		if (arg0.getSource() == preferencesMenuItem) {
			openPreferences();
			isSaved = false;
		}
		if (arg0.getSource() == saveAll) {
			saveChanges();
		}
		if (arg0.getSource() == resetAll) {
			WIDTH = 800;
			HEIGHT = 800;
			GamePanel.setAlienSpawnRate(1);
			super.setSize(WIDTH, HEIGHT);
			spawnPowerfulAliensCxb.setSelected(true);
			spawnAsteroidsCxb.setSelected(true);
			spawnPowerupsCxb.setSelected(true);
			saveChanges();
		}
		if (arg0.getSource() == hideStatsItem) {
			GamePanel.hideMenus();
		}
		if (arg0.getSource() == helpWithControlsItem) {
			JOptionPane.showMessageDialog(this,
					"GAME CONTROLS: Press the arrow keys or use WASD to move around    Use space to shoot projectiles at aliens.\n"
							+ "OTHER CONTROLS: Press escape to pause the game    Press F3 to hide the menus.\n"
							+ "DEVELOPER CONTROLS: Press the bracket keys [ ] to toggle between Game Phases    Press the end key to go to the end question");
		}
		if (arg0.getSource() == infoItem) {
			about();
		}
		if (arg0.getSource() == githubItem) {
			writeTextToClipboard("https://github.com/League-level2-student/league-level2-game-RedFox360?");
			int optionChosen = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to go to the website https://github.com/League-level2-student/league-level2-game-RedFox360?");
			if (optionChosen == 0) {
				openWebpage("https://github.com/League-level2-student/league-level2-game-RedFox360");
			}
		}
		if (arg0.getSource() == pauseGameItem) {
			GamePanel.pauseGame();
		}
		if (arg0.getSource() == closePref) {
			if (!isSaved) {
				int optionChosen = JOptionPane.showConfirmDialog(preferences,
						"You have unsaved changes. Do you want to save changes before closing?");
				if (optionChosen == 0) {
					saveChanges();
					preferences.dispose();
				} else if (optionChosen == 1) {
					preferences.dispose();
				}
			} else {
				preferences.dispose();
			}
		}
		if (arg0.getSource() == newGameItem) {
			GamePanel.currentState = GamePanel.MENU;
			GamePanel.clearAll();
			GamePanel.rocketShip = new Rocketship(400, 700, 60, 60, 20);
			GamePanel.car = new Car(700, 400, 100, 100);
			GamePanel.manager1 = new ObjectManagerP1(GamePanel.rocketShip);
			GamePanel.manager2 = new ObjectManagerP2(GamePanel.car);
		}
		if (arg0.getSource() == spawnPowerfulAliensCxb || arg0.getSource() == spawnAsteroidsCxb) {
			isSaved = false;
		}
		if (arg0.getSource() == easyMenuItem) {
			GamePanel.currentMode = GamePanel.EASY;
		}
		if (arg0.getSource() == mediumMenuItem) {
			GamePanel.currentMode = GamePanel.MEDIUM;
		}
		if (arg0.getSource() == hardMenuItem) {
			GamePanel.currentMode = GamePanel.HARD;
		}
		if (arg0.getSource() == toggleDevToolsItem) {
			toggleDevTools(false);
		}
		if (arg0.getSource() == fullScreenItem) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int screenWidth = (int) screenSize.getWidth();
			int screenHeight = (int) screenSize.getHeight();
			if (WIDTH == screenWidth && HEIGHT == screenHeight) {
				WIDTH = 800;
				HEIGHT = 800;
			} else {
				WIDTH = screenWidth;
				HEIGHT = screenHeight;
			}
			super.setSize(WIDTH, HEIGHT);
		}
		if (arg0.getSource() == writeStatsToFItem) {
			String content;
			if (ObjectManagerP2.scoreSet = false) {
				content = "Score: " + ObjectManagerP1.score;
			} else {
				content = "Score: " + ObjectManagerP2.score + "\n\nAliens Killed: " + ObjectManagerP2.aliensKilled;
			}
			writeTextToClipboard(content);
		}
		if (arg0.getSource() == openDevPaneItem) {
			devPane();
		}
		if (arg0.getSource() == backDev) {
			if (GamePanel.currentState == GamePanel.MENU) {
				GamePanel.currentState = GamePanel.WON;
			} else {
				GamePanel.currentState--;
			}
		}
		if (arg0.getSource() == forwDev) {
			if (GamePanel.currentState == GamePanel.WON) {
				GamePanel.currentState = GamePanel.MENU;
			} else {
				GamePanel.currentState++;
			}
		}
		if (arg0.getSource() == endGameDev) {
			GamePanel.currentState = GamePanel.GAME2;
			ObjectManagerP2.aliensKilled = 40;
		}
		if (arg0.getSource() == commandLineItem) {
			openCommandLine();
		}
		if (arg0.getSource() == enterCmdB) {
			cmd = cmdtxtfld.getText();
			if ("toggle aliens -spawn".equals(cmd)) {
				if (ObjectManagerP1.terminalSpawnAliens || ObjectManagerP2.terminalSpawnAliens) {
					ObjectManagerP1.terminalSpawnAliens = false;
					ObjectManagerP2.terminalSpawnAliens = false;
					cmdResult.setText("Aliens will no longer spawn in this game session");
				} else if (!ObjectManagerP1.terminalSpawnAliens || ObjectManagerP2.terminalSpawnAliens) {
					ObjectManagerP1.terminalSpawnAliens = true;
					ObjectManagerP2.terminalSpawnAliens = true;
					cmdResult.setText("Aliens will spawn in this game session");
				}
			} else if ("credits".equals(cmd)) {
				cmdResult.setText("Testers: Ishan Khandekar and Anika Prakash; Teacher: Cody from the LEAGUE");
			} else if ("easter egg".equals(cmd)) {
				ObjectManagerP1.spawnEEgg.start();
				cmdResult.setText("Easter eggs enabled ;)");
			} else {
				cmdResult.setText("Invalid command " + cmd);
			}
		}
		if (arg0.getSource() == devStates[0]) {
			GamePanel.currentState = GamePanel.MENU;
		}
		if (arg0.getSource() == devStates[1]) {
			GamePanel.currentState = GamePanel.GAME;
			GamePanel.clearAll();
		}
		if (arg0.getSource() == devStates[2]) {
			GamePanel.currentState = GamePanel.GAME2;
			GamePanel.clearAll();
		}
		if (arg0.getSource() == devStates[3]) {
			GamePanel.clearAll();
			GamePanel.currentState = GamePanel.GAME2;
			ObjectManagerP2.aliensKilled = 40;
		}
		if (arg0.getSource() == devStates[4]) {
			GamePanel.currentState = GamePanel.END;
		}
		if (arg0.getSource() == devStates[5]) {
			GamePanel.currentState = GamePanel.WON;
		}
	}

	public static void writeTextToClipboard(String s) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable transferable = new StringSelection(s);
		clipboard.setContents(transferable, null);
	}

	void toggleDevTools(boolean commandLine) {
		if (GamePanel.developerTools) {
			GamePanel.developerTools = false;
			fileMenu.removeAll();
			fileMenu.removeAll();
			fileMenu.add(currentLevelMenu);
			fileMenu.addSeparator();
			fileMenu.add(newGameItem);
			fileMenu.add(preferencesMenuItem);
			fileMenu.addSeparator();
			fileMenu.add(toggleDevToolsItem);
			fileMenu.addSeparator();
			fileMenu.add(writeStatsToFItem);
			fileMenu.add(exitMenuItem);
			if (commandLine) {
				cmdResult.setText("Developer tools toggled off");
			}
		} else if (!GamePanel.developerTools) {
			GamePanel.developerTools = true;
			fileMenu.removeAll();
			fileMenu.add(currentLevelMenu);
			fileMenu.addSeparator();
			fileMenu.add(newGameItem);
			fileMenu.add(preferencesMenuItem);
			fileMenu.addSeparator();
			fileMenu.add(toggleDevToolsItem);
			fileMenu.add(openDevPaneItem);
			fileMenu.add(developerGameStateMenu);
			fileMenu.addSeparator();
			fileMenu.add(writeStatsToFItem);
			fileMenu.add(exitMenuItem);
			if (commandLine) {
				cmdResult.setText("Developer tools toggled on");
			}
		}
	}
}