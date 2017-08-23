package com.pyeongchang.conch.conch;

/**
 * Created by hyojung on 2017-07-12.
 * 타임라인에 들어갈 항목 아이템
 * 덧글에 들어갈 항목 아이템
 */

public class Item {
    public int profileImg;
    public String simpleProfile;
    public String date;
    public String name;
    public String content;
    public Item comment;

    public Item(){

    }
    // 게시글 & 덧글
    public Item(String date, int profileImg, String name, String content){
        this.content = content;
        this.date = date;
        this.profileImg = profileImg;
        this.name = name;
    }

    public String getDate(){
        return this.date;
    }
    public String getSimpleProfile(){
        return this.simpleProfile;
    }
    public String getContent(){
        return this.content;
    }
    public String getName(){
        return this.name;
    }
    public Item getComment(){
        return this.comment;
    }

    public void setContent(String content){
        this.content = content;
    }


}
