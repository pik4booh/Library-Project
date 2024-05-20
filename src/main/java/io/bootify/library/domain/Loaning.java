package io.bootify.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;


@Entity
public class Loaning {

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
    private Integer idLoaning;

    @Column(nullable = false)
    private LocalDateTime loaningDate;

    @Column(nullable = false)
    private LocalDateTime expectedReturnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copy_book_id")
    private CopyBook copyBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_loaning_id")
    private TypeLoaning typeLoaning;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_loaning_id", unique = true)
    private ReturnLoaning returnLoaning;

    public Integer getIdLoaning() {
        return idLoaning;
    }

    public void setIdLoaning(final Integer idLoaning) {
        this.idLoaning = idLoaning;
    }

    public LocalDateTime getLoaningDate() {
        return loaningDate;
    }

    public void setLoaningDate(final LocalDateTime loaningDate) {
        this.loaningDate = loaningDate;
    }

    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(final LocalDateTime expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(final Member member) {
        this.member = member;
    }

    public CopyBook getCopyBook() {
        return copyBook;
    }

    public void setCopyBook(final CopyBook copyBook) {
        this.copyBook = copyBook;
    }

    public TypeLoaning getTypeLoaning() {
        return typeLoaning;
    }

    public void setTypeLoaning(final TypeLoaning typeLoaning) {
        this.typeLoaning = typeLoaning;
    }

    public ReturnLoaning getReturnLoaning() {
        return returnLoaning;
    }

    public void setReturnLoaning(final ReturnLoaning returnLoaning) {
        this.returnLoaning = returnLoaning;
    }

}
