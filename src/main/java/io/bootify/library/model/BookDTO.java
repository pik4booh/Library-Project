package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;


public class BookDTO {

    private Integer idBook;

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
    private LocalDateTime releaseDate;

    private Integer author;

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(final Integer idBook) {
        this.idBook = idBook;
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

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(final Integer author) {
        this.author = author;
    }

}
