package controller;

import java.util.HashSet;
import java.util.Set;

import model.Film;

public class TestClosestNeighboor {
	public static void main(String[] args) {
		Film film1 = new Film("film1",-2,-2);
		/**Film film2 = new Film("film2",3,3);
		Film film3 = new Film("film3",4,4);
		Film film4 = new Film("film4",5,5);*/
		Set<Film> films = new HashSet<Film>();
		
		films.add(film1);
		/**films.add(film2);
		films.add(film3);
		films.add(film4);*/
		Film film = ClosestNeighboor.closestNeighboor(films,film1);
		System.out.println(film.getFilmTitle());
		
	}
}
