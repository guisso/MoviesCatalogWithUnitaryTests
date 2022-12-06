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

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 */
public class GenreDaoTest {

    //
    // Naming conventions
    // https://medium.com/@stefanovskyi/unit-test-naming-conventions-dd9208eadbea
    //
    // Tests not working on methods without "test" prefix name
    // https://stackoverflow.com/questions/53433663/maven-not-running-junit-5-tests
    // 
    @Test
    public void checkStrings() {
        assertFalse("luis".equals("luis"));
    }

    @Test
    public void checkNewStrings() {
        assertFalse(new String("luis")
                .equals(new String("luis")));
    }

    @Test
    public void checkNewStringsReferences() {
        assertSame(new String("luis"), "luis");
    }

    @Test
    public void defaultValuesSetOnGenre() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Genre g = new Genre(null,
                            "Ficção Científica",
                            "Explosões ouvidas ao longe no espaço",
                            null);
                });
    }

    @Test
    public void genreWithNameLongerThan40ThrowsException() {
        assertDoesNotThrow(
                () -> {
                    Genre g = new Genre(null,
                            "123456789_123456789_123456789_123456789_1",
                            "0123456789_",
                            null);
                });
    }

    @Test
    public void notFoundGenreSearch() {
        assertNotNull(new GenreDao().findById(-1L));
    }

    @Test
    public void saveGenreAndRetrieveTheCorrectObject() {
        Genre g = new Genre(null,
                "Ficção Científica",
                "Explosões ouvidas ao longe no espaço",
                null);

//        assertThrows(IllegalArgumentException.class,
        assertDoesNotThrow(
                () -> {
                    g.setId(new GenreDao().saveOrUpdate(g));
                }
        );

        Genre retrieved = new GenreDao().findById(g.getId());

        assertNotEquals(g, retrieved);

        // TODO Ensure deletion of saved record.
    }

}
