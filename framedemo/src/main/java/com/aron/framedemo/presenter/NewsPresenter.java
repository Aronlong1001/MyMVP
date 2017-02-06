package com.aron.framedemo.presenter;

import com.aron.framedemo.annotation.Implement;
import com.aron.framedemo.presenter.impl.NewsPresenterImpl;

/**
 * Created by Aron on 2016/12/21.
 */
@Implement(NewsPresenterImpl.class)
public interface NewsPresenter {
    void onLoadNewsData();
    void onLoadMoreNewsData();
    void setNewsTypeAndId(String newsType, String newsId);
}
