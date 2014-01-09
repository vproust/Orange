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
	
	public MosaicsToBeClipped(){
		this.mosaicTL = new Mosaic(new MosaicPosition(0,0,0));
		this.mosaicTR = new Mosaic(new MosaicPosition(0,1,0));
		this.mosaicBL = new Mosaic(new MosaicPosition(1,0,0));
		this.mosaicBR = new Mosaic(new MosaicPosition(1,1,0));
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

	public void addFilmToSubMosaic(Film filmCurrent, MosaicPosition mosaicPosition) {
		
		if(mosaicPosition.getRowNumber()==0 && mosaicPosition.getColumnNumber()==0){
			this.mosaicTL.addFilm(filmCurrent);
		}
		else if(mosaicPosition.getRowNumber()==0 && mosaicPosition.getColumnNumber()==1){
			this.mosaicTR.addFilm(filmCurrent);
		}
		else if(mosaicPosition.getRowNumber()==1 && mosaicPosition.getColumnNumber()==0){
			this.mosaicBL.addFilm(filmCurrent);
		}
		else if(mosaicPosition.getRowNumber()==1 && mosaicPosition.getColumnNumber()==1){
			this.mosaicBR.addFilm(filmCurrent);
		}
		
	}
	
	
}
