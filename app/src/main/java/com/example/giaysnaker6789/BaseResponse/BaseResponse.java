package com.example.giaysnaker6789.BaseResponse;

import com.example.giaysnaker6789.models.bills;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mess")
    @Expose
    private String mess;
    @SerializedName("data")
    @Expose
    private List<Object> data = null;

    public BaseResponse(String status, String mess, List<Object> data) {
        this.status = status;
        this.mess = mess;
        this.data = data;
    }

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

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
