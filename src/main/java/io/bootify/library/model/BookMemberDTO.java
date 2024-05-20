package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;


public class BookMemberDTO {

    private Integer idBookMember;

    @NotNull
    private Integer onTheSpot;

    @NotNull
    private Integer takeAway;

    private Integer book;

    private Integer typeMember;

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

    public Integer getBook() {
        return book;
    }

    public void setBook(final Integer book) {
        this.book = book;
    }

    public Integer getTypeMember() {
        return typeMember;
    }

    public void setTypeMember(final Integer typeMember) {
        this.typeMember = typeMember;
    }

}
