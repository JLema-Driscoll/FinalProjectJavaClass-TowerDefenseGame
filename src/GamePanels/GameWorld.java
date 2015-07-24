package GamePanels;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JPanel;

import Bullets.Bullet;
import Land.BasicLand;
import Land.LoadLand;
import Main.DataAndGameStart;
import Monsters.BasicMonster;
import Towers.BasicTower;
import Towers.Mortar;
import Towers.RapidFireTower;

import Towers.Tower;
import WaveRuner.RunWave;

public class GameWorld extends JPanel implements MouseListener,
		MouseMotionListener, Runnable {

	/**
	 * sets whether the sounds play
	 */
	boolean playSounds = true;
	/**
	 * holds the music
	 */
	AudioClip take5;

	/**
	 * monsters death sounds
	 */
	AudioClip monsterDeath;
	/**
	 * holds the image for losing
	 */
	Image youLosePic = Toolkit.getDefaultToolkit().getImage("youLost.jpg");
	/**
	 * holds the image for winin
	 */
	Image youWinPic = Toolkit.getDefaultToolkit().getImage("youWin.jpg");
	/**
	 * holds if the user won or not
	 */
	boolean youWin;
	/**
	 * holds if the user lost or not
	 */
	boolean youLose;
	/**
	 * holds if the program is running or not
	 */
	boolean running = true;
	/**
	 * holds the number of dead monsters
	 */
	int deadMonsters = 0;
	/**
	 * holds the final project
	 */
	DataAndGameStart project;
	/**
	 * holds the lands
	 */
	BasicLand land[][];
	/**
	 * uses this to load the map
	 */
	LoadLand loading;
	/**
	 * sets the total number of columns to 25
	 */
	int numberOfColumns = 25;
	/**
	 * sets the total number of rows to 25
	 */
	int numberOfRows = 25;
	/**
	 * holds the user controls
	 */
	UserControlPanel controls;
	/**
	 * holds all of the towers
	 */
	Tower allTowers[] = new Tower[0];
	/**
	 * counts how many towers the user has
	 */
	int towerCounter = 0;

	/**
	 * used to view the place you are placing a tower
	 */
	Tower towerViewer;

	/**
	 * holds the tower threads
	 */
	Vector<Thread> towerThread = new Vector<Thread>();
	/**
	 * used to tell if a tower is selected
	 */
	boolean towerSelected;
	/**
	 * holds the users stats
	 */
	private UserStats stats;

	/**
	 * used to tell the program whether to show if the towerViewer should be
	 * drawn
	 */
	private boolean activateTowerViewer;

	/**
	 * holds the cost to upgrade cost
	 */
	int rangeCost;
	/**
	 * holds the cost to upgrade damage
	 */
	int damageCost;
	/**
	 * holds the cost to upgrade fire rate
	 */
	int fireRateCost;
	/**
	 * used to hold a selected tower when the user wants to move a tower
	 */
	private int selectedTower = -1;
	/**
	 * holds the wave running class so that monsters can be painted
	 */
	RunWave wave;
	/**
	 * holds the tower selected panel
	 */
	private SelectedTowerPanel theTowerSelected;

	/**
	 * stores what a mouse click equals
	 */
	private int mouseClickLeft = 1, mouseClickRight = 3;

	private final int upgradeRange = 0, upgradeDamage = 1, upgradeFireRate = 2;

	/**
	 * initializes the game world
	 * 
	 * @param projectz
	 *            sends in the project
	 */
	public GameWorld(DataAndGameStart projectz) {
		project = projectz;
		loading = new LoadLand(this);
		loading.readFile();
		controls = project.getUserControlPanel();

		stats = project.getUserStats();

		addMouseListener(this);
		addMouseMotionListener(this);
		URL url1 = null;
		try {
			url1 = new URL("file", "localhost", "cheesyTake5.mid");
			take5 = JApplet.newAudioClip(url1);

		} catch (Exception e) {
			// do something in case of problem
		}
		take5.play();
	}

	/**
	 * paints the window
	 */
	public void paintComponent(Graphics g) {
		// sets the color to white
		g.setColor(Color.WHITE);
		// paints the background
		g.fillRect(0, 0, getWidth(), getHeight());
		this.setSize(600, 600);
		// paints the tower
		if (land != null) {
			for (int i = 0; i < 25; i++) {
				for (int z = 0; z < 25; z++) {
					land[i][z].draw(g, getWidth(), getHeight(), numberOfRows,
							numberOfColumns);

				}
			}
		}
		try {
			for (int i = 0; i < allTowers.length; i++) {

				allTowers[i].draw(g, getWidth(), getHeight(), numberOfRows,
						numberOfColumns);

			}
		} catch (NullPointerException e) {

		} catch (ArrayIndexOutOfBoundsException e) {

		}

		if (towerViewer != null && activateTowerViewer) {
			towerViewer.draw(g, getWidth(), getHeight(), numberOfRows,
					numberOfColumns);
		}

		try {
			for (int i = 0; i < wave.getAllMonsters().size(); i++) {

				wave.getAllMonsters()
						.get(i)
						.draw(g, getWidth(), getHeight(), numberOfRows,
								numberOfColumns);
			}
		} catch (NullPointerException e) {

		}
		if (youLose) {

			g.drawImage(youLosePic, 0, 0, getWidth(), getHeight(), this);

		}
		if (youWin) {
			g.drawImage(youWinPic, 0, 0, getWidth(), getHeight(), this);
			g.setColor(Color.BLACK);
			g.drawString("You Win", 230, 100);
		}

	}

	/**
	 * controls the monsters death sounds
	 */
	public void monstersDeathSoundPlay() {

		URL url2 = null;
		try {
			url2 = new URL("file", "localhost", "MonsterDeath.wav");
			monsterDeath = JApplet.newAudioClip(url2);

		} catch (Exception e) {
			// do something in case of problem
		}
		monsterDeath.play();
	}

	public void playOrNotPlayMusic(boolean play) {
		if (play) {
			take5.play();
		} else if (!play) {
			take5.stop();
		}
	}

	/**
	 * sets if the suer can see the tower ranges or not
	 */
	public void setIfTowerRangesCanBeSeen() {

		for (int i = 0; i < allTowers.length; i++) {
			allTowers[i].setTransparent(controls.getCanSeeTowerRanges());

		}
		repaint();
	}

	/**
	 * deletes the selected object in an array
	 */
	public void deleteSelectedObjectInTheArray() {
		int usersNewTotalMoney = stats.getMoney()
				+ (int) (allTowers[selectedTower].getTowerCost() * .75);
		stats.setMoney(usersNewTotalMoney);
		int rowNumber = allTowers[selectedTower].getRow();
		int columnNumber = allTowers[selectedTower].getColumn();
		land[rowNumber][columnNumber].setCanYouBuildHere(true);
		Tower temp[] = allTowers;
		allTowers = new Tower[temp.length - 1];
		int counter = 0;
		for (int i = 0; i < temp.length; i++) {
			if (i != selectedTower) {
				allTowers[counter] = temp[i];
				counter++;
			}
		}
		resetTowers();

		towerCounter--;
		unSelectObject();
		repaint();
	}

	/**
	 * used to rest the tower thread after you delete a tower
	 */
	public void resetTowers() {
		for (int i = 0; i < getTowerThread().size(); i++) {
			getTowerThread().get(i).stop();
		}
		towerThread.removeAllElements();

		for (int i = 0; i < allTowers.length; i++) {
			Thread thread = new Thread(allTowers[i]);
			towerThread.add(thread);

		}
	}

	/**
	 * finds out if the monster is in bounds and hurts the monster if it is and
	 * kills the bullet
	 * 
	 * @param xPos
	 * @param yPos
	 * @param bullet
	 * @return returns if the bullet should be drawn or not
	 */
	public synchronized boolean withinBounds(double xPos, double yPos,
			Bullet bullet) {
		boolean toRet = false;
		if (xPos > 0 && xPos < getWidth() && yPos > 0 && yPos < getHeight()) {
			toRet = true;
		}
		for (int i = 0; i < wave.getAllMonsters().size(); i++) {
			int boxTopX = wave.getAllMonsters().get(i).getMoveX();
			int boxTopY = wave.getAllMonsters().get(i).getMoveY();
			int boxBottomX = wave.getAllMonsters().get(i).getBoundingBoxBotX();
			int boxBottomY = wave.getAllMonsters().get(i).getBoundingBoxBotY();
			if (xPos > boxTopX && xPos < boxBottomX && yPos > boxTopY
					&& yPos < boxBottomY) {
				toRet = false;

				if (!bullet.getTower().getTowerName().equals("Mortar")) {
					int newHealth = wave.getAllMonsters().get(i).getHealth()
							- bullet.getTower().getDamage();
					wave.getAllMonsters().get(i).setHealth(newHealth);
					if (wave.getAllMonsters().get(i).getHealth() <= 0) {
						wave.getAllMonsters().get(i)
								.letIncomingBulletsTargetsDead();
						wave.reportEndOfLife(wave.getAllMonsters().get(i), true);
						if (playSounds)
							monstersDeathSoundPlay();
						deadMonsters++;
					}

				} else {
					splashDamage(xPos, yPos, bullet);
				}
				break;
			}
		}

		return toRet;
	}

	/**
	 * used by the mortar towers to find out what else they hits aside for their
	 * original target
	 * 
	 * @param xPos
	 * @param yPos
	 * @param bullet
	 */
	public synchronized void splashDamage(double xPos, double yPos,
			Bullet bullet) {
		int bulletTopX = (int) (xPos - bullet.getDamageWidth());
		int bulletTopY = (int) (yPos - bullet.getDamageHeight());
		int bulletBottomX = (int) (xPos + bullet.getDamageWidth());
		int bulletBottomY = (int) (yPos + bullet.getDamageHeight());

		for (int i = 0; i < wave.getAllMonsters().size(); i++) {

			int boxCenterX = wave.getAllMonsters().get(i).getCenterX();
			int boxCenterY = wave.getAllMonsters().get(i).getCenterY();

			if (boxCenterX > bulletTopX && boxCenterX < bulletBottomX
					&& boxCenterY > bulletTopY && boxCenterY < bulletBottomY) {

				int newHealth = wave.getAllMonsters().get(i).getHealth()
						- bullet.getTower().getDamage();
				wave.getAllMonsters().get(i).setHealth(newHealth);

				if (wave.getAllMonsters().get(i).getHealth() <= 0) {
					wave.getAllMonsters().get(i)
							.letIncomingBulletsTargetsDead();
					wave.reportEndOfLife(wave.getAllMonsters().get(i), true);
					if (playSounds)
						monstersDeathSoundPlay();
					deadMonsters++;
					i--;
				}
			}

		}
	}

	/**
	 * finds out if a monster is in range and if it is returns that monster
	 * 
	 * @param centerX
	 * @param centerY
	 * @param radius
	 * @return returns a monster that is in range
	 */
	public BasicMonster withInRange(int centerX, int centerY, int radius) {

		BasicMonster target = null;
		for (int i = 0; i < wave.getAllMonsters().size(); i++) {

			BasicMonster monster = wave.getAllMonsters().get(i);
			// does the math to find if the click is inside uses the p. theorem
			int xDistance = monster.getCenterX() - centerX;
			xDistance = Math.abs(xDistance);
			int yDistance = monster.getCenterY() - centerY;
			yDistance = Math.abs(yDistance);
			int distance = (int) Math.sqrt(xDistance * xDistance + yDistance
					* yDistance);

			// if the distance from the center is greater than the radius return
			// false
			if (distance > radius) {

			}
			// if it isnt return true
			else {
				target = monster;
				break;
			}
		}
		return target;
	}

	/**
	 * important thingy
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * used to make towers
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX(), y = e.getY();
		int buttonClicked = e.getButton();

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
		if (mouseClickLeft == buttonClicked) {

			makeTowers(rowNumber, columnNumber);

			if (!towerSelected) {
				getTowerWhereClicked(rowNumber, columnNumber);
			} else if (towerSelected && selectedTower != -1) {
			}
		}
		if (mouseClickRight == buttonClicked) {
			controls.unSelectTowers();
			towerSelected = false;
			selectedTower = -1;
			activateTowerViewer = false;
			theTowerSelected.unSelectObject();
		}

		repaint();

	}

	/**
	 * unselects an object
	 */
	public void unSelectObject() {
		towerSelected = false;
		selectedTower = -1;
		theTowerSelected.unSelectObject();
	}

	/**
	 * used to get if a tower was clicked
	 * 
	 * @param rowNumber
	 *            sends in the clicked row
	 * @param columnNumber
	 *            sends in the clicked column
	 */
	public void getTowerWhereClicked(int rowNumber, int columnNumber) {
		if (!controls.getBasicTower() && !controls.getRapidFireTower()
				&& !controls.getMortarTower() && !controls.getSlowTower()) {

			for (int i = 0; i < allTowers.length; i++) {
				if (allTowers[i].getColumn() == columnNumber
						&& allTowers[i].getRow() == rowNumber) {
					selectedTower = i;
					theTowerSelected.setSelectedObject(allTowers[i]);
					towerSelected = true;
					// allTowers[i].
					break;
				}
			}
		}
	}

	/**
	 * @return the wave
	 */
	public RunWave getWave() {
		return wave;
	}

	/**
	 * Used to create towers
	 * 
	 * @param rowNumber
	 *            sends in the row clicked
	 * @param columnNumber
	 *            sends in the column clicked
	 */
	public void makeTowers(int rowNumber, int columnNumber) {
		// creates the towers
		if (controls.getBasicTower()
				&& land[rowNumber][columnNumber].getCanYouBuildHere()
				&& stats.getMoney() >= 150) {
			makeAllTowersArrayLonger();
			land[rowNumber][columnNumber].setCanYouBuildHere(false);
			allTowers[towerCounter] = new BasicTower(rowNumber, columnNumber,
					this);
			int usersNewTotalMoney = stats.getMoney()
					- allTowers[towerCounter].getTowerCost();
			stats.setMoney(usersNewTotalMoney);
			towerCounter++;
			setIfTowerRangesCanBeSeen();
			towerSelected = false;
			selectedTower = -1;
			allTowers[towerCounter-1].setSoundIsOn(playSounds);
			Thread thread = new Thread(allTowers[towerCounter - 1]);
			towerThread.add(thread);
		}
		if (controls.getRapidFireTower()
				&& land[rowNumber][columnNumber].getCanYouBuildHere()
				&& stats.getMoney() >= 200) {
			makeAllTowersArrayLonger();
			land[rowNumber][columnNumber].setCanYouBuildHere(false);
			allTowers[towerCounter] = new RapidFireTower(rowNumber,
					columnNumber, this);
			int usersNewTotalMoney = stats.getMoney()
					- allTowers[towerCounter].getTowerCost();
			stats.setMoney(usersNewTotalMoney);
			towerCounter++;
			setIfTowerRangesCanBeSeen();
			towerSelected = false;
			selectedTower = -1;
			Thread thread = new Thread(allTowers[towerCounter - 1]);
			allTowers[towerCounter-1].setSoundIsOn(playSounds);
			towerThread.add(thread);
		}
		if (controls.getMortarTower()
				&& land[rowNumber][columnNumber].getCanYouBuildHere()
				&& stats.getMoney() >= 200) {
			makeAllTowersArrayLonger();
			land[rowNumber][columnNumber].setCanYouBuildHere(false);
			allTowers[towerCounter] = new Mortar(rowNumber, columnNumber, this);

			int usersNewTotalMoney = stats.getMoney()
					- allTowers[towerCounter].getTowerCost();
			stats.setMoney(usersNewTotalMoney);
			towerCounter++;
			setIfTowerRangesCanBeSeen();
			towerSelected = false;
			selectedTower = -1;
			Thread thread = new Thread(allTowers[towerCounter - 1]);
			towerThread.add(thread);
			allTowers[towerCounter-1].setSoundIsOn(playSounds);
		}

	}

	/**
	 * upgrades the towers
	 * 
	 * @param whatToUpGrade
	 *            sends in what to upgrade
	 */
	public void upgradeTower(int whatToUpGrade) {

		rangeCost = (int) (.20 * allTowers[selectedTower].getTowerCost());
		damageCost = (int) (.20 * allTowers[selectedTower].getTowerCost());
		fireRateCost = (int) (.20 * allTowers[selectedTower].getTowerCost());
		if (whatToUpGrade == upgradeRange && rangeCost < stats.getMoney()) {
			allTowers[selectedTower].setRange(allTowers[selectedTower]
					.getRange()
					+ allTowers[selectedTower].getDefaultRangezIncrease());

			int usersNewTotalMoney = stats.getMoney() - rangeCost;

			allTowers[selectedTower].setTowerCost(allTowers[selectedTower]
					.getTowerCost() + (int) (rangeCost));
			stats.setMoney(usersNewTotalMoney);
			theTowerSelected.setTowerInformationsJLabels();
		}
		if (whatToUpGrade == upgradeDamage && damageCost < stats.getMoney()) {
			allTowers[selectedTower].setDamage(allTowers[selectedTower]
					.getDamage()
					+ allTowers[selectedTower].getDefaultDamagezIncrease());

			int usersNewTotalMoney = stats.getMoney() - damageCost;

			allTowers[selectedTower].setTowerCost(allTowers[selectedTower]
					.getTowerCost() + (int) (damageCost));
			stats.setMoney(usersNewTotalMoney);
			theTowerSelected.setTowerInformationsJLabels();
		}
		if (whatToUpGrade == upgradeFireRate && fireRateCost < stats.getMoney()) {
			allTowers[selectedTower].setFireRate(allTowers[selectedTower]
					.getFireRate()
					+ allTowers[selectedTower].getDefaultFireRatezIncrease());

			int usersNewTotalMoney = stats.getMoney() - fireRateCost;

			allTowers[selectedTower].setTowerCost(allTowers[selectedTower]
					.getTowerCost() + (int) (fireRateCost));
			stats.setMoney(usersNewTotalMoney);
			theTowerSelected.setTowerInformationsJLabels();
		}
		repaint();

	}

	/**
	 * makes the tower array longer
	 */
	public void makeAllTowersArrayLonger() {
		Tower[] temp = allTowers;
		allTowers = new Tower[temp.length + 1];
		for (int i = 0; i < temp.length; i++) {
			allTowers[i] = temp[i];
		}
	}

	/**
	 * holds if the mouse exits the screen
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		activateTowerViewer = false;
		repaint();
	}

	/**
	 * used for the tower image as it is placed
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
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
		activateTowerViewer = false;
		// creates the towers
		if (controls.getBasicTower()
				&& land[rowNumber][columnNumber].getCanYouBuildHere()) {

			towerViewer = new BasicTower(rowNumber, columnNumber, this);
			activateTowerViewer = true;
			towerViewer.setTransparent(true);
		}
		if (controls.getRapidFireTower()
				&& land[rowNumber][columnNumber].getCanYouBuildHere()) {

			towerViewer = new RapidFireTower(rowNumber, columnNumber, this);
			activateTowerViewer = true;
			towerViewer.setTransparent(true);

		}
		if (controls.getMortarTower()
				&& land[rowNumber][columnNumber].getCanYouBuildHere()) {

			towerViewer = new Mortar(rowNumber, columnNumber, this);
			activateTowerViewer = true;
			towerViewer.setTransparent(true);
		}

		repaint();

	}

	/**
	 * rungs the wave
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (running) {
			repaint();
			for (int i = 0; i < wave.getAllMonsters().size(); i++) {
				if (wave.getAllMonsters().get(i).getMoveX() > getWidth()) {
					getWave().reportEndOfLife(wave.getAllMonsters().get(i),
							false);
					deadMonsters++;
					stats.setHealth(stats.getHealth() - 20);

				}

			}

			for (int i = 0; i < getTowerThread().size(); i++) {
				if (!getTowerThread().get(i).isAlive()) {

					getTowerThread().get(i).start();

				}
			}

			if (deadMonsters == wave.getHowManyMonsters()) {
				resetWave();
				wave.setHowManyMonsters(wave.getHowManyMonsters() + 1);
				if (stats.getWaveNumber() == 20) {
					setYouWin(true);
					controls.getWaveRunner().setEnabled(false);
				}
			}
			if (stats.getHealth() <= 0) {
				youLose = true;
				stats.setHealth(0);
				controls.getWaveRunner().setEnabled(false);
			}

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		running = true;
		repaint();
	}

	public synchronized void resetWave() {
		deadMonsters = 0;
		wave.setMonsterCounter(0);
		controls.getWaveRunner().setEnabled(true);
		project.getGameWorldThread().stop();
		wave.getAllMonsters().removeAllElements();

		for (int i = 0; i < getTowerThread().size(); i++) {
			getTowerThread().get(i).stop();
		}
		towerThread.removeAllElements();

		for (int i = 0; i < allTowers.length; i++) {
			Thread thread = new Thread(allTowers[i]);
			towerThread.add(thread);

		}
		running = false;
		repaint();
	}

	/**
	 * @return the loading
	 */
	public LoadLand getLoading() {
		return loading;
	}

	/**
	 * @param deadMonsters
	 *            the deadMonsters to set
	 */
	public void setDeadMonsters(int deadMonsters) {
		this.deadMonsters = deadMonsters;
	}

	/**
	 * @return the towerThread
	 */
	public Vector<Thread> getTowerThread() {
		return towerThread;
	}

	public void setSelectedTowerPanel(SelectedTowerPanel theTowerSelectedz) {
		theTowerSelected = theTowerSelectedz;
	}

	/**
	 * sets the land from a saved file
	 * 
	 * @param newLand
	 *            sends in the land
	 */
	public void setLand(BasicLand[][] newLand) {
		land = newLand;

		repaint();
	}

	/**
	 * gets the basic land
	 * 
	 * @return returns the basic lands
	 */
	public BasicLand[][] getLand() {
		return land;
	}

	public void setWaveRunner(RunWave wavez) {
		wave = wavez;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the stats
	 */
	public UserStats getStats() {
		return stats;
	}

	/**
	 * @param playSounds the playSounds to set
	 */
	public void setPlaySounds(boolean playSounds) {
		
		for(int i=0;i<allTowers.length;i++)
		{
			allTowers[i].setSoundIsOn(playSounds);
		}
		
		this.playSounds = playSounds;
	}

	/**
	 * @param towerCounter
	 *            the towerCounter to set
	 */
	public void setTowerCounter(int towerCounter) {
		this.towerCounter = towerCounter;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * @return the allTowers
	 */
	public Tower[] getAllTowers() {
		return allTowers;
	}

	/**
	 * @param allTowers
	 *            the allTowers to set
	 */
	public void setAllTowers(Tower[] allTowers) {
		this.allTowers = allTowers;
	}

	/**
	 * @param youWin
	 *            the youWin to set
	 */
	public void setYouWin(boolean youWin) {
		this.youWin = youWin;
	}

	/**
	 * @param youLose
	 *            the youLose to set
	 */
	public void setYouLose(boolean youLose) {
		this.youLose = youLose;
	}
}
