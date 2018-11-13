package com.fitme.android.fitme;

/**
 * Created by marciarocha on 18/12/17.
 */

public interface BasePresenter<T> {

    void attachView(T view);

    void dettachView();
}
