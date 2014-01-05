/**
 * 
 */
package controller;

import java.util.Scanner;

/**
 * @author fabienrichard
 *
 */
public class TestGeneratorController {

	/**
	 * Appel principal du programme.
	 */
	public static void main(String[] args) {
		
		GeneratorController generatorController = new GeneratorController();
		
		//generatorController.generateMosaics("log",numberOfRows,numberOfColumns,mosaicHeight,mosaicWidth);
		generatorController.generateMosaics("./input/filmGenerateurRandomXY.txt",8,8,1200,1900);
	}
}
