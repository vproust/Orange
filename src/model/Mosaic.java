/**
 * 
 */
package model;

import java.util.Set;

/**
 * @author Valentin
 *
 */
public class Mosaic {
	private String MosaicName;
	private MosaicPosition MosaicPosition;
	private Set<Film> films;
	
	public Mosaic(model.MosaicPosition mosaicPosition) {
		super();
		MosaicPosition = mosaicPosition;
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
