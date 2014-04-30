package model;


public class Image {
	private String imageName;
	private Mosaic[][] mosaics;
	private int numberOfRows;
	private int numberOfColumns;
	private int mosaicHeight;
	private int mosaicWidth;
	private String logFilePath;
	private String outputPath;
	
	public Image(int numberOfRows, int numberOfColumns, int mosaicHeight, int mosaicWidth) {
		super();
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		this.mosaicHeight = mosaicHeight;
		this.mosaicWidth = mosaicWidth;
		this.mosaics = new Mosaic[numberOfRows][numberOfColumns];
	}
	
	public Image(int mosaicHeight, int mosaicWidth, String logFilePath, String outputPath) {
		super();
		this.mosaicHeight = mosaicHeight;
		this.mosaicWidth = mosaicWidth;
		this.logFilePath = logFilePath;
		this.outputPath = outputPath;
	}

	public void addMosaic(Mosaic mosaic){
		MosaicPosition mosaicPosition = mosaic.getMosaicPosition();
		this.mosaics[mosaicPosition.getRowNumber()][mosaicPosition.getColumnNumber()] = mosaic;
	}

	public Mosaic[][] getMosaicArray() {
		return mosaics;
	}

	public void setMosaicArray(Mosaic[][] mosaicArray) {
		this.mosaics = mosaicArray;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	public int getMosaicHeight() {
		return mosaicHeight;
	}

	public void setMosaicHeight(int mosaicHeight) {
		this.mosaicHeight = mosaicHeight;
	}

	public int getMosaicWidth() {
		return mosaicWidth;
	}

	public void setMosaicWidth(int mosaicWidth) {
		this.mosaicWidth = mosaicWidth;
	}

	public Mosaic getMosaic(MosaicPosition mosaicPosition) {
		return this.mosaics[mosaicPosition.getRowNumber()][mosaicPosition.getColumnNumber()];
	}

	public Mosaic getMosaic(int i, int j) {
		return this.mosaics[i][j];
	}

	/**
	 * @return the logFilePath
	 */
	public String getLogFilePath() {
		return logFilePath;
	}

	/**
	 * @param logFilePath the logFilePath to set
	 */
	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	/**
	 * @return the outputPath
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * @param outputPath the outputPath to set
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	
}
