package test.aron.com.retrofitdemo.api;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.RequestBody;

/**
 * Created by Aron on 2016/12/13.
 */
public class ApiParameters {

    public static RequestBody deviceInfoParams(){
        Gson gson = new Gson();
        HashMap<String, String> params = new HashMap<>();
        params.put("mac","00-23-5A-15-99-42");
        String entity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),entity);
        return body;
    }

    public static RequestBody storeFeatureParams(String storeId){
        Gson gson = new Gson();
        HashMap<String, String> params = new HashMap<>();
        params.put("storeId",storeId);
        String entity = gson.toJson(params);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),entity);
        return body;
    }
}
