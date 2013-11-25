/**
 * 
 */
package model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Valentin
 *
 */
public class Mosaic {
	private String MosaicName;
	private MosaicPosition MosaicPosition;
	private Set<Film> films;
	
	
	public void addFilm(Film film){
		this.getFilms().add(film);
	}
	
	public Mosaic(model.MosaicPosition mosaicPosition) {
		super();
		MosaicPosition = mosaicPosition;
		this.films = new HashSet<Film>();
	}
	public String getMosaicName() {
		return MosaicName;
	}
	public void setMosaicName(String mosaicName) {
		MosaicName = mosaicName;
	}
	public Set<Film> getFilms() {
		return films;
	}
	public void setFilms(Set<Film> films) {
		this.films = films;
	}
	public MosaicPosition getMosaicPosition() {
		return MosaicPosition;
	}
	public void setMosaicPosition(MosaicPosition mosaicPosition) {
		MosaicPosition = mosaicPosition;
	}
	
}
