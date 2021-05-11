package com.example.fraseslmg.objetos;

import java.util.Set;

public class Groups {

    private String id;
    private String name;
    private Set<Users> user_list;
    private Set<Phrases>phrases_list;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser_list(Set<Users> user_list) {
        this.user_list = user_list;
    }

    public void setPhrases_list(Set<Phrases> phrases_list) {
        this.phrases_list = phrases_list;
    }
}
