package com.mksengun.demoproject.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Post model
 */
public class Post extends RealmObject {

    @SerializedName("userId")
    private int userId;

    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    private String body;

    private User user;

    private RealmList<Comment> comments;

    public Post(int userId, int id, String title, String body, User user) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
        this.user = user;
    }

    public Post() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(RealmList<Comment> comments) {
        this.comments = comments;
    }
}
