package com.aron.framedemo.presenter.view;

import com.aron.framedemo.entity.NewsSummary;
import com.aron.framedemo.presenter.base.BaseView;

import java.util.List;

/**
 * Created by Aron on 2016/12/21.
 */
public interface NewsView extends BaseView {
    void onNewsResponse(List<NewsSummary> newsSummary, int type);
    void onNewsFailure(String msg);
}
