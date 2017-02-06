package test.aron.com.mvpdemo.entity;

/**
 * Created by Aron on 2016/12/9.
 */
public class DeviceInfoBean {

    private Obj obj;
    private String resCode;

    public Obj getObj() {
        return obj;
    }

    public String getResCode() {
        return resCode;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public static class Obj{
        private String deviceId;
        private String storeId;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }
    }
}
