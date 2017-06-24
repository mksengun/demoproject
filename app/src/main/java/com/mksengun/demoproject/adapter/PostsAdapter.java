package com.mksengun.demoproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mksengun.demoproject.R;

import com.mksengun.demoproject.activity.MainActivity;
import com.mksengun.demoproject.fragment.PostDetailFragment;
import com.mksengun.demoproject.fragment.PostListFragment;
import com.mksengun.demoproject.model.Post;
import com.mksengun.demoproject.model.local.PostLocalDataSource;
import com.mksengun.demoproject.model.remote.PostRemoteDataSource;
import com.mksengun.demoproject.model.repository.PostRepository;
import com.mksengun.demoproject.presenter.PostDetailPresenter;
import com.mksengun.demoproject.presenter.PostListPresenter;
import com.mksengun.demoproject.util.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 23/06/2017.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private static final String TAG = PostsAdapter.class.getSimpleName();

    private PostDetailFragment mPostDetailFragment;

    private List<Post> postsList;
    private List<View> transViews = new ArrayList<>();  //will be used for transition animation

    public class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle, textViewBody;
        public RelativeLayout layoutRootView;

        public PostViewHolder(View view) {
            super(view);
            textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewBody = view.findViewById(R.id.textViewBody);
            layoutRootView = view.findViewById(R.id.layoutItem);
        }
    }


    public PostsAdapter(List<Post> postsList) {
        this.postsList = postsList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {

        final Post tmpPost = postsList.get(position);

        Post post = postsList.get(position);
        holder.textViewTitle.setText(post.getTitle());
        holder.textViewBody.setText(post.getBody());

        //Set transition values for animation
        holder.textViewTitle.setTransitionName(String.format(MainActivity.activity
                .getString(R.string.transition_title), post.getId()));
        holder.textViewBody.setTransitionName((String.format(MainActivity.activity
                .getString(R.string.transition_body), post.getId())));
        holder.layoutRootView.setTransitionName((String.format(MainActivity.activity
                .getString(R.string.transition_layout), post.getId())));

        //adding to transition array
        transViews.add(holder.textViewTitle);
        transViews.add(holder.textViewBody);
        transViews.add(holder.layoutRootView);

        holder.layoutRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPostDetailFragment == null) {
                    mPostDetailFragment = mPostDetailFragment.newInstance(tmpPost.getId(),
                            tmpPost.getUserId(), tmpPost.getTitle(), tmpPost.getBody());
                }

                FragmentUtils.replaceFragmentInActivity(MainActivity.activity,
                        MainActivity.activity.getSupportFragmentManager(), mPostDetailFragment,
                        R.id.frameContent, transViews, "POST");

                mPostDetailFragment.setPresenter(new PostDetailPresenter(mPostDetailFragment,
                                new PostRepository(
                                        new PostRemoteDataSource().getInstance(),
                                        new PostLocalDataSource(
                                                MainActivity.activity.getApplicationContext())
                                                .getInstance(MainActivity.activity.
                                                        getApplicationContext()))
                        )
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

}