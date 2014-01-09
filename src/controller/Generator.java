package controller;

import model.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Generator {

	public boolean generateLevels(String logFilePath){

		Mosaic mosaic = new Mosaic(new MosaicPosition(0,0,0));
		recursiveGeneratorLevels(mosaic);
		return true;
	}

	public BufferedImage recursiveGeneratorLevels(Mosaic mosaic){

		if(mosaic.getNumberOfFilms() > 100){
			Mosaic mosaic0.0 = les films de la sous mosaique de ìmosaicî en haut ‡ gauche;
			Mosaic mosaic0.1 = les films de la sous mosaique de ìmosaicî en haut ‡ droite;
			Mosaic mosaic1.0 = les films de la sous mosaique de ìmosaicî en bas ‡ gauche;
			Mosaic mosaic1.1 = les films de la sous mosaique de ìmosaicî en bas ‡ droite;

			BufferedImage biMosaic0.0 =  recursiveGeneratorLevels(mosaic0.0);
			BufferedImage biMosaic0.1 =  recursiveGeneratorLevels(mosaic0.1);
			BufferedImage biMosaic1.0 =  recursiveGeneratorLevels(mosaic1.0);
			BufferedImage biMosaic0.1 =  recursiveGeneratorLevels(mosaic1.1);

			BufferedImage biMosaic = clip(biMosaic0.0,biMosaic0.1,biMosaic1.0,biMosaic1.1);
			write(biMosaic);
			return biMosaic;
		}

		else{
			BufferedImage biMosaic = generateMosaic(mosaic);
			write(mosaic);
			return biMosaic;
		}
	}
	// ressemble ‡ film to mosaicPosition
	Mosaic MosaicToSubMosac(Mosaic mosaic, position){};

	clip(BufferedImage biMosaic0.0, BufferedImage biMosaic0.1, BufferedImage biMosaic1.0, BufferedImage biMosaic1.1){};
	generateMosaic(Mosaic mosaic){};

	/**
	 * Cette méthode lis fichier texte contenant les noms de films et leur position et les ajoute dans une unique mosaique.
	 * 
	 * @param logFilePath Le chemin vers le répertoire du fichier contenant les noms de films.
	 * 
	 * @return filmSet Le conteneur des objets "film"
	 **/
	public boolean logFileToMosaic(String logFilePath){

		Mosaic mosaic = new Mosaic(new MosaicPosition(0,0,0));

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
