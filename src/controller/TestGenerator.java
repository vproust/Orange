package controller;
import model.Image;
import model.Mosaic;
import model.MosaicsToBeClipped;

public class TestGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		Image image = new Image(1200, 1900);
		
		Generator generator = new Generator();
		Mosaic testMosaic = generator.logFileToMosaic("./input/filmGenerateurRandomXY.txt");
		MosaicsToBeClipped mosaicToSubMosaic = generator.mosaicToSubMosaic(testMosaic);

		generator.generateMosaicImage(image, mosaicToSubMosaic.getMosaicTL());
		generator.generateMosaicImage(image, mosaicToSubMosaic.getMosaicTR());
		generator.generateMosaicImage(image, mosaicToSubMosaic.getMosaicBL());
		generator.generateMosaicImage(image, mosaicToSubMosaic.getMosaicBR());
		**/
		
		Image image = new Image(600, 1200);
		
		Generator generator = new Generator();
		//generator.generateLevels("./input/filmGenerateurRandomXY.txt",image);
  		generator.generateLevels("./input/MovieLens2factors.txt",image);
	}

}
