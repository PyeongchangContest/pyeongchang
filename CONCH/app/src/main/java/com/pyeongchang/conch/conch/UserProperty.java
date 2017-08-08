package com.pyeongchang.conch.conch;

/**
 * Created by Schwa on 2017-07-18.
 */

public class UserProperty {
    private int userLevel;
    private int userScore;

    public UserProperty(int userLevel,int userScore) {
        this.userLevel=userLevel;
        this.userScore=userScore;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }
}
