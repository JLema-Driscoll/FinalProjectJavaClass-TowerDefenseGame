package MapMakerPanels;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import MapMakerz.MapMaker;

/**
 * makes the control panel
 * 
 * @author Jeremy Driscoll
 * 
 */
public class ControlPanel implements ActionListener {

	/**
	 * creates the select shape combo box
	 */
	JComboBox selectLandType;


	/**
	 * Creates the normal buttons
	 * 
	 */
	JButton[] button;
	
	/**
	 * holds the current selected land
	 */
	int currentSelectedLand;
	
	/**
	 * creates the string that holds all the button names
	 */
	static final String[] BUTTON_STR = {  " Save Map  ",
			" Load Map  " };
	
	
	
	/**
	 * creates the string of the shape options
	 */
	static final String[] LAND_OPTIONS_STR = { "Grass", "Road" };
	
	/**
	 * holds the lab 8
	 */
	MapMaker q;
	/**
	 * holds in the world
	 */
	GameWorld world;

	/**
	 * initializes the control panel
	 * 
	 * @param lab
	 *            sends in the world
	 */
	public ControlPanel(MapMaker lab) {
		q = lab;
	}

	/**
	 * creates the control panel
	 * 
	 * @return returns the panel for the controls
	 */
	public JPanel createControls() {
		// gives the control panel space for 22 buttons in a row and creates the
		// panel
		JPanel controlPanel = new JPanel(new GridLayout(10, 1));

		// Creates the jcombo box that is used for the shapes
		selectLandType = new JComboBox(LAND_OPTIONS_STR);
		selectLandType.addActionListener(this);
		selectLandType.setEditable(false);

	
		controlPanel.add(selectLandType);

		// Creates the normal buttons
		button = new JButton[BUTTON_STR.length];
		for (int i = 0; i < BUTTON_STR.length; i++) {
			button[i] = new JButton(BUTTON_STR[i]);
			button[i].addActionListener(this);
			controlPanel.add(button[i]);
		}

		
		return controlPanel;
	}

	/**
	 * gets the current selected land
	 * 
	 * @return returns the land
	 */
	public int getCurrentSelectedLand() {
		return currentSelectedLand;
	}

	/**
	 * imports in the game world
	 * 
	 * @param z
	 *            gameworld
	 */
	public void importGameWorld(GameWorld z) {
		world = z;
	}



	/**
	 * gets what happens when a button is hit
	 */
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
	
		if (obj == button[0]) {
			world.saveToFile();
		}
		if (obj == button[1]) {
			world.loadFile();
		}
		
		// deals with the land
		String w = (String) selectLandType.getSelectedItem();
		if (w.equals("Grass")) {
			currentSelectedLand = 0;
		} else if (w.equals("Road")) {
			currentSelectedLand = 1;
		} else if (w.equals("River")) {
			currentSelectedLand = 2;
		} else if (w.equals("Mountain")) {
			currentSelectedLand = 3;
		} else {
			currentSelectedLand = 4;
		}
	}
}
