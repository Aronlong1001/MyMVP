package test.aron.com.retrofitdemo.presenter;

import test.aron.com.retrofitdemo.bean.deviceInfoBean;

/**
 * Created by Aron on 2016/12/11.
 */
public interface DeviceInfoIn {
    void onLoadDeviceInfoData();

    interface DeviceInfoView {
        void onResponse(deviceInfoBean response);

        void onFailure(String msg);
    }
}
