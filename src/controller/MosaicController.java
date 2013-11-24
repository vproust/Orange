/**
 * 
 */
package controller;

import model.Film;
import model.Image;
import model.Mosaic;
import model.MosaicPosition;

/**
 * @author fabienrichard
 *
 */
public class MosaicController {
	public void createMosaic(Image image, MosaicPosition mosaicPosition){
		Mosaic mosaic = new Mosaic(mosaicPosition);
		image.getMosaics().add(mosaic);
	}
	public void addFilmToMosaic(Film film, MosaicPosition mosaicPosition){
		
	}
}
