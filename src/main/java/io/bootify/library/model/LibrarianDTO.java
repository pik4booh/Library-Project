package io.bootify.library.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class LibrarianDTO {

    private Integer idLibrarian;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String pwd;

    private Integer role;

    public Integer getIdLibrarian() {
        return idLibrarian;
    }

    public void setIdLibrarian(final Integer idLibrarian) {
        this.idLibrarian = idLibrarian;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(final String pwd) {
        this.pwd = pwd;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(final Integer role) {
        this.role = role;
    }

}
