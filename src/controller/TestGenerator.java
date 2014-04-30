package controller;
import model.Image;

public class TestGenerator {

	public static void main(String[] args) {

		//on lance le timer
		long startTime = System.currentTimeMillis();
		
//		Image image = new Image(600, 1200, "./input/filmGenerateurRandomXY.txt", "./output/mosaic/");
		Image image = new Image(600, 1200, "./input/filmGenerateurRandomXY100.txt", "./output/mosaic/");
//		Image image = new Image(600, 1200, "./input/MovieLens2factors.txt", "./output/mosaic/");
//		Image image = new Image(600, 1200, "./input/imdb_factorsColumns2.txt", "./output/mosaic/");
		
		String keywords[] = new String[3];
		keywords[0] = "film14";
		keywords[1] = "";
		keywords[2] = "drama";
		
		Generator generator = new Generator();
		generator.generateLevels(image,keywords);
  		
  		//on arrete le timer
  		long endTime   = System.currentTimeMillis();
  		float totalTime = (endTime - startTime);
  		float totalTimeMin = (float) (endTime - startTime)/1000/60;
  		System.out.printf("Total running time : %.2f min (%.0f ms)", totalTimeMin, totalTime);
	}

}
