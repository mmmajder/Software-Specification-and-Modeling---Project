package controller;

import model.*;
import model.enums.ContributorType;
import utils.StringUtils;
import utils.exceptions.IdAlreadyExistsException;
import utils.exceptions.MissingValueException;
import utils.exceptions.NoGenreOfSuchNameException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EditionController {

    private Library library;

    public EditionController(Library library) {
        this.library = library;
    }

    public void create(String editionId, String title, String publisher, int numberOfPages,
                       String description, String genreName, LocalDate publishedDate, String language,
                       List<ContributorRole> contributorRoles) throws MissingValueException, IdAlreadyExistsException, NoGenreOfSuchNameException {

        validateInputValues(editionId, title, publisher, genreName, language, contributorRoles);
        Edition e = new Edition(editionId, title, publisher, numberOfPages,
                description, library.getGenre(genreName), publishedDate, language, contributorRoles);
        library.addEdition(e);
    }

    private void validateInputValues(String editionId, String title, String publisher,
                                     String genreName, String language, List<ContributorRole> contributorRoles) throws MissingValueException, IdAlreadyExistsException {
        if (StringUtils.isNullOrEmpty(editionId)) {
            throw new MissingValueException("editionId");
        }
        if (editionIdExists(editionId)) {
            throw new IdAlreadyExistsException();
        }
        if (StringUtils.isNullOrEmpty(title)) {
            throw new MissingValueException("title");
        }
        if (StringUtils.isNullOrEmpty(publisher)) {
            throw new MissingValueException("publisher");
        }
        if (StringUtils.isNullOrEmpty(genreName)){
            throw new MissingValueException("genre");
        }
        if (StringUtils.isNullOrEmpty(language)) {
            throw new MissingValueException("language");
        }
        if (contributorRoles.size() == 0) {
            throw new MissingValueException("contributorRoles");
        }
    }

    public void update(String editionId, String title, String publisher, int numberOfPages,
                       String description, LocalDate publishedDate, String language){
        Edition edition = library.getEdition(editionId);
        edition.setTitle(title);
        edition.setPublisher(publisher);
        edition.setNumberOfPages(numberOfPages);
        edition.setDescription(description);
        edition.setPublishedDate(publishedDate);
        edition.setLanguage(language);
    }

    public boolean editionIdExists(String editionId) {
        List<Edition> editions = library.getEditions();

        for (Edition e : editions) {
            if (e.getEditionId().equals(editionId)) {
                return true;
            }
        }

        return false;
    }

    public List<Edition> getRandomEditions(int n) {
        List<String> allEditionIds = library.getEditions().stream().map(Edition::getEditionId).collect(Collectors.toList());
        List<String> randomIds = new ArrayList<>();
        Random r = new Random();

        if (n > allEditionIds.size()) n = allEditionIds.size();

        for (int i = 0; i < n; i++) {
            int randIndex = r.nextInt(allEditionIds.size());
            String randId = allEditionIds.get(randIndex);
            allEditionIds.remove(randIndex);
            randomIds.add(randId);
        }

        return library.getEditions().stream().filter(edition -> randomIds.contains(edition.getEditionId())).collect(Collectors.toList());
    }

    public String getAuthorName(Edition edition) {
        return edition.getAuthor().getName() + " " + edition.getAuthor().getSurname();
    }

    public String getAuthorsStr(Edition edition){ return getContributorsStrConcatenated(edition, ContributorType.AUTHOR); }

    public String getTranslatorsStr(Edition edition){ return getContributorsStrConcatenated(edition, ContributorType.TRANSLATOR); }

    public String getIllustratorsStr(Edition edition){ return getContributorsStrConcatenated(edition, ContributorType.ILLUSTRATOR); }

    public String getCriticsStr(Edition edition){ return getContributorsStrConcatenated(edition, ContributorType.CRITIC); }

    public String getEditorsStr(Edition edition){ return getContributorsStrConcatenated(edition, ContributorType.EDITOR); }

    private String getContributorsStrConcatenated(Edition edition, ContributorType type){
        String concatenated = "";
        List<String> contributors = getContributorsStr(edition, type);

        for (int i = 0; i < contributors.size(); i++){
            concatenated += contributors.get(i);

            if (i != contributors.size() - 1){
                concatenated += ", ";
            }
        }

        return concatenated;
    }

    private List<String> getContributorsStr(Edition edition, ContributorType type){
        return edition.getContributors(type).stream()
                .map(contributor -> contributor.getName() + " " + contributor.getSurname())
                .collect(Collectors.toList());
    }

    public String getGenresConcatenated(Edition edition){
        String genresStr = "";
        List<Genre> genres = edition.getGenres();

        for (int i = 0; i < genres.size(); i++){
            genresStr += genres.get(i).getName();

            if (i != genres.size() - 1){
                genresStr += ", ";
            }
        }

        return genresStr;
    }
}
