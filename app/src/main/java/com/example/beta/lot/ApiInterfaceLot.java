package com.example.beta.lot;

import com.example.beta.rest.model.LotEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiInterfaceLot {

    @GET("api/v1/Lots")
    //Call<List<LotEntity>> getLot(@Header("Authorization") String authHeader);
    Call<List<LotEntity>> getLot(@Header("Authorization") String authHeader);
}





