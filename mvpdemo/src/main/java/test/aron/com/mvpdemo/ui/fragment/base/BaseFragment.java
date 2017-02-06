package test.aron.com.mvpdemo.ui.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.aron.com.mvpdemo.common.LogicProxy;
import test.aron.com.mvpdemo.presenter.base.BasePresenterImpl;
import test.aron.com.mvpdemo.view.base.BaseView;

/**
 * Created by Aron on 2016/12/21.
 */
public abstract class BaseFragment extends Fragment implements BaseView{
    protected BasePresenterImpl mPresenterImpl;
    protected View rootView;
    protected Context mContext = null;//context

    protected abstract int getLayoutResource();

    protected abstract void onInitView(Bundle savedInstanceState);

    protected Class getLogicClazz() {
        return null;
    }

    protected void onInitData2Remote() {
        if (getLogicClazz() != null)
            mPresenterImpl = getLogicImpl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutResource() != 0){
            rootView = inflater.inflate(getLayoutResource(), null);
        } else {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        this.onInitView(savedInstanceState);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInitData2Remote();
    }

    //获得该页面的实例
    public <T> T getLogicImpl() {
        return LogicProxy.getInstance().bind(getLogicClazz(), this);
    }
    @Override
    public void onStart() {
        if (mPresenterImpl != null && !mPresenterImpl.isViewBind()) {
            LogicProxy.getInstance().bind(getLogicClazz(), this);
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenterImpl != null)
            mPresenterImpl.detachView();
    }
}
