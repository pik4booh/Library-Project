package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class RoleDTO {

    private Integer idRole;

    @NotNull
    @Size(max = 255)
    private String name;

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

}
