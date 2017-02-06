package com.aron.framedemo.ui.activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.aron.framedemo.R;
import com.aron.framedemo.common.Constant;
import com.aron.framedemo.common.LoadType;
import com.aron.framedemo.entity.NewsPhotoDetail;
import com.aron.framedemo.entity.NewsSummary;
import com.aron.framedemo.presenter.NewsPresenter;
import com.aron.framedemo.presenter.impl.NewsPresenterImpl;
import com.aron.framedemo.presenter.view.NewsView;
import com.aron.framedemo.ui.activity.base.BaseActivity;
import com.aron.framedemo.ui.adapter.NewsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NewsView, SwipeRefreshLayout.OnRefreshListener,
        NewsListAdapter.OnNewsListItemClickListener {

    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mNewsRV;
    private NewsListAdapter mNewsListAdapter;
    private FloatingActionButton mFab;
    private boolean mIsAllLoaded;
    private String newsType, newsId;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mNewsRV = (RecyclerView) findViewById(R.id.news_rv);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewsRV.getLayoutManager().scrollToPosition(0);
            }
        });
        setSupportActionBar(mToolbar);
        mNewsListAdapter = new NewsListAdapter();
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    @Override
    protected void onInitData2Remote() {
        super.onInitData2Remote();
        newsType = Constant.HEADLINE_TYPE;
        newsId = Constant.HEADLINE_ID;
        loadData();
    }

    @Override
    protected Class getLogicClazz() {
        return NewsPresenter.class;
    }

    private void initRecyclerView() {
        mNewsRV.setHasFixedSize(true);
        mNewsRV.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mNewsRV.setItemAnimator(new DefaultItemAnimator());
        mNewsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!mIsAllLoaded && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1) {
                    ((NewsPresenterImpl) mPresenterImpl).onLoadMoreNewsData();
                    mNewsListAdapter.showFooter();
                    mNewsRV.scrollToPosition(mNewsListAdapter.getItemCount() - 1);
                }
            }

        });

        mNewsListAdapter.setOnItemClickListener(this);
        mNewsRV.setAdapter(mNewsListAdapter);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));
    }

    @Override
    public void onNewsResponse(List<NewsSummary> newsSummary, int loadType) {
        if (loadType == LoadType.TYPE_REFRESH_SUCCESS) {
            mSwipeRefreshLayout.setRefreshing(false);
            mNewsListAdapter.setList(newsSummary);
            mNewsListAdapter.notifyDataSetChanged();
        } else if (loadType == LoadType.TYPE_LOAD_MORE_SUCCESS) {
            mNewsListAdapter.hideFooter();
            if (newsSummary == null || newsSummary.size() == 0) {
                mIsAllLoaded = true;
                Snackbar.make(mNewsRV, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
            } else {
                mNewsListAdapter.addMore(newsSummary);
            }
        }
    }

    @Override
    public void onNewsFailure(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        mNewsListAdapter.hideFooter();
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    private void loadData() {
        ((NewsPresenterImpl) mPresenterImpl).setNewsTypeAndId(newsType, newsId);
        ((NewsPresenterImpl) mPresenterImpl).onLoadNewsData();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(View view, int position, boolean isPhoto) {
        if (isPhoto) {
            NewsPhotoDetail newsPhotoDetail = getPhotoDetail(position);
            goToPhotoDetailActivity(newsPhotoDetail);
        } else {
            goToNewsDetailActivity(view, position);
        }
    }

    private NewsPhotoDetail getPhotoDetail(int position) {
        NewsSummary newsSummary = mNewsListAdapter.getList().get(position);
        NewsPhotoDetail newsPhotoDetail = new NewsPhotoDetail();
        newsPhotoDetail.setTitle(newsSummary.getTitle());
        setPictures(newsSummary, newsPhotoDetail);
        return newsPhotoDetail;
    }

    private void setPictures(NewsSummary newsSummary, NewsPhotoDetail newsPhotoDetail) {
        List<NewsPhotoDetail.Picture> pictureList = new ArrayList<>();

        if (newsSummary.getAds() != null) {
            for (NewsSummary.AdsBean entity : newsSummary.getAds()) {
                setValuesAndAddToList(pictureList, entity.getTitle(), entity.getImgsrc());
            }
        } else if (newsSummary.getImgextra() != null) {
            for (NewsSummary.ImgextraBean entity : newsSummary.getImgextra()) {
                setValuesAndAddToList(pictureList, null, entity.getImgsrc());
            }
        } else {
            setValuesAndAddToList(pictureList, null, newsSummary.getImgsrc());
        }

        newsPhotoDetail.setPictures(pictureList);
    }

    private void setValuesAndAddToList(List<NewsPhotoDetail.Picture> pictureList, String title, String imgsrc) {
        NewsPhotoDetail.Picture picture = new NewsPhotoDetail.Picture();
        if (title != null) {
            picture.setTitle(title);
        }
        picture.setImgSrc(imgsrc);

        pictureList.add(picture);
    }

    private void goToPhotoDetailActivity(NewsPhotoDetail newsPhotoDetail) {
        Intent intent = new Intent(this, NewsPhotoDetailActivity.class);
        intent.putExtra(Constant.PHOTO_DETAIL, newsPhotoDetail);
        startActivity(intent);
    }

    private void goToNewsDetailActivity(View view, int position) {
        Intent intent = setIntent(position);
        startActivity(view, intent);
    }

    @NonNull
    private Intent setIntent(int position) {
        List<NewsSummary> newsSummaryList = mNewsListAdapter.getList();

        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra(Constant.NEWS_POST_ID, newsSummaryList.get(position).getPostid());
        intent.putExtra(Constant.NEWS_IMG_RES, newsSummaryList.get(position).getImgsrc());
        return intent;
    }

    private void startActivity(View view, Intent intent) {
        ImageView newsSummaryPhotoIv = (ImageView) view.findViewById(R.id.news_summary_photo_iv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(this, newsSummaryPhotoIv, Constant.TRANSITION_ANIMATION_NEWS_PHOTOS);
            startActivity(intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(this, intent, options.toBundle());
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
