/**
 * 
 */
package controller;

import java.util.Scanner;

/**
 * @author fabienrichard
 *
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GeneratorController generatorController = new GeneratorController();
		int numberOfRows;
		int numberOfColumns;
		int mosaicWidth;
		int mosaicHeight;
		
		/*Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir le nombres de lignes :");
		numberOfRows = sc.nextInt();
		System.out.println("Veuillez saisir le nombres de colonnes :");
		numberOfColumns = sc.nextInt();
		System.out.println("Veuillez saisir la largeur d'une mosaique en pixels :");
		mosaicWidth = sc.nextInt();
		System.out.println("Veuillez saisir la hauteur d'une mosaique en pixels :");
		mosaicHeight = sc.nextInt();
		
		generatorController.generateMosaics("./input/filmGenerateurRandomXY.txt",numberOfRows,numberOfColumns,mosaicHeight,mosaicWidth);
		*/
		generatorController.generateMosaics("./input/filmGenerateurRandomXY.txt",8,8,800,800);
	}

}
