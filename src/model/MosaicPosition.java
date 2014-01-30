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
	
	//genere une mosaic poisition a partir de la mosaique mère (incrementation du zoom et de la position)
	public MosaicPosition(MosaicPosition mosaicPosition, int isBottom, int isRight){
		this.rowNumber = mosaicPosition.getRowNumber()*2+isBottom;
		this.columnNumber = mosaicPosition.getColumnNumber()*2+isRight;
		this.zoomLevel = mosaicPosition.getZoomLevel()+1;
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
