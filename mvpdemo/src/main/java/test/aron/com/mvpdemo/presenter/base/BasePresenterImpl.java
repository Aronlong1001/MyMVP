package test.aron.com.mvpdemo.presenter.base;

import test.aron.com.mvpdemo.view.base.BaseView;

/**
 * Created by Aron on 2016/12/18.
 */
public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T>{
    protected T mView;

    @Override
    public void attachView(T mvpView) {
        this.mView = mvpView;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public boolean isViewBind() {
        return mView != null;
    }


    public T getView() {
        return mView;
    }
}
