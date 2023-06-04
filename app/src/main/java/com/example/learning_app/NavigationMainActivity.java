package com.example.learning_app;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
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
}

/*
 Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                    finish()
 */