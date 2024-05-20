package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class TypeLoaningDTO {

    private Integer idTypeLoaning;

    @NotNull
    @Size(max = 255)
    private String name;

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

}
