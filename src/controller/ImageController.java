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
import model.MosaicPosition;

/**
 * @author fabienrichard
 *
 */
public class ImageController {
	public Image image;
	public MosaicController mosaicController;
	public FilmController filmController;
	
	public ImageController() {
		super();
		mosaicController = new MosaicController();
		filmController = new FilmController();
	}


	public boolean generateMosaics(String logFilePath, int numberOfRows, int numberOfColumns, int mosaicHeight, int mosaicWidth){
		
		image = new Image(numberOfRows, numberOfColumns, mosaicHeight, mosaicWidth);
		
		//pour chaque ligne
		for(int i = 0; i<=numberOfRows;i++){
			//pour chaque colonne
			for(int j = 0; j<=numberOfColumns; i++){
				MosaicPosition mosaicPosition = new MosaicPosition(i, j);
				mosaicController.createMosaic(this.image, mosaicPosition);
			}
		}
		
		Set<Film> filmSet = readLog(logFilePath);
		
		Iterator<Film> it = filmSet.iterator();
		while(it.hasNext()){
			Film filmCurrent = it.next();
			MosaicPosition mosaicPosition = filmController.filmToMosaicPosition(filmCurrent);
			mosaicController.addFilmToMosaic(filmCurrent, mosaicPosition);
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
