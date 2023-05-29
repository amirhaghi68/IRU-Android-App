package com.example.IRUAndroidApp.api;

import com.example.IRUAndroidApp.models.Schedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api.php")
    Call<List<Schedule>> getData();
}
