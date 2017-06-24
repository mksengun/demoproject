package com.mksengun.demoproject.model.datasource;

import android.support.annotation.NonNull;

import com.mksengun.demoproject.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Main entry point for accessing Post data.
 *
 */

public interface PostDataSource {

    /**
     *  PostList callback
     */
    interface GetPostListCallback {

        void onPostListLoaded(List<Post> dataArrayList);
        void onDataNotAvailable();

    }

    /**
     *  Single post callback
     */
    interface GetPostCallback {

        void onPostLoaded(Post data);
        void onDataNotAvailable();

    }

    /**
     * This method will be used for getting post list.
     */
    void getPostList(@NonNull GetPostListCallback callback);


    /**
     * This method will be used for getting single post.
     */
    void getPost(@NonNull int postId, @NonNull GetPostCallback callback);


}
