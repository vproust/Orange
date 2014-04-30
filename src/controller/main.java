/**
 * 
 */
package controller;

import java.util.Scanner;

import com.martiansoftware.jsap.*;

import model.Image;

/**
 * @author fabienrichard
 *
 */
public class main {

	/**
	 * Appel principal du programme.
	 * @throws JSAPException 
	 */
	public static void main(String[] args) throws JSAPException {
		
		Generator generator = new Generator();
		
		/** int mosaicWidth;
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
		keywords[2] = "drama";*/
		
		JSAP jsap = new JSAP();
		FlaggedOption opt1 = new FlaggedOption("output")
		.setStringParser(JSAP.STRING_PARSER)
		.setRequired(true) 
		.setShortFlag('o') 
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt1);

		FlaggedOption opt2 = new FlaggedOption("input")
		.setStringParser(JSAP.STRING_PARSER)
		.setRequired(true) 
		.setShortFlag('i') 
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt2);

		FlaggedOption opt3 = new FlaggedOption("width")
		.setStringParser(JSAP.INTEGER_PARSER)
		.setDefault("1200")
		.setRequired(false) 
		.setShortFlag('w') 
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt3);

		FlaggedOption opt4 = new FlaggedOption("height")
		.setStringParser(JSAP.INTEGER_PARSER)
		.setDefault("900")
		.setRequired(false) 
		.setShortFlag('h') 
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt4);

		FlaggedOption opt5 = new FlaggedOption("keyWords")
		.setStringParser(JSAP.STRING_PARSER)
		.setRequired(false) 
		.setShortFlag('k') 
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt5);

		JSAPResult config = jsap.parse(args);
		System.out.println(config.getString("input"));
		System.out.println(config.getString("output"));
		System.out.println(config.getInt("width"));

		if (!config.success()) {
			System.err.println();
			System.err.println("Usage: java ");
			System.err.println("                "
					+ jsap.getUsage());
			System.err.println();
			// show full help as well
			System.err.println(jsap.getHelp());

		}
		if (config.success()) {
		Image image = new Image(config.getInt("height"), config.getInt("width"));
		
		String keywords[] = config.getStringArray("keyWords");
		System.out.println(keywords[0]);
		generator.generateLevels("./input/filmGenerateurRandomXY100.txt",image,keywords);
		}
	}

}
