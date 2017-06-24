package com.mksengun.demoproject.presenter;

import com.mksengun.demoproject.contract.PostListContract;
import com.mksengun.demoproject.model.Post;
import com.mksengun.demoproject.model.datasource.PostDataSource;
import com.mksengun.demoproject.model.repository.PostRepository;
import android.support.annotation.NonNull;
import android.util.Log;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link com.mksengun.demoproject.fragment.PostListFragment}),
 * retrieves the data ({@link PostRepository}) and updates the UI as required.
 */
public class PostListPresenter implements PostListContract.Presenter {


    private static final String TAG = PostListPresenter.class.getSimpleName();

    private final PostListContract.View mPostListView;
    private final PostRepository mPostRepository;

    /**
     * Creates a presenter for the list view.
     *
     * @param view the PostList view
     * @param repository the Post repository
     */
    public PostListPresenter(@NonNull PostListContract.View view,
                             @NonNull PostRepository repository) {
        mPostListView = checkNotNull(view, "PostListContract.View cannot be null");
        mPostRepository = checkNotNull(repository, "PostRepository cannot be null");
    }

    @Override
    public void getListData() {

        mPostListView.showLoading();

        mPostRepository.getPostList(new PostDataSource.GetPostListCallback() {
            @Override
            public void onPostListLoaded(List<Post> dataList) {
                mPostListView.setListData(dataList);
                mPostListView.hideLoading();
            }

            @Override
            public void onDataNotAvailable() {
                Log.e(TAG, "onDataNotAvaiable");
                mPostListView.hideLoading();
            }
        });


    }
}
