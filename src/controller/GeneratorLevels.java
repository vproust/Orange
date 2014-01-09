/**
 * 
 */
package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author fabienrichard
 *
 */
public class GeneratorLevels {
	
	public boolean GenerateLevels(String logFilePath){
		
		try {
		
			BufferedReader br = new BufferedReader(new FileReader(logFilePath));
			int lines = 0;
			while (br.readLine() != null){
				lines++;
			}
			br.close();
			
			double numberOfMosaicsNeeded = Math.floor(lines/400);
			
			int numberOfZoomLevels = 0;
			
			while(Math.pow(4, numberOfZoomLevels) < numberOfMosaicsNeeded){
				numberOfZoomLevels++;
			}
			
			int numberOfMosaicsGenerated = (int) Math.pow(4, numberOfZoomLevels);
				
			System.out.println("mosaics = " +numberOfMosaicsGenerated + " niveaux = " + numberOfZoomLevels);
			
			
			
		} catch (IOException e) {
			System.err.format("Exception occurred trying to read '%s'.", logFilePath);
			e.printStackTrace();
		}
		
		
		
		return true;
	}
	
}
