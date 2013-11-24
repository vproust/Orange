package model;

/**
 * @author Valentin
 * 
 */
public class Film {
	private String filmTitle;
	private float filmX;
	private float filmY;
	
	public Film(String filmTitle, float filmX, float filmY) {
		super();
		this.filmTitle = filmTitle;
		this.filmX = filmX;
		this.filmY = filmY;
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
