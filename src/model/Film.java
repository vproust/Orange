package model;

/**
 * @author Valentin
 * 
 */
public class Film {
	private String filmTitle;
	private double filmX;
	private double filmY;
	
	public Film(String filmTitle, double filmX, double filmY) {
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
	public void setFilmX(double filmX) {
		this.filmX = filmX;
	}
	public double getFilmY() {
		return filmY;
	}
	public void setFilmY(double filmY) {
		this.filmY = filmY;
	}
	
}
