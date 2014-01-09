/**
 * 
 */
package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

/**
 * @author Valentin
 *
 */
public class Mosaic {
	private String mosaicName;
	private MosaicPosition mosaicPosition;
	private Set<Film> films;
	
	public Mosaic( MosaicPosition mosaicPosition) {
		super();
		this.films = new HashSet<Film>();
		this.mosaicPosition = mosaicPosition;
		this.setMosaicName(Integer.toString(mosaicPosition.getRowNumber())+"."+Integer.toString(mosaicPosition.getColumnNumber()));
	}
	
	public void addFilm(Film film){
		this.films.add(film);
	}
	
	public void writeMosaicOnDisk(Image image){
		
		int mosaicHeight = image.getMosaicHeight();
		int mosaicWidth = image.getMosaicWidth();
		
		BufferedImage bi = new BufferedImage(mosaicWidth, mosaicHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics2D ig2 = bi.createGraphics(); 
		int fontSize =20; //Taille de police défini en dur, a modifier ensuite..

		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		ig2.setFont(font);
	    ig2.setPaint(Color.red);
	    Iterator<Film> it = this.getFilms().iterator();
		while(it.hasNext()){
			Film filmCurrent = it.next();
			String message = filmCurrent.getFilmTitle();
			
			double XPositionOnMosaic = ((filmCurrent.getFilmX()/100)+1)*image.getNumberOfColumns()/2 - Math.floor(((filmCurrent.getFilmX()/100)+1)*image.getNumberOfColumns()/2);
			double YPositionOnMosaic = ((filmCurrent.getFilmY()/100)+1)*image.getNumberOfRows()/2 - Math.floor(((filmCurrent.getFilmY()/100)+1)*image.getNumberOfRows()/2);;
			
			ig2.drawString(message, (int)Math.floor(XPositionOnMosaic*mosaicWidth), (int)Math.floor(YPositionOnMosaic*mosaicHeight));
		}

		try {
			ImageIO.write(bi, "PNG", new File("./output/mosaic"+this.getMosaicName()+".PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getMosaicName() {
		return mosaicName;
	}
	public void setMosaicName(String mosaicName) {
		this.mosaicName = mosaicName;
	}
	public void setMosaicName() {
		this.mosaicName = this.getMosaicPosition().getRowNumber()+"."+this.getMosaicPosition().getColumnNumber()+"."+this.getMosaicPosition().getZoomLevel();
	}
	public Set<Film> getFilms() {
		return films;
	}
	public void setFilms(Set<Film> films) {
		this.films = films;
	}
	public MosaicPosition getMosaicPosition() {
		return mosaicPosition;
	}
	public void setMosaicPosition(MosaicPosition mosaicPosition) {
		this.mosaicPosition = mosaicPosition;
	}
	
}
