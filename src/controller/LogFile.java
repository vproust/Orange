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
	 * Cette m�thode lis fichier texte contenant les noms de films et leur position et les ajoute dans une unique mosaique.
	 * 
	 * @param logFilePath Le chemin vers le r�pertoire du fichier contenant les noms de films.
	 * 
	 * @return filmSet Le conteneur des objets "film"
	 **/
	public Mosaic logFileToMosaic(String logFilePath){

		Mosaic mosaic = new Mosaic(new MosaicPosition(0,0,0), new MosaicClipPosition(0,0));

		BufferedReader br = null;

		try {

			//variables pour la ligne courante
			String sCurrentLine;

			br = new BufferedReader(new FileReader(logFilePath));

			while ((sCurrentLine = br.readLine()) != null) {

				Film film = logFileLineToFilmRandomXY(sCurrentLine);
				//Film film = logFileLineToFilmMovieLens(sCurrentLine);
//				Film film = logFileLineToFilmImdb(sCurrentLine);
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

	public Film logFileLineToFilmMovieLens(String line){

		String[] words, coords;
		String filmTitle;
		float filmX, filmY;

		words = line.split(";");

		filmTitle = words[0];

		coords = words[1].split(",");

		filmX = Float.parseFloat(coords[0])+1;
		filmY = Float.parseFloat(coords[1])+1;

		Film film = new Film(filmTitle,filmX,filmY);

		return film;
	}

	public Film logFileLineToFilmRandomXY(String line){

		String[] words;
		String filmTitle;
		float filmX, filmY;

		words = line.split("\t");

		filmTitle = words[0];
		filmX = Float.parseFloat(words[1])/100+1;
		filmY = Float.parseFloat(words[2])/100+1;

		Film film = new Film(filmTitle,filmX,filmY);

		return film;
	}

	public Film logFileLineToFilmImdb(String line){

		String[] words;
		String filmTitle;
		float filmX, filmY;

		words = line.split(";");

		filmTitle = words[0];
		filmX = Float.parseFloat(words[1])+1;
		filmY = Float.parseFloat(words[2])+1;

		Film film = new Film(filmTitle,filmX,filmY);

		return film;
	}

}
