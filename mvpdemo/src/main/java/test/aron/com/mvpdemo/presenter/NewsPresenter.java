package test.aron.com.mvpdemo.presenter;

import test.aron.com.mvpdemo.annotation.Implement;
import test.aron.com.mvpdemo.presenter.impl.NewsPresenterImpl;

/**
 * Created by Aron on 2016/12/21.
 */
@Implement(NewsPresenterImpl.class)
public interface NewsPresenter{
    void onLoadNewsData();
    void onLoadMoreNewsData();
    void setNewsTypeAndId(String newsType, String newsId);
}
