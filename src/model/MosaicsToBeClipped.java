/**
 * 
 */
package model;

/**
 * @author fabienrichard
 *
 */
public class MosaicsToBeClipped {
	
	private Mosaic mosaicTL;
	private Mosaic mosaicTR;
	private Mosaic mosaicBL;
	private Mosaic mosaicBR;
	
	public MosaicsToBeClipped(MosaicPosition mosaicMotherPosition){
		this.mosaicBL = new Mosaic(new MosaicPosition(mosaicMotherPosition,0,0), new MosaicClipPosition(0,0));
		this.mosaicBR = new Mosaic(new MosaicPosition(mosaicMotherPosition,0,1), new MosaicClipPosition(0,1));
		this.mosaicTL = new Mosaic(new MosaicPosition(mosaicMotherPosition,1,0), new MosaicClipPosition(1,0));
		this.mosaicTR = new Mosaic(new MosaicPosition(mosaicMotherPosition,1,1), new MosaicClipPosition(1,1));
	}
	
	public Mosaic getMosaicTL() {
		return mosaicTL;
	}
	public void setMosaicTL(Mosaic mosaicTL) {
		this.mosaicTL = mosaicTL;
	}
	public Mosaic getMosaicTR() {
		return mosaicTR;
	}
	public void setMosaicTR(Mosaic mosaicTR) {
		this.mosaicTR = mosaicTR;
	}
	public Mosaic getMosaicBL() {
		return mosaicBL;
	}
	public void setMosaicBL(Mosaic mosaicBL) {
		this.mosaicBL = mosaicBL;
	}
	public Mosaic getMosaicBR() {
		return mosaicBR;
	}
	public void setMosaicBR(Mosaic mosaicBR) {
		this.mosaicBR = mosaicBR;
	}

	public void addFilmToSubMosaic(Film filmCurrent, MosaicClipPosition mosaicClipPosition) {
		
		if(mosaicClipPosition.getRowNumber()==0 && mosaicClipPosition.getColumnNumber()==0){
			this.mosaicBL.addFilm(filmCurrent);
		}
		else if(mosaicClipPosition.getRowNumber()==0 && mosaicClipPosition.getColumnNumber()==1){
			this.mosaicBR.addFilm(filmCurrent);
		}
		else if(mosaicClipPosition.getRowNumber()==1 && mosaicClipPosition.getColumnNumber()==0){
			this.mosaicTL.addFilm(filmCurrent);
		}
		else if(mosaicClipPosition.getRowNumber()==1 && mosaicClipPosition.getColumnNumber()==1){
			this.mosaicTR.addFilm(filmCurrent);
		}
		
	}
	
	
}
