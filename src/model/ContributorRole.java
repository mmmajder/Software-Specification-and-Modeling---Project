package model;

import model.enums.ContributorType;

public class ContributorRole {
    private ContributorType role;
    private Contributor contributor;
    private Edition edition;

    public ContributorRole() {
    }

    public ContributorRole(ContributorType role, Contributor contributor, Edition edition) {
        this.role = role;
        this.contributor = contributor;
        this.edition = edition;
    }
}
