package com.pyeongchang.conch.conch.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAYEON on 2017-08-16.
 */

public class communityDTO {
    private String communityName;
    private int maxNumOfPeople;
    private boolean isSecrete;
    private int score;
    /**************************************************/
    //타임라인 추가하기 (타임라인아이템 객체가 필요해여)
    /**************************************************/
    private List<String> user = new ArrayList<String>();
        // (유저 객체도 필요하지 않을까 추후 고려 그냥 List<String> 이어도 될 듯 싶기도 함)
    private List<String> processingMission = new ArrayList<String>();
        //미션의 기여자, 진행상황 등을 표시하기 위해서는 미션도 하나의 객체가 필요할 것 같기도 함
    private List<String> completedMission = new ArrayList<String>();
        //processingMission과 이하 내용 같음
    private List<String> route = new ArrayList<String>();

    public String getCommunityName() {
        return communityName;
    }

    public int getMaxNumOfPeople() {
        return maxNumOfPeople;
    }

    public boolean isSecrete() {
        return isSecrete;
    }

    public int getScore() {
        return score;
    }

    public List<String> getUser() {
        return user;
    }

    public List<String> getProcessingMission() {
        return processingMission;
    }

    public List<String> getCompletedMission() {
        return completedMission;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public void setMaxNumOfPeople(int maxNumOfPeople) {
        this.maxNumOfPeople = maxNumOfPeople;
    }

    public void setSecrete(boolean secrete) {
        isSecrete = secrete;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
