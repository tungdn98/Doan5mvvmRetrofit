package com.example.giaysnaker6789.BaseResponse;

import com.example.giaysnaker6789.models.bills;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Billresponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mess")
    @Expose
    private String mess;
    @SerializedName("data")
    @Expose
    private List<bills> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public List<bills> getData() {
        return data;
    }

    public void setData(List<bills> data) {
        this.data = data;
    }
}
