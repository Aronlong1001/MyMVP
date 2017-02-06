package test.aron.com.mvpdemo.ui.activity.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import test.aron.com.mvpdemo.R;
import test.aron.com.mvpdemo.common.LogicProxy;
import test.aron.com.mvpdemo.presenter.base.BasePresenterImpl;
import test.aron.com.mvpdemo.view.base.BaseView;

/**
 * Created by Aron on 2016/12/18.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected BasePresenterImpl mPresenterImpl;

    protected abstract int getLayoutResource();

    protected abstract void onInitView();

    protected Class getLogicClazz() {
        return null;
    }

    protected void onInitData2Remote() {
        if (getLogicClazz() != null)
            mPresenterImpl = getLogicImpl();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent();
        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource());
        }
        this.onInitView();
        this.onInitData2Remote();
    }

    //获得该页面的实例
    public <T> T getLogicImpl() {
        return LogicProxy.getInstance().bind(getLogicClazz(), this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarTranslucent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenterImpl != null && !mPresenterImpl.isViewBind()) {
            LogicProxy.getInstance().bind(getLogicClazz(), this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenterImpl != null)
            mPresenterImpl.detachView();
    }
}
