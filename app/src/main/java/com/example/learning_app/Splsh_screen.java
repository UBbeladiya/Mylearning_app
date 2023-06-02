package com.example.learning_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Splsh_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splsh_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splsh_screen);

        // on below line we are calling handler to run a task
        // for specific time interval
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
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
        }else{
            Intent i = new Intent(getApplicationContext(), MainActivity.class);

            // on below line we are
            // starting a new activity.
            startActivity(i);
            finish();
        }

    }
}