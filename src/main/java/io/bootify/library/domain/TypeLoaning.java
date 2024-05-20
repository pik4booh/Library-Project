package io.bootify.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;


@Entity
public class TypeLoaning {

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
    private Integer idTypeLoaning;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "typeLoaning")
    private Set<Loaning> loanings;

    public Integer getIdTypeLoaning() {
        return idTypeLoaning;
    }

    public void setIdTypeLoaning(final Integer idTypeLoaning) {
        this.idTypeLoaning = idTypeLoaning;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Loaning> getLoanings() {
        return loanings;
    }

    public void setLoanings(final Set<Loaning> loanings) {
        this.loanings = loanings;
    }

}
