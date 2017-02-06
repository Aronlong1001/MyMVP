package test.aron.com.mvpdemo.view;

import java.util.List;

import test.aron.com.mvpdemo.entity.NewsSummary;
import test.aron.com.mvpdemo.view.base.BaseView;

/**
 * Created by Aron on 2016/12/21.
 */
public interface NewsView extends BaseView {
    void onNewsResponse(List<NewsSummary> newsSummary, int type);
    void onNewsFailure(String msg);
}
