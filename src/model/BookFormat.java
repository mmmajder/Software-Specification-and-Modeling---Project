package model;

public class BookFormat {
    private int bookFormatId;
    private double height;
    private double width;
    private double thickness;

    public BookFormat(int bookFormatId, double height, double width, double thickness) {
        this.bookFormatId = bookFormatId;
        this.height = height;
        this.width = width;
        this.thickness = thickness;
    }
}
