package com.pyeongchang.conch.conch;

import java.util.List;

/**
 * Created by GAYEON on 2017-08-17.
 */

public class MissionItem {
    private String missionName;
    private int progress;
    private List<String> contributor; //User형 리스트로 변경해야할 지 고려!

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getMissionName() {

        return missionName;
    }

    public int getProgress() {
        return progress;
    }

    public List<String> getContributor() {
        return contributor;
    }
}
