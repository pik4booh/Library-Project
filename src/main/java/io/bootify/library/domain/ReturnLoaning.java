package io.bootify.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;


@Entity
public class ReturnLoaning {

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
    private Long idReturnLoaning;

    @Column(nullable = false)
    private String returnDate;

    @OneToOne(
            mappedBy = "returnLoaning",
            fetch = FetchType.LAZY
    )
    private Loaning loaning;

    public Long getIdReturnLoaning() {
        return idReturnLoaning;
    }

    public void setIdReturnLoaning(final Long idReturnLoaning) {
        this.idReturnLoaning = idReturnLoaning;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final String returnDate) {
        this.returnDate = returnDate;
    }

    public Loaning getLoaning() {
        return loaning;
    }

    public void setLoaning(final Loaning loaning) {
        this.loaning = loaning;
    }

}
