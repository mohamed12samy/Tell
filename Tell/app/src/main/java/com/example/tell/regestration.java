package com.example.tell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import controllers.post_Controller;

public class regestration extends AppCompatActivity {

    private LinearLayout signin;
    private LinearLayout signup;

    private Button signUp;
    private Button signIn;


    private EditText Email;
    private EditText Password;

    private String email, password;

    //signup***/
    private EditText Name;
    private EditText Phone;
    //************/

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);

        signin = findViewById(R.id.signInLayout);
        signup = findViewById(R.id.signUpLayout);

        signIn = findViewById(R.id.SignIN);
        signUp = findViewById(R.id.signUP);

        Name = findViewById(R.id.name);
        Phone = findViewById(R.id.phoneNum);

        FireBase.getInstance().initialize();

        mAuth = FireBase.getInstance().getmFirebaseAuth();


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Email = findViewById(R.id.emailSignin);
                Password = findViewById(R.id.passwordSignin);

                email = Email.getText().toString();
                password = Password.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(regestration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithEmail:success");
                                    FireBase.getInstance().setmFirebaseUser(mAuth.getCurrentUser());
                                    GetInside();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //         updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Email = findViewById(R.id.emailSignup);
                Password = findViewById(R.id.passwordSignup);


                mAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                        .addOnCompleteListener(regestration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "createUserWithEmail:success");
                                    FireBase.getInstance().setmFirebaseUser(mAuth.getCurrentUser());

                                    User mUser = new User(FireBase.getInstance().getmFirebaseUser());
                                    mUser.setmUserNme(Name.getText().toString());
                                    mUser.setmPhone(Phone.getText().toString());
                                    mUser.StoreUsers(mUser);

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(Name.getText().toString())
                                            .build();

                                    FireBase.getInstance().getmFirebaseUser().updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("TAG", "User profile updated.");
                                                    }
                                                }
                                            });


                                    GetInside();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });

    }

    private void GetInside() {
        Intent i = new Intent(regestration.this, Home.class);
        startActivity(i);
        finish();
    }

    public void GoToSignUp(View view) {
        signin.setVisibility(View.GONE);
        signup.setVisibility(View.VISIBLE);
    }
}
