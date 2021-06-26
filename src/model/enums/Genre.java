package model.enums;

public enum Genre {
    FANTASY("Fantasy"),
    SCI_FI("Science fiction"),
    MYSTERY("Mystery"),
    HISTORY("History"),
    THRILLER("Triller"),
    HORROR("Horror"),
    ROMANCE("Romance"),
    WESTERN("Western"),
    DYSTOPIAN("Dystopian"),
    CONTEMPORARY("Contemprorary");

    public final String label;

    private Genre(String label) {
        this.label = label;
    }
}
