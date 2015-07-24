package Main;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.MenuBar;

import javax.swing.JFrame;

import GamePanels.GameMenuBar;
import GamePanels.GameWorld;
import GamePanels.SelectedTowerPanel;
import GamePanels.UserControlPanel;
import GamePanels.UserStats;
import MenuPanels.InterfaceMainMenu;
import Towers.BasicTower;
import WaveRuner.RunWave;

/**
 * 
 * @author Jeremy Driscoll Starts up the lab
 */
public class DataAndGameStart extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * holds the menu
	 */
	private GameMenuBar gameMenu;
	/**
	 * holds the user controls on the east panel
	 */
	private UserControlPanel controls;
	/**
	 * holds the selected tower
	 */
	private SelectedTowerPanel selectedTower;
	/**
	 * holds the gameworld Panel
	 */
	private GameWorld gameWorld;
	/**
	 * holds the game world thread
	 */
	private Thread gameWorldThread;
	/**
	 * holds the user stats panel
	 */
	private UserStats stats;
	/**
	 * used to run the monster waves
	 */
	RunWave wave;
	/**
	 * 
	 */
	private InterfaceMainMenu mainMenu;
	
	/**
	 * the main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		DataAndGameStart startUp = new DataAndGameStart("Attack of The Cubes", 920, 700);

	}

	public DataAndGameStart(String theTitle, int theWidth, int theHeight) {
		super();

		// Initializes the panels

		// Initializes the menu
		gameMenu = new GameMenuBar(this);
		// Initializes the control panel have to send in the menu because a
		// getGameMenu gives an error
		controls = new UserControlPanel(this);
		// must be made before gameworld
		stats = new UserStats(this);
		gameWorld = new GameWorld(this);
		mainMenu= new InterfaceMainMenu(this);
		selectedTower = new SelectedTowerPanel(this, gameWorld);

		controls.setGame(gameWorld);
		wave = new RunWave(this, gameWorld, controls, stats);

		controls.setWave(wave);
		gameWorld.setWaveRunner(wave);
		layoutSetup(theTitle, theWidth, theHeight);

		// sets the frame to be visable
		setVisible(true);

	}

	public void reset() {
		String[] resetStats = { "Wave Number 0", "Bases Health 200/200", "$400" };
		stats.setSTAT_LABELS_STR(resetStats);
		stats.updateLabels();
		BasicTower[] allTowers = new BasicTower[0];
		gameWorld.setAllTowers(allTowers);
		gameWorld.setTowerCounter(0);
		gameWorld.setDeadMonsters(0);
		wave.setMonsterCounter(0);
		controls.getWaveRunner().setEnabled(true);
		wave.getTimer().stop();
		wave.getAllMonsters().removeAllElements();
		wave.setMonsterCounter(0);
		wave.setHowManyMonsters(5);
		wave.setMonsterHealth(100);
		gameWorldThread.stop();
		gameWorld.setYouWin(false);
		gameWorld.setYouLose(false);	

	}

	/**
	 * creates the layout
	 * 
	 * @param theTitle
	 * @param theWidth
	 * @param theHeight
	 */
	public void layoutSetup(String theTitle, int theWidth, int theHeight) {

		// Creates the frame and stuff
		new JFrame(theTitle) {
			private static final long serialVersionUID = 1L;

			// paints the objects
			public void paint(Graphics g) {
				paintComponents(g);
			}
		};

		// closes the application if the exit button is hit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// sets width and height
		setSize(theWidth, theHeight);
		// Makes the applet have a border layout
		setLayout(new BorderLayout());
	
	
		add(mainMenu, BorderLayout.CENTER);
		
		
	}
	public void startMapMaker(){
		this.remove(mainMenu);
		
		
		
		this.validate();
	}
	public void StartGame(){
		this.remove(mainMenu);
		
		// Creates the menu
		add(gameMenu, BorderLayout.NORTH);
				// Creates the control panel
		add(controls, BorderLayout.EAST);
				
		add(stats, BorderLayout.SOUTH);
		add(selectedTower, BorderLayout.WEST);
		gameWorld.setSelectedTowerPanel(selectedTower);
		add(gameWorld, BorderLayout.CENTER);
		this.validate();
		
	}
	
	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public UserControlPanel getUserControlPanel() {
		return controls;
	}

	public UserStats getUserStats() {
		return stats;
	}

	/**
	 * @return the gameWorldThread
	 */
	public Thread getGameWorldThread() {
		gameWorldThread = new Thread(gameWorld);
		return gameWorldThread;
	}

}
