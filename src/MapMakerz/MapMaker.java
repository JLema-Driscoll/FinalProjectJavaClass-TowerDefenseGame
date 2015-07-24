package MapMakerz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;

import MapMakerPanels.ControlPanel;
import MapMakerPanels.GameWorld;
import MapMakerPanels.MenuBar;

/**
 * Deals with the world
 * 
 * @author Jeremy Driscoll
 * 
 */
public class MapMaker {

	/**
	 * the main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		MapMaker gworld = new MapMaker("Game World", 600, 400);

	}

	int numberOfRows = 25, numberOfColumns = 25;

	/**
	 * creates JFrame
	 * 
	 */
	JFrame myFrame;
	/**
	 * Initializes the menu bar
	 * 
	 */
	private MenuBar menu;
	/**
	 * holds the control panel
	 */
	private ControlPanel controls;
	/**
	 * holds the center panel / the game world
	 */
	private GameWorld centerPanel;
	

	/**
	 * Creates the world
	 * 
	 * @param theTitle
	 *            Gives the world a title
	 * @param theWidth
	 *            sends in the width
	 * @param theHeight
	 *            sends in the height
	 */
	public MapMaker(String theTitle, int theWidth, int theHeight) {

		// magic thing :D
		super();

		menu = new MenuBar(this);
		controls = new ControlPanel(this);
		
		// Sets the layout
		layoutSetup(theTitle, theWidth, theHeight);

		// sets the frame to be visable
		myFrame.setVisible(true);

	}

	/**
	 * Sets up the applet
	 * 
	 * @param theTitle
	 *            Sends in the title
	 * @param theWidth
	 *            Sends in the width the applet should be
	 * @param theHeight
	 *            sends in the height the applet should be
	 */
	public void layoutSetup(String theTitle, int theWidth, int theHeight) {

		// Creates the frame and stuff
		myFrame = new JFrame(theTitle) {
			private static final long serialVersionUID = 1L;

			// paints the objects
			public void paint(Graphics g) {
				paintComponents(g);
			}
		};
		// closes the application if the exit button is hit
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// sets width and height
		myFrame.setSize(theWidth, theHeight);
		// Makes the applet have a border layout
		myFrame.setLayout(new BorderLayout());
		// Creates the center of the border layout

		centerPanel = new GameWorld(this, numberOfRows, numberOfColumns,
				myFrame, controls);

		myFrame.add(centerPanel, BorderLayout.CENTER);

		// myFrame.add(centerPanel, BorderLayout.WEST);
		// creates and adds the controls
		myFrame.add(controls.createControls(), BorderLayout.EAST);
		// creates and adds the menu
		myFrame.add(menu.createControls(), BorderLayout.NORTH);
		// commands

		

		controls.importGameWorld(centerPanel);
	}


}