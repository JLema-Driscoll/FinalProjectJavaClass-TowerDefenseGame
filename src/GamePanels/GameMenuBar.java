package GamePanels;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Main.DataAndGameStart;



/**
 * makes the menu
 * 
 * @author Jeremy Driscoll
 * 
 */
public class GameMenuBar extends JMenuBar implements ActionListener {
	/**
	 * Does Something
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * creates the menu bars string
	 */
	private final String[] MENU_STR = { "File" };
	/**
	 * creates the exit option string
	 */
	private final String[] FILE_OPTIONS_STR = { "New Game","Load Map","Stop Music","Disable Sounds","Exit" };

	/**
	 * sets if music should be played or not
	 */
	private boolean playSounds=false;
	/**
	 * creates the file options menu
	 */
	private JMenuItem[] FILE_OPTION;
	
	/**
	 * holds if the music should be playing or not
	 */
	private boolean play=false;

	/**
	 * holds the final project object
	 */
	DataAndGameStart project;
	
	public GameMenuBar(DataAndGameStart projectz) {
		
		project=projectz;
		
				// creates the jmenu edit
				FILE_OPTION = new JMenuItem[FILE_OPTIONS_STR.length];

				// creates the menu options for edit and file
				for (int i = 0; i < MENU_STR.length; i++) {
					JMenu menu = new JMenu(MENU_STR[i]);
					if (i == 0) {
						for (int x = 0; x < FILE_OPTIONS_STR.length; x++) {
							FILE_OPTION[x] = new JMenuItem(FILE_OPTIONS_STR[x]);
							menu.add(FILE_OPTION[x]);
							FILE_OPTION[x].addActionListener(this);
						}
					}

					// adds them to the menu bar
					add(menu);
				}
	}

	

	
	/**
	 * finds out what was hit in the menu
	 */
	public void actionPerformed(ActionEvent e) {
		// gets the object that was hit
		Object obj = e.getSource();
		// Exits the program when exit is hit
		if (obj == FILE_OPTION[0]) {
			project.reset();
		}
		if (obj == FILE_OPTION[1]) {
			try{
			project.reset();
			}
			catch(NullPointerException ez)
			{
				
			}
			project.getGameWorld().getLoading().fileLoader();
			project.getGameWorld().getLoading().readFile();
			project.getGameWorld().repaint();
		}
		if (obj == FILE_OPTION[4]) {
			System.exit(0);
		}
		if (obj == FILE_OPTION[2])
		{

			project.getGameWorld().playOrNotPlayMusic(play);
			if(play)
			{
				play=false;
				FILE_OPTION[2].setText("Stop Music");
			}
			else
			{
				play=true;
				FILE_OPTION[2].setText("Start Music");
			}
		}

		if (obj == FILE_OPTION[3])
		{
			project.getGameWorld().setPlaySounds(playSounds);
		
		
			if(playSounds)
			{
				playSounds=false;
				FILE_OPTION[3].setText("Disable Sounds");
			}
			else
			{
				playSounds=true;
				FILE_OPTION[3].setText("Enable Sounds");
			}
		}
	}
}
