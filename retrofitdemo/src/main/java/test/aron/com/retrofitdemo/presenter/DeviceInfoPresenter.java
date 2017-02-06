package test.aron.com.retrofitdemo.presenter;


import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.aron.com.retrofitdemo.Common;
import test.aron.com.retrofitdemo.api.ApiParameters;
import test.aron.com.retrofitdemo.api.RestApi;
import test.aron.com.retrofitdemo.api.deviceInfoService;
import test.aron.com.retrofitdemo.bean.deviceInfoBean;

/**
 * Created by Aron on 2016/12/11.
 */
public class DeviceInfoPresenter implements DeviceInfoIn {

    private StoreFeatureIn.StoreFeatureView view;

    public DeviceInfoPresenter(StoreFeatureIn.StoreFeatureView view) {
        this.view = view;
    }

    @Override
    public void onLoadDeviceInfoData() {
        Subscriber<deviceInfoBean> subscriber = new Subscriber<deviceInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(deviceInfoBean deviceInfoBean) {
                String storeId = deviceInfoBean.getObj().getStoreId();
                StoreFeaturePresenter presenter = new StoreFeaturePresenter(view, storeId);
                presenter.onLoadStoreFeatureData();
            }
        };

        Retrofit retrofit = RestApi.getInstance().createApiClient(Common.baseUrl);
        final deviceInfoService service = retrofit.create(deviceInfoService.class);
        RequestBody body = ApiParameters.deviceInfoParams();
        service.getDeviceInfo_(body)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
