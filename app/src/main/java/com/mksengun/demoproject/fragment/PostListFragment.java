package com.mksengun.demoproject.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mksengun.demoproject.R;
import com.mksengun.demoproject.adapter.PostsAdapter;
import com.mksengun.demoproject.contract.PostListContract;
import com.mksengun.demoproject.model.Post;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostListFragment extends Fragment implements PostListContract.View {

    private static final String TAG = PostListFragment.class.getSimpleName();


    private PostListContract.Presenter mPresenter;


    private List<Post> postList;
    private RecyclerView recyclerView;
    private PostsAdapter mAdapter;
    private View rootView;
    private ProgressBar progressBar;

    public PostListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     */
    public static PostListFragment newInstance() {
        PostListFragment fragment = new PostListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_post_list, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);

        mPresenter.getListData();

        return rootView;
    }

    @Override
    public void setPresenter(PostListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }


    /**
     * This method is responsible for updating view with given data.
     * Data may come from locale or database.
     * This method is called from {@link com.mksengun.demoproject.presenter.PostListPresenter}
     *
     * @param list
     */
    @Override
    public void setListData(List<Post> list) {
        postList = list;
        mAdapter = new PostsAdapter(postList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
    }
}
