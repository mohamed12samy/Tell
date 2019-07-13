package controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tell.FireBase;
import com.example.tell.Post;
import com.example.tell.Post_Adapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class post_Controller {

    public static void StorePost(String text, final Context context)
    {
        FireBase.getInstance().initializeMesseageDatabaseReference("post");
        String ID = FireBase.getInstance().getmFirebaseUser().getUid();
        String name = FireBase.getInstance().getmFirebaseUser().getDisplayName();
        Post post = new Post(name,ID, text, 0);
        FireBase.getInstance().getmMesseageDatabaseReference().push().setValue(post)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Can't post now try again later", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public static void retrievePosts(final Context context, final Post_Adapter postAdapter)
    {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("bhbh", "onChildAdded:" + dataSnapshot.getKey());
                // A new comment has been added, add it to the displayed list
                Post post = dataSnapshot.getValue(Post.class);
                //User user = new User();
                //post.setUserName(user.retrievingUserNameByID(post.getmUserID()));
                postAdapter.add(post);
                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("kh", "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Post newPost = dataSnapshot.getValue(Post.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("aa", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("aa", "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Post movedPost = dataSnapshot.getValue(Post.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("aza", "postComments:onCancelled", databaseError.toException());
                Toast.makeText(context, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        FireBase.getInstance().getmMesseageDatabaseReference().addChildEventListener(FireBase.getInstance().getmChildEventListener());
    }
//****************************************************************************************************************//
    public static void retrieveFirstTenPosts(final Context context, final Post_Adapter postAdapter , final ArrayList<Post> posts)
    {
        Query myTopPostsQuery = FirebaseDatabase.getInstance().getReference().child("post").orderByChild("date")
                .limitToFirst(5);
        myTopPostsQuery.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("bhbh", "onChildAdded:" + dataSnapshot.getKey());
                Post post = dataSnapshot.getValue(Post.class);
                postAdapter.add(post);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("kh", "onChildChanged:" + dataSnapshot.getKey());
                Post newPost = dataSnapshot.getValue(Post.class);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("aa", "onChildRemoved:" + dataSnapshot.getKey());
                int position = postAdapter.getPosition(dataSnapshot.getValue(Post.class));
                if(position != -1)
                {
                    postAdapter.remove(dataSnapshot.getValue(Post.class));
                    posts.remove(position);
                }
                postAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("aa", "onChildMoved:" + dataSnapshot.getKey());
                Post movedPost = dataSnapshot.getValue(Post.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Failed to load Posts.",Toast.LENGTH_SHORT).show();
            }

        });


    }
}
