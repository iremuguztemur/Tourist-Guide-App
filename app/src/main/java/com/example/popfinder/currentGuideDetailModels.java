package com.example.popfinder;

public class currentGuideDetailModels {

    private String id;
    private String location;
    private String name;
    private String phone;
    private String explain;
    private String profile_photo;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    private Boolean isActive;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getProfilePhoto() {
        return profile_photo;
    }

    public void setProfilePhoto(String profile_photo) {
        this.profile_photo = profile_photo;
    }
}
