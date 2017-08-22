package com.pyeongchang.conch.conch;

import java.util.List;

/**
 * Created by GAYEON on 2017-08-17.
 */

public class User {
    private String userName;
    private String email;
    private String pwd;
    private String nation;
    private String info;
    private int score;
    private int photo;
    private boolean isRunner;
    private int distance; //If isRunner is true, the value of distance is valid;
    private List<String> communityList; // Community number is limited to 5;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setRunner(boolean runner) {
        isRunner = runner;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getUserName() {

        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public String getNation() {
        return nation;
    }

    public String getInfo() {
        return info;
    }

    public int getScore() {
        return score;
    }

    public int getPhoto() {
        return photo;
    }

    public boolean isRunner() {
        return isRunner;
    }

    public int getDistance() {
        return distance;
    }

    public List<String> getCommunityList() {
        return communityList;
    }
}
