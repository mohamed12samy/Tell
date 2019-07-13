package com.example.tell;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//model
public class Post {
    private String mPostText;
    private String mUserID;
    //private String userphotoUrl;
    private int userphotoUrl;
    private String date;
    private String UserName;

    public Post() {
    }

    public Post(String Name,String userID, String postT, int userPhotoURL) {

        mUserID = userID;
        mPostText = postT;
        userphotoUrl = userPhotoURL;
        UserName = Name;
        setDate();
    }

    /* public String getmUserName() {
         return "sami";
     }
 */
    public String getmPostText() {
        return mPostText;
    }

    public int getUserphotoUrl() {
        return userphotoUrl;
    }

    public String getmUserID() {
        return mUserID;
    }

    public String getDate() {
        return date;
    }

    private void setDate() {
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        this.date = df.format(current);
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
