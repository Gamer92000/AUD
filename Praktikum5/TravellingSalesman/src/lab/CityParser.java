package lab;

/**
 * Aufgabe H1a)
 * 
 * Abgabe von: Philip Jonas Franz (2447302), Julian Imhof (2689225) und Nicolas Petermann (2918103)
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.text.AbstractDocument.BranchElement;

import frame.City;

public class CityParser {
	
	private String _filename;
	
	public CityParser(String filename) {
		_filename = filename;
	}
	
	/**
	 * Read cities from the given file and enter them into a LinkedList.
	 * Each city is in its own line, written as "x;y". 
	 * Throw a RuntimeException if anything goes wrong.
	 * Return the LinkedList of the cities in the same order as they were in the file.
	 */
	@SuppressWarnings("resource")
	public LinkedList<City> readFile() {
		BufferedReader br;
		LinkedList<City> returnList = new LinkedList<City>();
		String line;
		int id = 0;
		try {br = new BufferedReader(new FileReader(_filename));}
		catch (FileNotFoundException e) {return null;}
		try {
			while ((line = br.readLine()) != null) {
				if (line.length() == 0) continue;
				City _new = new City(id, Double.parseDouble(line.substring(0, line.indexOf(";"))), Double.parseDouble(line.substring(line.indexOf(";")+1)));
				returnList.add(_new);
				id++;
			}
		} catch (IOException e) {return null;} 
		
        // TODO
		return returnList;
	}
}
