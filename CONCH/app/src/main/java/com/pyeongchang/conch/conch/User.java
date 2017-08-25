package com.pyeongchang.conch.conch;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by GAYEON on 2017-08-17.
 */

public class User {
    private String userName;
    private String id;
    private String pwd;
    private String nation;
    private String info="Hello";

    private int level=1;
    private int score=0;
    private int photo=R.drawable.horang;
    private boolean isRunner=false;
    private int distance=0; //If isRunner is true, the value of distance is valid;
    private List<String> communityList=new List<String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends String> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends String> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public String get(int i) {
            return null;
        }

        @Override
        public String set(int i, String s) {
            return null;
        }

        @Override
        public void add(int i, String s) {

        }

        @Override
        public String remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<String> subList(int i, int i1) {
            return null;
        }
    }; // Community number is limited to 5;
    public User(String userName, String id, String pwd, String nation){
        this.userName=userName;
        this.id = id;
        this.pwd=pwd;
        this.nation=nation;
    }
    public User(String userName, String id, String nation){
        this.userName=userName;
        this.id = id;
        this.nation=nation;
    }
    public User(){

    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public void setRunner(boolean runner) {
        isRunner = runner;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getUserName() {

        return userName;
    }

    public String getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    public String getNation() {
        return nation;
    }

    public String getInfo() {
        return info;
    }

    public int getScore() {
        return score;
    }

    public int getPhoto() {
        return photo;
    }

    public boolean isRunner() {
        return isRunner;
    }

    public int getDistance() {
        return distance;
    }

    public List<String> getCommunityList() {
        return communityList;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
