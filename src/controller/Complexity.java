package controller;

import model.Image;


/**
 * @author fabienrichard
 *
 */
public class Complexity {
	
	/** appel principal de la methode de test de complexite en temps
	 * @param args
	 */
	public static void main(String[] args) {
		
		//echauffement
//		String logFilePath = "./input/filmGenerateurRandomXY10.txt";
//		String outputPath = "./output/mosaic10/";
//		launchTest(logFilePath,outputPath);

//		logFilePath = "./input/filmGenerateurRandomXY10.txt";
//		outputPath = "./output/mosaic10/";
//		launchTest(logFilePath,outputPath);
//		
//		logFilePath = "./input/filmGenerateurRandomXY100.txt";
//		outputPath = "./output/mosaic100/";
//		launchTest(logFilePath,outputPath);
//		
//		logFilePath = "./input/filmGenerateurRandomXY1000.txt";
//		outputPath = "./output/mosaic1000/";
//		launchTest(logFilePath,outputPath);
//		
//		logFilePath = "./input/filmGenerateurRandomXY10000.txt";
//		outputPath = "./output/mosaic10000/";
//		launchTest(logFilePath,outputPath);
		
		String logFilePath = "./input/imdb_factorsColumns2.txt";
		String outputPath = "./output/mosaic100000/";
		launchTest(logFilePath,outputPath);
	}

	/** cette methode permet de lancer un timer et l'exécution de la génération des mosaiques pour effectuer des tests de complexité en temps
	 * @param logFilePath
	 * @param outputPath
	 */
	public static void launchTest(String logFilePath, String outputPath){
		//on lance le timer
		long startTime = System.currentTimeMillis();

		Generator generator = new Generator();

		String keywords[] = {"gang"};

		Image image = new Image(450, 600, logFilePath, outputPath);

		generator.generateLevels(image,keywords);

		//on arrete le timer
		long endTime   = System.currentTimeMillis();
		float totalTime = (endTime - startTime);
		float totalTimeMin = (float) (endTime - startTime)/1000/60;
		System.out.printf("%s : %.2f min (%.0f ms)",logFilePath, totalTimeMin, totalTime);
		System.out.println('\n');
	}

}
