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
import java.time.LocalDateTime;


@Entity
public class Sanction {

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
    private Integer idSanction;

    @Column(nullable = false)
    private LocalDateTime dateBegin;

    @Column(nullable = false)
    private LocalDateTime dateEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copy_book_id")
    private CopyBook copyBook;

    public Integer getIdSanction() {
        return idSanction;
    }

    public void setIdSanction(final Integer idSanction) {
        this.idSanction = idSanction;
    }

    public LocalDateTime getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(final LocalDateTime dateBegin) {
        this.dateBegin = dateBegin;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(final LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
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

}
