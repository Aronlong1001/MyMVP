package test.aron.com.mvpdemo.view;

import test.aron.com.mvpdemo.entity.DeviceInfoBean;
import test.aron.com.mvpdemo.view.base.BaseView;

/**
 * Created by Aron on 2016/12/18.
 */
public interface DeviceInfoView extends BaseView{
    void onDeviceInfoResponse(DeviceInfoBean deviceInfoBean);
    void onDeviceInfoFailure(String msg);
}
