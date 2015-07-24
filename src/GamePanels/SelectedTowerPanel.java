package GamePanels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.DataAndGameStart;
import Towers.Tower;

public class SelectedTowerPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Label for the towers type
	 */
	JLabel towerName = new JLabel("  No Tower Selected  ");

	/**
	 * holds the tower options
	 */
	JButton[] towerOptions;

	/**
	 * holds the tower data and what will happen if you upgrade
	 */
	JLabel[] upgradeAndCurentTowerData;

	/**
	 * holds the string data of the towers
	 */
	String[] upgradeAndCurentTowerData_str = { "", "", "", "", "", "", "", "",
			"" };
	/**
	 * holds the string of tower options
	 */
	final static String[] TOWER_OPTIONS_STR = { "Range", "Damage", "Fire Rate",
			"Sell Tower" , "Unselect Tower"};
	/**
	 * stores the finalproject object
	 */
	DataAndGameStart project;

	/**
	 * holds the selected tower
	 */
	Tower selectedObject;
	/**
	 * stores the gameworld
	 */
	GameWorld game;

	public SelectedTowerPanel(DataAndGameStart projectz, GameWorld gamez) {
		game = gamez;
		project = projectz;
		setLayout(new GridLayout(16, 1));

		add(towerName);

		JLabel upgradesTowerLabel = new JLabel("Upgrades for Towers");
		add(upgradesTowerLabel);

		towerOptions = new JButton[TOWER_OPTIONS_STR.length];
		for (int i = 0; i < TOWER_OPTIONS_STR.length; i++) {
			towerOptions[i] = new JButton(TOWER_OPTIONS_STR[i]);
			towerOptions[i].addActionListener(this);
			towerOptions[i].setEnabled(false);
			add(towerOptions[i]);
		}
		upgradeAndCurentTowerData = new JLabel[upgradeAndCurentTowerData_str.length];
		for (int i = 0; i < upgradeAndCurentTowerData_str.length; i++) {
			upgradeAndCurentTowerData[i] = new JLabel(
					upgradeAndCurentTowerData_str[i]);
			add(upgradeAndCurentTowerData[i]);
		}

	}

	/**
	 * sets up the panel when an object is selected
	 * 
	 * @param selectedObjectz
	 *            sends in the selected object
	 */
	public void setSelectedObject(Tower selectedObjectz) {
		selectedObject = selectedObjectz;
		towerName.setText(" " + selectedObjectz.getTowerName());

		for (int i = 0; i < TOWER_OPTIONS_STR.length; i++) {
			towerOptions[i].setEnabled(true);
		}

		setTowerInformationsJLabels();

	}

	/**
	 * sets the tower information to be correct
	 */
	public void setTowerInformationsJLabels() {
		upgradeAndCurentTowerData[1].setText("Range: "
				+ selectedObject.getRange() + "(+"
				+ selectedObject.getDefaultRangezIncrease() + ")");
		upgradeAndCurentTowerData[2].setText("Cost:"
				+ (int) (.20 * selectedObject.getTowerCost()));
		upgradeAndCurentTowerData[3].setText("Damage: "
				+ selectedObject.getDamage() + "(+"
				+ selectedObject.getDefaultDamagezIncrease() + ")");
		upgradeAndCurentTowerData[4].setText("Cost:"
				+ (int) (.20 * selectedObject.getTowerCost()));
		upgradeAndCurentTowerData[5].setText("Fire Rate: "
				+ selectedObject.getFireRate() + "(+"
				+ selectedObject.getDefaultFireRatezIncrease() + ")");
		upgradeAndCurentTowerData[6].setText("Cost:"
				+ (int) (.20 * selectedObject.getTowerCost()));
		upgradeAndCurentTowerData[7].setText("Sells For: "
				+ selectedObject.getTowerCost() * .75);
	}

	/**
	 * makes the panel looks like it unselects the object
	 */
	public void unSelectObject() {
		towerName.setText("  No Tower Selected  ");
		for (int i = 0; i < TOWER_OPTIONS_STR.length; i++) {
			towerOptions[i].setEnabled(false);
		}
		for (int i = 0; i < upgradeAndCurentTowerData.length; i++) {
			upgradeAndCurentTowerData[i].setText("");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();

		if (obj == towerOptions[3]) {
			game.deleteSelectedObjectInTheArray();
		}

		if (obj == towerOptions[0]) {
			game.upgradeTower(0);
		}
		if (obj == towerOptions[1]) {
			game.upgradeTower(1);
		}
		if (obj == towerOptions[2]) {
			game.upgradeTower(2);
		}
		if(obj== towerOptions[4])
		{
			game.unSelectObject();
		}

	}

}
