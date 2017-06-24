package com.mksengun.demoproject.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mksengun.demoproject.R;
import com.mksengun.demoproject.contract.PostDetailContract;
import com.mksengun.demoproject.model.Post;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostDetailFragment extends Fragment implements PostDetailContract.View {

    private static final String TAG = PostDetailFragment.class.getSimpleName();

    private PostDetailContract.Presenter mPresenter;


    private static final String ARG_POST_ID = "ARG_POST_ID";
    private static final String ARG_USER_ID = "ARG_USER_ID";
    private static final String ARG_POST_TITLE = "ARG_POST_TITLE";
    private static final String ARG_POST_BODY = "ARG_POST_BODY";

    private String mBody, mTitle;
    private int mPostId, mUserId;

    private View rootView;
    private CardView cardView;
    private TextView textViewTitle, textViewBody, textViewComment, textViewName;
    private ProgressBar progressBar;


    public PostDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param postId
     * @param userId
     * @param title
     * @param body
     * @return
     */
    public static PostDetailFragment newInstance(int postId, int userId, String title, String body) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POST_ID, ""+postId);
        args.putString(ARG_USER_ID, ""+userId);
        args.putString(ARG_POST_TITLE, title);
        args.putString(ARG_POST_BODY, body);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPostId = Integer.parseInt(getArguments().getString(ARG_POST_ID));
            mUserId = Integer.parseInt(getArguments().getString(ARG_USER_ID));
            mTitle = getArguments().getString(ARG_POST_TITLE);
            mBody = getArguments().getString(ARG_POST_BODY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);
        textViewBody = rootView.findViewById(R.id.textViewBody);
        textViewTitle = rootView.findViewById(R.id.textViewTitle);
        textViewComment = rootView.findViewById(R.id.textViewComment);
        textViewName = rootView.findViewById(R.id.textViewName);
        progressBar = rootView.findViewById(R.id.progressBar);
        cardView = rootView.findViewById(R.id.cardView);

        textViewTitle.setTransitionName((String.format(getString(R.string.transition_title), mPostId)));
        textViewBody.setTransitionName((String.format(getString(R.string.transition_body), mPostId)));
        cardView.setTransitionName((String.format(getString(R.string.transition_layout), mPostId)));

        mPresenter.setArgData(mTitle,mBody);
        mPresenter.getPostData(mPostId);

        return rootView;
    }

    @Override
    public void setPresenter(PostDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * This method will be called from {@link com.mksengun.demoproject.presenter.PostDetailPresenter}
     * View is updated by this method from data that comes from local or remote
     *
     * @param post data for view
     */
    @Override
    public void setPostData(Post post) {
        if(post.getUser()!=null){
            textViewName.setText(post.getUser().getName());
        }
        textViewComment.setText(String.format(getString(R.string.text_total_comment),post.getComments().size()));
        textViewBody.setText(post.getBody());
        textViewTitle.setText(post.getTitle());
    }

    /**
     * This method sets data from arguments which came from previous screen
     * @param title title of post
     * @param body body of post
     */
    @Override
    public void setArgData(String title, String body) {
        textViewTitle.setText(title);
        textViewBody.setText(body);
    }
}
