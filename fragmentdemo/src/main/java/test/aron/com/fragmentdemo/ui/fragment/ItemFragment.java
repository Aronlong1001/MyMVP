package test.aron.com.fragmentdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import test.aron.com.fragmentdemo.R;
import test.aron.com.fragmentdemo.impl.CallBackImpl;
import test.aron.com.fragmentdemo.listener.ViewCallBack;
import test.aron.com.fragmentdemo.ui.adapter.MyRecyclerViewAdapter;

/**
 * Created by Aron on 2016/12/28.
 */
public class ItemFragment extends Fragment implements ViewCallBack, SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private RecyclerView mNewsRV;
    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private View rootView;
    private List<String> list = new ArrayList<>();
    private String type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        if (getArguments() != null){
            type = getArguments().getString("type");
        }
        switch (type){
            case "headline":
                setData("头条");
                break;
            case "finance":
                setData("财经");
                break;
            case "society":
                setData("社会");
                break;
            case "military":
                setData("军事");
                break;
            case "tech":
                setData("科技");
                break;
        }
    }

    private void setData(String content) {
        for (int i = 0; i < 10; i++){
            list.add(content + "数据" + content + "数据" + i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_layout, null);
        initView();
        onInitData2Remote();
        return rootView;
    }

    private void onInitData2Remote() {
        CallBackImpl call = new CallBackImpl(this);
        call.onResponse();
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mNewsRV = (RecyclerView) rootView.findViewById(R.id.news_rv);
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mNewsRV.setHasFixedSize(true);
        mNewsRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mNewsRV.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity());
        mNewsRV.setAdapter(mRecyclerViewAdapter);
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getIntArray(R.array.gplus_colors));
    }

    @Override
    public void onRefresh() {
        CallBackImpl call = new CallBackImpl(this);
        call.onResponse();
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerViewAdapter.setList(list);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure() {

    }
}
