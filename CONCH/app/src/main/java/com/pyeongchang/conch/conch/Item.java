package com.pyeongchang.conch.conch;

/**
 * Created by hyojung on 2017-07-12.
 * 타임라인에 들어갈 항목 아이템
 * 덧글에 들어갈 항목 아이템
 */

public class Item {
    public String simpleProfile;
    public String date;
    public int like;
    public String name;
    public String content;

    // 게시글
    public Item(String date, int like, String name, String content){
        this.content = content;
        this.date = date;
        this.like = like;
        this.name = name;
    }
    //덧글
    public Item(String date, String name, String content){
        this.name = name;
        this.date = date;
        this.content = content;
    }
    public String getDate(){
        return this.date;
    }
    public int getLike(){
        return this.like;
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
}
