package controller;

import model.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
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
		Mosaic mosaic = logFileToMosaic(logFilePath);
		recursiveLevelGenerator(image, mosaic);
		return true;
	}

	public BufferedImage recursiveLevelGenerator(Image image, Mosaic mosaic){
		System.out.println("rÈcursion"+mosaic.getMosaicPosition().getZoomLevel());

		if(mosaic.getNumberOfFilms() > 1000){

			MosaicsToBeClipped mosaicsToBeClipped = mosaicToSubMosaic(mosaic);

			Mosaic mosaicTL = mosaicsToBeClipped.getMosaicTL();//les films de la sous mosaique de ìmosaicî en haut ‡ gauche 
			Mosaic mosaicTR = mosaicsToBeClipped.getMosaicTR();//les films de la sous mosaique de ìmosaicî en haut ‡ droite;
			Mosaic mosaicBL = mosaicsToBeClipped.getMosaicBL();//les films de la sous mosaique de ìmosaicî en bas ‡ gauche;
			Mosaic mosaicBR = mosaicsToBeClipped.getMosaicBR();//les films de la sous mosaique de ìmosaicî en bas ‡ droite;

			mosaicTL.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),0,0));
			mosaicTR.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),0,1));
			mosaicBL.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),1,0));
			mosaicBR.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),1,1));

			BufferedImage biMosaicTL =  recursiveLevelGenerator(image, mosaicTL);
			BufferedImage biMosaicTR =  recursiveLevelGenerator(image, mosaicTR);
			BufferedImage biMosaicBL =  recursiveLevelGenerator(image, mosaicBL);
			BufferedImage biMosaicBR =  recursiveLevelGenerator(image, mosaicBR);

			BufferedImage biMosaic = clip(image, biMosaicTL,biMosaicTR,biMosaicBL,biMosaicBR);
			try {
				ImageIO.write(biMosaic, "PNG", new File("./output/mosaic"+mosaic.getMosaicPosition().getZoomLevel()+"."+mosaic.getMosaicPosition().getColumnNumber()+"."+mosaic.getMosaicPosition().getRowNumber()+".PNG"));
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
			double columnNumber = Math.floor(filmCurrent.getFilmX());
			double rowNumber = Math.floor(filmCurrent.getFilmY());
			
			//selon que le film soit TL,TR,BL ou BR, on le recentre pour que le niveau de zoom inferieur recupere une mosaique avec des films repartis entre 0 et 2
			
			//variables pour le calcul de la position :
			int a = 0, b = 0;

			if(rowNumber==0){
				//TL a et b = 0
				//TR a = -1;
				if(columnNumber==1){
					a = -1;
				}
			}
			else if(rowNumber==1){
				//BL
				if(columnNumber==0){
					b = -1;
				}
				//BR
				if(columnNumber==1){
					a = -1;
					b = -1;
				}
			}

			filmCurrent.setFilmX((float) (filmCurrent.getFilmX()+a)*2);
			filmCurrent.setFilmY((float) (filmCurrent.getFilmY()+b)*2);

			//System.out.println(mosaic.getMosaicPosition().getZoomLevel()+" -> "+columnNumber+" et "+rowNumber+" "+filmCurrent.getFilmTitle()+": "+filmCurrent.getFilmX()+'/'+filmCurrent.getFilmY()+" a="+a+";b="+b);

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

		int fontSize =40; //Taille de police dÈfini en dur, a modifier ensuite.
		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		ig2.setFont(font);
		ig2.setPaint(Color.red);

		Iterator<Film> it = mosaic.getFilms().iterator();

		while(it.hasNext()){
			Film filmCurrent = it.next();
			String message = filmCurrent.getFilmTitle();

			//double XPositionOnMosaic = ((filmCurrent.getFilmX()/100)+1) - Math.floor(((filmCurrent.getFilmX()/100)+1));
			//double YPositionOnMosaic = ((filmCurrent.getFilmY()/100)+1) - Math.floor(((filmCurrent.getFilmY()/100)+1));
			
			double XPositionOnMosaic = filmCurrent.getFilmX()/2;
			double YPositionOnMosaic = filmCurrent.getFilmY()/2;
			System.out.println("X="+XPositionOnMosaic+"Y="+YPositionOnMosaic);
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

	public BufferedImage clip(Image image,BufferedImage biMosaicTL, BufferedImage biMosaicTR, BufferedImage biMosaicBL, BufferedImage biMosaicBR){
		BufferedImage bi = new BufferedImage(image.getMosaicWidth(), image.getMosaicHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig2 = bi.createGraphics();

		// insertion de l'image en haut ‡ gauche
		java.awt.Image imageTL = Toolkit.getDefaultToolkit().createImage(biMosaicTL.getSource());
		ig2.drawImage(imageTL,0,0,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);

		// insertion de l'image en haut ‡ droite
		java.awt.Image imageTR = Toolkit.getDefaultToolkit().createImage(biMosaicTL.getSource());
		ig2.drawImage(imageTR,image.getMosaicWidth()/2,0,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);

		// insertion de l'image en bas ‡ gauche
		java.awt.Image imageBL = Toolkit.getDefaultToolkit().createImage(biMosaicTL.getSource());
		ig2.drawImage(imageBL,0,image.getMosaicHeight()/2,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);


		// insertion de l'image en bas ‡ droite
		java.awt.Image imageBR = Toolkit.getDefaultToolkit().createImage(biMosaicTL.getSource());
		ig2.drawImage(imageBR,image.getMosaicWidth()/2,image.getMosaicHeight()/2,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);

		return bi;
	};

	/**
	 * Cette méthode lis fichier texte contenant les noms de films et leur position et les ajoute dans une unique mosaique.
	 * 
	 * @param logFilePath Le chemin vers le répertoire du fichier contenant les noms de films.
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
				//on déplace le repere en bas a gauche. les coordonnees deviennent comprises entre 0 et 2
				filmX = Float.parseFloat(words[1])/100+1;
				filmY = Float.parseFloat(words[2])/100+1;

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
