package model;

import java.util.Set;

public class Image {
	private String imageName;
	private Set<Mosaic> mosaics;
	private int numberOfRows;
	private int numberOfColumns;
	private int mosaicHeight;
	private int mosaicWidth;
	
	public Image(int numberOfRows, int numberOfColumns, int mosaicHeight,
			int mosaicWidth) {
		super();
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		this.mosaicHeight = mosaicHeight;
		this.mosaicWidth = mosaicWidth;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Set<Mosaic> getMosaics() {
		return mosaics;
	}

	public void setMosaics(Set<Mosaic> mosaics) {
		this.mosaics = mosaics;
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
	
}
