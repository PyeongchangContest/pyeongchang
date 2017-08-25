package com.pyeongchang.conch.conch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAYEON on 2017-08-17.
 */

public class User {
    private String userName;
    private String id;
    private String pwd;
    private String nation;
    private String info="Hello";

    private int level=1;
    private int score=0;
    private int photo=R.drawable.horang;
    private boolean isRunner=false;
    private int distance=0; //If isRunner is true, the value of distance is valid;
    private List<String> communityList=new ArrayList<>(); // Community number is limited to 5;

    private List<String> msgList=new ArrayList<>();
    public User(String userName, String id, String pwd, String nation){
        this.userName=userName;
        this.id = id;
        this.pwd=pwd;
        this.nation=nation;
        msgList.add(":) "+userName+"님, 회원가입을 축하합니다.");
    }
    public User(String userName, String id, String nation){
        this.userName=userName;
        this.id = id;
        this.nation=nation;
    }
    public User(){

    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getId() {
        return id;
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

    public List<String> getMsgList() {
        return msgList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
