package com.mksengun.demoproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mksengun.demoproject.R;
import com.mksengun.demoproject.fragment.PostListFragment;
import com.mksengun.demoproject.model.local.PostLocalDataSource;
import com.mksengun.demoproject.model.remote.PostRemoteDataSource;
import com.mksengun.demoproject.model.repository.PostRepository;
import com.mksengun.demoproject.presenter.PostListPresenter;
import com.mksengun.demoproject.util.FragmentUtils;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();

    private PostListFragment mPostListFragment;

    public static MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        mPostListFragment = (PostListFragment)
                getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (mPostListFragment == null) {
            mPostListFragment = mPostListFragment.newInstance();
        }
        FragmentUtils.addFragmentToActivity(getSupportFragmentManager(),
                mPostListFragment, R.id.frameContent);

        mPostListFragment.setPresenter(
                new PostListPresenter(
                        mPostListFragment,
                        PostRepository.getInstance(PostRemoteDataSource.getInstance(),
                                PostLocalDataSource.getInstance(getApplicationContext())
                        )
                )
        );

    }
}
