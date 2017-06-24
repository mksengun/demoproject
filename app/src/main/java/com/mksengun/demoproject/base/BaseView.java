package com.mksengun.demoproject.base;

/**
 * Created by mustafa on 23/06/2017.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);
    void showLoading();
    void hideLoading();

}

