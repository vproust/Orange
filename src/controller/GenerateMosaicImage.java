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

public class GenerateMosaicImage {

	public BufferedImage writeMosaicOnDisk(Image image, Mosaic mosaic){

		int mosaicHeight = image.getMosaicHeight();
		int mosaicWidth = image.getMosaicWidth();

		BufferedImage bi = new BufferedImage(mosaicWidth, mosaicHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics2D ig2 = bi.createGraphics(); 
		int fontSize =20; //Taille de police défini en dur, a modifier ensuite..

		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		ig2.setFont(font);
		ig2.setPaint(Color.pink);
		Iterator<Film> it = mosaic.getFilms().iterator();
		
		while(it.hasNext()){
			Film filmCurrent = it.next();
			String message = filmCurrent.getFilmTitle();

			double XPositionOnMosaic = ((filmCurrent.getFilmX()/100)+1)*image.getNumberOfColumns()/2 - Math.floor(((filmCurrent.getFilmX()/100)+1)*image.getNumberOfColumns()/2);
			double YPositionOnMosaic = ((filmCurrent.getFilmY()/100)+1)*image.getNumberOfRows()/2 - Math.floor(((filmCurrent.getFilmY()/100)+1)*image.getNumberOfRows()/2);;

			ig2.drawString(message, (int)Math.floor(XPositionOnMosaic*mosaicWidth), (int)Math.floor(YPositionOnMosaic*mosaicHeight));
		}

		try {
			ImageIO.write(bi, "PNG", new File("./output/mosaic"+mosaic.getMosaicName()+".PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bi;
	} 

}
