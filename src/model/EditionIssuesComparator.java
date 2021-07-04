package model;

import java.util.Comparator;

public class EditionIssuesComparator implements Comparator<EditionIssues> {

    @Override
    public int compare(EditionIssues firstEditionIssues, EditionIssues secondEditionIssues) {
        return Integer.compare(firstEditionIssues.getNumOfIssues(), secondEditionIssues.getNumOfIssues());
    }
}
