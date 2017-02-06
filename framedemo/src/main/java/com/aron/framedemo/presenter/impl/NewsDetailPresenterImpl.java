package com.aron.framedemo.presenter.impl;

import com.aron.framedemo.BaseApplication;
import com.aron.framedemo.api.RetrofitManager;
import com.aron.framedemo.entity.NewsDetail;
import com.aron.framedemo.presenter.NewsDetailPresenter;
import com.aron.framedemo.presenter.base.BasePresenterImpl;
import com.aron.framedemo.presenter.view.NewsDetailView;
import com.aron.framedemo.util.TransformUtils;

import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.functions.Func1;

/**
 * Created by Aron on 2016/12/30.
 */
public class NewsDetailPresenterImpl extends BasePresenterImpl<NewsDetailView> implements NewsDetailPresenter {

    private String mPostId;

    @Override
    public void onLoadNewsDetailData() {
        RetrofitManager.getInstance().getNewsDetailObservable(mPostId)
                .map(new Func1<Map<String,NewsDetail>, NewsDetail>() {

                    @Override
                    public NewsDetail call(Map<String, NewsDetail> stringNewsDetailMap) {
                        NewsDetail newsDetail = stringNewsDetailMap.get(mPostId);
                        changeNewsDetail(newsDetail);
                        return newsDetail;
                    }
                })
                .compose(TransformUtils.<NewsDetail>defaultSchedulers())
                .subscribe(new Observer<NewsDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        getView().onNewsDetailFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        getView().hideProgress();
                        getView().onNewsDetailResponse(newsDetail);
                    }
                });
    }

    @Override
    public void setPosId(String posId) {
        mPostId = posId;
    }

    private void changeNewsDetail(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        if (isChange(imgSrcs)) {
            String newsBody = newsDetail.getBody();
            newsBody = changeNewsBody(imgSrcs, newsBody);
            newsDetail.setBody(newsBody);
        }
    }

    private boolean isChange(List<NewsDetail.ImgBean> imgSrcs) {
        return imgSrcs != null && imgSrcs.size() >= 2 && BaseApplication.isHavePhoto();
    }
    private String changeNewsBody(List<NewsDetail.ImgBean> imgSrcs, String newsBody) {
        for (int i = 0; i < imgSrcs.size(); i++) {
            String oldChars = "<!--IMG#" + i + "-->";
            String newChars;
            if (i == 0) {
                newChars = "";
            } else {
                newChars = "<img src=\"" + imgSrcs.get(i).getSrc() + "\" />";
            }
            newsBody = newsBody.replace(oldChars, newChars);
        }
        return newsBody;
    }
}
