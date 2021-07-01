package model;

import java.time.LocalDate;
import java.util.Comparator;

public class Sorter {

    public static Comparator<Edition> EditionTitleAscComparator = new Comparator<Edition>() {

        @Override
        public int compare(Edition e1, Edition e2) {
            String editionTitle1 = e1.getTitle().toUpperCase();
            String editionTitle2 = e2.getTitle().toUpperCase();

            return editionTitle1.compareTo(editionTitle2);
        }
    };

    public static Comparator<Edition> EditionTitleDescComparator = new Comparator<Edition>() {

        @Override
        public int compare(Edition e1, Edition e2) {
            String editionTitle1 = e1.getTitle().toUpperCase();
            String editionTitle2 = e2.getTitle().toUpperCase();

            return editionTitle2.compareTo(editionTitle1);
        }
    };

    public static Comparator<Edition> EditionPublishedDateAscComparator = new Comparator<Edition>() {

        @Override
        public int compare(Edition e1, Edition e2) {
            LocalDate editionPublishedDate1 = e1.getPublishedDate();
            LocalDate editionPublishedDate2 = e2.getPublishedDate();

            return editionPublishedDate1.compareTo(editionPublishedDate2);
        }
    };

    public static Comparator<Edition> EditionPublishedDateDescComparator = new Comparator<Edition>() {

        @Override
        public int compare(Edition e1, Edition e2) {
            LocalDate editionPublishedDate1 = e1.getPublishedDate();
            LocalDate editionPublishedDate2 = e2.getPublishedDate();

            return editionPublishedDate2.compareTo(editionPublishedDate1);
        }
    };
}
