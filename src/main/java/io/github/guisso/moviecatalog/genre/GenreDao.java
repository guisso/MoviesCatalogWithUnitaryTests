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
package io.github.guisso.moviecatalog.genre;

import io.github.guisso.moviecatalog.repository.Dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe GenreDao
 *
 * CREATE TABLE `genre` ( 
 *  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
 *  `name` varchar(40) NOT NULL, 
 *  `description` varchar(200) NOT NULL, 
 *  PRIMARY KEY (`id`), 
 *  UNIQUE KEY `id` (`id`) 
 * ) ENGINE=InnoDB;
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 * @version 0.1, 2022-12-02
 */
public class GenreDao
        extends Dao<Genre> {

    public static final String TABLE = "genre";

    @Override
    public String getSaveStatment() {
        return "insert into " + TABLE 
                + " (name, description)"
                + " values (?, ?)";
    }

    @Override
    public String getUpdateStatment() {
        return "update " + TABLE 
                + " set name = ?, description = ?"
                + " where id = ?";
    }

    @Override
    public void composeSaveOrUpdateStatement(PreparedStatement pstmt, Genre e) {
        try {
            pstmt.setString(1, e.getName());
            pstmt.setString(2, e.getDescription());
            
            if(e.getId() != null && e.getId() > 0) {
                pstmt.setLong(3, e.getId());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(GenreDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getFindByIdStatment() {
        return "select id, name, description"
                + " from " + TABLE 
                + " where id = ?";
    }

    @Override
    public String getFindAllStatment() {
        return "select id, name, description"
                + " from " + TABLE;
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
     * Extracts the genre from the result set without the associated movies
     * @param resultSet The record in the database
     * @return The genre located
     */
    @Override
    public Genre extractObject(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                return new Genre(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        // Without cascade on movies!
                        null
                ); 
            } catch (SQLException ex) {
                Logger.getLogger(GenreDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

}
