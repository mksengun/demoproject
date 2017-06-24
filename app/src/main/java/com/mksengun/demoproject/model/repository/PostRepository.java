package com.mksengun.demoproject.model.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mksengun.demoproject.model.Post;
import com.mksengun.demoproject.model.datasource.PostDataSource;
import com.mksengun.demoproject.model.local.PostLocalDataSource;
import com.mksengun.demoproject.model.remote.PostRemoteDataSource;

import java.util.List;
import java.util.List;

import io.realm.Realm;

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
     * This method asks remote repository for post list. If data comes from remote, then locale data
     * will be updated and returned via callback. If remote fails then goes for local data and returns
     * via callback.
     *
     * @param callback
     */
    @Override
    public void getPostList(@NonNull final GetPostListCallback callback) {
        checkNotNull(callback);

        mPostRemoteDataSource.getPostList(new GetPostListCallback() {
            @Override
            public void onPostListLoaded(List<Post> dataList) {
                mPostLocalDataSource.setPostList(dataList);
                callback.onPostListLoaded(dataList);
            }

            @Override
            public void onDataNotAvailable() {
                mPostLocalDataSource.getPostList(new GetPostListCallback() {
                    @Override
                    public void onPostListLoaded(List<Post> dataList) {
                        callback.onPostListLoaded(dataList);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });

    }

    /**
     * This method asks remote repository for single post. If data comes from remote, then locale data
     * will be updated and returned via callback. If remote fails then goes for local data and returns
     * via callback.
     *
     * @param postId
     * @param callback
     */
    @Override
    public void getPost(@NonNull final int postId, @NonNull final GetPostCallback callback) {
        checkNotNull(postId);
        checkNotNull(callback);

        mPostRemoteDataSource.getPost(postId,new GetPostCallback() {
            @Override
            public void onPostLoaded(Post data) {
                mPostLocalDataSource.setPost(data);
                callback.onPostLoaded(data);
            }

            @Override
            public void onDataNotAvailable() {

                mPostLocalDataSource.getPost(postId, new GetPostCallback() {
                    @Override
                    public void onPostLoaded(Post data) {
                        callback.onPostLoaded(data);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
                callback.onDataNotAvailable();
            }
        });







    }
    
}
