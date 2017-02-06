package com.aron.framedemo.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aron.framedemo.BaseApplication;
import com.aron.framedemo.R;
import com.aron.framedemo.common.Constant;
import com.aron.framedemo.entity.NewsDetail;
import com.aron.framedemo.presenter.NewsDetailPresenter;
import com.aron.framedemo.presenter.impl.NewsDetailPresenterImpl;
import com.aron.framedemo.presenter.view.NewsDetailView;
import com.aron.framedemo.ui.activity.base.BaseActivity;
import com.aron.framedemo.util.MyUtils;
import com.aron.framedemo.util.TransformUtils;
import com.aron.framedemo.widget.URLImageGetter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Aron on 2016/12/30.
 */
public class NewsDetailActivity extends BaseActivity implements NewsDetailView {

    private TextView mNewsDetailFromTv;
    private ImageView mNewsDetailPhotoIv;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mToolbarLayout;
    private AppBarLayout mAppBar;
    private TextView mNewsDetailBodyTv;
    private FloatingActionButton mFab;
    private ProgressBar mProgressBar;
    private View mMaskView;

    private String mNewsTitle;
    private String mShareLink;
    private URLImageGetter mUrlImageGetter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onInitView() {
        mNewsDetailFromTv = (TextView) findViewById(R.id.news_detail_from_tv);
        mNewsDetailBodyTv = (TextView) findViewById(R.id.news_detail_body_tv);
        mNewsDetailPhotoIv = (ImageView) findViewById(R.id.news_detail_photo_iv);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mMaskView = findViewById(R.id.mask_view);
    }

    @Override
    protected void onInitData2Remote() {
        super.onInitData2Remote();
        String postId = getIntent().getStringExtra(Constant.NEWS_POST_ID);
        ((NewsDetailPresenterImpl) mPresenterImpl).setPosId(postId);
        ((NewsDetailPresenterImpl) mPresenterImpl).onLoadNewsDetailData();
    }

    @Override
    protected Class getLogicClazz() {
        return NewsDetailPresenter.class;
    }

    @Override
    public void onNewsDetailResponse(NewsDetail newsDetail) {
        mShareLink = newsDetail.getShareLink();
        mNewsTitle = newsDetail.getTitle();
        String newsSource = newsDetail.getSource();
        String newsTime = MyUtils.formatDate(newsDetail.getPtime());
        String newsBody = newsDetail.getBody();
        String NewsImgSrc = getImgSrcs(newsDetail);
        setToolBarLayout(mNewsTitle);
        mNewsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));
        setNewsDetailPhotoIv(NewsImgSrc);
        setNewsDetailBodyTv(newsDetail, newsBody);
    }

    @Override
    public void onNewsDetailFailure(String msg) {
        mProgressBar.setVisibility(View.GONE);
    }

    private void setToolBarLayout(String newsTitle) {
        mToolbarLayout.setTitle(newsTitle);
        mToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        mToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white));
    }

    private void setNewsDetailPhotoIv(String imgSrc) {
        Glide.with(this).load(imgSrc).asBitmap()
                .placeholder(R.drawable.ic_loading)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .error(R.drawable.ic_load_fail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mNewsDetailPhotoIv);
    }

    private void setNewsDetailBodyTv(final NewsDetail newsDetail, final String newsBody) {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .compose(TransformUtils.<Long>defaultSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mProgressBar.setVisibility(View.GONE);
                        mFab.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.RollIn).playOn(mFab);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        setBody(newsDetail, newsBody);
                    }
                });
    }

    private void setBody(NewsDetail newsDetail, String newsBody) {
        int imgTotal = newsDetail.getImg().size();
        if (isShowBody(newsBody, imgTotal)) {
            mUrlImageGetter = new URLImageGetter(mNewsDetailBodyTv, newsBody, imgTotal);
            mNewsDetailBodyTv.setText(Html.fromHtml(newsBody, mUrlImageGetter, null));
        } else {
            mNewsDetailBodyTv.setText(Html.fromHtml(newsBody));
        }
    }
    private boolean isShowBody(String newsBody, int imgTotal) {
        return BaseApplication.isHavePhoto() && imgTotal >= 2 && newsBody != null;
    }
    private String getImgSrcs(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        String imgSrc;
        if (imgSrcs != null && imgSrcs.size() > 0) {
            imgSrc = imgSrcs.get(0).getSrc();
        } else {
            imgSrc = getIntent().getStringExtra(Constant.NEWS_IMG_RES);
        }
        return imgSrc;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
