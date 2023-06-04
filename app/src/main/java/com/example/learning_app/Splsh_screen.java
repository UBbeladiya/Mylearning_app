package com.example.learning_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splsh_screen extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splsh_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splsh_screen);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if(user != null){
            Intent i = new Intent(getApplicationContext(), NavigationMainActivity.class);
            Log.e("User",user.getUid());
            // on below line we are
            // starting a new activity.
            startActivity(i);
            finish();
        }else if(acct!=null) {

            Log.e("acct",acct.getEmail());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // on below line we are
                        // creating a new intent
                        Intent i = new Intent(getApplicationContext(), NavigationMainActivity.class);

                        // on below line we are
                        // starting a new activity.
                        startActivity(i);

                        // on the below line we are finishing
                        // our current activity.
                        finish();

                    }
                }, 2000);

        }
        else{
            Intent i = new Intent(getApplicationContext(), MainActivity.class);

            // on below line we are
            // starting a new activity.
            startActivity(i);
            finish();
        }
        // on below line we are calling handler to run a task
        // for specific time interval


    }
}