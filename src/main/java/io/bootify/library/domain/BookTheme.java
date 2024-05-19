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
public class BookTheme {

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
    private Integer idBookTheme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public Integer getIdBookTheme() {
        return idBookTheme;
    }

    public void setIdBookTheme(final Integer idBookTheme) {
        this.idBookTheme = idBookTheme;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(final Theme theme) {
        this.theme = theme;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(final Book book) {
        this.book = book;
    }

}
