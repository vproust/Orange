package model;

import java.util.Set;

public class Image {
	private String imageName;
	private Set<Mosaic> mosaics;
	private int numberofRows;
	private int numberofColumns;
	private int mosaicHeight;
	private int mosaicWidth;
	public Image(int numberofRows, int numberofColumns) {
		super();
		NumberofRows = numberofRows;
		NumberofColumns = numberofColumns;
	}
	
	
}
