/**
 * 
 */
package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.Image;
import model.Film;
import model.Mosaic;
import model.MosaicPosition;

/**
* Controller dont la fonction est de générer des mosaiques a partir d'un fichier de log et de paramètres de clipping (nombre de mosaiques, taille en pixels)
* @author fabienrichard
* @version 1.0
* @date 27/11/2013
*/
public class GeneratorController {
	
	public Image image;
	
	public GeneratorController() {
		super();
	}


	public boolean generateMosaics(String logFilePath, int numberOfRows, int numberOfColumns, int mosaicHeight, int mosaicWidth){
		
		image = new Image(numberOfRows, numberOfColumns, mosaicHeight, mosaicWidth);
		
		//pour chaque ligne
		for(int i = 0; i<numberOfRows;i++){
			//pour chaque colonne
			for(int j = 0; j<numberOfColumns; j++){
				MosaicPosition mosaicPosition = new MosaicPosition(i, j);
				Mosaic mosaic = new Mosaic(this.image, mosaicPosition);
			}
		}

		System.out.println("fin de create mosaic");
		
		Set<Film> filmSet = readLog(logFilePath);
		
		System.out.println("fin de lecture log");
		
		//Pour chaque film
		Iterator<Film> itFilm = filmSet.iterator();
		while(itFilm.hasNext()){
			Film filmCurrent = itFilm.next();
			MosaicPosition mosaicPosition = filmCurrent.filmToMosaicPosition(image);
			filmCurrent.addFilmToMosaic(image, mosaicPosition);
		}
				
		//pour chaque ligne de la matrice de mosaiques
		for(int i = 0; i<numberOfRows;i++){
			//pour chaque colonne  de la matrice de mosaiques
			for(int j = 0; j<numberOfColumns; j++){
				Mosaic mosaicCurrent = image.getMosaic(i,j);
				mosaicCurrent.writeMosaicOnDisk(image);
			}
		}
		
		return true;
	}
	
	public Set<Film> readLog(String logFilePath){
		
		BufferedReader br = null;

		//set pour la reponse
		Set<Film> filmSet = new HashSet<Film>();
		
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
				filmSet.add(film);
				
				//System.out.println(film.getNom()+":"+film.getPositionX()+"/"+film.getPositionY());
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
		return filmSet;
	}
	
	
}
