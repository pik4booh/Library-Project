package io.bootify.library.model;


public class BookThemeDTO {

    private Integer idBookTheme;
    private Integer theme;
    private Integer book;

    public Integer getIdBookTheme() {
        return idBookTheme;
    }

    public void setIdBookTheme(final Integer idBookTheme) {
        this.idBookTheme = idBookTheme;
    }

    public Integer getTheme() {
        return theme;
    }

    public void setTheme(final Integer theme) {
        this.theme = theme;
    }

    public Integer getBook() {
        return book;
    }

    public void setBook(final Integer book) {
        this.book = book;
    }

}
