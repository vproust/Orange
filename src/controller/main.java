/**
 * 
 */
package controller;

import java.io.File;
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

		JSAP jsap = new JSAP();
		FlaggedOption opt1 = new FlaggedOption("outputPath")
		.setStringParser(JSAP.STRING_PARSER)
		.setRequired(true) 
		.setShortFlag('o') 
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt1);

		FlaggedOption opt2 = new FlaggedOption("logFilePath")
		.setStringParser(JSAP.STRING_PARSER)
		.setRequired(true) 
		.setShortFlag('l') 
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

		FlaggedOption opt5 = new FlaggedOption("keywords")
		.setStringParser(JSAP.STRING_PARSER)
		.setRequired(false) 
		.setShortFlag('k')
		.setList(true)
		.setListSeparator(';')
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt5);

		JSAPResult config = jsap.parse(args);

		if (!config.success()) {
			System.err.println();
			System.err.println("Usage: java ");
			System.err.println("                "+ jsap.getUsage());
			System.err.println();
			// show full help as well
			System.err.println(jsap.getHelp());
		}
		
		if (config.success()) {
			
			String logFilePath = config.getString("logFilePath");
			String outputPath = config.getString("outputPath");
			
			String keywords[] = config.getStringArray("keywords");
			
			Image image = new Image(config.getInt("height"), config.getInt("width"), logFilePath, outputPath);
			
			generator.generateLevels(image,keywords);
		}
	}

}
