package com.example.giaysnaker6789.BaseResponse;

import com.example.giaysnaker6789.models.bills2;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class bills2BaseResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mess")
    @Expose
    private String mess;
    @SerializedName("data")
    @Expose
    private List<bills2> data = null;

    public bills2BaseResponse(String status, String mess, List<bills2> data) {
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

    public List<bills2> getData() {
        return data;
    }

    public void setData(List<bills2> data) {
        this.data = data;
    }
}
