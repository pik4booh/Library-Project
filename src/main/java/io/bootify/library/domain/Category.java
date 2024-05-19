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
public class Category {

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
    private Integer idCategory;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<BookCategory> bookCategories;

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(final Integer idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<BookCategory> getBookCategories() {
        return bookCategories;
    }

    public void setBookCategories(final Set<BookCategory> bookCategories) {
        this.bookCategories = bookCategories;
    }

}
