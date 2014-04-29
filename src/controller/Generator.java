package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

import model.Film;
import model.Image;
import model.Mosaic;
import model.MosaicClipPosition;
import model.MosaicPosition;
import model.MosaicsToBeClipped;


public class Generator {

	public boolean generateLevels(String logFilePath, Image image, String[] keywords){
		LogFile logFile = new LogFile();
		Mosaic mosaic = logFile.logFileToMosaic(logFilePath);
		recursiveLevelGenerator(image, mosaic, keywords);
		return true;
	}

	public BufferedImage recursiveLevelGenerator(Image image, Mosaic mosaic, String[] keywords){

		if(mosaic.getNumberOfFilms() > 50){

			MosaicsToBeClipped mosaicsToBeClipped = mosaicToSubMosaic(mosaic);

			Mosaic mosaicTL = mosaicsToBeClipped.getMosaicTL();//les films de la sous mosaique de �mosaic� en haut � gauche 
			Mosaic mosaicTR = mosaicsToBeClipped.getMosaicTR();//les films de la sous mosaique de �mosaic� en haut � droite;
			Mosaic mosaicBL = mosaicsToBeClipped.getMosaicBL();//les films de la sous mosaique de �mosaic� en bas � gauche;
			Mosaic mosaicBR = mosaicsToBeClipped.getMosaicBR();//les films de la sous mosaique de �mosaic� en bas � droite;

			mosaicTL.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),0,0));
			mosaicTR.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),0,1));
			mosaicBL.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),1,0));
			mosaicBR.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),1,1));

			BufferedImage biMosaicTL =  recursiveLevelGenerator(image, mosaicTL, keywords);
			BufferedImage biMosaicTR =  recursiveLevelGenerator(image, mosaicTR, keywords);
			BufferedImage biMosaicBL =  recursiveLevelGenerator(image, mosaicBL, keywords);
			BufferedImage biMosaicBR =  recursiveLevelGenerator(image, mosaicBR, keywords);

			BufferedImage biMosaic = clip(image, biMosaicTL,biMosaicTR,biMosaicBL,biMosaicBR);
			try {
				new File("./output/mosaic/"+mosaic.getMosaicPosition().getZoomLevel()+"/"+mosaic.getMosaicPosition().getRowNumber()).mkdirs();
				ImageIO.write(biMosaic, "PNG", new File("./output/mosaic/"+mosaic.getMosaicPosition().getZoomLevel()+"/"+mosaic.getMosaicPosition().getRowNumber()+"/"+mosaic.getMosaicPosition().getColumnNumber()+".png"));
				System.out.println("clip : "+mosaic.getMosaicName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return biMosaic;
		}

		else{
			BufferedImage biMosaic = generateMosaicImage(image, mosaic, keywords, false);
			return biMosaic;
		}
	}

	/** cette methode divise une mosaique en 4 sous mosaiques et renvoie un objet MosaicsToBeClipped
	 * @param mosaic : la mosaique qu'il faut diviser en 4 sous mosaiques
	 * @return : les mosaiques a assembler
	 */
	public MosaicsToBeClipped mosaicToSubMosaic(Mosaic mosaic){

		//MosaicToBeClipped pour la reponse
		MosaicsToBeClipped mosaicsToBeClipped = new MosaicsToBeClipped(mosaic.getMosaicPosition());

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
				//BL a et b = 0
				//BR a = -1;
				if(columnNumber==1){
					a = -1;
				}
			}
			else if(rowNumber==1){
				//TL
				if(columnNumber==0){
					b = -1;
				}
				//TR
				if(columnNumber==1){
					a = -1;
					b = -1;
				}
			}

			filmCurrent.setFilmX((float) (filmCurrent.getFilmX()+a)*2);
			filmCurrent.setFilmY((float) (filmCurrent.getFilmY()+b)*2);

			//on creer une position de mosaique 
			MosaicClipPosition mosaicClipPosition = new MosaicClipPosition((int)rowNumber, (int) columnNumber);

			//on ajoute le film a la bonne sous mosaique
			mosaicsToBeClipped.addFilmToSubMosaic(filmCurrent,mosaicClipPosition);
		}
		return mosaicsToBeClipped;
	}

	/** cette methode ecrit une mosaique de feuille sur le disque
	 * @param image : pour les parametres de taille; mosaic : la mosaique qu'il faut ecrire sur le disque
	 * @param keywords 
	 * @return : le bufferImage
	 */
	public BufferedImage generateMosaicImage(Image image, Mosaic mosaic, String[] keywords, boolean containsKeyword){

		int mosaicHeight = image.getMosaicHeight();
		int mosaicWidth = image.getMosaicWidth();

		BufferedImage bi = new BufferedImage(mosaicWidth, mosaicHeight, BufferedImage.TYPE_INT_ARGB);
		BufferedImage biWithTitles = new BufferedImage(mosaicWidth, mosaicHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics2D ig2 = bi.createGraphics(); 
		Graphics2D ig2WithTitles = biWithTitles.createGraphics(); 

		int fontSize = mosaicHeight/40; //Taille de police d�finie par rapport a la hauteur de l'image
		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		
		ig2.setFont(font);
		ig2.setPaint(Color.black);

		ig2WithTitles.setFont(font);
		ig2WithTitles.setPaint(Color.black);
		
		//contient le mot clé, on colore en rouge
		if(containsKeyword){
			ig2.setColor(Color.red);
			ig2.fillRect(0, 0, mosaicWidth, mosaicHeight);
			
			ig2WithTitles.setColor(Color.red);
			ig2WithTitles.fillRect(0, 0, mosaicWidth, mosaicHeight);
		}

		int radius = fontSize/5;

		Iterator<Film> it = mosaic.getFilms().iterator();

		while(it.hasNext()){
			Film filmCurrent = it.next();
			
			ig2.setPaint(Color.black);
			ig2WithTitles.setPaint(Color.black);
			
			//changement de couleur si le titre du film correspond a un des mots clé
			for(int i = 0; i<keywords.length; i++){
				
				if(keywords[i]!="" && filmCurrent.getFilmTitle().contains(keywords[i])){
					
					//si on etait pas deja en train d'ecrire sur un fond rouge
					if(!containsKeyword){
						return generateMosaicImage(image, mosaic, keywords, true);
					}
					
					ig2.setPaint(Color.white);
					ig2WithTitles.setPaint(Color.white);
				}
				
			}
			
			//on recherche son plus proche voisin
			Set<Film> setFilmsLocal = new HashSet<Film>(mosaic.getFilms());
			Film closestFilm = ClosestNeighboor.closestNeighboor(setFilmsLocal,filmCurrent);
			
			String filmTitle = filmCurrent.getFilmTitle();

			double XPositionFilmOnMosaic = filmCurrent.getFilmX()/2;
			double YPositionFilmOnMosaic = 1- filmCurrent.getFilmY()/2;
			
			//on dessine les points
			Shape circle = new Ellipse2D.Double((int)Math.floor(XPositionFilmOnMosaic*mosaicWidth) - radius, (int)Math.floor(YPositionFilmOnMosaic*mosaicHeight) - radius, 2.0 * radius, 2.0 * radius);
			ig2.fill(circle);
			ig2.draw(circle);
			
			//on dessine le point sur la mosa�que qui contient les titres
			ig2WithTitles.fill(circle);
			ig2WithTitles.draw(circle);
			
			if(closestFilm != null){
				double XPositionClosestFilmOnMosaic = closestFilm.getFilmX()/2;
				double YPositionClosestFilmOnMosaic = 1-closestFilm.getFilmY()/2;
				
				Draw.drawArrow(ig2WithTitles, (int)Math.floor(XPositionFilmOnMosaic*mosaicWidth), (int)Math.floor(YPositionFilmOnMosaic*mosaicHeight), (int)Math.floor(XPositionClosestFilmOnMosaic*mosaicWidth), (int)Math.floor(YPositionClosestFilmOnMosaic*mosaicHeight));
			}
			
			// décalage si le titre du film déborde à droite de l'image
			int offsetWidth = 0, offsetHeight = 0;
			if(ig2.getFontMetrics().stringWidth(filmTitle) > mosaicWidth-Math.floor(XPositionFilmOnMosaic*mosaicWidth)){
				offsetWidth = ig2.getFontMetrics().stringWidth(filmTitle);
			}
			// décalage si le titre du film déborde en bas de l'image
			if(fontSize > mosaicHeight-Math.floor(YPositionFilmOnMosaic*mosaicHeight)){
				offsetHeight = fontSize;
			}
			ig2WithTitles.drawString(filmTitle, (int)Math.floor(XPositionFilmOnMosaic*mosaicWidth - offsetWidth), (int)Math.floor(YPositionFilmOnMosaic*mosaicHeight)+fontSize - offsetHeight);
		}

		try {
			new File("./output/mosaic/"+mosaic.getMosaicPosition().getZoomLevel()+"/"+mosaic.getMosaicPosition().getRowNumber()).mkdirs();
			ImageIO.write(biWithTitles, "PNG", new File("./output/mosaic/"+mosaic.getMosaicPosition().getZoomLevel()+"/"+mosaic.getMosaicPosition().getRowNumber()+"/"+mosaic.getMosaicPosition().getColumnNumber()+".png"));
			System.out.println("leaf : "+mosaic.getMosaicName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bi;
	}

	public BufferedImage clip(Image image,BufferedImage biMosaicTL, BufferedImage biMosaicTR, BufferedImage biMosaicBL, BufferedImage biMosaicBR){

		BufferedImage bi = new BufferedImage(image.getMosaicWidth(), image.getMosaicHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig2 = bi.createGraphics();

		// insertion de l'image en haut � gauche
		java.awt.Image imageTL = Toolkit.getDefaultToolkit().createImage(biMosaicTL.getSource());
		ig2.drawImage(imageTL,0,0,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);
		biMosaicTL=null;

		// insertion de l'image en haut � droite
		java.awt.Image imageTR = Toolkit.getDefaultToolkit().createImage(biMosaicTR.getSource());
		ig2.drawImage(imageTR,image.getMosaicWidth()/2,0,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);
		biMosaicTR=null;

		// insertion de l'image en bas � gauche
		java.awt.Image imageBL = Toolkit.getDefaultToolkit().createImage(biMosaicBL.getSource());
		ig2.drawImage(imageBL,0,image.getMosaicHeight()/2,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);
		biMosaicBL=null;

		// insertion de l'image en bas � droite
		java.awt.Image imageBR = Toolkit.getDefaultToolkit().createImage(biMosaicBR.getSource());
		ig2.drawImage(imageBR,image.getMosaicWidth()/2,image.getMosaicHeight()/2,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);
		biMosaicBR=null;

		return bi;
	};

}
