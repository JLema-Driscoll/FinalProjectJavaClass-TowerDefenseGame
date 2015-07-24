package Land;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;

import GamePanels.GameWorld;



public class LoadLand {
	
	String fileName="Map01.txt";
	GameWorld world;

	public LoadLand(GameWorld worldz) {
		world = worldz;
	}
	
	
	/**
	 * reads the file and restores the map
	 */
	public void readFile() {
		
		try {
			// variables needed in making land
			String sCurrentLine;
			int rows = 0;
			int columns = 0;
			int row = 0;
			int column = 0;
			int landType = -1;
			BufferedReader br = new BufferedReader(
					new FileReader(fileName));
			int counterFirst = 0;
			BasicLand landToLoad[][];
			landToLoad = new BasicLand[1][1];
			// reads and makes the land
			while ((sCurrentLine = br.readLine()) != null) {
				if (counterFirst == 0) {
					rows = Integer.parseInt(sCurrentLine.substring(0,
							sCurrentLine.indexOf(" ")));
					columns = Integer.parseInt(sCurrentLine
							.substring(sCurrentLine.indexOf(" ") + 1));
					landToLoad = new BasicLand[rows][columns];
					counterFirst++;
				} else {
					row = Integer.parseInt(sCurrentLine.substring(0,
							sCurrentLine.indexOf(" ")));
					column = Integer.parseInt(sCurrentLine.substring(
							sCurrentLine.indexOf(" ") + 1,
							sCurrentLine.indexOf(".")));
					landType = Integer.parseInt(sCurrentLine
							.substring(sCurrentLine.indexOf(".") + 1,sCurrentLine.indexOf("!")));

					landToLoad[row][column] = new BasicLand(world,landType, row,
							column);

				}
				world.setLand(landToLoad);
			}
			br.close(); 
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		
	}
	/**
	 * loads the file
	 */
	public void fileLoader() {

		// code from
		// http://www.java2s.com/Tutorial/Java/0240__Swing/GettingandSettingtheSelectedFileofaJFileChooserDialog.htm
		JFileChooser chooser = new JFileChooser();
		File f;
		try {
			f = new File(new File("filename.txt").getCanonicalPath());
			chooser.setSelectedFile(f);
			chooser.showOpenDialog(null);
			File curFile = chooser.getSelectedFile();
			if(curFile.getName().contains("txt"))
			fileName = curFile.getName();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} 
	}
}
