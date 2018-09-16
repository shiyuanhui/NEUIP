package com.shiyuanhui.presenter;

import com.shiyuanhui.view.IBaseView;

public class BasePresenter<V extends IBaseView> implements IPresenter<V> {

    private V mView;

    @Override
    public void attachView(V view) {
        mView = view;

    }

    @Override
    public void detachView() {
        mView = null;

    }

    public boolean isAttachView()
    {
        return mView != null;
    }

    public V getView()
    {
        return mView;
    }

    public void checkViewAttach() throws ViewNotAttachException
    {
        if (!isAttachView())
        {
            throw new ViewNotAttachException();
        }
    }

    public static class ViewNotAttachException extends RuntimeException
    {
        public ViewNotAttachException() {
            super();
        }
    }
}
