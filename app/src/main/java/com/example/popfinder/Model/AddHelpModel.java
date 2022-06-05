package com.example.popfinder.Model;


public class AddHelpModel {

    private String id;
    private String location;
    private String name;
    private String answer;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    private Boolean isActive;

    public AddHelpModel() {
    }

    public AddHelpModel(String location, String name, String answer) {
        this.location = location;
        this.name = name;
        this.answer=answer;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}