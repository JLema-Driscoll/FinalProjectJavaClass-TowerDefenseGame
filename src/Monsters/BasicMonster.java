package Monsters;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import Bullets.Bullet;
import GamePanels.GameWorld;
import Land.BasicLand;

public class BasicMonster implements Runnable {

	int killReward = 20;

	/**
	 * holds where the start of the monster will be drawn for x
	 */
	int moveX;
	/**
	 * holds where the start of the monster will be drawn for y
	 */
	int moveY;
	/**
	 * holds all the lands
	 */
	BasicLand allLand[][];
	/**
	 * holds if the monster is in the center of a piece of land
	 */
	boolean centerOfALand = true;

	/**
	 * holds the direction the monster is going
	 */
	int direction;

	/**
	 * used to easily identify the direction the monster is going
	 */
	int north = 0, east = 1, south = 2, west = 3;

	/**
	 * holds the row and column
	 */
	int row, column;

	/**
	 * a monster starts out alive
	 */
	boolean isAlive = true;

	/**
	 * holds the speed of the monster. The lower the number the fast it goes
	 */
	long speedOfMonster = 20;
	/**
	 * holds the game world
	 */
	GameWorld world;
	
	private Vector<Bullet> incomingBullets= new Vector<Bullet>();

	int health = 100;
	int centerX;
	int centerY;
	private int boundingBoxBotX = 0;
	private int boundingBoxBotY = 0;

	int locationX = 0;
	int locationY = 0;

	/**
	 * 
	 * @param row
	 * @param column
	 * @param allLandz
	 */
	public BasicMonster(int row, int column, BasicLand allLandz[][],
			int healthz, GameWorld worldz) {
		allLand = allLandz;
		world = worldz;
		health = healthz;
	}

	/**
	 * sets its next move
	 */
	public void getNextMove() {

		if (allLand[row][column].getLandStartX() == moveX
				&& allLand[row][column].getLandStartY() == moveY) {
			centerOfALand = true;
		}
		if (centerOfALand) {

			while (isAlive) {
				if (direction == north) {
					if (row - 1 > -1) {
						if (allLand[row - 1][column].getCanAMonsterMoveHere()) {
							direction = north;
							centerOfALand = false;
							row--;
							break;
						}
					}
					if (column - 1 > -1) {
						if (allLand[row][column - 1].getCanAMonsterMoveHere()) {
							direction = west;
							centerOfALand = false;
							column--;
							break;
						}
					}
					if (column + 1 < 25) {
						if (allLand[row][column + 1].getCanAMonsterMoveHere()) {
							direction = east;
							centerOfALand = false;
							column++;
							break;
						}
					}
				}

				if (direction == east) {

					if (column + 1 < 25) {
						if (allLand[row][column + 1].getCanAMonsterMoveHere()) {
							direction = east;
							centerOfALand = false;
							column++;
							break;
						}
					}
					if (row - 1 > -1) {
						if (allLand[row - 1][column].getCanAMonsterMoveHere()) {
							direction = north;
							centerOfALand = false;
							row--;
							break;
						}
					}
					if (row + 1 < 25) {
						if (allLand[row + 1][column].getCanAMonsterMoveHere()) {
							direction = south;
							centerOfALand = false;
							row++;
							break;
						}
					}

					break;

				} else if (direction == south) {
					if (row + 1 < 25) {
						if (allLand[row + 1][column].getCanAMonsterMoveHere()) {
							direction = south;
							centerOfALand = false;
							row++;
							break;
						}
					}
					if (column - 1 > -1) {
						if (allLand[row][column - 1].getCanAMonsterMoveHere()) {
							direction = west;
							centerOfALand = false;
							column--;
							break;
						}
					}
					if (column + 1 < 25) {
						if (allLand[row][column + 1].getCanAMonsterMoveHere()) {
							direction = east;
							centerOfALand = false;
							column++;
							break;
						}
					}

				} else if (direction == west) {

					if (column - 1 > -1) {
						if (allLand[row][column - 1].getCanAMonsterMoveHere()) {
							direction = west;
							centerOfALand = false;
							column--;
							break;
						}
					}
					if (row - 1 > -1) {
						if (allLand[row - 1][column].getCanAMonsterMoveHere()) {
							direction = north;
							centerOfALand = false;
							row--;
							break;
						}
					}
					if (row + 1 < 25) {
						if (allLand[row + 1][column].getCanAMonsterMoveHere()) {
							direction = south;
							centerOfALand = false;
							row++;
							break;
						}
					}
				}

			}

		}

		if (direction == north) {
			moveY--;
		} else if (direction == east) {
			moveX++;
		} else if (direction == south) {
			moveY++;
		} else if (direction == west) {
			moveX--;
		}

	}

	public void addToIncomingBullets(Bullet incoming)
	{
		incomingBullets.add(incoming);
	}
	
	public void letIncomingBulletsTargetsDead()
	{
		for(int i=0; i<incomingBullets.size();i++)
		{
			if(incomingBullets.get(i)!=null)
			{
				incomingBullets.get(i).setTargetIsAlive(false);
			}
		}
	}
	
	
	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Draws the object
	 * 
	 * @param g
	 * @param width
	 * @param height
	 * @param numberOfRows
	 * @param numberOfColumns
	 */
	public void draw(Graphics g, int width, int height, int numberOfRows,
			int numberOfColumns) {
		int widthOfIcon = (width / numberOfColumns) + 1;
		int heightOfIcon = (height / numberOfRows) + 1;
		findBottomOfBoundingBox(widthOfIcon, heightOfIcon);
		int startX = moveX;
		int startY = moveY;
		centerX = startX + (widthOfIcon / 2);
		centerY = startY + (heightOfIcon / 2);
		locationX = startX;
		locationY = startY;
		g.setColor(Color.black);
		if(health<=160)
		{
			g.setColor(Color.GREEN);
		}
		if(health<=120)
			g.setColor(Color.BLUE);
		if (health <= 80)
			g.setColor(Color.YELLOW);
		if (health <= 40)
			g.setColor(Color.RED);
		g.fillRect(startX, startY, widthOfIcon, heightOfIcon);
	}

	/**
	 * @return the killReward
	 */
	public int getKillReward() {
		return killReward;
	}

	/**
	 * @param killReward
	 *            the killReward to set
	 */
	public void setKillReward(int killReward) {
		this.killReward = killReward;
	}

	/**
	 * makes the monster move
	 */
	public void run() {

		while (true) {
			if (isAlive) {
				getNextMove();

				try {
					Thread.sleep(speedOfMonster);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * @param health
	 *            the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	public void findBottomOfBoundingBox(int widthOfIcon, int heightOfIcon) {
		boundingBoxBotX = moveX + widthOfIcon;
		boundingBoxBotY = moveY + heightOfIcon;
	}

	/**
	 * @return the moveX
	 */
	public int getMoveX() {
		return moveX;
	}

	/**
	 * @return the moveY
	 */
	public int getMoveY() {
		return moveY;
	}

	/**
	 * @return the centerX
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * @return the centerY
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * @return the boundingBoxBotX
	 */
	public int getBoundingBoxBotX() {
		return boundingBoxBotX;
	}

	/**
	 * @return the boundingBoxBotY
	 */
	public int getBoundingBoxBotY() {
		return boundingBoxBotY;
	}
}
