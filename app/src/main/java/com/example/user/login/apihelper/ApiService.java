package com.example.user.login.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.user.login.Model.DetailTiket;
import com.example.user.login.Model.Gedung;
import com.example.user.login.Model.GedungResult;
import com.example.user.login.Model.HSResult;
import com.example.user.login.Model.Lantai;
import com.example.user.login.Model.ListLtM;
import com.example.user.login.Model.Mobil;
import com.example.user.login.Model.NotifResult;
import com.example.user.login.Model.ProfileModel;
import com.example.user.login.Model.TiketResult;
import com.example.user.login.Model.UserModel;

/**
 * Created by User on 25/09/2019.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    retrofit2.Call<ResponseBody> loginRequest(@Field("email") String email,
                                              @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    retrofit2.Call<ResponseBody> registerRequest(@Field("nama") String nama,
                                                 @Field("email") String email,
                                                 @Field("password") String password,
                                                 @Field("password1") String password1,
                                                 @Field("nohp") String nohp);

    @GET("profile/{id}")
    retrofit2.Call<UserModel> getProf(@Path("id") String id);

    @FormUrlEncoded
    @POST("editprofile")
    Call<ResponseBody> edprof(@Field("id") String id, @Field("nama") String nama, @Field("nohp") String nohp);

    @FormUrlEncoded
    @POST("tambahmobil")
    Call<ResponseBody> addMobil(@Field("id") String id,
                                @Field("tipe") String tipe,
                                @Field("noplat") String noplat);

    @GET("gedung/search/{Nama_Gedung}")
    Call<GedungResult> getGdg(@Path("Nama_Gedung") String nmgdg);

    @GET("lantai/{id}")
    Call<GedungResult> getSlot(@Path("id") String id);

    @GET("gedung/{id}")
    Call<GedungResult> getLt(@Path("id") String id);

    @GET("gedung/{id}")
    Call<GedungResult> getinfgdg(@Path("id") String id);

    @GET("slot/{id}")
    Call<GedungResult> getInfSlot(@Path("id") String id);

    @GET("ticket/{id}/batal")
    Call<ResponseBody> btlPesan(@Path("id") String id);

    @GET("listicket/{id}")
    Call<TiketResult> getTiket(@Path("id") String id);

    @GET("infoticket/{id}")
    Call<DetailTiket> getdeTicket(@Path("id") String id);

    @GET("mobil/{id}")
    Call<UserModel> getInfMbl(@Path("id") String id);

    @GET("mobil/{id}/delete")
    Call<ResponseBody> dMobil(@Path("id") String id);

    @GET("historyorder/{id}")
    Call<TiketResult> historder(@Path("id") String id);

    @GET("historysaldo/{id}")
    Call<HSResult> histsaldo(@Path("id") String id);

    @FormUrlEncoded
    @POST("kirimnotif")
    Call<ResponseBody> postKeluhan(@Field("tid") String id, @Field("slotid") String slotid);

    @GET("notif/{id}")
    Call<NotifResult> getKeluhan(@Path("id") String id);

    @FormUrlEncoded
    @POST("pesan")
    Call<ResponseBody> postPesan(@Field("userid") String id,
                                 @Field("slotid") String idslot,
                                 @Field("mobilid") String idmobil);

}
