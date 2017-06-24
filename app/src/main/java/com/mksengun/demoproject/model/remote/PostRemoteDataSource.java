package com.mksengun.demoproject.model.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mksengun.demoproject.model.Post;
import com.mksengun.demoproject.model.datasource.PostDataSource;
import com.mksengun.demoproject.rest.ApiClient;
import com.mksengun.demoproject.rest.ApiInterface;

import java.util.ArrayList;

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

    @Override
    public void getPostList(@NonNull final GetPostListCallback callback) {

        Call<ArrayList<Post>> call = apiService.getPosts();

        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                Log.i(TAG, "onResponse");
                callback.onPostListLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void getPost(@NonNull int postId, @NonNull GetPostListCallback callback) {

    }
}
