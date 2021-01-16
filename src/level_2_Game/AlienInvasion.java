package level_2_Game;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class AlienInvasion implements ActionListener {
	JFrame frame, preferences;
	GamePanel panel;
	JMenuBar menubar;
	JMenu fileMenu, viewMenu, helpMenu, currentLevelMenu;
	JMenuItem exitMenuItem, preferencesMenuItem, hideStatsItem, helpWithControlsItem, infoItem, githubItem, newGameItem,
			pauseGameItem, easyMenuItem, mediumMenuItem, hardMenuItem, toggleDevToolsItem, fullScreenItem, reloadItem;
	JCheckBox spawnPowerfulAliensCxb, spawnAsteroidsCxb;
	boolean isSaved = false;
	public static int WIDTH = 800;
	public static int HEIGHT = 800;
	JTextField sizeX, sizeY;
	JTextField alienSpawnRate;
	JButton saveAll, resetAll, closePref;

	public static void main(String[] args) {
		if (System.getProperty("os.name").contains("Mac")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("apple.awt.application.name", "Alien Invasion");
		}
		AlienInvasion gameClass = new AlienInvasion();
		gameClass.setup();
		gameClass.addMenubar();
	}

	public AlienInvasion() {
		frame = new JFrame("Alien Invasion");
		panel = new GamePanel();
	}

	public void setup() {
		menubar = new JMenuBar();
		fileMenu = new JMenu("File");
		exitMenuItem = new JMenuItem("Exit");
		infoItem = new JMenuItem("Project Info");
		spawnPowerfulAliensCxb = new JCheckBox("Spawn Powerful Aliens");
		spawnAsteroidsCxb = new JCheckBox("Spawn Asteroids");
		toggleDevToolsItem = new JMenuItem("Toggle Developer Shortcuts");
		currentLevelMenu = new JMenu("Current Mode");
		reloadItem = new JMenuItem("Reload");
		fullScreenItem = new JMenuItem("Zoom");
		easyMenuItem = new JMenuItem("Easy");
		mediumMenuItem = new JMenuItem("Medium");
		hardMenuItem = new JMenuItem("Hard");
		viewMenu = new JMenu("View");
		helpMenu = new JMenu("Info");
		newGameItem = new JMenuItem("New Game");
		pauseGameItem = new JMenuItem("Pause Game");
		githubItem = new JMenuItem("View Source Code");
		helpWithControlsItem = new JMenuItem("Controls");
		hideStatsItem = new JMenuItem("Hide/Show Stats");
		preferencesMenuItem = new JMenuItem("Preferences");
		frame.setVisible(true);
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.add(panel);
		frame.setJMenuBar(menubar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(panel);
		frame.setResizable(false);
		spawnPowerfulAliensCxb.setSelected(true);
		spawnAsteroidsCxb.setSelected(true);
	}

	void addMenubar() {
		fileMenu.add(toggleDevToolsItem);
		fileMenu.add(currentLevelMenu);
		fileMenu.addSeparator();
		fileMenu.add(newGameItem);
		fileMenu.add(preferencesMenuItem);
		fileMenu.add(exitMenuItem);
		viewMenu.add(hideStatsItem);
		viewMenu.add(pauseGameItem);
		viewMenu.addSeparator();
		viewMenu.add(fullScreenItem);
		viewMenu.add(reloadItem);
		helpMenu.add(infoItem);
		helpMenu.add(githubItem);
		helpMenu.add(helpWithControlsItem);
		menubar.add(fileMenu);
		menubar.add(viewMenu);
		menubar.add(helpMenu);
		currentLevelMenu.add(easyMenuItem);
		currentLevelMenu.add(mediumMenuItem);
		currentLevelMenu.add(hardMenuItem);
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
		reloadItem.addActionListener(this);
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
		ppanel.add(saveAll);
		ppanel.add(resetAll);
		ppanel.add(closePref);
		sizeX.addActionListener(this);
		sizeY.addActionListener(this);
		saveAll.addActionListener(this);
		sizeX.setPreferredSize(new Dimension(200, 30));
		sizeY.setPreferredSize(new Dimension(200, 30));
		alienSpawnRate.setPreferredSize(new Dimension(330, 30));
		spawnPowerfulAliensCxb.setPreferredSize(new Dimension(240, 30));
		spawnAsteroidsCxb.setPreferredSize(new Dimension(240, 30));
		alienSpawnRate.addActionListener(this);
		closePref.addActionListener(this);
		resetAll.addActionListener(this);
		preferences.add(ppanel);
		preferences.setVisible(true);
		preferences.setPreferredSize(new Dimension(500, 180));
		preferences.pack();
		spawnPowerfulAliensCxb.addActionListener(this);
		spawnAsteroidsCxb.addActionListener(this);
	}

	private static void openWebpage(String urlString) {
		try {
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void saveChanges() {
		try {
			WIDTH = Integer.parseInt(sizeX.getText());
			HEIGHT = Integer.parseInt(sizeY.getText());
			frame.setSize(WIDTH, HEIGHT);
		} catch (Exception e) {

		}
		try {
			GamePanel.setAlienSpawnRate(Double.parseDouble(alienSpawnRate.getText()));
		} catch (Exception e) {

		}
		ObjectManagerP1.spawnPowerfulAliens = spawnPowerfulAliensCxb.isSelected();
		ObjectManagerP1.spawnAsteroids = spawnAsteroidsCxb.isSelected();
		isSaved = true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == exitMenuItem) {
			System.exit(0);
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
			frame.setSize(WIDTH, HEIGHT);
			spawnPowerfulAliensCxb.setSelected(true);
			spawnAsteroidsCxb.setSelected(true);
		}
		if (arg0.getSource() == hideStatsItem) {
			GamePanel.hideMenus();
		}
		if (arg0.getSource() == helpWithControlsItem) {
			JOptionPane.showMessageDialog(frame,
					"GAME CONTROLS: Press the arrow keys or use WASD to move around    Use space to shoot projectiles at aliens.\n"
							+ "OTHER CONTROLS: Press escape to pause the game    Press F3 to hide the menus.\n"
							+ "DEVELOPER CONTROLS: Press the bracket keys [ ] to toggle between Game Phases    Press the end key to go to the end question");
		}
		if (arg0.getSource() == infoItem) {
			JOptionPane.showMessageDialog(frame,
					"This Java project is designed on/for macOS. You might experience minor issues when playing on a different OS.\nDeveloped by Sameer Prakash 2020-21");
		}
		if (arg0.getSource() == githubItem) {
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
			if (GamePanel.developerTools) {
				GamePanel.developerTools = false;
			} else if (!GamePanel.developerTools) {
				GamePanel.developerTools = true;
			}
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
			frame.setSize(WIDTH, HEIGHT);
		}
		if (arg0.getSource() == reloadItem) {
			GamePanel.reload();
		}
	}
	
}