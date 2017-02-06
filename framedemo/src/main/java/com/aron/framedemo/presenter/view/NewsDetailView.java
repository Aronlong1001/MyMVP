package com.aron.framedemo.presenter.view;

import com.aron.framedemo.entity.NewsDetail;
import com.aron.framedemo.presenter.base.BaseView;

/**
 * Created by Aron on 2016/12/30.
 */
public interface NewsDetailView extends BaseView {
    void onNewsDetailResponse(NewsDetail newsDetail);
    void onNewsDetailFailure(String msg);
}
