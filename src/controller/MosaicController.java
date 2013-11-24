/**
 * 
 */
package controller;

import model.Film;
import model.Mosaic;
import model.MosaicPosition;

/**
 * @author fabienrichard
 *
 */
public class MosaicController {
	public Mosaic createMosaic(MosaicPosition mosaicPosition){
		return new Mosaic(mosaicPosition);
	}
	public void addFilmToMosaic(Film film, MosaicPosition mosaicPosition){
		
	}
	public void writeMosaicOnDisk(Mosaic mosaic){
		
	}
}
