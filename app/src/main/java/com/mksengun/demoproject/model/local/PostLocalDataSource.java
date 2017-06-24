package com.mksengun.demoproject.model.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mksengun.demoproject.model.datasource.PostDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by mustafa on 23/06/2017.
 */

public class PostLocalDataSource implements PostDataSource {

    private static PostLocalDataSource INSTANCE;


    public PostLocalDataSource() {
    }

    public static PostLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getPostList(@NonNull GetPostListCallback callback) {

    }

    @Override
    public void getPost(@NonNull int postId, @NonNull GetPostListCallback callback) {

    }
}
