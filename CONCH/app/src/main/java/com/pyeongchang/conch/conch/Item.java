package com.pyeongchang.conch.conch;

/**
 * Created by hyojung on 2017-07-12.
 * 타임라인에 들어갈 항목 아이템
 */

public class Item {
    public String simpleProfile;
    public String content;

    public Item(String content){
        this.content = content;
    }

    public String getSimpleProfile(){
        return this.simpleProfile;
    }
    public String getContent(){
        return this.content;
    }
}
