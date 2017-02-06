package test.aron.com.retrofitdemo.ui;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.aron.com.retrofitdemo.R;
import test.aron.com.retrofitdemo.api.deviceInfoService;
import test.aron.com.retrofitdemo.base.BaseActivity;
import test.aron.com.retrofitdemo.bean.StoreFeatureBean;
import test.aron.com.retrofitdemo.bean.deviceInfoBean;
import test.aron.com.retrofitdemo.presenter.DeviceInfoPresenter;
import test.aron.com.retrofitdemo.presenter.StoreFeatureIn;

public class MainActivity extends BaseActivity implements  StoreFeatureIn.StoreFeatureView{

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        TextView retrofit = (TextView) findViewById(R.id.retrofit);
        retrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRetrofitRequest();
            }
        });
        TextView retrofit_rxjava = (TextView) findViewById(R.id.retrofit_rxjava);
        retrofit_rxjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceInfoPresenter presenter = new DeviceInfoPresenter(MainActivity.this);
                presenter.onLoadDeviceInfoData();
            }
        });
    }


    private void startRetrofitRequest() {
        String baseUrl = "http://10.201.129.66:22080/blgroup-osp-screen-api/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        deviceInfoService service = retrofit.create(deviceInfoService.class);
        Gson gson = new Gson();
        HashMap<String, String> hash = new HashMap<>();
        hash.put("macAddress","00-23-5A-15-99-42");
        String entity = gson.toJson(hash);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),entity);
        Call<deviceInfoBean> call = service.getDeviceInfo(body);
        call.enqueue(new Callback<deviceInfoBean>() {
            @Override
            public void onResponse(Call<deviceInfoBean> call, Response<deviceInfoBean> response) {

            }

            @Override
            public void onFailure(Call<deviceInfoBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStoreFeatureResponse(StoreFeatureBean response) {
        Log.d("onStoreFeatureResponse", response.getObj().get(0).getDictName());
    }

    @Override
    public void onStoreFeatureFailure(String msg) {
        Log.d("onStoreFeatureFailure",msg);
    }
}
