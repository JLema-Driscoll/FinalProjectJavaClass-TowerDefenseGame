package MapMakerPanels;

import java.awt.Graphics;
import java.awt.GridLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import LandObjects_MapMaker.Land;
import MapMakerz.MapMaker;
import MapMakerz.MapSaverAndLoader;

/**
 * the game world
 * 
 * @author Jeremy Driscoll
 * 
 */
public class GameWorld extends JPanel implements MouseListener,
		MouseMotionListener {

	/**
	 * stuff
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * counts stuff
	 */
	int counter = 0;
	/**
	 * holds the mainframe
	 */
	JFrame mainFrame;
	/**
	 * holds the lab
	 */
	MapMaker labz;
	/**
	 * holds all the land
	 */
	Land[][] allLands;
	/**
	 * holds the control panel
	 */
	ControlPanel controls;
	/**
	 * holds the total number of rows and columns
	 */
	int numberOfRows, numberOfColumns;
	/**
	 * holds the map saver and loader
	 */
	private MapSaverAndLoader mapLoadandSaver;

	/**
	 * initializes the frame
	 * 
	 * @param lab
	 * @param numberOfRowz
	 * @param numberOfColumnz
	 * @param myFrame
	 * @param control
	 * @param commandz
	 */
	public GameWorld(MapMaker lab, int numberOfRowz, int numberOfColumnz,
			JFrame myFrame, ControlPanel control) {

		labz = lab;
		mainFrame = myFrame;
		controls = control;
		numberOfRows = numberOfRowz;
		numberOfColumns = numberOfColumnz;
		allLands = new Land[numberOfRows][numberOfColumns];
		// adds the mouseListener
		addMouseListener(this);
		addMouseMotionListener(this);
		// draws rows

		makeLands();

		mapLoadandSaver = new MapSaverAndLoader(this);

	}

	/**
	 * loads a file for the map
	 */
	public void loadFile() {
		mapLoadandSaver.readFile();
	}

	/**
	 * saves a map file
	 */
	public void saveToFile() {
		mapLoadandSaver.writeList(numberOfRows, numberOfColumns, allLands);
	}

	/**
	 * loads the game but not really
	 */
	public void loadGameFile() {
		// gameSaverAndLoaderz.readFile();
	}

	/**
	 * draws the grid and objects
	 * 
	 * @param g
	 *            sends in the graphics package thing
	 */
	public void draw(Graphics g) {

		

		for (int i = 0; i < numberOfRows; i++) {
			for (int z = 0; z < numberOfColumns; z++) {
				if (allLands[i][z] != null) {
					allLands[i][z].draw(g, getWidth(), getHeight(),
							numberOfRows, numberOfColumns);
				}
			}
		}
// draws rows
		for (int x = 0; x < numberOfRows; x++) {
			g.drawLine(0, (int) (((double) getHeight() / numberOfRows) * x),
					getWidth(),
					(int) (((double) getHeight() / numberOfRows) * x));
		}
		// draws columns
		for (int y = 0; y < numberOfColumns; y++) {
			g.drawLine((int) (((double) getWidth() / numberOfColumns) * y), 0,
					(int) (((double) getWidth() / numberOfColumns) * y),
					getHeight());
		}
		mainFrame.repaint();
	}

	/**
	 * gets the control panel
	 * 
	 * @return sends out the control panel
	 */
	public ControlPanel getControls() {
		return controls;
	}

	/**
	 * paints things and sets the monsters visablities of mosnters
	 */
	public void paintComponent(Graphics g) {

		draw(g);

	}

	/**
	 * makes the land
	 */
	public void makeLands() {
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				allLands[row][column] = new Land(this, -1, row, column);
			}
		}

	}

	/**
	 * changes the land
	 * 
	 * @param row
	 *            sends in the row of the land
	 * @param column
	 *            sends in the column of the land
	 */
	public void changeLands(int row, int column) {
		allLands[row][column].setLandType(getLandType());
		repaint();
	}

	/**
	 * gets the lands type
	 * 
	 * @return returns the lands type
	 */
	public int getLandType() {

		return controls.getCurrentSelectedLand();
	}

	/**
	 * sets the lands type and repaints
	 * 
	 * @param set
	 *            sends in what to set it as
	 */
	public void setLand(Land set[][]) {
		allLands = set;
		repaint();
	}

	

	

	/**
	 * if the mouse is clicked land is made in edit mode
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX(), y = e.getY();

		// Creates the counters for the rows and columns
		int columnNumber = 0, rowNumber = 0;

		// finds the column that the click is in
		for (int w = 1; w < numberOfColumns + 1; w++) {
			if (x < (int) (((double) getWidth() / numberOfColumns) * w)) {
				columnNumber = w - 1;
				break;
			}
		}
		// finds the row the click is in
		for (int w = 1; w < numberOfRows + 1; w++) {
			if (y < (int) (((double) getHeight() / numberOfRows) * w)) {
				rowNumber = w - 1;
				break;
			}
		}

			changeLands(rowNumber, columnNumber);
		
		mainFrame.repaint();

	}

	



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * paints the land if the mouse is dragged
	 */
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX(), y = e.getY();

		// Creates the counters for the rows and columns
		int columnNumber = 0, rowNumber = 0;

		// finds the column that the click is in
		for (int w = 1; w < numberOfColumns + 1; w++) {
			if (x < (int) (((double) getWidth() / numberOfColumns) * w)) {
				columnNumber = w - 1;
				break;
			}
		}
		// finds the row the click is in
		for (int w = 1; w < numberOfRows + 1; w++) {
			if (y < (int) (((double) getHeight() / numberOfRows) * w)) {
				rowNumber = w - 1;
				break;
			}
		}
		
			changeLands(rowNumber, columnNumber);
		
		mainFrame.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
