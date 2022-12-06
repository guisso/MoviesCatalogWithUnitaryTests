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
package io.github.guisso.moviecatalog.movie;

import io.github.guisso.moviecatalog.genre.GenreDao;
import io.github.guisso.moviecatalog.genre.Genre;
import io.github.guisso.moviecatalog.repository.Dao;
import io.github.guisso.moviecatalog.repository.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe MovieDao
 *
 * CREATE TABLE `movie` (
 *  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
 *  `title` varchar(100) NOT NULL,
 *  `year` smallint(6) NOT NULL,
 *  `rate` char(3) NOT NULL,
 *  `genre_id` bigint(20) unsigned NOT NULL,
 *  PRIMARY KEY (`id`),
 *  UNIQUE KEY `id` (`id`),
 *  KEY `genre_id` (`genre_id`),
 *  CONSTRAINT `movie_ibfk_1` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`)
 * ) ENGINE=InnoDB;
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 * @version 0.1, 2022-12-02
 */
public class MovieDao
        extends Dao<Movie> {

    public static final String TABLE = "movie";

    @Override
    public String getSaveStatment() {
        return "insert into " + TABLE
                + " (title, year, rate, genre_id)"
                + " values (?, ?, ?, ?)";
    }

    @Override
    public String getUpdateStatment() {
        return "update " + TABLE
                + " set title = ?, year = ?, rate = ?, genre_id = ?"
                + " where id = ?";
    }

    @Override
    public void composeSaveOrUpdateStatement(PreparedStatement pstmt, Movie e) {
        try {
            pstmt.setString(1, e.getTitle());
            pstmt.setShort(2, e.getYear());
            pstmt.setString(3, e.getRate().getCode());
            pstmt.setLong(4, e.getGenre().getId());

            if (e.getId() != null && e.getId() > 0) {
                pstmt.setLong(5, e.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getFindByIdStatment() {
        return "select id, title, year, rate, genre_id"
                + " from " + TABLE
                + " where id = ?";
    }

    @Override
    public String getFindAllStatment() {
        return "select id, title, year, rate, genre_id"
                + " from " + TABLE;
    }

    /**
     * SQL statement to use to find movies by genre
     *
     * @return SQL statement
     */
    public String getFindAllByGenreIdStatment() {
        return "select id, title, year, rate, genre_id"
                + " from " + TABLE
                + " where genre_id = ?";
    }
    
    private String getfindAllByPartialNameStatment() {
        return " select id, title, year, rate, genre_id"
                + " from " + TABLE
                + " where title like ?";
    }

    public List<Movie> findAllByGenre(Genre genre) {
        return findAllByGenreId(genre.getId());
    }

    public List<Movie> findAllByGenreId(Long genreId) {

        try ( PreparedStatement preparedStatement
                = DbConnection.getConnection().prepareStatement(
                        getFindAllByGenreIdStatment())) {

            // Replacement genre ID to query
            preparedStatement.setLong(1, genreId);

            // Show the full sentence
            System.out.println(">> SQL: " + preparedStatement);

            // Performs the query on the database
            ResultSet resultSet = preparedStatement.executeQuery();

            // Returns the respective object
            return extractObjects(resultSet);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }
    
    public List<Movie> findAllByPartialName(String partialName) {

        try ( PreparedStatement preparedStatement
                = DbConnection.getConnection().prepareStatement(
                        getfindAllByPartialNameStatment())) {

            preparedStatement.setString(1, "%" + partialName + "%");

            // Show the full sentence
            System.out.println(">> SQL: " + preparedStatement);

            // Performs the query on the database
            ResultSet resultSet = preparedStatement.executeQuery();

            // Returns the respective object
            return extractObjects(resultSet);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }

        return null;
    }

    @Override
    public String getMoveToTrashStatement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getRestoreFromTrashStatement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getFindAllOnTrashStatement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Extracts the movie from the result set with the associated genre
     *
     * @param resultSet The record in the database
     * @return The movie located
     */
    @Override
    public Movie extractObject(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                return new Movie(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getShort("year"),
                        Movie.castStringToRate(resultSet.getString("rate")),
                        // Cascade!
                        new GenreDao().findById(resultSet.getLong("genre_id"))
                );
            } catch (SQLException ex) {
                Logger.getLogger(MovieDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    

}
