package com.pyeongchang.conch.conch;

import java.util.List;

/**
 * Created by GAYEON on 2017-08-17.
 */

public class MissionItem {
    private String mission;
    private int progress;
//    private List<String> contributor; //User형 리스트로 변경해야할 지 고려!
    public MissionItem(){

    }
    public MissionItem(String mission) {
        this.mission = mission;
        this.progress = 0;
    }

    public MissionItem(String mission, int progress) {
        this.mission = mission;
        this.progress = progress;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getMissionName() {

        return mission;
    }

    public int getProgress() {
        return progress;
    }

//    public List<String> getContributor() {
//        return contributor;
//    }
}
