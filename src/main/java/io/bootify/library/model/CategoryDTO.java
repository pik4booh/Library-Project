package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CategoryDTO {

    private Integer idCategory;

    @NotNull
    @Size(max = 255)
    private String name;

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

}
