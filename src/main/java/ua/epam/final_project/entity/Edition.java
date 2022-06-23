package ua.epam.final_project.entity;

import java.io.Serializable;

public class Edition implements Serializable {

    private int id;
    private String titleEn;
    private String titleUa;
    private String textEn;
    private String textUa;
    private String imagePath;
    private int genreId;
    private int price;

    public Edition() {}

    public Edition(String titleEn, String titleUa, String textEn, String textUa, int genreId) {
        this.titleEn = titleEn;
        this.titleUa = titleUa;
        this.textEn = textEn;
        this.textUa = textUa;
        this.genreId = genreId;
        this.imagePath = "";
        this.price = 0;
    }

    public Edition(String titleEn, String titleUa, String textEn, String textUa, int price, int genreId) {
        this.titleEn = titleEn;
        this.titleUa = titleUa;
        this.textEn = textEn;
        this.textUa = textUa;
        this.genreId = genreId;
        this.imagePath = "";
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String title) {
        this.titleEn = title;
    }

    public String getTitleUa() {
        return titleUa;
    }

    public void setTitleUa(String titleUa) {
        this.titleUa = titleUa;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public String getTextUa() {
        return textUa;
    }

    public void setTextUa(String textUa) {
        this.textUa = textUa;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}
