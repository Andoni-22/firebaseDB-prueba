package com.example.fraseslmg.objetos;

import java.util.Set;

public class Groups {

    private String id;
    private String name;
    private String desc;
    private int img;
    private Users group_owner;
    private Set<Users> user_list;
    private Set<Phrases>phrases_list;

    public Groups(String name, String desc, int img) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.img = img;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Users getUser() {
        return group_owner;
    }

    public void setUser(Users group_owner) {
        this.group_owner = group_owner;
    }

    public Set<Users> getUser_list() {
        return user_list;
    }

    public void setUser_list(Set<Users> user_list) {
        this.user_list = user_list;
    }

    public Set<Phrases> getPhrases_list() {
        return phrases_list;
    }

    public void setPhrases_list(Set<Phrases> phrases_list) {
        this.phrases_list = phrases_list;
    }
}
