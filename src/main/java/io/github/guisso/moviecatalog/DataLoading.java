/*
 * CC BY-NC-SA 4.0
 *
 * Copyright 2022 Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;.
 *
 * Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0)
 *
 * You are free to:
 *   Share - copy and redistribute the material in any medium or format
 *   Adapt - remix, transform, and build upon the material
 *
 * Under the following terms:
 *   Attribution - You must give appropriate credit, provide 
 *   a link to the license, and indicate if changes were made.
 *   You may do so in any reasonable manner, but not in any 
 *   way that suggests the licensor endorses you or your use.
 *   NonCommercial - You may not use the material for commercial purposes.
 *   ShareAlike - If you remix, transform, or build upon the 
 *   material, you must distribute your contributions under 
 *   the same license as the original.
 *   No additional restrictions - You may not apply legal 
 *   terms or technological measures that legally restrict 
 *   others from doing anything the license permits.
 *
 * Notices:
 *   You do not have to comply with the license for elements 
 *   of the material in the public domain or where your use 
 *   is permitted by an applicable exception or limitation.
 *   No warranties are given. The license may not give you 
 *   all of the permissions necessary for your intended use. 
 *   For example, other rights such as publicity, privacy, 
 *   or moral rights may limit how you use the material.
 */
package io.github.guisso.moviecatalog;

import io.github.guisso.moviecatalog.genre.Genre;
import io.github.guisso.moviecatalog.genre.GenreDao;
import io.github.guisso.moviecatalog.movie.Movie;
import io.github.guisso.moviecatalog.movie.MovieDao;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 */
public class DataLoading {

    public static void main(String[] args) {

        Genre horror
                = new Genre(null,
                        "Terror",
                        "Sombras, sustos e muito mais",
                        // W/o movies
                        null);

        Genre adventure
                = new Genre(null,
                        "Aventura",
                        "Adrenalina e suspense",
                        // W/o movies
                        null);

        Movie it = new Movie(
                null,
                "A coisa",
                (short) 2022,
                // "14"
                Movie.Rate.Age14,
                // Associated genre cannot be null
                horror
        );

        Movie dracula = new Movie(
                null,
                "Drácula",
                (short) 2021,
                // "16"
                Movie.Rate.Age16,
                // Associated genre cannot be null
                horror
        );

        Movie tarantula = new Movie(
                null,
                "Aranha!",
                (short) 1977,
                // "16"
                Movie.Rate.Adult,
                // Associated genre cannot be null
                horror
        );

        Movie everest = new Movie(
                null,
                "Everest",
                (short) 2019,
                // "16"
                Movie.Rate.Age14,
                // Associated genre cannot be null
                adventure
        );

        // Association for bidirectional navigation
        horror.setMovies(Arrays.asList(it, dracula, tarantula));
        adventure.setMovies(Arrays.asList(everest));

        // Saves only the genre! Not any movies.
        Long idAux;

        try {
            idAux = new GenreDao().saveOrUpdate(horror);
            horror.setId(idAux);

            idAux = new GenreDao().saveOrUpdate(adventure);
            adventure.setId(idAux);

            // Saves only the movies! But ID for genre is used.
            idAux = new MovieDao().saveOrUpdate(it);
            it.setId(idAux);

            idAux = new MovieDao().saveOrUpdate(dracula);
            dracula.setId(idAux);

            idAux = new MovieDao().saveOrUpdate(tarantula);
            tarantula.setId(idAux);

            idAux = new MovieDao().saveOrUpdate(everest);
            everest.setId(idAux);
        } catch (Exception ex) {
            Logger.getLogger(DataLoading.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        // Retrieves and display stored horror movies
        List<Movie> horrorFilms
                = new MovieDao().findAllByGenre(horror);

        for (Movie m : horrorFilms) {
            System.out.println(">> "
                    + m.getTitle() + ", "
                    + m.getGenre().getName());
        }
    }
}
