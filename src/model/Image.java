package model;

import java.util.HashSet;
import java.util.Set;

public class Image {
	private String imageName;
	private Mosaic[][] mosaics;
	private int numberOfRows;
	private int numberOfColumns;
	private int mosaicHeight;
	private int mosaicWidth;
	
	public Image(int numberOfRows, int numberOfColumns, int mosaicHeight, int mosaicWidth) {
		super();
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		this.mosaicHeight = mosaicHeight;
		this.mosaicWidth = mosaicWidth;
		this.mosaics = new Mosaic[numberOfRows][numberOfColumns];
	}
	
	public Image(int mosaicHeight, int mosaicWidth) {
		super();
		this.mosaicHeight = mosaicHeight;
		this.mosaicWidth = mosaicWidth;
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
	
}
