package controller;

import model.*;
import model.enums.ContributorType;
import model.enums.MemberType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EditionControllerTest {

    @Test
    void editionIdExistsTrue() {
        Library library = new Library();
        Edition edition = new Edition("1");
        library.addEdition(edition);
        EditionController editionController = new EditionController(library);

        assertTrue(editionController.editionIdExists("1"));
    }

    @Test
    void editionIdExistsFalse() {
        Library library = new Library();
        Edition edition = new Edition("1");
        library.addEdition(edition);
        EditionController editionController = new EditionController(library);

        assertFalse(editionController.editionIdExists("33"));
    }

    @Test
    void getAuthorNameTrue() {
        Library library = new Library();
        Edition edition = new Edition("1");
        addTestContributorRole(edition);
        library.addEdition(edition);
        EditionController editionController = new EditionController(library);

        assertEquals(editionController.getAuthorName(edition), "Kontributor Kontributic");
    }

    @Test
    void getAuthorNameFalse() {
        Library library = new Library();
        Edition edition = new Edition("1");
        addTestContributorRole(edition);
        library.addEdition(edition);
        EditionController editionController = new EditionController(library);

        assertNotEquals(editionController.getAuthorName(edition), "Drugoime Prezime");
    }

    private void addTestContributorRole(Edition edition){
        Contributor contributor = new Contributor();
        contributor.setName("Kontributor");
        contributor.setSurname("Kontributic");
        ContributorRole contributorRole = new ContributorRole(ContributorType.AUTHOR, contributor, edition);
        edition.addContributorRole(contributorRole);
    }

    @Test
    void getAuthorsStrTrue() {
        Library library = new Library();
        Edition edition = new Edition("1");
        addTestContributorRole(edition);
        addTestContributorRole(edition);
        library.addEdition(edition);
        EditionController editionController = new EditionController(library);

        assertEquals(editionController.getAuthorsStr(edition), "Kontributor Kontributic, Kontributor Kontributic");
    }

    @Test
    void getAuthorsStrFalse() {
        Library library = new Library();
        Edition edition = new Edition("1");
        addTestContributorRole(edition);
        addTestContributorRole(edition);
        library.addEdition(edition);
        EditionController editionController = new EditionController(library);

        assertNotEquals(editionController.getAuthorsStr(edition), "Drugoime Prezime, Drugoime Prezime");
    }

    @Test
    void getGenresConcatenated() {
        Library library = new Library();
        Edition edition = new Edition("1");
        addGenres(edition);
        library.addEdition(edition);
        EditionController editionController = new EditionController(library);

        assertEquals(editionController.getGenresConcatenated(edition), "Genre1, Genre2");
    }

    @Test
    void getGenresConcatenatedFalse() {
        Library library = new Library();
        Edition edition = new Edition("1");
        addGenres(edition);
        library.addEdition(edition);
        EditionController editionController = new EditionController(library);

        assertNotEquals(editionController.getGenresConcatenated(edition), "GenreNOT1, GenreNOT2");
    }

    private void addGenres(Edition edition){
        Genre genre1 = new Genre();
        genre1.setName("Genre1");
        edition.addGenre(genre1);

        Genre genre2 = new Genre();
        genre2.setName("Genre2");
        edition.addGenre(genre2);
    }
}