package GamePanels;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.DataAndGameStart;

public class UserStats extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * holds the labels for the user statistics
	 */
	JLabel[] statLabels;

	String[] STAT_LABELS_STR = { "Wave Number 0", "Bases Health 200/200",
			"$400" };

	/**
	 * holds the final class object
	 */
	DataAndGameStart project;

	/**
	 * sets up the users statistics
	 * 
	 * @param projectz
	 *            sends in the final project object
	 */
	public UserStats(DataAndGameStart projectz) {
		// sest the layout
		setLayout(new GridLayout(1, 3));
		project = projectz;

		// makes the JLabels
		statLabels = new JLabel[STAT_LABELS_STR.length];
		for (int i = 0; i < STAT_LABELS_STR.length; i++) {
			statLabels[i] = new JLabel(STAT_LABELS_STR[i]);
			add(statLabels[i]);
		}
	}

	public void updateLabels() {
		for (int i = 0; i < STAT_LABELS_STR.length; i++) {
			statLabels[i].setText((STAT_LABELS_STR[i]));
		}
	}

	/**
	 * sets the wave number
	 * 
	 * @param number
	 *            sends in the new wave number
	 */
	public void setWaveNumber(int number) {
		statLabels[0].setText("Wave Number " + number);
	}

	/**
	 * gets the wave number
	 * 
	 * @return returns what the wave number is
	 */
	public int getWaveNumber() {
		int toRet = Integer.parseInt(statLabels[0].getText().substring(
				statLabels[0].getText().lastIndexOf(" ") + 1));
		return toRet;
	}

	/**
	 * gets the health
	 * 
	 * @return returns the health
	 */
	public int getHealth() {

		int toRet = Integer.parseInt(statLabels[1].getText().substring(
				statLabels[1].getText().lastIndexOf(" ") + 1,
				statLabels[1].getText().indexOf("/")));
		return toRet;
	}

	/**
	 * sets the health
	 * 
	 * @param newHealth
	 *            sends in the new health amount
	 */
	public void setHealth(int newHealth) {
		statLabels[1].setText("Bases Health " + newHealth + "/200");
	}

	/**
	 * @param sTAT_LABELS_STR
	 *            the sTAT_LABELS_STR to set
	 */
	public void setSTAT_LABELS_STR(String[] sTAT_LABELS_STR) {
		STAT_LABELS_STR = sTAT_LABELS_STR;
	}

	/**
	 * gets the money
	 * 
	 * @return returns how much money the user has
	 */
	public int getMoney() {
		int toRet = Integer.parseInt(statLabels[2].getText().substring(
				statLabels[2].getText().indexOf("$") + 1));
		return toRet;
	}

	/**
	 * sets the money
	 * 
	 * @param money
	 *            gives the new amount of money
	 */
	public void setMoney(int money) {

		statLabels[2].setText("$" + money);
	}

}
