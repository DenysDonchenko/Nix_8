package ua.com.alevel.db.impl;

import ua.com.alevel.db.FilmDB;
import ua.com.alevel.entity.Film;
import ua.com.alevel.util.DBHelper;

public class FilmDBImpl implements FilmDB {

    private static FilmDBImpl instance;

    private Film[] films;

    private static Long film_id = 1L;
    public static int realSizeArray = 0;
    private static int capacity = 10;
    private static int indexNewFilm = 0;

    private FilmDBImpl() {
        films = new Film[capacity];
    }

    public static FilmDBImpl getInstance() {
        if (instance == null) {
            instance = new FilmDBImpl();
        }
        return instance;
    }

    @Override
    public void create(Film film) {
        if (realSizeArray == capacity - 1) {
            films = DBHelper.newCapacity(capacity, films);
            capacity = films.length;
        }
        film.setId(film_id++);
        films[indexNewFilm] = film;
        indexNewFilm++;
        realSizeArray++;
    }

    @Override
    public void update(Film film) {

        films[findIndexById(film.getId())] = film;
    }

    @Override
    public void delete(Long id) {
        int indexFilm = findIndexById(id);
        films = DBHelper.removeItems(films, indexFilm);
        realSizeArray--;
    }

    @Override
    public Film findById(Long id) {
        for (int i = 0; i < films.length; i++) {
            if (films[i].getId() == id) {
                return films[i];
            }
        }
        throw new RuntimeException("Film with id - " + id + " not found");
    }

    @Override
    public Film[] findAll() {
        Film[] allFilms = new Film[realSizeArray];
        for (int i = 0; i < realSizeArray; i++) {
            if (films[i] != null) {
                allFilms[i] = films[i];
            }
        }
        return allFilms;
    }

    @Override
    public int count() {
        return realSizeArray;
    }

    private int findIndexById(Long id) {

        for (int i = 0; i < films.length; i++) {
            if (films[i].getId() == id) {
                return i;
            }
        }
        throw new RuntimeException("Films with id - " + id + " not found");
    }
}