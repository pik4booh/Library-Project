package io.bootify.library.model;


public class BookCategoryDTO {

    private Integer idBookCategory;
    private Integer category;
    private Integer book;

    public Integer getIdBookCategory() {
        return idBookCategory;
    }

    public void setIdBookCategory(final Integer idBookCategory) {
        this.idBookCategory = idBookCategory;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(final Integer category) {
        this.category = category;
    }

    public Integer getBook() {
        return book;
    }

    public void setBook(final Integer book) {
        this.book = book;
    }

}
