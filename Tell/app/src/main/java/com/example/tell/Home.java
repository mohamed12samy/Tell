package com.example.tell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controllers.post_Controller;


public class Home extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 2;
    public static final int RC_SIGN_IN = 1;

    private ImageButton imgB;

    private Post_Adapter postAdapter;
    private EditText mPostText;
    private Button mPostButton;

    private ArrayList <Post> posts = new ArrayList<>();


    private User mUser;

    private void initializingAdapter() {
        postAdapter = new Post_Adapter(getApplicationContext(),R.layout.post,posts);
        ListView listView = findViewById(R.id.postList);
        listView.setAdapter(postAdapter);
    }

    private void OpenImageDialogue() {
        imgB = (ImageButton) findViewById(R.id.imageButton);
        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                //here we use startActivityForResult instead of startActivity because here there is an returning value (result) which is the photo and will use it in onActivityResult
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

            }
        });
    }

    //Enable And Disable post button
    private void Enable_DisablePostButton() {
        mPostButton = (Button) findViewById(R.id.PostB);

        mPostText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { //disabling and enabling the send button under some conditions
                if (charSequence.toString().trim().length() > 0) {
                    mPostButton.setEnabled(true);
                } else {
                    mPostButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void AuthStateListener() {
        FireBase.getInstance().setmAuthStateListener(
                new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FireBase.getInstance().setmFirebaseUser(firebaseAuth.getCurrentUser());
                        if (FireBase.getInstance().getmFirebaseUser() != null) {
                            post_Controller.retrieveFirstTenPosts(Home.this , postAdapter,posts);
                        } else //if user is not signed in or he just signed out
                        {
                            Intent i = new Intent(Home.this, regestration.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }
        );
    }

    private void SigningOUT()  {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Home.this, "Signing Out completed successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        mPostText = findViewById(R.id.EditTextpost);

       // FireBase.getInstance().initialize();
        FireBase.getInstance().initializeMesseageDatabaseReference("post");

        initializingAdapter();
        OpenImageDialogue();
        // Enable post button when there's text in editText
        Enable_DisablePostButton();

        AuthStateListener();

    }
    //CREATING THE MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    //ACTION THAT TAKEN WHEN CERTAIN SELECTION IS CLICKED
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                SigningOUT();
                break;
            case R.id.sittings_menu:
                Intent i = new Intent(Home.this, Setting.class);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    // Send button sends a message and clears the EditText /**onclick method**/
    public void SubmitPost(View view) {
        String text = mPostText.getText().toString();
        post_Controller.StorePost(text , Home.this);
        mPostText.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        FireBase.getInstance().getmFirebaseAuth().addAuthStateListener(FireBase.getInstance().getmAuthStateListener()); //ensure authentication when resuming the app
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (FireBase.getInstance().getmAuthStateListener() != null)
            FireBase.getInstance().getmFirebaseAuth().removeAuthStateListener(FireBase.getInstance().getmAuthStateListener()); //remove authentication when pausing the app
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(Home.this, "Signing IN completed successfully", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(Home.this, "Canceled!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
                Toast.makeText(Home.this , "PHOTOSELECTED" ,Toast.LENGTH_LONG).show();
                Uri selectedImageUri = data.getData();
                final StorageReference photoRef = FireBase.getInstance().getmChatPhotosStorageReference().child(selectedImageUri.getLastPathSegment());
                photoRef.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshotthe /*key to getting thr\e URL of the file that was just sent to the storage*/) {
                        photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri dlUri = uri;

                            }
                        });
                    }
                });
            }
        }
    }
}
