package controller;

import java.util.Iterator;
import java.util.Set;

import model.Film;

public class ClosestNeighboor {
	
	public static Film closestNeighboor(Set<Film> films,Film film){
	
		films.remove(film);
		
		if(films.isEmpty()){
			return null;
		}
		
		Iterator<Film> it = films.iterator();
		double distanceMin = Double.MAX_VALUE;

		Film closestNeighboorFilm = new Film();
		
		while(it.hasNext()){
			Film filmCurrent = it.next();
			// distance = racine((Yb-Ya)�+(Xb-Xa)�)
			double distance = Math.sqrt(Math.pow((film.getFilmY()-filmCurrent.getFilmY()),2)+Math.pow(film.getFilmX()-filmCurrent.getFilmX(),2));
			if(distanceMin>distance){
				distanceMin=distance;
				closestNeighboorFilm = filmCurrent;
			}
		}
		
		return closestNeighboorFilm;	
	}

}
