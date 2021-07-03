package model;

import java.util.ArrayList;
import java.util.List;

public class Contributor {
    private int contributorId;
    private String name;
    private String surname;
    private String biography;
    private List<ContributorRole> contributorRoles;

    public Contributor() {
        this.contributorRoles = new ArrayList<>();
        this.biography = null;
    }

    public Contributor(int contributorId, String name, String surname, String biography) {
        this.contributorId = contributorId;
        this.name = name;
        this.surname = surname;
        this.biography = biography;
        this.contributorRoles = new ArrayList<>();
    }

    public int getContributorId() {
        return contributorId;
    }

    public void addContributorRole(ContributorRole role) {
        this.contributorRoles.add(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
