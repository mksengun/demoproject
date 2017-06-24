package com.mksengun.demoproject.model.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mksengun.demoproject.model.Comment;
import com.mksengun.demoproject.model.Post;
import com.mksengun.demoproject.model.User;
import com.mksengun.demoproject.model.datasource.PostDataSource;
import com.mksengun.demoproject.rest.ApiClient;
import com.mksengun.demoproject.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mustafa on 23/06/2017.
 */

public class PostRemoteDataSource implements PostDataSource {

    private static final String TAG = PostRemoteDataSource.class.getSimpleName();

    private static PostRemoteDataSource INSTANCE;
    private ApiInterface apiService;

    public static PostRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostRemoteDataSource();
        }
        return INSTANCE;
    }

    public PostRemoteDataSource() {
        this.apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    /**
     * This method simply gets from posts from remote via {@link ApiInterface#getPosts()}
     *
     * @param callback
     */
    @Override
    public void getPostList(@NonNull final GetPostListCallback callback) {

        Call<List<Post>> call = null;
        try {
            call = apiService.getPosts();
        } catch (IllegalArgumentException ex) {
            callback.onDataNotAvailable();
        }

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                // successfully got data from server
                callback.onPostListLoaded(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                // failed getting remote data
                callback.onDataNotAvailable();
            }
        });

    }

    /**
     * This method is to get a single post from remote. However we have to achive this goal via
     * 3 separate requests.
     * Firstly, {@link ApiInterface#getPost(int)} called if success {@link ApiInterface#getPostComments(int)}
     * called, then {@link ApiInterface#getUser(int)} if success data will be returned with callback
     *
     * @param postId
     * @param callback
     */
    @Override
    public void getPost(@NonNull int postId, @NonNull final GetPostCallback callback) {

        Call<Post> call = null;
        try {
            call = apiService.getPost(postId);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, ex.getMessage());
            callback.onDataNotAvailable();
        }

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(final Call<Post> call, final Response<Post> response) {
                // successfully got data from server
                getPostComments(response.body().getId(), new GetPostComment() {
                    @Override
                    public void onCommentsLoaded(RealmList<Comment> commentList) {
                        response.body().setComments(commentList);

                        getUser(response.body().getId(), new GetUser() {
                            @Override
                            public void onUserLoaded(User user) {
                                response.body().setUser(user);
                                callback.onPostLoaded(response.body());
                            }
                            @Override
                            public void onDataNotAvailable() {
                                callback.onDataNotAvailable();
                            }
                        });
                    }
                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                // failed getting remote data
                callback.onDataNotAvailable();
            }
        });
    }


    /**
     * This method gets comments of a post.
     * @param postId requested postId
     * @param callback
     */
    public void getPostComments(@NonNull final int postId, @NonNull final GetPostComment callback) {
        Call<RealmList<Comment>> call = null;
        try {
            call = apiService.getPostComments(postId);
        } catch (IllegalArgumentException ex) {
            callback.onDataNotAvailable();
        }

        call.enqueue(new Callback<RealmList<Comment>>() {
            @Override
            public void onResponse(Call<RealmList<Comment>> call, Response<RealmList<Comment>> response) {
                // successfully got data from server
                callback.onCommentsLoaded(response.body());
            }

            @Override
            public void onFailure(Call<RealmList<Comment>> call, Throwable t) {
                // failed getting remote data
                callback.onDataNotAvailable();
            }
        });
    }

    /**
     * This method gets a single user from remote server
     * @param userId requested user
     * @param callback
     */
    public void getUser(@NonNull final int userId, @NonNull final GetUser callback) {
        Call<User> call = null;
        try {
            call = apiService.getUser(userId);
        } catch (IllegalArgumentException ex) {
            callback.onDataNotAvailable();
        }

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // successfully got data from server
                callback.onUserLoaded(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // failed getting remote data
                callback.onDataNotAvailable();
            }
        });
    }

    interface GetPostComment {
        void onCommentsLoaded(RealmList<Comment> commentList);

        void onDataNotAvailable();
    }

    interface GetUser {
        void onUserLoaded(User user);

        void onDataNotAvailable();
    }


}
