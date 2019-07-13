package com.example.tell;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FireBase {
    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMesseageDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser mFirebaseUser;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;

    private static FireBase mfireBase = new FireBase();
    private FireBase() {

    }

    public static synchronized FireBase getInstance() {
        return mfireBase;
    }

    public void initialize() {
        //initializing firebase
        this.mFirebaseDatabase = FirebaseDatabase.getInstance(); /*main point to get access to our database*/ //GETINSTANCE instead of new (getting existing obj rather than creating a new one singleton pattern)
        this.mFirebaseAuth = FirebaseAuth.getInstance();
        this.mFirebaseStorage = FirebaseStorage.getInstance();
        this.mChatPhotosStorageReference = this.mFirebaseStorage.getReference().child("users_photos"); //binding specific location

    }

    public void initializeMesseageDatabaseReference(String parent) {
        this.mMesseageDatabaseReference = this.mFirebaseDatabase.getReference().child(parent); //binding specific location
    }

    public DatabaseReference getmMesseageDatabaseReference() {
        return mMesseageDatabaseReference;
    }

    public FirebaseAuth.AuthStateListener getmAuthStateListener() {
        return mAuthStateListener;
    }

    public ChildEventListener getmChildEventListener() {
        return mChildEventListener;
    }

    public void setmChildEventListener(ChildEventListener mChildEventListener) {
        this.mChildEventListener = mChildEventListener;
    }

    public FirebaseAuth getmFirebaseAuth() {
        return mFirebaseAuth;
    }

    public void setmAuthStateListener(FirebaseAuth.AuthStateListener mAuthStateListener) {
        this.mAuthStateListener = mAuthStateListener;
    }

    public FirebaseUser getmFirebaseUser() {
        return mFirebaseUser;
    }

    public void setmFirebaseUser(FirebaseUser mFirebaseUser) {
        this.mFirebaseUser = mFirebaseUser;
    }

    public StorageReference getmChatPhotosStorageReference() {
        return mChatPhotosStorageReference;
    }

    public void setmChatPhotosStorageReference(StorageReference mChatPhotosStorageReference) {
        this.mChatPhotosStorageReference = mChatPhotosStorageReference;
    }
}
