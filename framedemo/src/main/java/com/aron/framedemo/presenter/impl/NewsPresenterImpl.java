package com.aron.framedemo.presenter.impl;

import com.aron.framedemo.api.RetrofitManager;
import com.aron.framedemo.common.LoadType;
import com.aron.framedemo.entity.NewsSummary;
import com.aron.framedemo.presenter.NewsPresenter;
import com.aron.framedemo.presenter.base.BasePresenterImpl;
import com.aron.framedemo.presenter.view.NewsView;
import com.aron.framedemo.util.MyUtils;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Aron on 2016/12/21.
 */
public class NewsPresenterImpl extends BasePresenterImpl<NewsView> implements NewsPresenter {

    private String mNewsId;
    private String mNewsType;
    private int mStartPage;
    private boolean mIsRefresh = true;

    @Override
    public void onLoadNewsData() {
        mIsRefresh = true;
        mStartPage = 0;
        loadNewsData();
    }

    private void loadNewsData(){
        RetrofitManager.getInstance().getNewsListObservable(mNewsType, mNewsId, mStartPage)
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> stringListMap) {
                        return Observable.from(stringListMap.get(mNewsId));
                    }
                })
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        return newsSummary;
                    }
                })
                .distinct()
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                .compose(MyUtils.<List<NewsSummary>>defaultSchedulers())
                .subscribe(new Subscriber<List<NewsSummary>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        getView().onNewsFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(List<NewsSummary> newsSummaries) {
                        mStartPage += 20;
                        getView().hideProgress();
                        int loadType = mIsRefresh ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        getView().onNewsResponse(newsSummaries, loadType);
                    }
                });
    }
    @Override
    public void onLoadMoreNewsData() {
        mIsRefresh = false;
        loadNewsData();
    }

    @Override
    public void setNewsTypeAndId(String newsType, String newsId) {
        mNewsId = newsId;
        mNewsType = newsType;
    }

}
