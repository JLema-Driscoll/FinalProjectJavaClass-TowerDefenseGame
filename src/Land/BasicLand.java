package Land;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import GamePanels.GameWorld;

public class BasicLand {

	/**
	 * used to determine if the land can be built on
	 */
	private boolean canYouBuildHere;

	/**
	 * finds out if a monster can move here
	 */
	private boolean canAMonsterMoveHere;

	/**
	 * used to determine the row and column that the object is on
	 */
	private int row, column;

	

	/**
	 * holds where the land starts used for the monsters
	 */
	private int landStartX, landStartY;
	/**
	 * holds what type of land the game is using
	 */
	private int landType;

	/**
	 * stores all of the possible pictures for the land
	 */
	private Image grassIcon = Toolkit.getDefaultToolkit().getImage("grass.jpg"),
			roadIcon = Toolkit.getDefaultToolkit().getImage("road.jpg");
	/**
	 * stores the land types
	 */
	final int grass = 0, road = 1;
	/**
	 * holds the game world
	 */
	GameWorld world;

	/**
	 * used to set the lands when the map is loaded
	 */
	private boolean loaded;

	/**
	 * creates a basic land
	 * 
	 * @param worldz
	 *            sends in the world
	 * @param landTypez
	 *            sends in the land type
	 * @param rowz
	 *            sends in the row
	 * @param columnz
	 *            sendsin the column
	 */
	public BasicLand(GameWorld worldz, int landTypez, int rowz, int columnz) {
		world = worldz;
		row = rowz;
		column = columnz;
		landType = landTypez;
	}

	/**
	 * Draws the land
	 * 
	 * @param myContext
	 *            sends in the graphic stuff
	 * @param width
	 *            sends in the width of the app
	 * @param height
	 *            sends in the height of the app
	 * @param numberOfRows
	 *            sends in the total number of rows
	 * @param numberOfColumns
	 *            sends in the total number of columns
	 */
	public void draw(Graphics myContext, int width, int height,
			int numberOfRows, int numberOfColumns) {

		// prints the image to fill one box
		int startX = (int) (column * ((double) width / numberOfColumns));
		int startY = (int) (row * ((double) height / numberOfRows));
		int widthOfIcon = (width / numberOfColumns) + 1;
		int heightOfIcon = (height / numberOfRows) + 1;
		landStartX = startX;
		landStartY = startY;
		if (landType == grass) {
			myContext.drawImage(grassIcon, startX, startY, widthOfIcon,
					heightOfIcon, world);
			if (!loaded) {
				canYouBuildHere = true;
				canAMonsterMoveHere = false;
				loaded = true;
			}
		} else if (landType == road) {
			myContext.drawImage(roadIcon, startX, startY, widthOfIcon,
					heightOfIcon, world);
			if (!loaded) {
				canYouBuildHere = false;
				canAMonsterMoveHere = true;
				loaded = true;
			}
		}

	}

	/**
	 * gets if the user can build
	 * 
	 * @return returns if the user can build or not
	 */
	public boolean getCanYouBuildHere() {
		return canYouBuildHere;
	}

	/**
	 * sets if the user can build there or not
	 * 
	 * @param canYouBuildHere
	 *            sends in if the user can build there or not
	 */
	public void setCanYouBuildHere(boolean canYouBuildHerez) {
		canYouBuildHere = canYouBuildHerez;
	}

	/**
	 * gets if a monster can move here
	 * 
	 * @return returns if a monster can move here
	 */
	public boolean getCanAMonsterMoveHere() {
		return canAMonsterMoveHere;
	}

	/**
	 * sets if the monster can move here
	 * 
	 * @param canAMonsterMoveHere
	 *            sends in if the user can move there or not
	 */
	public void setCanAMonsterMoveHere(boolean canAMonsterMoveHerez) {
		canAMonsterMoveHere = canAMonsterMoveHerez;
	}

	/**
	 * @return the landStartX
	 */
	public int getLandStartX() {
		return landStartX;
	}

	/**
	 * @return the landStartY
	 */
	public int getLandStartY() {
		return landStartY;
	}
}
