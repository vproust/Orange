package model;

public class MosaicPosition {
	int rowNumber;
	int columnNumber;
	int zoomLevel;
	
	public MosaicPosition(int rowNumber, int columnNumber, int zoomLevel){
		super();
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
		this.zoomLevel = zoomLevel;
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public int getColumnNumber() {
		return columnNumber;
	}
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	public int getZoomLevel() {
		return zoomLevel;
	}
	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
}
