package com.example.popfinder.Model.DirectionPlaceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DirectionResponseModel {

    @SerializedName("routes")
    @Expose
    List<com.example.popfinder.Model.DirectionPlaceModel.DirectionRouteModel> directionRouteModels;


    public List<com.example.popfinder.Model.DirectionPlaceModel.DirectionRouteModel> getDirectionRouteModels() {
        return directionRouteModels;
    }

    public void setDirectionRouteModels(List<com.example.popfinder.Model.DirectionPlaceModel.DirectionRouteModel> directionRouteModels) {
        this.directionRouteModels = directionRouteModels;
    }
}
