package test.aron.com.retrofitdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aron on 2016/12/11.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayoutResource();
    protected abstract void onInitView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResource() != 0){
            setContentView(getLayoutResource());
        }
        this.onInitView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
