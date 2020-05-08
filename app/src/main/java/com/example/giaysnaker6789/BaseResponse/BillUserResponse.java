package com.example.giaysnaker6789.BaseResponse;

import java.util.List;

import com.example.giaysnaker6789.models.bills;
import com.example.giaysnaker6789.models.billuser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillUserResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mess")
    @Expose
    private String mess;
    @SerializedName("data")
    @Expose
    private List<billuser> data = null;

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

    public List<billuser> getData() {
        return data;
    }

    public void setData(List<billuser> data) {
        this.data = data;
    }
}
