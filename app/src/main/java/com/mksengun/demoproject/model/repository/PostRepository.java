package com.mksengun.demoproject.model.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mksengun.demoproject.model.Post;
import com.mksengun.demoproject.model.datasource.PostDataSource;
import com.mksengun.demoproject.model.local.PostLocalDataSource;
import com.mksengun.demoproject.model.remote.PostRemoteDataSource;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by mustafa on 23/06/2017.
 */

public class PostRepository implements PostDataSource {

    private static final String TAG = PostRepository.class.getSimpleName();


    private static PostRepository INSTANCE = null;

    private final PostRemoteDataSource mPostRemoteDataSource;

    private final PostLocalDataSource mPostLocalDataSource;

    public PostRepository(@NonNull PostRemoteDataSource postRemoteDataSource,
                          @NonNull PostLocalDataSource postLocalDataSource) {
        mPostRemoteDataSource = checkNotNull(postRemoteDataSource);
        mPostLocalDataSource = checkNotNull(postLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param postRemoteDataSource the remote data source
     * @param postLocalDataSource  the database data source
     * @return the {@link PostRepository} instance
     */
    public static PostRepository getInstance(PostRemoteDataSource postRemoteDataSource,
                                             PostLocalDataSource postLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PostRepository(postRemoteDataSource, postLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(PostRemoteDataSource, PostLocalDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getPostList(@NonNull final GetPostListCallback callback) {
        checkNotNull(callback);

        mPostRemoteDataSource.getPostList(new GetPostListCallback() {
            @Override
            public void onPostListLoaded(ArrayList<Post> dataArrayList) {
                callback.onPostListLoaded(dataArrayList);
            }

            @Override
            public void onDataNotAvailable() {
                mPostLocalDataSource.getPostList(new GetPostListCallback() {
                    @Override
                    public void onPostListLoaded(ArrayList<Post> dataArrayList) {
                        Log.i(TAG, "onResponse");
                        callback.onPostListLoaded(dataArrayList);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        Log.e(TAG, "onDataNotAvailable");
                        callback.onDataNotAvailable();
                    }
                });
            }
        });

    }

    @Override
    public void getPost(@NonNull int postId, @NonNull GetPostListCallback callback) {

    }
}
