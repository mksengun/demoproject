package com.mksengun.demoproject.contract;

import com.mksengun.demoproject.base.BaseView;
import com.mksengun.demoproject.model.Post;

/**
 *  This is contract between view and presenter which is
 *  {@link com.mksengun.demoproject.fragment.PostDetailFragment} and
 *  {@link com.mksengun.demoproject.presenter.PostDetailPresenter}
 */
public interface PostDetailContract {

    interface View extends BaseView<Presenter> {

        void setPostData(Post post);

        void setArgData(String title, String body);

    }

    interface Presenter{

        void getPostData(int postId);

        void setArgData(String title, String body);

    }

}
