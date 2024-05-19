package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class AuthorDTO {

    private Integer idAuthor;

    @NotNull
    @Size(max = 255)
    private String name;

    public Integer getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(final Integer idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
