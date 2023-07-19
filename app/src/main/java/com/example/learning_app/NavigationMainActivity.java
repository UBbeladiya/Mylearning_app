package com.example.learning_app;

import static android.content.ContentValues.TAG;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning_app.databinding.ActivityNavigationMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NavigationMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationMainBinding binding;
    GoogleSignInAccount acct;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    boolean Sign_In;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String User_Name,User_profile;
    FirebaseUser user = null;

    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    long totalUsageTime;
    String sh_date;
    String my_id ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();



        binding = ActivityNavigationMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct == null){
             user = mAuth.getCurrentUser();
        }
        String my_id = mAuth.getCurrentUser().getUid();
        Log.e(" My id is ",my_id);

        setSupportActionBar(binding.appBarNavigationMain.toolbar);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_logout).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if(user != null){
            db.collection("Student")
                    .whereEqualTo("email",user.getEmail())
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    User_Name = document.getData().get("name").toString();
                                    User_profile = document.getData().get("imageUrl").toString();
                                    Log.e("Profrile",User_Name);
                                    Log.e("Profrile",User_profile);

                                }
                                NavigationView  navigationView = findViewById(R.id.nav_view);
                                View headerView = navigationView.getHeaderView(0);
                                TextView usename=headerView.findViewById(R.id.userName);
                                TextView useemali=headerView.findViewById(R.id.userEmali);
                                ImageView imageView=headerView.findViewById(R.id.imageView);

                                    Log.e("Profrile",User_Name);
                                    Log.e("Profrile",User_profile);
                                    usename.setText(User_Name);
                                    useemali.setText(user.getEmail());
                                    String imageUrl = User_profile; // replace with your image URL
                                    Glide.with(getApplicationContext())
                                            .load(imageUrl)
                                            .into(imageView);



                            }else {
                                Log.d(TAG, "signInWithEmail:Fail");
                            }
                        }
                    });
        }else {
            updateNaveheader();
        }

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        sh_date = sh.getString("Current_Date","");
        Log.e("Current_Date", sh_date.toString());

        if (sh_date.isEmpty()) {
            Toast.makeText(this, "Add new date: " + currentDate, Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("Current_Date", currentDate);
            myEdit.apply();
        }else if(!sh_date.equals(currentDate)){
            totalUsageTime=0;
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("Current_Date", currentDate);
            myEdit.apply();
            Toast.makeText(this, "Update new Date", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Stored date is different from the current date", Toast.LENGTH_SHORT).show();
        }
        if (checkAppUsagePermission()) {
            totalUsageTime = getTotalUsageTime();
            Calendar calendar;
            calendar = Calendar.getInstance();
            //date format is:  "Date-Month-Year Hour:Minutes am/pm"
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm a"); //Date and time

//Day of Name in full form like,"Saturday", or if you need the first three characters you have to put "EEE" in the date format and your result will be "Sat".
            SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
            Date date = new Date();
            String dayName = sdf_.format(date);
            Log.e("Day = ", dayName);
            String formattedTime = formatTime(totalUsageTime);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> Data = new HashMap<>();
            Data.put("Hours",formattedTime);
            Data.put("Day",dayName);
            Data.put("Date",currentDate);


            db.collection("Student").document(my_id).collection("Report").
                    document(currentDate).
                    set(Data).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        // inside on failure method we are
                        // displaying a failure message.
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Fail to update the data..", Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            requestAppUsagePermission();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateNaveheader(){

        NavigationView  navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView usename=headerView.findViewById(R.id.userName);
        TextView useemali=headerView.findViewById(R.id.userEmali);
        ImageView imageView=headerView.findViewById(R.id.imageView);
        if(acct !=null){
            usename.setText(acct.getDisplayName());
            useemali.setText(acct.getEmail());
            String imageUrl = acct.getPhotoUrl().toString(); // replace with your image URL
            Glide.with(this)
                    .load(imageUrl)
                    .into(imageView);
        }else {
            Log.e("Profrile",User_Name);
            Log.e("Profrile",User_profile);
            usename.setText(User_Name);
            useemali.setText(user.getEmail());
            String imageUrl = User_profile; // replace with your image URL
            Glide.with(this)
                    .load(imageUrl)
                    .into(imageView);
        }



    }

    public void LogOut(MenuItem item) {

        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {

                // Create the object of AlertDialog Builder class
                AlertDialog.Builder builder = new AlertDialog.Builder(NavigationMainActivity.this);

                // Set the message show for the Alert time
                builder.setMessage("Do you want to exit ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // When the user click yes button then app will close
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                    finish();
                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();


            }
        });

        mAuth.signOut();


    }
//=========================================================================
    private boolean checkAppUsagePermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());
        return (mode == AppOpsManager.MODE_ALLOWED);
    }

    private void requestAppUsagePermission() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }

    private long getTotalUsageTime() {
        long totalUsageTime = 0;

        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1); // 24 hours ago
        Log.e("calendar",calendar.toString());
        long startTime = calendar.getTimeInMillis();

        long endTime = System.currentTimeMillis();
        List<UsageStats> appUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);


        for (UsageStats usageStats : appUsageStats) {
            if (usageStats.getPackageName().equals(getPackageName())) {
                totalUsageTime += usageStats.getTotalTimeInForeground();
            }
        }


        return totalUsageTime;
    }

    private String formatTime(long timeInMillis) {
        long seconds = (timeInMillis / 1000) % 60;
        long minutes = (timeInMillis / (1000 * 60)) % 60;
        long hours = (timeInMillis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

/*
 Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                    finish()
 */