package MenuPanels;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import Main.DataAndGameStart;

public class InterfaceMainMenu extends JPanel implements ActionListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton[] options;
	JLabel MenuTitle= new JLabel("Main Menu");
	/**
	 * holds the string of tower options
	 */
	final static String[] OPTIONS_STR = { "Play Game","Load Map", "Edit Map", "Exit"};
	
	/**
	 * holds the final project
	 */
	DataAndGameStart data;
	
	/**
	 * @param args
	 */
	public InterfaceMainMenu(DataAndGameStart dataz){
		// TODO Auto-generated method stub
		data=dataz;
		setLayout(new GridLayout(16, 1));
		add(MenuTitle);
		options = new JButton[OPTIONS_STR.length];
		for(int i =0; i< OPTIONS_STR.length; i++){
			options[i] = new JButton(OPTIONS_STR[i]);
			options[i].addActionListener(this);
			add(options[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj == options[0]) {
			data.StartGame();
		}
		
		if (obj == options [1]){
			try{
				
				}
				catch(NullPointerException ez)
				{
					
				}
				data.getGameWorld().getLoading().fileLoader();
				data.getGameWorld().getLoading().readFile();
		}
		if (obj == options[2])
		{
			data.startMapMaker();
		}
		if (obj == options[3]){
			System.exit(0);

		}
		
	}

}
