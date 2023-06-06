package com.example.learning_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Splsh_screen extends AppCompatActivity {
    FirebaseAuth mAuth;


    private static final int PERMISSION_REQUEST_CODE = 123;
    private TextView tvUsageTime;
    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    long totalUsageTime;
    String sh_date;

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