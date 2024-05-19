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
public class Theme {

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
    private Integer idTheme;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "theme")
    private Set<BookTheme> bookThemes;

    public Integer getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(final Integer idTheme) {
        this.idTheme = idTheme;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<BookTheme> getBookThemes() {
        return bookThemes;
    }

    public void setBookThemes(final Set<BookTheme> bookThemes) {
        this.bookThemes = bookThemes;
    }

}
