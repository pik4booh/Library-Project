package io.bootify.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;


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
    private Integer idReturnLoaning;

    @Column(nullable = false)
    private LocalDateTime returnDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loaning_id", unique = true)
    private Loaning loaning;

    public Integer getIdReturnLoaning() {
        return idReturnLoaning;
    }

    public void setIdReturnLoaning(final Integer idReturnLoaning) {
        this.idReturnLoaning = idReturnLoaning;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public Loaning getLoaning() {
        return loaning;
    }

    public void setLoaning(final Loaning loaning) {
        this.loaning = loaning;
    }

}
