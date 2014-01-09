package controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.Film;
import model.Image;
import model.Mosaic;
import model.MosaicPosition;
import model.MosaicsToBeClipped;

public class MosaicToSubMosac {

	/** cette methode divise une mosaique en 4 sous mosaiques et renvoie un objet MosaicsToBeClipped
	 * @param mosaic : la mosaique qu'il faut diviser en 4 sous mosaiques
	 * @return : les mosaiques a assembler
	 */
	public MosaicsToBeClipped MosaicToSubMosaic(Mosaic mosaic){

		//MosaicToBeClipped pour la reponse
		MosaicsToBeClipped mosaicsToBeClipped = new MosaicsToBeClipped();
		
		//Pour chaque film
		Iterator<Film> itFilm = mosaic.getFilms().iterator();

		// Les films sont ensuite inscris dans leur mosaique correspondante
		while(itFilm.hasNext()){
			Film filmCurrent = itFilm.next();
			
			//on recupere la partie entiere pour avoir la position du film dans la matrice de mosaiques
			double columnNumber = Math.floor((filmCurrent.getFilmX()/100)+1);
			double rowNumber = Math.floor((filmCurrent.getFilmY()/100)+1);
			
			//on creer une position de mosaique 
			MosaicPosition mosaicPosition = new MosaicPosition((int)rowNumber, (int) columnNumber, 0);
			
			//on ajoute le film a la bonne sous mosaique
			mosaicsToBeClipped.addFilmToSubMosaic(filmCurrent,mosaicPosition);
		}
		return mosaicsToBeClipped;
	}
}
