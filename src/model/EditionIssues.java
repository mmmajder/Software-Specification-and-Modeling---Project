package model;

import java.util.Comparator;

public class EditionIssues {

    private String editionId;
    private int numOfIssues;

    public EditionIssues(String editionId, int numOfIssues){
        this.editionId = editionId;
        this.numOfIssues = numOfIssues;
    }

    public String getEditionId() { return editionId; }

    public int getNumOfIssues() { return numOfIssues; }

}


