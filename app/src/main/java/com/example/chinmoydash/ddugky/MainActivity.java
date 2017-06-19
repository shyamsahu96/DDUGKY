package com.example.chinmoydash.ddugky;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //TO-DO Implement Firebase Authentication

    /**
     * 1-Create a FirebaseAuth instance.
     * 2-Attach a AuthStateListener to that instance to know when user sign in or sign up or sign out
     */
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public void launchEmployerActivity(View view) {

        Intent intent = new Intent(MainActivity.this, EmployerActivity.class);
        startActivity(intent);

    }

    public void launchCandidateActivity(View view) {

        Intent intent = new Intent(MainActivity.this, CandidateActivity.class);
        startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();

        //We will create auth state listener when it is null and will be done only once
        if (mAuthStateListener == null) {
            mAuthStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    //This firebaseAuth object in the above method holds the users information if anyone has signed in or null.
                    //Get the Firebase user object and check if it is null
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        //Already Signed in
                        finish();
                        startActivity(new Intent(MainActivity.this, UserInfoAct.class));
                    } else {
                        //No user signed in
                    }
                }
            };
        }
        //Always attach the listener outside the if clause other wise it will not attach again once detached.
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Check if auth object is null and then remove listener
        if (mFirebaseAuth != null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }


}
