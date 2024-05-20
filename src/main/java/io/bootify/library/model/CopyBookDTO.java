package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;


public class CopyBookDTO {

    private Integer idCopyBook;

    @Size(max = 255)
    private String language;

    @NotNull
    @Size(max = 255)
    private String isbn;

    private LocalDateTime editDate;

    private Integer pageNumber;

    @Size(max = 255)
    private String cover;

    private Integer book;

    public Integer getIdCopyBook() {
        return idCopyBook;
    }

    public void setIdCopyBook(final Integer idCopyBook) {
        this.idCopyBook = idCopyBook;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(final LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(final Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(final String cover) {
        this.cover = cover;
    }

    public Integer getBook() {
        return book;
    }

    public void setBook(final Integer book) {
        this.book = book;
    }

}
