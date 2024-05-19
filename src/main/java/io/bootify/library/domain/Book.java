package io.bootify.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
public class Book {

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
    private Integer idBook;

    @Column(nullable = false)
    private String title;

    @Column
    private String summary;

    @Column
    private String collection;

    @Column(nullable = false)
    private String coteNumber;

    @Column(nullable = false)
    private LocalDateTime releaseDate;

    @Column
    private String author;

    @OneToMany(mappedBy = "book")
    private Set<BookTheme> bookThemes;

    @OneToMany(mappedBy = "book")
    private Set<CopyBook> copyBooks;

    @OneToMany(mappedBy = "book")
    private Set<BookMember> bookMembers;

    @OneToMany(mappedBy = "book")
    private Set<BookCategory> bookCategories;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public Set<BookTheme> getBookThemes() {
        return bookThemes;
    }

    public void setBookThemes(final Set<BookTheme> bookThemes) {
        this.bookThemes = bookThemes;
    }

    public Set<CopyBook> getCopyBooks() {
        return copyBooks;
    }

    public void setCopyBooks(final Set<CopyBook> copyBooks) {
        this.copyBooks = copyBooks;
    }

    public Set<BookMember> getBookMembers() {
        return bookMembers;
    }

    public void setBookMembers(final Set<BookMember> bookMembers) {
        this.bookMembers = bookMembers;
    }

    public Set<BookCategory> getBookCategories() {
        return bookCategories;
    }

    public void setBookCategories(final Set<BookCategory> bookCategories) {
        this.bookCategories = bookCategories;
    }

}
