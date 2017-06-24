package com.mksengun.demoproject.presenter;

import android.support.annotation.NonNull;
import com.mksengun.demoproject.contract.PostDetailContract;
import com.mksengun.demoproject.model.Post;
import com.mksengun.demoproject.model.datasource.PostDataSource;
import com.mksengun.demoproject.model.repository.PostRepository;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link com.mksengun.demoproject.fragment.PostDetailFragment}),
 * retrieves the data ({@link PostRepository}) and updates the UI as required.
 */
public class PostDetailPresenter implements PostDetailContract.Presenter{

    private static final String TAG = PostDetailPresenter.class.getSimpleName();

    private final PostDetailContract.View mPostDetailView;
    private final PostRepository mPostRepository;

    /**
     * Creates a presenter for the PostDetail view.
     *
     * @param view the detail view
     * @param repository the Post repository
     */
    public PostDetailPresenter(@NonNull PostDetailContract.View view,
                             @NonNull PostRepository repository) {
        mPostDetailView= checkNotNull(view, "PostDetailContract.View cannot be null");
        mPostRepository = checkNotNull(repository, "PostRepository cannot be null");
    }

    /**
     * Gets data from model and updates UI
     * @param postId
     */
    @Override
    public void getPostData(final int postId) {

        mPostDetailView.showLoading();

        mPostRepository.getPost(postId, new PostDataSource.GetPostCallback() {
            @Override
            public void onPostLoaded(Post data) {
                mPostDetailView.setPostData(data);
                mPostDetailView.hideLoading();
            }

            @Override
            public void onDataNotAvailable() {
                mPostDetailView.hideLoading();
            }
        });

    }

    @Override
    public void setArgData(String title, String body) {
        mPostDetailView.showLoading();
        mPostDetailView.setArgData(title,body);
        mPostDetailView.hideLoading();
    }
}
