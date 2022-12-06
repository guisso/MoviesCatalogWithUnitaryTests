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

import io.github.guisso.moviecatalog.entity.Entity;
import io.github.guisso.moviecatalog.movie.Movie;
import java.util.List;
import java.util.Objects;

/**
 * Class Genre
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 * @version 0.1, 2022-12-02
 */
public class Genre
        extends Entity {

    private String name;
    private String description;
    private List<Movie> movies;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Genre() {
    }

    public Genre(Long id, String name, String description, List<Movie> movies) {
        super(id);
        setName(name);
        setDescription(description);
        this.movies = movies;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getName() {
        return name;
    }

    public final void setName(String name) {
        name = name.trim();

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (name.length() > 40) {
            throw new IllegalArgumentException("Name cannot be longer than 40 characters");
        }

        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        description = description.trim();

        if (description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        if (name.length() > 200) {
            throw new IllegalArgumentException("Description cannot be longer than 200 characters");
        }

        this.description = description;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="hashCode/equals/toString">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(getId());
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.description);
        hash = 17 * hash + Objects.hashCode(this.movies);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Genre other = (Genre) obj;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        return "Genre{"
                + "id=" + getId()
                + ", name=" + name
                + ", description=" + description
                + ", movies=" + movies
                + '}';
    }
    //</editor-fold>

}
