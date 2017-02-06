package test.aron.com.fragmentdemo.impl;

import android.os.Handler;
import android.os.Message;

import test.aron.com.fragmentdemo.listener.CallBack;
import test.aron.com.fragmentdemo.listener.ViewCallBack;

/**
 * Created by Aron on 2016/12/28.
 */
public class CallBackImpl implements CallBack {

    private ViewCallBack view;

    public CallBackImpl(ViewCallBack view) {
        this.view = view;
    }

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            view.hideProgress();
            view.onSuccess();
        }
    };
    @Override
    public void onResponse() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
