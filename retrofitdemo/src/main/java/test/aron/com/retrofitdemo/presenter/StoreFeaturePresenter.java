package test.aron.com.retrofitdemo.presenter;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.aron.com.retrofitdemo.Common;
import test.aron.com.retrofitdemo.api.ApiParameters;
import test.aron.com.retrofitdemo.api.RestApi;
import test.aron.com.retrofitdemo.api.StoreFeatureService;
import test.aron.com.retrofitdemo.bean.StoreFeatureBean;

/**
 * Created by Aron on 2016/12/13.
 */
public class StoreFeaturePresenter implements StoreFeatureIn {
    private StoreFeatureView view;
    private String storeId;

    public StoreFeaturePresenter(StoreFeatureView view, String storeId) {
        this.view = view;
        this.storeId = storeId;
    }

    @Override
    public void onLoadStoreFeatureData() {
        Subscriber<StoreFeatureBean> subscriber = new Subscriber<StoreFeatureBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.onStoreFeatureFailure(e.getMessage());
            }

            @Override
            public void onNext(StoreFeatureBean storeFeatureBean) {
                view.onStoreFeatureResponse(storeFeatureBean);
            }
        };
        Retrofit retrofit = RestApi.getInstance().createApiClient(Common.baseUrl);
        StoreFeatureService service = retrofit.create(StoreFeatureService.class);
        RequestBody body = ApiParameters.storeFeatureParams(storeId);
        service.getStoreFeature(body).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
