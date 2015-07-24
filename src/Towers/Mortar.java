/**
 * 
 */
package Towers;

import java.awt.Color;
import java.awt.Graphics;

import GamePanels.GameWorld;

/**
 * @author Jeremy Driscoll
 * 
 */
public class Mortar extends Tower {
	/**
	 * holds the default range of this type of tower
	 */
	static int rangez = 6;

	/**
	 * holds the default damage of this type of tower
	 */
	static int damagez = 15;
	/**
	 * holds the default fire rate for this tower
	 */
	static int fireRatez = 100;
	/**
	 * holds the default cost of this type of tower
	 */
	static int cost = 200;

	/**
	 * holds the default range increase when upgraded for this object
	 */
	static int defaultRangezIncrease=1;
	/**
	 * holds the default damage increase when upgraded for this object
	 */
	static int defaultDamagezIncrease=5;
	/**
	 * holds the default fire rate increase when upgraded for this object
	 */
	static int defaultFireRatezIncrease=-1;

	/**
	 * 
	 * @param rowz
	 * @param columnz
	 */
	public Mortar(int rowz, int columnz, GameWorld worldz) {
		super(rowz, columnz, rangez, damagez, cost, fireRatez,
				defaultRangezIncrease, defaultDamagezIncrease,
				defaultFireRatezIncrease, "Mortar",  worldz);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Draws the basicTower
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
	public void draw(Graphics g, int width, int height, int numberOfRows,
			int numberOfColumns) {
	
		super.draw(g, width, height, numberOfRows, numberOfColumns);
	}
	public Color getColorOfRange()
	{
		float rbg = (float) .08;
		float red = (float) 0.01;
		float green = (float) .99;
		float blue = (float) .001;
			Color rangeColor = new Color(red, green, blue, rbg);
			return rangeColor;
	}
	public Color getTowerColor()
	{
		return Color.GREEN;
	}
}
