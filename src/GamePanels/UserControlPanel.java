package GamePanels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import Main.DataAndGameStart;
import WaveRuner.RunWave;

public class UserControlPanel extends JPanel implements ActionListener {

	/**
	 * Does stuff
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * holds the tower building buttons
	 */
	JToggleButton[] towers;

	/**
	 * holds the game world
	 */
	GameWorld game;
	/**
	 * holds the the button for seeing tower ranges
	 */
	JToggleButton seeTowerRanges = new JToggleButton("See Tower Ranges");
	/**
	 * holds whether you can see the range of towers or not
	 */
	private boolean canSeeTowerRanges;
	/**
	 * Holds the tower names
	 */
	static final String[] TOWER_NAMES_STR = { "Basic Tower: Cost 150",
			"Rapid Fire Tower: Cost 200", "Mortar Tower: Cost 200" };

	/**
	 * used to run a wave
	 */
	private JButton waveRunner = new JButton("Next Wave");

	/**
	 * used to run the wave of monsters attacking
	 */
	private RunWave wave;

	/**
	 * holds if the basic tower is selected
	 */
	private boolean basicTower;
	/**
	 * holds if rapid fire tower is selected
	 */
	private boolean rapidFireTower;
	/**
	 * holds if mortar tower is selected
	 */
	private boolean mortarTower;
	/**
	 * holds if the slow tower is selected
	 */
	private boolean slowTower;

	/**
	 * holds the final class object
	 */
	DataAndGameStart project;

	/**
	 * creates the control panel
	 */
	public UserControlPanel(DataAndGameStart projectz) {
		// Creates how the panels layout looks

		project = projectz;
		// creates the new layout
		setLayout(new GridLayout(10, 1));
		// Creates the tower buttons
		towers = new JToggleButton[TOWER_NAMES_STR.length];
		for (int i = 0; i < TOWER_NAMES_STR.length; i++) {
			towers[i] = new JToggleButton(TOWER_NAMES_STR[i]);
			towers[i].addActionListener(this);
			add(towers[i]);
		}
		JLabel dumby = new JLabel("");
		add(dumby);
		add(seeTowerRanges);
		seeTowerRanges.addActionListener(this);
		JLabel dumby2 = new JLabel("");
		add(dumby2);
		JLabel dumby3 = new JLabel("");
		add(dumby3);
		add(waveRunner);
		waveRunner.addActionListener(this);
	}

	/**
	 * finds out what button is clicked and what to do when that button is
	 * clicked
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();

		if (seeTowerRanges.isSelected() && seeTowerRanges == obj) {
			canSeeTowerRanges = true;
			game.setIfTowerRangesCanBeSeen();
			towers[0].setSelected(false);
			towers[1].setSelected(false);
			towers[2].setSelected(false);

		} else if (!seeTowerRanges.isSelected() && seeTowerRanges == obj) {
			canSeeTowerRanges = false;
			game.setIfTowerRangesCanBeSeen();
			towers[0].setSelected(false);
			towers[1].setSelected(false);
			towers[2].setSelected(false);

		}
		getIfAndWhichTowerIsSelected(e);

	}

	/**
	 * unselects the tower create
	 */
	public void unSelectTowers() {
		towers[0].setSelected(false);
		towers[1].setSelected(false);
		towers[2].setSelected(false);

		basicTower = false;
		rapidFireTower = false;
		mortarTower = false;
		slowTower = false;
	}

	/**
	 * @param wave
	 *            the wave to set
	 */
	public void setWave(RunWave wave) {
		this.wave = wave;
	}

	/**
	 * gets if a tower toggle button is selected and then changes the booleans
	 * of the buttons
	 * 
	 * @param e
	 *            sends in the actions
	 */
	public void getIfAndWhichTowerIsSelected(ActionEvent e) {
		Object obj = e.getSource();
		// how towers are selected for
		setBasicTower(false);
		setRapidFireTower(false);
		setMortarTower(false);
		setSlowTower(false);
		if (towers[0].isSelected() && towers[0] == obj) {
			setBasicTower(true);
			setRapidFireTower(false);
			setMortarTower(false);
			setSlowTower(false);
			towers[1].setSelected(false);
			towers[2].setSelected(false);

			game.unSelectObject();
		}
		if (towers[1].isSelected() && towers[1] == obj) {
			setBasicTower(false);
			setRapidFireTower(true);
			setMortarTower(false);
			setSlowTower(false);
			towers[0].setSelected(false);
			towers[2].setSelected(false);

			game.unSelectObject();
		}
		if (towers[2].isSelected() && towers[2] == obj) {
			setBasicTower(false);
			setRapidFireTower(false);
			setMortarTower(true);
			setSlowTower(false);
			towers[0].setSelected(false);
			towers[1].setSelected(false);

			game.unSelectObject();
		}

		if (waveRunner == obj) {
			wave.runWave();
			towers[0].setSelected(false);
			towers[1].setSelected(false);
			towers[2].setSelected(false);

			waveRunner.setEnabled(false);
		}
	}

	public void setGame(GameWorld worldz) {
		game = worldz;
	}

	/**
	 * get if the user can see the towers or not
	 * 
	 * @return returns if the user can see the towers or not
	 */
	public boolean getCanSeeTowerRanges() {
		return canSeeTowerRanges;
	}

	/**
	 * gets if the control panel has basic tower selected
	 * 
	 * @return returns if the basic tower is selected
	 */
	public boolean getBasicTower() {
		return basicTower;
	}

	/**
	 * sets if the basic tower is selected or not
	 * 
	 * @param basicTower
	 *            sends in if it is selected or not
	 */
	public void setBasicTower(boolean basicTower) {
		this.basicTower = basicTower;
	}

	/**
	 * gets if the rapid fire tower is selected
	 * 
	 * @return returns if the rapid fire tower is selected
	 */
	public boolean getRapidFireTower() {
		return rapidFireTower;
	}

	/**
	 * sets if the rapid fire tower is selected
	 * 
	 * @param rapidFireTower
	 *            sets if the rapid fire tower is selected or not
	 */
	public void setRapidFireTower(boolean rapidFireTower) {
		this.rapidFireTower = rapidFireTower;
	}

	/**
	 * gets if the mortar tower is selected
	 * 
	 * @return returns if the mortar tower is selected
	 */
	public boolean getMortarTower() {
		return mortarTower;
	}

	/**
	 * sets if the mortar tower is selected
	 * 
	 * @param mortarTower
	 *            sends in if the mortar tower is selected or not
	 */
	public void setMortarTower(boolean mortarTower) {
		this.mortarTower = mortarTower;
	}

	/**
	 * @return the waveRunner
	 */
	public JButton getWaveRunner() {
		return waveRunner;
	}

	/**
	 * gets if the slower tower is selected
	 * 
	 * @return returns if the slow tower is selected
	 */
	public boolean getSlowTower() {
		return slowTower;
	}

	/**
	 * sets the slow tower is selected
	 * 
	 * @param slowTower
	 *            sends in if the slow tower is selected or not
	 */
	public void setSlowTower(boolean slowTower) {
		this.slowTower = slowTower;
	}

}
