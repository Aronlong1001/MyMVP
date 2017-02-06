package test.aron.com.mvpdemo.presenter.base;

/**
 * Created by Aron on 2016/12/18.
 */
public interface BasePresenter<V> {
    void attachView(V mvpView);
    void detachView();
}
