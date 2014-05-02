/**
 * 
 */
package controller;

import java.io.File;
import java.util.Scanner;

import com.martiansoftware.jsap.*;

import model.Image;

/**
 * @author richfab-vproust
 *
 */
public class main {

	/**
	 * Appel principal du programme de génération des mosaiques
	 * @throws JSAPException 
	 */
	public static void main(String[] args) throws JSAPException {

		/** on instancie le générateur de mosaiques*/
		Generator generator = new Generator();

		/** Début de la définition des paramètres du programme */
		/** Chemin de sortie pour l'enregistrement des mosaiques*/
		JSAP jsap = new JSAP();
		FlaggedOption opt1 = new FlaggedOption("outputPath")
		.setStringParser(JSAP.STRING_PARSER)
		.setRequired(true) 
		.setShortFlag('o') 
		.setLongFlag(JSAP.NO_LONGFLAG);
		
		jsap.registerParameter(opt1);

		/** Chemin d'entrée du fichier de log des films*/
		FlaggedOption opt2 = new FlaggedOption("logFilePath")
		.setStringParser(JSAP.STRING_PARSER)
		.setRequired(true) 
		.setShortFlag('l') 
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt2);

		/** Largeur d'une mosaique en pixels*/
		FlaggedOption opt3 = new FlaggedOption("width")
		.setStringParser(JSAP.INTEGER_PARSER)
		.setDefault("1200")
		.setRequired(false) 
		.setShortFlag('w') 
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt3);

		/** Hauteur d'une mosaique en pixels*/
		FlaggedOption opt4 = new FlaggedOption("height")
		.setStringParser(JSAP.INTEGER_PARSER)
		.setDefault("900")
		.setRequired(false) 
		.setShortFlag('h') 
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt4);

		/** Tableau de mots clés à rechercher dans les mosaiques*/
		FlaggedOption opt5 = new FlaggedOption("keywords")
		.setStringParser(JSAP.STRING_PARSER)
		.setRequired(false) 
		.setShortFlag('k')
		.setList(true)
		.setListSeparator(';')
		.setLongFlag(JSAP.NO_LONGFLAG);

		jsap.registerParameter(opt5);

		JSAPResult config = jsap.parse(args);
		/** Fin de la définition des paramètres du programme */

		/** Si les parametres n'ont pas été bien entrés, on affiche l'aide */
		if (!config.success()) {
			System.err.println();
			System.err.println("Usage: java ");
			System.err.println("                "+ jsap.getUsage());
			System.err.println();
			// show full help as well
			System.err.println(jsap.getHelp());
		}
		
		/** Si les parametres ont bien été entrés*/
		if (config.success()) {
			
			/** On récupere les chemins de fichier de log et d'output*/
			String logFilePath = config.getString("logFilePath");
			String outputPath = config.getString("outputPath");
			
			/** On récupère les mots clés éventuellement passés*/
			String keywords[] = config.getStringArray("keywords");
			
			/** On instancie un objet image avec ces paramêtres*/
			Image image = new Image(config.getInt("height"), config.getInt("width"), logFilePath, outputPath);
			
			/** On lance la génération des mosaiques*/
			generator.generateLevels(image,keywords);
		}
	}

}
