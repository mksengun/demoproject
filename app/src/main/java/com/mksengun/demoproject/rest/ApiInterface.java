package com.mksengun.demoproject.rest;

import com.mksengun.demoproject.model.Comment;
import com.mksengun.demoproject.model.Post;
import com.mksengun.demoproject.model.User;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Api interface for geting data from remote
 */
public interface ApiInterface {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{postId}")
    Call<Post> getPost(@Path("postId") int postId);

    @GET("posts/{postId}/comments")
    Call<RealmList<Comment>> getPostComments(@Path("postId") int postId);

    @GET("users")
    Call<User> getUsers();

    @GET("users/{userId}")
    Call<User> getUser(@Path("userId") int userId);
}