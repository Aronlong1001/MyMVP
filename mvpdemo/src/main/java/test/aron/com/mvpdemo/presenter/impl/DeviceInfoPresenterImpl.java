package test.aron.com.mvpdemo.presenter.impl;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.aron.com.mvpdemo.api.ApiParameters;
import test.aron.com.mvpdemo.api.RestApi;
import test.aron.com.mvpdemo.api.ApiService;
import test.aron.com.mvpdemo.common.Common;
import test.aron.com.mvpdemo.entity.DeviceInfoBean;
import test.aron.com.mvpdemo.presenter.DeviceInfoPresenter;
import test.aron.com.mvpdemo.presenter.base.BasePresenterImpl;
import test.aron.com.mvpdemo.view.DeviceInfoView;

/**
 * Created by Aron on 2016/12/18.
 */
public class DeviceInfoPresenterImpl extends BasePresenterImpl<DeviceInfoView> implements DeviceInfoPresenter {
    @Override
    public void onLoadDeviceInfoData() {
        Subscriber<DeviceInfoBean> subscriber = new Subscriber<DeviceInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onDeviceInfoFailure(e.getMessage());
            }

            @Override
            public void onNext(DeviceInfoBean deviceInfoBean) {
                getView().onDeviceInfoResponse(deviceInfoBean);
            }
        };
        Retrofit retrofit = RestApi.getInstance().createApiClient(Common.NETEAST_HOST);
        final ApiService service = retrofit.create(ApiService.class);
        RequestBody body = ApiParameters.deviceInfoParams();
        service.getDeviceInfo_(body)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
