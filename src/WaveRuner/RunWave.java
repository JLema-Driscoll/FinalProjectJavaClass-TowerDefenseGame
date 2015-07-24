package WaveRuner;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Timer;

import Bullets.BasicBullet;
import GamePanels.GameWorld;
import GamePanels.UserControlPanel;
import GamePanels.UserStats;
import Main.DataAndGameStart;
import Monsters.BasicMonster;
import Towers.BasicTower;
import Towers.Tower;

public class RunWave implements ActionListener {
	/**
	 * counts the number of monsters
	 */
	private int monsterCounter = 0;
	/**
	 * timer for the monsters
	 */
	private Timer timer;
	/**
	 * holds the final projects
	 */
	private DataAndGameStart project;
	/**
	 * holds the game world
	 */
	private GameWorld world;
	
	/**
	 * holds the user stats
	 */
	private UserStats stats;
	/**
	 * holds the monsters health
	 */
	private int monsterHealth = 100;
	/**
	 * decides how many monsters to make
	 */
	private int howManyMonsters = 5;

	/**
	 * holds the threads of the monsters
	 */
	private Vector<Thread> allMonstersThread = new Vector<Thread>();
	/**
	 * holds all the monsters in the wave
	 */
	private Vector<BasicMonster> allMonsters = new Vector<BasicMonster>();


	public RunWave(DataAndGameStart projectz, GameWorld worldz,
			UserControlPanel controlz, UserStats statz) {
		project = projectz;
		world = worldz;
		stats = statz;
	}

	public void runWave() {
		
		stats.setWaveNumber(stats.getWaveNumber() + 1);
		project.getGameWorldThread().start();
		timer = new Timer(1000, this);
		timer.setInitialDelay(100);
		timer.start();

	}

	public void actionPerformed(ActionEvent e) {

		BasicMonster monster = new BasicMonster(0, 0, world.getLand(),
				monsterHealth, world);
		Thread monsterThread = new Thread(monster);
		allMonsters.add(monster);
		allMonstersThread.add(monsterThread);
		monsterThread.start();
		monsterCounter++;
		if (monsterCounter >= howManyMonsters) {
			timer.stop();
			monsterHealth = monsterHealth + 25;

		}
	}

	public synchronized void reportEndOfLife(BasicMonster b, boolean towerKilled) {
		int index = allMonsters.indexOf(b);
		
			allMonsters.remove(index);
			allMonstersThread.remove(index);
			if (towerKilled) {
				int usersNewTotalMoney = stats.getMoney() + b.getKillReward();
				stats.setMoney(usersNewTotalMoney);
			}
		
	}

	/**
	 * @param monsterHealth
	 *            the monsterHealth to set
	 */
	public void setMonsterHealth(int monsterHealth) {
		this.monsterHealth = monsterHealth;
	}

	/**
	 * @return the howManyMonsters
	 */
	public int getHowManyMonsters() {
		return howManyMonsters;
	}

	/**
	 * @return the allMonsters
	 */
	public Vector<BasicMonster> getAllMonsters() {
		return allMonsters;
	}


	/**
	 * @param monsterCounter
	 *            the monsterCounter to set
	 */
	public void setMonsterCounter(int monsterCounter) {
		this.monsterCounter = monsterCounter;
	}

	/**
	 * @return the timer
	 */
	public Timer getTimer() {
		return timer;
	}

	/**
	 * @param howManyMonsters
	 *            the howManyMonsters to set
	 */
	public void setHowManyMonsters(int howManyMonsters) {
		this.howManyMonsters = howManyMonsters;
	}

}
