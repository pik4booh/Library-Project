package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ThemeDTO {

    private Integer idTheme;

    @NotNull
    @Size(max = 255)
    private String name;

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

}
