package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.Film;
import model.Mosaic;
import model.MosaicClipPosition;
import model.MosaicPosition;

public class LogFile {
	/**
	 * Cette méthode lis fichier texte contenant les noms de films et leur position et les ajoute dans une unique mosaique.
	 * 
	 * @param logFilePath Le chemin vers le répertoire du fichier contenant les noms de films.
	 * 
	 * @return filmSet Le conteneur des objets "film"
	 **/
	public Mosaic logFileToMosaic(String logFilePath){

		Mosaic mosaic = new Mosaic(new MosaicPosition(0,0,0), new MosaicClipPosition(0,0));

		BufferedReader br = null;

		try {

			//variables pour la ligne courante
			String sCurrentLine;
			String[] words, coords;
			String filmTitle;
			float filmX, filmY;

			br = new BufferedReader(new FileReader(logFilePath));

			while ((sCurrentLine = br.readLine()) != null) {

				//pour randomXY.txt
				//words = sCurrentLine.split("\t");
				//filmX = Float.parseFloat(words[1])/100+1;
				//filmY = Float.parseFloat(words[2])/100+1;
				
				//pour movieLens
				words = sCurrentLine.split(";");
				
				filmTitle = words[0];

				coords = words[1].split(",");

				//on déplace le repere en bas a gauche. les coordonnees deviennent comprises entre 0 et 2
				//pour movieLens
				filmX = Float.parseFloat(coords[0])+1;
				filmY = Float.parseFloat(coords[1])+1;

				Film film = new Film(filmTitle,filmX,filmY);
				mosaic.addFilm(film);
			}

		} catch (IOException e) {
			System.err.format("Exception occurred trying to read '%s'.", logFilePath);
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return mosaic;
	}

}
