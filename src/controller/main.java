/**
 * 
 */
package controller;

import java.util.Scanner;

import model.Image;

/**
 * @author fabienrichard
 *
 */
public class main {

	/**
	 * Appel principal du programme.
	 */
	public static void main(String[] args) {
		
		Generator generator = new Generator();
		
		int mosaicWidth;
		int mosaicHeight;
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir la largeur d'une mosaique en pixels :");
		mosaicWidth = sc.nextInt();
		System.out.println("Veuillez saisir la hauteur d'une mosaique en pixels :");
		mosaicHeight = sc.nextInt();
		
		Image image = new Image(mosaicHeight, mosaicWidth);
		
		String keywords[] = new String[3];
		keywords[0] = "action";
		keywords[1] = "comedy";
		keywords[2] = "drama";
		
		generator.generateLevels("./input/imdb_factorsColumns2.txt",image,keywords);
	}

}
