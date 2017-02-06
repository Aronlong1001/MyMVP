package test.aron.com.mvpdemo.presenter;

import test.aron.com.mvpdemo.annotation.Implement;
import test.aron.com.mvpdemo.presenter.impl.DeviceInfoPresenterImpl;

/**
 * Created by Aron on 2016/12/18.
 */
@Implement(DeviceInfoPresenterImpl.class)
public interface DeviceInfoPresenter {
    void onLoadDeviceInfoData();
}
