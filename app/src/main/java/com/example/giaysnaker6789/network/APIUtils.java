package com.example.giaysnaker6789.network;

public class APIUtils {
    public  static final String Base_Url="http://192.168.1.6:80/snaker6789/public/"; // địa chỉ ip /project in xampp
    public static DataClient getData(){
        return BaseService.getClient(Base_Url).create(DataClient.class);
    }
}
