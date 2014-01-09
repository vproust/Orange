package model;

public class MosaicPosition {
	int rowNumber;
	int columnNumber;
	
	public MosaicPosition(int rowNumber, int columnNumber) {
		super();
		this.rowNumber = rowNumber;
		this.columnNumber = columnNumber;
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
}
