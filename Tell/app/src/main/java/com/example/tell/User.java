package com.example.tell;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class User {
    private String mUserNme;
    private String Email;
    private String mUsrePhoto;
    private String mBio;
    private String ID;
    private String mPhone;

    public User() {}

    public User(FirebaseUser firebaseUser) {
        this.mUserNme = firebaseUser.getDisplayName();
        this.Email = firebaseUser.getEmail();
        this.ID = firebaseUser.getUid();
    }

    public void cleaningUP() {
        this.Email = "";
        this.ID = "";
        this.mUserNme = "";
        this.mBio = "";
        this.mUsrePhoto = "";
    }

    public String getmUserNme() {
        return mUserNme;
    }

    public String getEmail() {
        return Email;
    }

    public String getmUsrePhoto() {
        return mUsrePhoto;
    }

    public String getmBio() {
        return mBio;
    }

    public void setmUserNme(String mUserNme) {
        this.mUserNme = mUserNme;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setmUsrePhoto(String mUsrePhoto) {
        this.mUsrePhoto = mUsrePhoto;
    }

    public void setmBio(String mBio) {
        this.mBio = mBio;
    }

    /*public String getID() {
        return ID;
    }*/

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public void StoreUsers(User user) {

        FireBase.getInstance().initializeMesseageDatabaseReference("user");
        FireBase.getInstance().getmMesseageDatabaseReference().child(this.ID).setValue(user);
    }

 public String retrievingUserNameByID(String ID ) {
     final String[] Name = new String[1];
        FireBase.getInstance().initializeMesseageDatabaseReference("user");
     FireBase.getInstance().getmMesseageDatabaseReference().child(ID).child("mUserName");
     ValueEventListener valEv = new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             Name[0] = dataSnapshot.getValue(String.class);
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {
             Log.e("ERORR happened", "The read failed: " + databaseError.getDetails());

         }
     };
     FireBase.getInstance().getmMesseageDatabaseReference().addListenerForSingleValueEvent(valEv);
        //Log.i("asdfr","dddd");
    return Name[0];
 }
}