package com.mksengun.demoproject.model;

import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User model
 */
public class User extends RealmObject{

    @PrimaryKey
    @SerializedName("id")
    private int id ;

    @SerializedName("name")
    private String name ;


    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
