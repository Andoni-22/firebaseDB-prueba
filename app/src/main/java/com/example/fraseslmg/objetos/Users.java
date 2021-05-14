package com.example.fraseslmg.objetos;

import java.util.Set;

public class Users {

    private String id;
    private String name;
    private String apellidos;
    private int telefono;
    private String email;
    private Set<Phrases> phrases_list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Phrases> getPhrases_list() {
        return phrases_list;
    }

    public void setPhrases_list(Set<Phrases> phrases_list) {
        this.phrases_list = phrases_list;
    }
}
