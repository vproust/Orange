package controller;

import model.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;


public class Generator {

	public boolean generateLevels(String logFilePath){
		
		Image image = new Image(1200, 1900);

		Mosaic mosaic = new Mosaic(new MosaicPosition(0,0,0));
		recursiveLevelGenerator(image, mosaic);
		return true;
	}

	public BufferedImage recursiveLevelGenerator(Image image, Mosaic mosaic){

		if(mosaic.getNumberOfFilms() > 100){

			Mosaic mosaicTL = mosaicToSubMosaic(mosaic).getMosaicTL();//les films de la sous mosaique de �mosaic� en haut � gauche 
			Mosaic mosaicTR = mosaicToSubMosaic(mosaic).getMosaicTR();//les films de la sous mosaique de �mosaic� en haut � droite;
			Mosaic mosaicBL = mosaicToSubMosaic(mosaic).getMosaicBL();//les films de la sous mosaique de �mosaic� en bas � gauche;
			Mosaic mosaicBR = mosaicToSubMosaic(mosaic).getMosaicBR();//les films de la sous mosaique de �mosaic� en bas � droite;
			
			mosaicTL.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),0,0));
			mosaicTR.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),0,1));
			mosaicBL.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),1,0));
			mosaicBR.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),1,1));
			
			BufferedImage biMosaicTL =  recursiveLevelGenerator(image, mosaicTL);
			BufferedImage biMosaicTR =  recursiveLevelGenerator(image, mosaicTR);
			BufferedImage biMosaicBL =  recursiveLevelGenerator(image, mosaicBL);
			BufferedImage biMosaicBR =  recursiveLevelGenerator(image, mosaicBR);

			BufferedImage biMosaic = clip(biMosaicTL,biMosaicTR,biMosaicBL,biMosaicBR);
			
			try {
				ImageIO.write(biMosaic, "PNG", new File("./output/mosaic"+""+".PNG"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			return biMosaic;
		}

		else{
			BufferedImage biMosaic = generateMosaicImage(image, mosaic);
			return biMosaic;
		}
	}
	
	/** cette methode divise une mosaique en 4 sous mosaiques et renvoie un objet MosaicsToBeClipped
	 * @param mosaic : la mosaique qu'il faut diviser en 4 sous mosaiques
	 * @return : les mosaiques a assembler
	 */
	public MosaicsToBeClipped mosaicToSubMosaic(Mosaic mosaic){

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
	
	/** cette methode ecrit une mosaique de feuille sur le disque
	 * @param image : pour les parametres de taille; mosaic : la mosaique qu'il faut ecrire sur le disque
	 * @return : le bufferImage
	 */
	public BufferedImage generateMosaicImage(Image image, Mosaic mosaic){

		int mosaicHeight = image.getMosaicHeight();
		int mosaicWidth = image.getMosaicWidth();

		BufferedImage bi = new BufferedImage(mosaicWidth, mosaicHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics2D ig2 = bi.createGraphics(); 
		
		int fontSize =20; //Taille de police d�fini en dur, a modifier ensuite.
		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		ig2.setFont(font);
		ig2.setPaint(Color.red);
		
		Iterator<Film> it = mosaic.getFilms().iterator();
		
		while(it.hasNext()){
			Film filmCurrent = it.next();
			String message = filmCurrent.getFilmTitle();

			double XPositionOnMosaic = ((filmCurrent.getFilmX()/100)+1) - Math.floor(((filmCurrent.getFilmX()/100)+1));
			double YPositionOnMosaic = ((filmCurrent.getFilmY()/100)+1) - Math.floor(((filmCurrent.getFilmY()/100)+1));

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

	public BufferedImage clip(BufferedImage biMosaicTL, BufferedImage biMosaicTR, BufferedImage biMosaicBL, BufferedImage biMosaicBR){
		
		return null;
	};

	/**
	 * Cette m�thode lis fichier texte contenant les noms de films et leur position et les ajoute dans une unique mosaique.
	 * 
	 * @param logFilePath Le chemin vers le r�pertoire du fichier contenant les noms de films.
	 * 
	 * @return filmSet Le conteneur des objets "film"
	 **/
	public Mosaic logFileToMosaic(String logFilePath){

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
				//on d�place le repere en bas a gauche
				filmX = Float.parseFloat(words[1]);
				filmY = Float.parseFloat(words[2]);

				Film film = new Film(filmTitle,filmX,filmY);
				mosaic.addFilm(film);
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

		return mosaic;
	}

}
