package com.pyeongchang.conch.conch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Schwa on 2017-07-18.
 */

public class TorchCommunity {
    private ArrayList<String> userList=new ArrayList<>();
    private int communityScore;
    private int communityRank;
    private String communityName;
    private int maxPeople;
    private boolean isSecret;
    private int racingLevel; //db에서 미션을 난이도별로 받아도기 위한 변수. 더 나은 구현 알고리즘이 있을까 고민 필요
    /**************************************************/
    //타임라인 추가하기 (타임라인아이템 객체가 필요해여)
    private  List<Item> timeLine;
    private List<Item> commentTimeLine;
    /**************************************************/
    private String runner; //현재 해당 커뮤니티의 주자를 저장하기 위한 변수
    // (유저 객체도 필요하지 않을까 추후 고려 그냥 List<String> 이어도 될 듯 싶기도 함)
    private MissionItem racingMission;
    private MissionItem InvitationMission;
    private MissionItem Quiz;
    private List<MissionItem> completedMission = new ArrayList<MissionItem>();
    private List<String> route = new ArrayList<String>();

    public TorchCommunity(String leader,String communityName,int maxPeople,boolean isSecret){
        userList.add(leader);
        communityScore=0;
        racingLevel = 0;
        this.communityName=communityName;
        this.maxPeople=maxPeople;
        this.isSecret=isSecret;
    }

    public TorchCommunity() {
    }

    public int getCommunityScore() {
        return communityScore;
    }

    public void setCommunityScore(int communityScore) {
        this.communityScore = communityScore;
    }

    public ArrayList<String > getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<String > userList) {
        this.userList = userList;}

    public int getCommunityRank() {
        return communityRank;
    }

    public void setCommunityRank(int communityRank) {
        this.communityRank = communityRank;
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

    public int getRacingLevel() {
        return racingLevel;
    }

    public void setRacingLevel(int racingLevel) {
        this.racingLevel = racingLevel;
    }

    public String getRunner() {
        return runner;
    }

    public void setRunner(String runner) {
        this.runner = runner;
    }

    public MissionItem getRacingMission() {
        return racingMission;
    }

    public void setRacingMission(MissionItem racingMission) {
        this.racingMission = racingMission;
    }

    public MissionItem getInvitationMission() {
        return InvitationMission;
    }

    public void setInvitationMission(MissionItem invitationMission) {
        InvitationMission = invitationMission;
    }

    public MissionItem getQuiz() {
        return Quiz;
    }

    public void setQuiz(MissionItem quiz) {
        Quiz = quiz;
    }

    public List<MissionItem> getCompletedMission() {
        return completedMission;
    }

    public void setCompletedMission(List<MissionItem> completedMission) {
        this.completedMission = completedMission;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }
}
