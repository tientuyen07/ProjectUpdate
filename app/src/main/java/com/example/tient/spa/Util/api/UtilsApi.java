package com.example.tient.spa.Util.api;

public class UtilsApi {

    public static final String BASE_URL_API = "http://192.168.1.155/spa/";

    //public static final String BASE_URL_API="https://spaproject.000webhostapp.com/spa/";
    public static BaseApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
