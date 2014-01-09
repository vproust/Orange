package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.Film;
import model.Mosaic;
import model.MosaicPosition;

public class Generator {
	
	/**
	 * Cette méthode lis fichier texte contenant les noms de films et leur position et les ajoute dans une unique mosaique.
	 * 
	 * @param logFilePath Le chemin vers le répertoire du fichier contenant les noms de films.
	 * 
	 * @return filmSet Le conteneur des objets "film"
	 **/
	public boolean logFileToMosaic(String logFilePath){
		
		Mosaic mosaic = new Mosaic(new MosaicPosition(0,0));
		
		BufferedReader br = null;

		try {

			//variables pour la ligne courante
			String sCurrentLine;
			String[] words;
			String filmTitle;
			float filmX, filmY;

			br = new BufferedReader(new FileReader(logFilePath));

			while ((sCurrentLine = br.readLine()) != null) {

				words = sCurrentLine.split("\t");
				filmTitle = words[0];
				//on déplace le repere en bas a gauche
				filmX = Float.parseFloat(words[1]);
				filmY = Float.parseFloat(words[2]);

				Film film = new Film(filmTitle,filmX,filmY);
				mosaic.addFilm(film);
			}

		} catch (IOException e) {
			System.err.format("Exception occurred trying to read '%s'.", logFilePath);
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return true;
	}

}
