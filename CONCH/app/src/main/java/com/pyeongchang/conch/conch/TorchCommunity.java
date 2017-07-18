package com.pyeongchang.conch.conch;

import java.util.ArrayList;

/**
 * Created by Schwa on 2017-07-18.
 */

public class TorchCommunity {
    private ArrayList<UserProperty> userList=new ArrayList<>();
    private int communityScore;
    private String communityName;
    private int maxPeople;
    private boolean isSecret;

    public TorchCommunity(UserProperty leader,String communityName,int maxPeople,boolean isSecret){
        userList.add(leader);
        communityScore=0;
        this.communityName=communityName;
        this.maxPeople=maxPeople;
        this.isSecret=isSecret;
    }

    public int getCommunityScore() {
        return communityScore;
    }

    public void setCommunityScore(int communityScore) {
        this.communityScore = communityScore;
    }

    public ArrayList<UserProperty> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<UserProperty> userList) {
        this.userList = userList;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public boolean isSecret() {
        return isSecret;
    }

    public void setSecret(boolean secret) {
        isSecret = secret;
    }
}
