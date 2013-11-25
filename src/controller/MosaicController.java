/**
 * 
 */
package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

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
		mosaic.setMosaicName(Integer.toString(mosaicPosition.getRowNumber())+"."+Integer.toString(mosaicPosition.getColumnNumber()));
		image.addMosaic(mosaic);
	}
	public void addFilmToMosaic(Image image,Film film, MosaicPosition mosaicPosition){
		image.getMosaics().iterator().next().addFilm(film);
	}
	
	public void writeMosaicOnDisk(Mosaic mosaic,int mosaicHeight,int mosaicWidth){
		BufferedImage bi = new BufferedImage(mosaicWidth, mosaicHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics2D ig2 = bi.createGraphics(); 
		int fontSize =20; //Taille de police défini en dur, a modifier ensuite..

		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		ig2.setFont(font);
	    ig2.setPaint(Color.red);
	    Iterator<Film> it = mosaic.getFilms().iterator();
		while(it.hasNext()){
			Film filmCurrent = it.next();
			String message = filmCurrent.getFilmTitle();
			
			ig2.drawString(message, ((int) filmCurrent.getFilmX()*(mosaicWidth/2)/100)+(mosaicWidth/2), ((int) filmCurrent.getFilmY()*(mosaicHeight/2)/100)+(mosaicHeight/2));
		}

		try {
			ImageIO.write(bi, "PNG", new File("./output/mosaic"+mosaic.getMosaicName()+".PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
