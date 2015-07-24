package MapMakerPanels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import MapMakerz.MapMaker;

/**
 * makes the menu
 * 
 * @author Jeremy Driscoll
 * 
 */
public class MenuBar implements ActionListener {
	/**
	 * creates the menu bars string
	 */
	private final String[] MENU_STR = { "File" };
	/**
	 * creates the exit option string
	 */
	private final String[] FILE_OPTIONS_STR = { "Exit" };

	/**
	 * creates the file options menu
	 */
	private JMenuItem[] FILE_OPTION;
	/**
	 * creates the edit options menu
	 */
	private JMenuItem[] EDIT_OPTIONS;

	/**
	 * stores the world
	 */
	private MapMaker q;

	/**
	 * initalizes the menu
	 * 
	 * @param lab
	 *            sends in the world
	 */
	public MenuBar(MapMaker lab) {
		q = lab;
	}

	/**
	 * sets up the menu bar
	 * 
	 * @return returns the menu bar
	 */
	public JMenuBar createControls() {

		// creates the menu bar
		JMenuBar menuBar = new JMenuBar();
		// creates the jmenu edit
		FILE_OPTION = new JMenuItem[FILE_OPTIONS_STR.length];

		// creates the menu options for edit and file
		for (int i = 0; i < MENU_STR.length; i++) {
			JMenu menu = new JMenu(MENU_STR[i]);
			if (i == 0) {
				for (int x = 0; x < FILE_OPTIONS_STR.length; x++) {
					FILE_OPTION[x] = new JMenuItem(FILE_OPTIONS_STR[x]);
					menu.add(FILE_OPTION[x]);
					FILE_OPTION[x].addActionListener(this);
				}
			}

			// adds them to the menu bar
			menuBar.add(menu);
		}

		// returns the menu bar
		return menuBar;

	}

	/**
	 * finds out what was hit in the menu
	 */
	public void actionPerformed(ActionEvent e) {
		// gets the object that was hit
		Object obj = e.getSource();
		// Exits the program when exit is hit
		if (obj == FILE_OPTION[0]) {
			System.exit(0);
		}

	}
}