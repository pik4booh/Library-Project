package io.bootify.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
public class CopyBook {

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
    private Integer idCopyBook;

    @Column
    private String language;

    @Column(nullable = false)
    private String isbn;

    @Column
    private LocalDateTime editDate;

    @Column
    private Integer pageNumber;

    @Column
    private String cover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "copyBook")
    private Set<Loaning> loanings;

    @OneToMany(mappedBy = "copyBook")
    private Set<Sanction> sanctions;

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

    public Book getBook() {
        return book;
    }

    public void setBook(final Book book) {
        this.book = book;
    }

    public Set<Loaning> getLoanings() {
        return loanings;
    }

    public void setLoanings(final Set<Loaning> loanings) {
        this.loanings = loanings;
    }

    public Set<Sanction> getSanctions() {
        return sanctions;
    }

    public void setSanctions(final Set<Sanction> sanctions) {
        this.sanctions = sanctions;
    }

}
