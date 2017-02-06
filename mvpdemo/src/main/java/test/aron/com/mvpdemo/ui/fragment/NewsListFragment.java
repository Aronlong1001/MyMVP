package test.aron.com.mvpdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import test.aron.com.mvpdemo.R;
import test.aron.com.mvpdemo.common.LoadType;
import test.aron.com.mvpdemo.entity.NewsSummary;
import test.aron.com.mvpdemo.listener.OnItemClickListener;
import test.aron.com.mvpdemo.presenter.NewsPresenter;
import test.aron.com.mvpdemo.presenter.impl.NewsPresenterImpl;
import test.aron.com.mvpdemo.ui.adapter.NewsListAdapter;
import test.aron.com.mvpdemo.ui.fragment.base.BaseFragment;
import test.aron.com.mvpdemo.view.NewsView;

/**
 * Created by Aron on 2016/12/21.
 */
public class NewsListFragment extends BaseFragment implements NewsView, SwipeRefreshLayout.OnRefreshListener,
        OnItemClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private RecyclerView mNewsRV;
    private NewsListAdapter mNewsListAdapter;
    private boolean mIsAllLoaded;
    private String mNewsId;
    private String mNewsType;
    private FloatingActionButton mFab;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState) {
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewsRV.getLayoutManager().scrollToPosition(0);
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mTextView = (TextView) rootView.findViewById(R.id.empty_view);
        mNewsRV = (RecyclerView) rootView.findViewById(R.id.news_rv);
        mNewsListAdapter = new NewsListAdapter();
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initValues();
        super.onCreate(savedInstanceState);
    }

    private void initValues() {
        if (getArguments() != null){
            mNewsId = getArguments().getString("newsId");
            mNewsType = getArguments().getString("newsType");
        }
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.gplus_colors));
    }
    private void initRecyclerView() {
        mNewsRV.setHasFixedSize(true);
        mNewsRV.setLayoutManager(new LinearLayoutManager(getActivity(),
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
                    ((NewsPresenterImpl)mPresenterImpl).onLoadMoreNewsData();
                    mNewsListAdapter.showFooter();
                    mNewsRV.scrollToPosition(mNewsListAdapter.getItemCount() - 1);
                }
            }

        });

        mNewsListAdapter.setOnItemClickListener(this);
        mNewsRV.setAdapter(mNewsListAdapter);
    }
    @Override
    public void onNewsResponse(List<NewsSummary> newsSummary, int loadType) {
        if (loadType == LoadType.TYPE_REFRESH_SUCCESS){
            mSwipeRefreshLayout.setRefreshing(false);
            mNewsListAdapter.setList(newsSummary);
            mNewsListAdapter.notifyDataSetChanged();
            checkIsEmpty(newsSummary);
        }else if (loadType == LoadType.TYPE_LOAD_MORE_SUCCESS){
            mNewsListAdapter.hideFooter();
            if (newsSummary == null || newsSummary.size() == 0) {
                mIsAllLoaded = true;
                Snackbar.make(mNewsRV, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
            } else {
                mNewsListAdapter.addMore(newsSummary);
            }
        }
    }

    private void checkIsEmpty(List<NewsSummary> newsSummary) {
        if (newsSummary == null && mNewsListAdapter.getList() == null) {
            mNewsRV.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);

        } else {
            mNewsRV.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNewsFailure(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        mNewsListAdapter.hideFooter();
    }

    @Override
    protected Class getLogicClazz() {
        return NewsPresenter.class;
    }

    @Override
    protected void onInitData2Remote() {
        super.onInitData2Remote();
        ((NewsPresenterImpl)mPresenterImpl).setNewsTypeAndId(mNewsType, mNewsId);
        ((NewsPresenterImpl)mPresenterImpl).onLoadNewsData();
    }

    @Override
    public void showProgress() {
 //       mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);

        Log.d("fffffff","sdfasdfasdfasdfd");
    }

    @Override
    public void onRefresh() {
        ((NewsPresenterImpl)mPresenterImpl).setNewsTypeAndId(mNewsType, mNewsId);
        ((NewsPresenterImpl)mPresenterImpl).onLoadNewsData();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("ssssss","rrrrrrrr");
    }
}
