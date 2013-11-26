/**
 * 
 */
package controller;

import model.Film;
import model.Image;
import model.MosaicPosition;

/**
 * @author fabienrichard
 *
 */
public class FilmController {
	public MosaicPosition filmToMosaicPosition(Image image, Film film){
		
		int numberOfColumns = image.getNumberOfColumns();
		int numberOfRows = image.getNumberOfRows();
		
		double c = Math.floor(((film.getFilmX()/100)+1)*numberOfColumns/2);
		double r = Math.floor(((film.getFilmY()/100)+1)*numberOfRows/2);
		
		MosaicPosition mosaicPosition = new MosaicPosition((int)c,(int)r);
		System.out.println(mosaicPosition.getColumnNumber()+";"+mosaicPosition.getRowNumber()+"\n");
		
		return mosaicPosition;
	}
}
