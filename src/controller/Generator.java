package controller;

import model.*;
import java.awt.image.BufferedImage;


public class Generator {
	
	public boolean generateLevels(String logFilePath){
		
		Mosaic mosaic = new Mosaic(new MosaicPosition(0,0,0));
		recursiveGeneratorLevels(mosaic);
		return true;
	}

	public BufferedImage recursiveGeneratorLevels(Mosaic mosaic){
		
		if(mosaic.getNumberOfFilms() > 100){
			Mosaic mosaic0.0 = les films de la sous mosaique de “mosaic” en haut à gauche;
	Mosaic mosaic0.1 = les films de la sous mosaique de “mosaic” en haut à droite;
	Mosaic mosaic1.0 = les films de la sous mosaique de “mosaic” en bas à gauche;
	Mosaic mosaic1.1 = les films de la sous mosaique de “mosaic” en bas à droite;

	BufferedImage biMosaic0.0 =  recursiveGeneratorLevels(mosaic0.0);
	BufferedImage biMosaic0.1 =  recursiveGeneratorLevels(mosaic0.1);
	BufferedImage biMosaic1.0 =  recursiveGeneratorLevels(mosaic1.0);
	BufferedImage biMosaic0.1 =  recursiveGeneratorLevels(mosaic1.1);
			
	BufferedImage biMosaic = clip(biMosaic0.0,biMosaic0.1,biMosaic1.0,biMosaic1.1);
	write(biMosaic);
	return biMosaic;
		}

		else{
			BufferedImage biMosaic = generateMosaic(mosaic);
			write(mosaic);
			return biMosaic;
		}
	}

	public Mosaic logFileToMosaic(String logFilePath){	};

	// ressemble à film to mosaicPosition
	Mosaic MosaicToSubMosac(Mosaic mosaic, position){};

	clip(BufferedImage biMosaic0.0, BufferedImage biMosaic0.1, BufferedImage biMosaic1.0, BufferedImage biMosaic1.1){};
	generateMosaic(Mosaic mosaic){};


}
