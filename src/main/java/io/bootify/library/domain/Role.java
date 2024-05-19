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
public class Role {

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
    private Integer idRole;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<Librarian> librarians;

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(final Integer idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Librarian> getLibrarians() {
        return librarians;
    }

    public void setLibrarians(final Set<Librarian> librarians) {
        this.librarians = librarians;
    }

}
