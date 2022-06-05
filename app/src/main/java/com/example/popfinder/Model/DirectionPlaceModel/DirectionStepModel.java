package com.example.popfinder.Model.DirectionPlaceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DirectionStepModel {

    @SerializedName("distance")
    @Expose
    private com.example.popfinder.Model.DirectionPlaceModel.DirectionDistanceModel distance;
    @SerializedName("duration")
    @Expose
    private com.example.popfinder.Model.DirectionPlaceModel.DirectionDurationModel duration;
    @SerializedName("end_location")
    @Expose
    private com.example.popfinder.Model.DirectionPlaceModel.EndLocationModel endLocation;
    @SerializedName("html_instructions")
    @Expose
    private String htmlInstructions;
    @SerializedName("polyline")
    @Expose
    private com.example.popfinder.Model.DirectionPlaceModel.DirectionPolylineModel polyline;
    @SerializedName("start_location")
    @Expose
    private com.example.popfinder.Model.DirectionPlaceModel.StartLocationModel startLocation;
    @SerializedName("travel_mode")
    @Expose
    private String travelMode;
    @SerializedName("maneuver")
    @Expose
    private String maneuver;

    public com.example.popfinder.Model.DirectionPlaceModel.DirectionDistanceModel getDistance() {
        return distance;
    }

    public void setDistance(com.example.popfinder.Model.DirectionPlaceModel.DirectionDistanceModel distance) {
        this.distance = distance;
    }

    public com.example.popfinder.Model.DirectionPlaceModel.DirectionDurationModel getDuration() {
        return duration;
    }

    public void setDuration(com.example.popfinder.Model.DirectionPlaceModel.DirectionDurationModel duration) {
        this.duration = duration;
    }

    public com.example.popfinder.Model.DirectionPlaceModel.EndLocationModel getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(com.example.popfinder.Model.DirectionPlaceModel.EndLocationModel endLocation) {
        this.endLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public com.example.popfinder.Model.DirectionPlaceModel.DirectionPolylineModel getPolyline() {
        return polyline;
    }

    public void setPolyline(com.example.popfinder.Model.DirectionPlaceModel.DirectionPolylineModel polyline) {
        this.polyline = polyline;
    }

    public com.example.popfinder.Model.DirectionPlaceModel.StartLocationModel getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(com.example.popfinder.Model.DirectionPlaceModel.StartLocationModel startLocation) {
        this.startLocation = startLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }
}
