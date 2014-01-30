package model;

/**
 * @author Valentin
 * 
 */
public class Film {
	private String filmTitle;
	private float filmX;
	private float filmY;
	
	public Film(){
		super();
	}
	
	public Film(String filmTitle, float filmX, float filmY) {
		super();
		this.filmTitle = filmTitle;
		this.filmX = filmX;
		this.filmY = filmY;
	}
	
	public MosaicPosition filmToMosaicPosition(Image image){
		
		int numberOfColumns = image.getNumberOfColumns();
		int numberOfRows = image.getNumberOfRows();
		
		//on recupere la partie entiere pour avoir la position du film dans la matrice de mosaiques
		double columnNumber = Math.floor(((this.filmX/100)+1)*numberOfColumns/2);
		double rowNumber = Math.floor(((this.filmY/100)+1)*numberOfRows/2);
		
		MosaicPosition mosaicPosition = new MosaicPosition((int)rowNumber,(int)columnNumber,0);
		
		//on affiche la position du film dans la matrice de mosaiques dans la console
		//System.out.println(mosaicPosition.getColumnNumber()+";"+mosaicPosition.getRowNumber()+"\n");
		
		return mosaicPosition;
	}
	
	public void addFilmToMosaic(Image image, MosaicPosition mosaicPosition){
		//on ajoute le film a la mosaique
		image.getMosaic(mosaicPosition).addFilm(this);
	}
	
	public String getFilmTitle() {
		return filmTitle;
	}
	public void setFilmTitle(String filmTitle) {
		this.filmTitle = filmTitle;
	}
	public double getFilmX() {
		return filmX;
	}
	public void setFilmX(float filmX) {
		this.filmX = filmX;
	}
	public double getFilmY() {
		return filmY;
	}
	public void setFilmY(float filmY) {
		this.filmY = filmY;
	}
	
}
