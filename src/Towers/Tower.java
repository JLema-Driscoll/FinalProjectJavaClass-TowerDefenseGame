package Towers;

import Bullets.BasicBullet;
import Bullets.Bullet;
import Bullets.MortarBullet;
import GamePanels.GameWorld;
import Monsters.BasicMonster;
import WaveRuner.RunWave;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.Timer;

public abstract class Tower implements Runnable {
	/**
	 * holds fire speed
	 */
	int fireSpeed;
	/**
	 * holds the radius
	 */
	int radius = 0;
	
	/**
	 * holds if the sound should be played or not
	 */
	boolean soundIsOn=true;
	/** 
	 * holds the music
	 */
	AudioClip towerShooter;
	/**
	 * holds the damage a tower does per second
	 */
	private int damage;

	/**
	 * decides whether to draw the range or not
	 */
	private boolean transparent;
	/**
	 * holds the towers location
	 */
	private int row, column;

	/**
	 * holds the towers range
	 */
	private int range;

	// private monsters[];

	/**
	 * holds the towers cost
	 */
	int towerCost;

	/**
	 * holds the towers type
	 */
	String towerType;

	/**
	 * holds the fire rate for the tower
	 */
	int fireRate;

	/**
	 * holds the default range increase when upgraded for this object
	 */
	int defaultRangezIncrease;
	/**
	 * holds the default damage increase when upgraded for this object
	 */
	int defaultDamagezIncrease;
	/**
	 * holds the default fire rate increase when upgraded for this object
	 */
	int defaultFireRatezIncrease;
	BasicMonster monster;
	GameWorld world;
	BasicMonster monsterTarget;
	Vector<Bullet> bullet;

	/**
	 * @return the thread
	 */
	public Vector<Thread> getThread() {
		return thread;
	}

	Vector<Thread> thread;
	double startX, startY;
	public static final double MAX_BULLET_SPEED = 20;
	int centerX = 0;
	int centerY = 0;

	/**
	 * 
	 * @param rowz
	 * @param columnz
	 * @param rangez
	 * @param damagez
	 * @param cost
	 * @param fireRatez
	 * @param defaultRangezIncreasez
	 * @param defaultDamagezIncreasez
	 * @param defaultFireRatezIncreasez
	 * @param towerTypez
	 */
	public Tower(int rowz, int columnz, int rangez, int damagez, int cost,
			int fireRatez, int defaultRangezIncreasez,
			int defaultDamagezIncreasez, int defaultFireRatezIncreasez,
			String towerTypez, GameWorld worldz) {
		row = rowz;
		column = columnz;
		range = rangez;
		damage = damagez;
		towerCost = cost;
		towerType = towerTypez;
		fireRate = fireRatez;
		defaultRangezIncrease = defaultRangezIncreasez;
		defaultDamagezIncrease = defaultDamagezIncreasez;
		defaultFireRatezIncrease = defaultFireRatezIncreasez;
		world = worldz;
		fireSpeed = fireRatez;
	}

