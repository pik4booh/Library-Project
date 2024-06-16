package io.bootify.library.model;

import io.bootify.library.domain.Book;
import io.bootify.library.domain.TypeMember;
import jakarta.validation.constraints.NotNull;


public class BookMemberDTO {

    private Integer idBookMember;

    @NotNull
    private Integer onTheSpot;

    @NotNull
    private Integer takeAway;

    private Book book;

    private TypeMember typeMember;

    public Integer getIdBookMember() {
        return idBookMember;
    }

    public void setIdBookMember(final Integer idBookMember) {
        this.idBookMember = idBookMember;
    }

    public Integer getOnTheSpot() {
        return onTheSpot;
    }

    public void setOnTheSpot(final Integer onTheSpot) {
        this.onTheSpot = onTheSpot;
    }

    public Integer getTakeAway() {
        return takeAway;
    }

    public void setTakeAway(final Integer takeAway) {
        this.takeAway = takeAway;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(final Book book) {
        this.book = book;
    }

    public TypeMember getTypeMember() {
        return typeMember;
    }

    public void setTypeMember(final TypeMember typeMember) {
        this.typeMember = typeMember;
    }

}
