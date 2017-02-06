package com.aron.framedemo.presenter;

import com.aron.framedemo.annotation.Implement;
import com.aron.framedemo.presenter.impl.NewsDetailPresenterImpl;

/**
 * Created by Aron on 2016/12/30.
 */
@Implement(NewsDetailPresenterImpl.class)
public interface NewsDetailPresenter {
    void onLoadNewsDetailData();
    void setPosId(String posId);
}