	/**
	 * runs the program
	 */
	public void run() {

		// Thread bulletThread;

		int count = 0;
		bullet = new Vector<Bullet>();
		thread = new Vector<Thread>();
		// bulletThread = new Thread(bullet);
		while (true) {
			{
				count++;
				if (count % fireSpeed == 0) {
					BasicMonster monster = isMonsterInRange();
					monsterTarget = monster;
					if (monster != null) {

						double xDistance = monster.getCenterX() - centerX;
						double yDistance = monster.getCenterY() - centerY;

						double speed = MAX_BULLET_SPEED;

						double scale = speed
								/ Math.sqrt(xDistance * xDistance + yDistance
										* yDistance);

						double vx = scale * xDistance;
						double vy = scale * yDistance;
						URL url1=null;
						try{
							url1 = new URL ("file", "localhost", "TowerShooter.wav");
							towerShooter = JApplet.newAudioClip(url1);			

						}catch(Exception e){
							//	do something in case of problem
						}
						if(soundIsOn)
						{
						towerShooter.play();
						}
						createBullet(startX, startY, vx, vy);
					}
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * draws the tower
	 * 
	 * @param g
	 *            sends in the graphics
	 */
	public synchronized void draw(Graphics g, int width, int height,
			int numberOfRows, int numberOfColumns) {

		centerX = column * width / numberOfColumns
				+ (width / numberOfColumns / 2);
		centerY = row * height / numberOfRows + (height / numberOfRows / 2);
		startX = (column + 0.5) * (1.0 * width / numberOfColumns);
		startY = (row + 0.5) * (1.0 * height / numberOfRows);

		int widthOfIcon = (width / numberOfColumns) + 1;
		int heightOfIcon = (height / numberOfRows) + 1;
		radius = widthOfIcon * getRange() - 1 + widthOfIcon / 2;

		int startX = (int) (getColumn() * ((double) width / numberOfColumns));
		int startY = (int) (getRow() * ((double) height / numberOfRows));
		if (getTransparent()) {
			startX = startX - (widthOfIcon * getRange());
			startY = startY - (heightOfIcon * getRange());

			g.setColor(getColorOfRange());
			g.fillOval(startX, startY, widthOfIcon * (getRange() * 2 + 1),
					heightOfIcon * (getRange() * 2 + 1));
			g.setColor(Color.BLACK);
			g.drawOval(startX, startY, widthOfIcon * (getRange() * 2 + 1),
					heightOfIcon * (getRange() * 2 + 1));

		}

		// prints the image to fill one box
		startX = (int) (getColumn() * ((double) width / numberOfColumns));
		startY = (int) (getRow() * ((double) height / numberOfRows));

		g.setColor(getTowerColor());
		g.fillOval(startX, startY, widthOfIcon, heightOfIcon);

		if (bullet != null) {
			for (int i = 0; i < bullet.size(); i++) {
				bullet.get(i).draw(g);
			}
		}
	}

	public abstract Color getColorOfRange();

	public abstract Color getTowerColor();

	/**
	 * @param bullet
	 *            the bullet to set
	 */
	public void setBullet(Vector<Bullet> bullet) {
		this.bullet = bullet;
	}

	/**
	 * @param soundIsOn the soundIsOn to set
	 */
	public void setSoundIsOn(boolean soundIsOn) {
		this.soundIsOn = soundIsOn;
	}

	public synchronized void reportBulletEndOfLife(Bullet bullet2) {
		int index = bullet.indexOf(bullet2);
		try {
			bullet.remove(index);
			thread.remove(index);
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}

	private synchronized void createBullet(double x, double y, double vx,
			double vy) {

		Bullet b = new BasicBullet(this, x, y, vx, vy, monsterTarget);
		if (towerType.equals("Baisc Tower")
				|| towerType.equals("Rapid Fire Tower")) {
			b = new BasicBullet(this, x, y, vx, vy, monsterTarget);
		} else if (towerType.equals("Mortar")) {
			b = new MortarBullet(this, x, y, vx, vy, monsterTarget);
		}

		bullet.add(b);
		Thread t = new Thread(b);
		thread.add(t);
		t.start();
	}

	/**
	 * @return the world
	 */
	public GameWorld getWorld() {
		return world;
	}

	public void setMonsters(BasicMonster monsterz) {
		monster = monsterz;
	}

	/**
	 * @return the monster
	 */
	public BasicMonster getMonster() {
		return monster;
	}

	/**
	 * gets the towers name
	 * 
	 * @return returns the towers name
	 */
	public String getTowerName() {
		return towerType;
	}

	/**
	 * gets the cost
	 * 
	 * @return returns the cost
	 */
	public int getTowerCost() {
		return towerCost;
	}

	/**
	 * gets the damage
	 * 
	 * @return
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * sets the damage
	 * 
	 * @param damage
	 *            sends in the new damage
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * gets the row
	 * 
	 * @return returns the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * sets the row
	 * 
	 * @param row
	 *            sends in the new row
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * gets the column
	 * 
	 * @return returns the column number
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * sets the column
	 * 
	 * @param column
	 *            sends in the new column number
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * gets the range
	 * 
	 * @return returns the range
	 */
	public int getRange() {
		return range;
	}

	/**
	 * sets the range
	 * 
	 * @param range
	 *            returns the range
	 */
	public void setRange(int range) {
		this.range = range;
	}

	/**
	 * @return the bullet
	 */
	public Vector<Bullet> getBullet() {
		return bullet;
	}

	/**
	 * gets the transparency
	 * 
	 * @return returns if it is transparent or not
	 */
	public boolean getTransparent() {
		return transparent;
	}

	/**
	 * sets the transparency
	 * 
	 * @param transparent
	 *            sends in what to set the transparency to
	 */
	public void setTransparent(boolean transparentz) {
		transparent = transparentz;
	}

	/**
	 * @param towerCost
	 *            the towerCost to set
	 */
	public void setTowerCost(int towerCost) {
		this.towerCost = towerCost;
	}

	/**
	 * @return the fireRate
	 */
	public int getFireRate() {
		return fireRate;
	}

	/**
	 * @param fireRate
	 *            the fireRate to set
	 */
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}

	/**
	 * @return the defaultRangezIncrease
	 */
	public int getDefaultRangezIncrease() {
		return defaultRangezIncrease;
	}

	/**
	 * @param defaultRangezIncrease
	 *            the defaultRangezIncrease to set
	 */
	public void setDefaultRangezIncrease(int defaultRangezIncrease) {
		this.defaultRangezIncrease = defaultRangezIncrease;
	}

	/**
	 * @return the defaultDamagezIncrease
	 */
	public int getDefaultDamagezIncrease() {
		return defaultDamagezIncrease;
	}

	/**
	 * @param defaultDamagezIncrease
	 *            the defaultDamagezIncrease to set
	 */
	public void setDefaultDamagezIncrease(int defaultDamagezIncrease) {
		this.defaultDamagezIncrease = defaultDamagezIncrease;
	}

	/**
	 * @return the defaultFireRatezIncrease
	 */
	public int getDefaultFireRatezIncrease() {
		return defaultFireRatezIncrease;
	}

	/**
	 * @return the maxBulletSpeed
	 */
	public static double getMaxBulletSpeed() {
		return MAX_BULLET_SPEED;
	}

	/**
	 * @param defaultFireRatezIncrease
	 *            the defaultFireRatezIncrease to set
	 */
	public void setDefaultFireRatezIncrease(int defaultFireRatezIncrease) {
		this.defaultFireRatezIncrease = defaultFireRatezIncrease;
	}

	public BasicMonster isMonsterInRange() {
		return world.withInRange(centerX, centerY, radius);
	}
}
