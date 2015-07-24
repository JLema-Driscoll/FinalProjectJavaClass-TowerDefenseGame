package MapMakerz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import LandObjects_MapMaker.Land;
import MapMakerPanels.GameWorld;

/**
 * Saves the map
 * 
 * @author Jeremy Driscoll
 * 
 */
public class MapSaverAndLoader {
	GameWorld world;

	/**
	 * constructor of the map saver and loader
	 * 
	 * @param z
	 *            sends in the world
	 */
	public MapSaverAndLoader(GameWorld z) {
		world = z;
	}

	/**
	 * saves the land to a files
	 * 
	 * @param totalRows
	 *            sends in the total number of rows
	 * @param totalColumns
	 *            sends in the total number of columns
	 * @param landz
	 *            sends in the land
	 */
	public void writeList(int totalRows, int totalColumns, Land landz[][]) {

		PrintWriter out = null;

		// code I got from joel
		try {

			out = new PrintWriter(new FileWriter("Map01.txt"));
			out.println(totalRows + " " + totalColumns);
			for (int i = 0; i < totalRows; i++)
				for (int j = 0; j < totalColumns; j++)
					out.println(landz[i][j].printOutStatement());

		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Caught " + "ArrayIndexOutOfBoundsException: "
					+ e.getMessage());

		} catch (IOException e) {
			System.err.println("Caught IOException: " + e.getMessage());

		} finally {
			if (out != null) {

				out.close();
			} else {
				System.out.println("PrintWriter not open");
			}
		}
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
			boolean visable;
			BufferedReader br = new BufferedReader(
					new FileReader("Map01.txt"));
			int counterFirst = 0;
			Land landToLoad[][];
			landToLoad = new Land[1][1];
			// reads and makes the land
			while ((sCurrentLine = br.readLine()) != null) {
				if (counterFirst == 0) {
					rows = Integer.parseInt(sCurrentLine.substring(0,
							sCurrentLine.indexOf(" ")));
					columns = Integer.parseInt(sCurrentLine
							.substring(sCurrentLine.indexOf(" ") + 1));
					landToLoad = new Land[rows][columns];
					counterFirst++;
				} else {
					row = Integer.parseInt(sCurrentLine.substring(0,
							sCurrentLine.indexOf(" ")));
					column = Integer.parseInt(sCurrentLine.substring(
							sCurrentLine.indexOf(" ") + 1,
							sCurrentLine.indexOf(".")));
					landType = Integer.parseInt(sCurrentLine
							.substring(sCurrentLine.indexOf(".") + 1,sCurrentLine.indexOf("!")));

					landToLoad[row][column] = new Land(world, landType, row,
							column);

				}
				world.setLand(landToLoad);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
