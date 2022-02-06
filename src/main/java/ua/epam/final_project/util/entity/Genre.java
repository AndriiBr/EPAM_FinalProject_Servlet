package ua.epam.final_project.util.entity;

import java.util.Map;

public class Genre {
    Map<Integer, String> genres;

    public Map<Integer, String> getGenreList() {
        return genres;
    }

    public void setGenreList(Map<Integer, String> genreList) {
        this.genres = genreList;
    }
}
