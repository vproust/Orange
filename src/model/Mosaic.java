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
	private class MosaicPosition {
		int rowNumber;
		int columnNumber;
	}
	private Set<Film> films;
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
	
}
