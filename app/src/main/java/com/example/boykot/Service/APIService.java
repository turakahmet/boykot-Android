package com.example.boykot.Service;

import com.example.boykot.pojo.BoykotMarka;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("/urunler")
    Call<List<BoykotMarka>> getAllBoykotUrunler();
    @GET("urunler/byType")
    Call<List<BoykotMarka>> getAllBoykotUrunlerByType(@Query("type") String type);
    @GET("urunler/byBarkod")
    Call<List<BoykotMarka>> getBoykotUrunByBarcot(@Query("barkod") String barkod);
}
