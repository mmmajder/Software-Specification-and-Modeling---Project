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

    public int getBookFormatId() { return bookFormatId; }

    public double getHeight() { return height; }

    public double getWidth() { return width; }

    public double getThickness() { return thickness; }
}
