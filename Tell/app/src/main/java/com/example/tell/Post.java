package com.example.tell;

public class Post {
    private String mUserName ;
    private String mPostText ;
    //private String userphotoUrl;
    private int userphotoUrl;

    public Post(){}

    public Post (String name ,String postT ,int userPhotoURL){

        mUserName = name;
        mPostText = postT;
        userphotoUrl = userPhotoURL;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPostText() {
        return mPostText;
    }

    public void setmPostText(String mPostText) {
        this.mPostText = mPostText;
    }

    public int getUserphotoUrl() {
        return userphotoUrl;
    }

    public void setUserphotoUrl(int userphotoUrl) {
        this.userphotoUrl = userphotoUrl;
    }
}
