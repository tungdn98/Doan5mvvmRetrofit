package com.example.giaysnaker6789.BaseResponse;

import com.example.giaysnaker6789.models.bills;
import com.example.giaysnaker6789.models.user1s;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseUser1s {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mess")
    @Expose
    private String mess;
    @SerializedName("data")
    @Expose
    private user1s data = null;

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

    public user1s getData() {
        return data;
    }

    public void setData(user1s data) {
        this.data = data;
    }
}
