package com.example.beta.rest;

import com.example.beta.rest.model.ApiResquestUserAuth;
import com.example.beta.rest.model.UserEntity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

   // String baseUrl="http://vps-19596d03.vps.ovh.net/";
    String baseUrl="http://54.38.183.225:19999/";
    @POST("api/v1/auth/authenticate")
   // Call<ApiRequestUserAuth> userSignIn(@Body UserEntitySpring user);
     Call<ApiResquestUserAuth>userSingnIn(@Body UserEntity userEntity);





}
