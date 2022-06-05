package com.example.popfinder.WebServices;

import com.example.popfinder.Model.DirectionPlaceModel.DirectionResponseModel;
import com.example.popfinder.Model.GoogleResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {


    @GET
    Call<GoogleResponseModel> getNearByPlaces(@Url String url);

    @GET
    Call<DirectionResponseModel> getDirection(@Url String url);

}
