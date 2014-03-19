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
		
		//on lance le timer
		long startTime = System.currentTimeMillis();
		
		Image image = new Image(600, 1200);
		
		Generator generator = new Generator();
		//generator.generateLevels("./input/filmGenerateurRandomXY.txt",image);
  		generator.generateLevels("./input/MovieLens2factors.txt",image);
  		
  		//on arrete le timer
  		long endTime   = System.currentTimeMillis();
  		float totalTime = (endTime - startTime);
  		float totalTimeMin = (float) (endTime - startTime)/1000/60;
  		System.out.printf("Total running time : %.2f min (%.0f ms)", totalTimeMin, totalTime);
	}

}
