package com.shiyuanhui.presenter;

import com.shiyuanhui.view.IBaseView;

public interface IPresenter<V extends IBaseView>{

    void attachView(V view);

    void detachView();
}
