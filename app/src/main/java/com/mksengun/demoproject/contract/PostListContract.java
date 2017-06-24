package com.mksengun.demoproject.contract;

/**
 * Created by mustafa on 23/06/2017.
 */


import com.mksengun.demoproject.base.BaseView;
import com.mksengun.demoproject.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 *  This specifies the contract betwee view and presenter which is
 *  {@link com.mksengun.demoproject.fragment.PostListFragment} and
 *  {@link com.mksengun.demoproject.presenter.PostListPresenter}
 */
public interface PostListContract {

    interface View extends BaseView<Presenter> {

        void setListData(List<Post> postList);

    }

    interface Presenter{

        void getListData();

    }
}