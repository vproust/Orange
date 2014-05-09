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


/**
 * @author richfab-vproust
 *
 */
public class Generator {

	/**méthode qui génère tous les niveaux de mosaiques
	 * @param image (contient les parametres de taille, chemin de sortie et d'entrée)
	 * @param keywords (mots clé à rechercher dans les mosaiques)
	 * @return
	 */
	public boolean generateLevels(Image image, String[] keywords){
		/** on instancie le liseur de logs*/
		LogFile logFile = new LogFile();
		/** on remplit la mosaique avec les films du log */
		Mosaic mosaic;
		try {
			mosaic = logFile.logFileToMosaic(image.getLogFilePath());
			/** on supprime le dossier output et son contenu s'il existe déjà */
			deleteDir(new File(image.getOutputPath()));
			/** on lance la fonction recursive*/
			recursiveLevelGenerator(image, mosaic, keywords);
		} catch (IOException e) {
			System.err.format("Error trying to read '%s'. Check file path.\n", image.getLogFilePath());
		}
		return true;
	}

	/**méthode récursive qui génère un niveau complet de mosaiques
	 * @param image
	 * @param mosaic
	 * @param keywords
	 * @return
	 */
	public BufferedImage recursiveLevelGenerator(Image image, Mosaic mosaic, String[] keywords){

		/** Le nombre maximum de films a positionner sur un mosaique est calculé en fonction de la hauteur d'une mosaique*/
		int maxNumberOfFilms = image.getMosaicHeight()/20;
		
		/** Si le nombre de film contenu dans la mosaique est supérieur au nombre maximum de films, alors on divise la mossique en 4*/
		if(mosaic.getNumberOfFilms() > maxNumberOfFilms){

			/** On instancie un objet MosaicToBeClipped qui contient les 4 mosaiques qui devront etre assemblée (clipée)*/
			MosaicsToBeClipped mosaicsToBeClipped = mosaicToSubMosaic(mosaic);

			/** On instancie ou récupère les 4 sous mosaic TopLeft, TopRight, BottomLeft et BottomRight*/
			Mosaic mosaicTL = mosaicsToBeClipped.getMosaicTL();/** les films de la sous mosaique en haut a gauche */
			Mosaic mosaicTR = mosaicsToBeClipped.getMosaicTR();/** les films de la sous mosaique en haut a droite */
			Mosaic mosaicBL = mosaicsToBeClipped.getMosaicBL();/** les films de la sous mosaique en bas a gauche */
			Mosaic mosaicBR = mosaicsToBeClipped.getMosaicBR();/** les films de la sous mosaique en bas a droite */
			
			/** On définie la position globale qu'on chacune des sous mosaiques et on indique si elles sont TR, TL, BR ou BL */
			mosaicTL.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),0,0));
			mosaicTR.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),0,1));
			mosaicBL.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),1,0));
			mosaicBR.setMosaicPosition(new MosaicPosition(mosaic.getMosaicPosition(),1,1));

			/** On génère l'image en buffer des 4 sous mosaiques grace a un appel récursif */
			BufferedImage biMosaicTL =  recursiveLevelGenerator(image, mosaicTL, keywords);
			BufferedImage biMosaicTR =  recursiveLevelGenerator(image, mosaicTR, keywords);
			BufferedImage biMosaicBL =  recursiveLevelGenerator(image, mosaicBL, keywords);
			BufferedImage biMosaicBR =  recursiveLevelGenerator(image, mosaicBR, keywords);

			/** Une fois les 4 sous mosaiques générées, on les assemble et on écrit l'image sur le disque */
			BufferedImage biMosaic = clip(image, biMosaicTL,biMosaicTR,biMosaicBL,biMosaicBR);
			try {
				/** On créer le dossier qui contiendra le fichier PNG s'il n'existe pas encore*/
				new File(image.getOutputPath()+mosaic.getMosaicPosition().getZoomLevel()+"/"+mosaic.getMosaicPosition().getRowNumber()).mkdirs();
				/** On écrit l'image sur le disque */
				ImageIO.write(biMosaic, "PNG", new File(image.getOutputPath()+mosaic.getMosaicPosition().getZoomLevel()+"/"+mosaic.getMosaicPosition().getRowNumber()+"/"+mosaic.getMosaicPosition().getColumnNumber()+".png"));
				System.out.println("clip : "+mosaic.getMosaicName());
			} catch (IOException e) {
				e.printStackTrace();
			}			
			return biMosaic;
		}

		/** Sinon (le nombre de film contenu dans la mosaique est infieur) au nombre maximum de films, alors on écrit la mosaique sur le disque*/
		else{
			BufferedImage biMosaic = generateMosaicImage(image, mosaic, keywords, false);
			return biMosaic;
		}
	}
	
	/** cette methode divise une mosaique en 4 sous mosaiques et renvoie un objet MosaicsToBeClipped
	 * @param dir : le dossier a supprimer
	 * @return : boolean
	 */
	public static boolean deleteDir(File dir) {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    return dir.delete();
	}

	/** cette methode divise une mosaique en 4 sous mosaiques et renvoie un objet MosaicsToBeClipped
	 * @param mosaic : la mosaique qu'il faut diviser en 4 sous mosaiques
	 * @return : les mosaiques a assembler
	 */
	public MosaicsToBeClipped mosaicToSubMosaic(Mosaic mosaic){

		/** MosaicToBeClipped pour la reponse*/
		MosaicsToBeClipped mosaicsToBeClipped = new MosaicsToBeClipped(mosaic.getMosaicPosition());

		/** Pour chaque film*/
		Iterator<Film> itFilm = mosaic.getFilms().iterator();

		/**  Les films sont ensuite inscris dans leur mosaique correspondante*/
		while(itFilm.hasNext()){
			Film filmCurrent = itFilm.next();

			/** on recupere la partie entiere pour avoir la position du film dans la matrice de mosaiques*/
			double columnNumber = Math.floor(filmCurrent.getFilmX());
			double rowNumber = Math.floor(filmCurrent.getFilmY());

			/** selon que le film soit TL,TR,BL ou BR, on le recentre pour que le niveau de zoom inferieur recupere une mosaique avec des films repartis entre 0 et 2*/

			/** variables pour le calcul de la position :*/
			int a = 0, b = 0;

			if(rowNumber==0){
				/** BL a et b = 0*/
				/** BR a = -1;*/
				if(columnNumber==1){
					a = -1;
				}
			}
			else if(rowNumber==1){
				/** TL*/
				if(columnNumber==0){
					b = -1;
				}
				/** TR*/
				if(columnNumber==1){
					a = -1;
					b = -1;
				}
			}

			filmCurrent.setFilmX((float) (filmCurrent.getFilmX()+a)*2);
			filmCurrent.setFilmY((float) (filmCurrent.getFilmY()+b)*2);

			/** on creer une position de mosaique */
			MosaicClipPosition mosaicClipPosition = new MosaicClipPosition((int)rowNumber, (int) columnNumber);

			/** on ajoute le film a la bonne sous mosaique*/
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
		
		int maxNumberOfFilms = image.getMosaicHeight()/20;

		int fontSize = mosaicHeight/maxNumberOfFilms; /** Taille de police definie par rapport a la hauteur de l'image*/
		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		
		ig2.setFont(font);
		ig2.setPaint(Color.black);

		ig2WithTitles.setFont(font);
		ig2WithTitles.setPaint(Color.black);
		
		/** contient le mot clé, on colore en rouge*/
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
			
			/** changement de couleur si le titre du film correspond a un des mots clé*/
			for(int i = 0; i<keywords.length; i++){
				
				if(keywords[i]!="" && filmCurrent.getFilmTitle().contains(keywords[i])){
					
					/** si on etait pas deja en train d'ecrire sur un fond rouge*/
					if(!containsKeyword){
						return generateMosaicImage(image, mosaic, keywords, true);
					}
					
					ig2.setPaint(Color.white);
					ig2WithTitles.setPaint(Color.white);
				}
				
			}
			
			/** on recherche son plus proche voisin*/
			Set<Film> setFilmsLocal = new HashSet<Film>(mosaic.getFilms());
			Film closestFilm = ClosestNeighboor.closestNeighboor(setFilmsLocal,filmCurrent);
			
			String filmTitle = filmCurrent.getFilmTitle();

			double XPositionFilmOnMosaic = filmCurrent.getFilmX()/2;
			double YPositionFilmOnMosaic = 1- filmCurrent.getFilmY()/2;
			
			/** on dessine les points*/
			Shape circle = new Ellipse2D.Double((int)Math.floor(XPositionFilmOnMosaic*mosaicWidth) - radius, (int)Math.floor(YPositionFilmOnMosaic*mosaicHeight) - radius, 2.0 * radius, 2.0 * radius);
			ig2.fill(circle);
			ig2.draw(circle);
			
			/** on dessine le point sur la mosaique qui contient les titres*/
			ig2WithTitles.fill(circle);
			ig2WithTitles.draw(circle);
			
			if(closestFilm != null){
				double XPositionClosestFilmOnMosaic = closestFilm.getFilmX()/2;
				double YPositionClosestFilmOnMosaic = 1-closestFilm.getFilmY()/2;
				
				Draw.drawArrow(ig2WithTitles, (int)Math.floor(XPositionFilmOnMosaic*mosaicWidth), (int)Math.floor(YPositionFilmOnMosaic*mosaicHeight), (int)Math.floor(XPositionClosestFilmOnMosaic*mosaicWidth), (int)Math.floor(YPositionClosestFilmOnMosaic*mosaicHeight));
			}
			
			/**  décalage si le titre du film déborde à droite de l'image*/
			int offsetWidth = 0, offsetHeight = 0;
			if(ig2.getFontMetrics().stringWidth(filmTitle) > mosaicWidth-Math.floor(XPositionFilmOnMosaic*mosaicWidth)){
				offsetWidth = ig2.getFontMetrics().stringWidth(filmTitle);
			}
			/**  décalage si le titre du film déborde en bas de l'image*/
			if(fontSize > mosaicHeight-Math.floor(YPositionFilmOnMosaic*mosaicHeight)){
				offsetHeight = fontSize;
			}
			ig2WithTitles.drawString(filmTitle, (int)Math.floor(XPositionFilmOnMosaic*mosaicWidth - offsetWidth), (int)Math.floor(YPositionFilmOnMosaic*mosaicHeight)+fontSize - offsetHeight);
		}

		try {
			new File(image.getOutputPath()+mosaic.getMosaicPosition().getZoomLevel()+"/"+mosaic.getMosaicPosition().getRowNumber()).mkdirs();
			ImageIO.write(biWithTitles, "PNG", new File(image.getOutputPath()+mosaic.getMosaicPosition().getZoomLevel()+"/"+mosaic.getMosaicPosition().getRowNumber()+"/"+mosaic.getMosaicPosition().getColumnNumber()+".png"));
			System.out.println("leaf : "+mosaic.getMosaicName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
	}

	public BufferedImage clip(Image image,BufferedImage biMosaicTL, BufferedImage biMosaicTR, BufferedImage biMosaicBL, BufferedImage biMosaicBR){

		BufferedImage bi = new BufferedImage(image.getMosaicWidth(), image.getMosaicHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig2 = bi.createGraphics();

		/**  insertion de l'image en haut a gauche*/
		java.awt.Image imageTL = Toolkit.getDefaultToolkit().createImage(biMosaicTL.getSource());
		ig2.drawImage(imageTL,0,0,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);
		biMosaicTL=null;

		/**  insertion de l'image en haut a droite*/
		java.awt.Image imageTR = Toolkit.getDefaultToolkit().createImage(biMosaicTR.getSource());
		ig2.drawImage(imageTR,image.getMosaicWidth()/2,0,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);
		biMosaicTR=null;

		/**  insertion de l'image en bas a gauche*/
		java.awt.Image imageBL = Toolkit.getDefaultToolkit().createImage(biMosaicBL.getSource());
		ig2.drawImage(imageBL,0,image.getMosaicHeight()/2,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);
		biMosaicBL=null;

		/**  insertion de l'image en bas a droite*/
		java.awt.Image imageBR = Toolkit.getDefaultToolkit().createImage(biMosaicBR.getSource());
		ig2.drawImage(imageBR,image.getMosaicWidth()/2,image.getMosaicHeight()/2,image.getMosaicWidth()/2,image.getMosaicHeight()/2,null);
		biMosaicBR=null;

		return bi;
	};

}
