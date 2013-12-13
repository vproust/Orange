/**
 * 
 */
package controller;

import java.util.Scanner;

/**
 * @author fabienrichard
 *
 */
public class Test {

	/**
	 * Appel principal du programme.
	 */
	public static void main(String[] args) {
		
		GeneratorController generatorController = new GeneratorController();
		
		//generatorController.generateMosaics("log",numberOfRows,numberOfColumns,mosaicHeight,mosaicWidth);
		generatorController.generateMosaics("./input/filmGenerateurRandomXY.txt",2,3,800,800);
	}

}
