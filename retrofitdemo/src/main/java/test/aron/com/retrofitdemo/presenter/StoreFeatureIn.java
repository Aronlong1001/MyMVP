package test.aron.com.retrofitdemo.presenter;

import test.aron.com.retrofitdemo.bean.StoreFeatureBean;

/**
 * Created by Aron on 2016/12/13.
 */
public interface StoreFeatureIn {
    void onLoadStoreFeatureData();
    interface StoreFeatureView{
        void onStoreFeatureResponse(StoreFeatureBean response);
        void onStoreFeatureFailure(String msg);
    }
}
