package Bullets;

import java.awt.Color;
import java.awt.Graphics;

import Monsters.BasicMonster;
import Towers.Tower;

public abstract class Bullet implements Runnable {
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private double xPos = 0, yPos = 0;
	private double vx, vy;
	private int bulletTimer = 500;
	private int damageWidth;

	private int damageHeight;

	private Tower tower;
	private BasicMonster target;
	/**
	 * target will be alive when a bullet is made 
	 */
	private Boolean targetIsAlive=true;
	/**
	 * constructor for bullet
	 * 
	 * @param towerz
	 * @param bulletSpeed
	 */
	public Bullet(Tower towerz, double startX, double startY, double dx,
			double dy, int damageWidthz, int damageHeightz, BasicMonster targetz) {
		xPos = startX;
		yPos = startY;
		vx = dx;
		vy = dy;
		tower = towerz;
		damageWidth = damageWidthz;
		damageHeight = damageHeightz;
		target = targetz;
		target.addToIncomingBullets(this);
	}

	/**
	 * runs the bullet
	 */
	public void run() {
		boolean isAlive = true;
		while (isAlive) {
			
			if(targetIsAlive)
			{
				monsterChecker();
			}
			xPos += vx;
			yPos += vy;
			bulletTimer--;
			
			if (tower.getWorld().withinBounds(xPos, yPos, this)
					&& (bulletTimer > 0)) {
				
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} else {
				isAlive = false;
				tower.reportBulletEndOfLife(this);
			}
		}
	
	}

	public void monsterChecker() {

		
		double xDistance = target.getCenterX() - getxPos();
		double yDistance = target.getCenterY() - getyPos();

		double speed = getTower().getMaxBulletSpeed();

		double scale = speed
				/ Math.sqrt(xDistance * xDistance + yDistance * yDistance);

		double vx = scale * xDistance;
		double vy = scale * yDistance;

		setVx(vx);
		setVy(vy);

	}

	public abstract Color getBulletColor();

	public void draw(Graphics g) {
		if (tower.getTowerName().equals("Mortar")) {

			g.setColor(getColorOfRange());
			g.fillOval((int) (xPos - damageWidth) + 7,
					(int) (yPos - damageHeight) + 7,
					(int) (damageWidth * 2) - 14, (int) (damageHeight * 2) - 14);

		}

		g.setColor(getBulletColor());
		g.fillOval((int) (xPos - WIDTH / 2), (int) (yPos - HEIGHT / 2), WIDTH,
				HEIGHT);

	}

	public abstract Color getColorOfRange();

	/**
	 * @return the damageWidth
	 */
	public int getDamageWidth() {
		return damageWidth;
	}

	/**
	 * @return the damageHeight
	 */
	public int getDamageHeight() {
		return damageHeight;
	}

	/**
	 * @return the xPos
	 */
	public double getxPos() {
		return xPos;
	}

	/**
	 * @return the yPos
	 */
	public double getyPos() {
		return yPos;
	}

	/**
	 * @param targetIsAlive the targetIsAlive to set
	 */
	public void setTargetIsAlive(Boolean targetIsAlive) {
		this.targetIsAlive = targetIsAlive;
	}

	/**
	 * @param vx
	 *            the vx to set
	 */
	public void setVx(double vx) {
		this.vx = vx;
	}

	/**
	 * @param vy
	 *            the vy to set
	 */
	public void setVy(double vy) {
		this.vy = vy;
	}

	/**
	 * @return the tower
	 */
	public Tower getTower() {
		return tower;
	}
}
