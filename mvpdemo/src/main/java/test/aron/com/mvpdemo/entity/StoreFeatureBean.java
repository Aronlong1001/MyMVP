package test.aron.com.mvpdemo.entity;

import java.util.List;

/**
 * Created by Aron on 2016/12/13.
 */
public class StoreFeatureBean {
    private String resCode;
    private List<ServiceObj> obj;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public List<ServiceObj> getObj() {
        return obj;
    }

    public void setObj(List<ServiceObj> obj) {
        this.obj = obj;
    }

    public static class ServiceObj {
        String fee;
        String dictValue;
        String picUrl;
        String serviceDesc;
        String addr;
        String dictName;

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getDictValue() {
            return dictValue;
        }

        public void setDictValue(String dictValue) {
            this.dictValue = dictValue;
        }

        public String getServiceDesc() {
            return serviceDesc;
        }

        public void setServiceDesc(String serviceDesc) {
            this.serviceDesc = serviceDesc;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getDictName() {
            return dictName;
        }

        public void setDictName(String dictName) {
            this.dictName = dictName;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

    }
}
