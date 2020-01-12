package com.example.user.login.apihelper;

/**
 * Created by User on 25/09/2019.
 */

public class UtilApi {
    public static final String BASE_URL_API =
            "http://10.0.2.2/Skripsi/public/api/";

    // Mendeklarasikan Interface BaseApiService
    public static ApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }
}
