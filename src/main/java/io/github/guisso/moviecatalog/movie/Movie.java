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

import io.github.guisso.moviecatalog.genre.Genre;
import io.github.guisso.moviecatalog.entity.Entity;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class Movie
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 * @version 0.1, 2022-12-02
 */
public class Movie
        extends Entity {

    private String title;
    private Short year;
    private Rate rate;
    private Genre genre;

    //<editor-fold defaultstate="collapsed" desc="Enum casting">
    // https://www.baeldung.com/java-cast-int-to-enum
    private final static Map<String, Rate> codeToRateMapping;

    static {
        codeToRateMapping = new HashMap<>();
        for (Rate r : Rate.values()) {
            codeToRateMapping.put(r.getCode(), r);
        }
    }

    public static Rate castStringToRate(String code) {
        return codeToRateMapping.get(code);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Movie() {
    }

    public Movie(Long id, String title, Short year, Rate rate, Genre genre) {
        super(id);
        setTitle(title);
        this.year = year;
        this.rate = rate;
        this.genre = genre;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        title = title.trim();
        
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        
        if (title.length() > 200) {
            throw new IllegalArgumentException("Title cannot be longer than 200 characters");
        }
        
        this.title = title;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    //</editor-fold>

//    public enum SimpleRate {
//        Free, Age10, Age12, Age14, Age16, Adult;
//    }
    
    public enum Rate {
        Free("F"),
        Age10("10"),
        Age12("12"),
        Age14("14"),
        Age16("16"),
        Adult("18+");

        private final String code;

        Rate(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="hashCode/equals/toString">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.getId());
        hash = 59 * hash + Objects.hashCode(this.title);
        hash = 59 * hash + Objects.hashCode(this.year);
        hash = 59 * hash + Objects.hashCode(this.rate);
        hash = 59 * hash + Objects.hashCode(this.genre);
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
        final Movie other = (Movie) obj;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        return "Movie{"
                + "id=" + getId()
                + ", title=" + title
                + ", year=" + year
                + ", rate=" + rate
                + ", genre=" + genre
                + '}';
    }
    //</editor-fold>

}
