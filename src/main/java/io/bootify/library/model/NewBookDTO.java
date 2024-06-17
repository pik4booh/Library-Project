package io.bootify.library.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.bootify.library.domain.Category;
import io.bootify.library.domain.Theme;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NewBookDTO {

    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String summary;

    @Size(max = 255)
    private String collection;

    @NotNull
    @Size(max = 255)
    private String coteNumber;

    @NotNull
    private String releaseDate;

    @Size(max = 255)
    private String author;

    private List<Category> categories = new ArrayList<>(); // Initialize the list

    private List<Theme> themes = new ArrayList<>(); // Initialize the list

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(final String collection) {
        this.collection = collection;
    }

    public String getCoteNumber() {
        return coteNumber;
    }

    public void setCoteNumber(final String coteNumber) {
        this.coteNumber = coteNumber;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }
}
