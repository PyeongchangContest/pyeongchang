package com.pyeongchang.conch.conch;

/**
 * Created by Gayeon on 2017-08-03.
 */

public class RunnerListviewItem {
    private int profile;
    private String name;
    private String date;

    public RunnerListviewItem(int profile, String name, String date) {
        this.profile = profile;
        this.name = name;
        this.date = date;
    }

    public int getProfile() {
        return profile;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
