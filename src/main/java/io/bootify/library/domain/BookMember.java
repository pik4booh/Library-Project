package io.bootify.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;


@Entity
public class BookMember {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer idBookMember;

    @Column(nullable = false)
    private Integer onTheSpot;

    @Column(nullable = false)
    private Integer takeAway;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_member_id")
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
