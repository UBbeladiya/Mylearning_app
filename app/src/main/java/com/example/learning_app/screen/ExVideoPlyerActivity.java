package com.example.learning_app.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;


import com.example.learning_app.R;

//import com.pd.lookatme.LookAtMe;


import java.util.HashMap;
import java.util.Locale;

public class ExVideoPlyerActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    HashMap<String, String> hashMap;
    TextView content_Text,topic;
    ImageButton btnText;
//    LookAtMe lookAtMe;

    String live_url = "https://firebasestorage.googleapis.com/v0/b/learning-d3962.appspot.com/o/video%2Fvideoplayback.mp4?alt=media&token=9004f345-ae1c-4502-847d-a85ead5119db&_gl=1*1qxuo13*_ga*MTcxMDEyNzE5MS4xNjcxMjU4MTUy*_ga_CW55HF8NVT*MTY4NjAwMjE1OC4zOS4xLjE2ODYwMDI0MTUuMC4wLjA.";

    ProgressBar spiiner;
    ImageView fullScreenOp;
    FrameLayout frameLayout;
    VideoView videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_video_plyer);

        Intent intent = getIntent();
        hashMap = (HashMap<String, String>) intent.getSerializableExtra("hashMap");
        Log.e("Code", hashMap.get("Code"));
        getSupportActionBar().setTitle(hashMap.get("Topic"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spiiner = findViewById(R.id.progressBar);
        fullScreenOp = findViewById(R.id.fullScreenOp);
        frameLayout = findViewById(R.id.frameLayout);
        //-----------------------------------------------------------------


        videoPlayer = findViewById(R.id.videoView);


        Uri videoUrl = Uri.parse(live_url);
        videoPlayer.setVideoURI(videoUrl);
        MediaController mc = new MediaController(this);
        videoPlayer.setMediaController(mc);

        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoPlayer.start();
                spiiner.setVisibility(View.GONE);
            }
        });

        fullScreenOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().hide();
                fullScreenOp.setVisibility(View.GONE);
                frameLayout.setLayoutParams(new ConstraintLayout.LayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
                videoPlayer.setLayoutParams(new FrameLayout.LayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)));
            }
        });



        //--------------------------------------------------------------------





        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        btnText =  findViewById(R.id.volume_up);
        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(hashMap.get("description"),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
       /* lookAtMe = findViewById(R.id.lookme);
        lookAtMe.init(this);
        //  lookAtMe.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.greet));
        lookAtMe.setVideoPath(live_url);// to use video from a url
        lookAtMe.start();
        lookAtMe.setLookMe();*/
        content_Text = findViewById(R.id.content_Text);


        topic = findViewById(R.id.topic);
        content_Text.setText(hashMap.get("description"));
        topic.setText(hashMap.get("Topic"));

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        fullScreenOp.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int heightValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,220,getResources().getDisplayMetrics());
        frameLayout.setLayoutParams(new ConstraintLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heightValue)));
        videoPlayer.setLayoutParams(new FrameLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heightValue)));
        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            super.onBackPressed();
        }
    }

    public void PdfView(View view) {
        Intent i = new Intent(ExVideoPlyerActivity.this, CodeViewActivity.class);
        Log.e("Code",hashMap.get("Code"));
        i.putExtra("Code",hashMap.get("Code"));
        i.putExtra("XML",hashMap.get("XML"));
        startActivity(i);

    }

    public void Design_View(View view) {
        Intent i = new Intent(ExVideoPlyerActivity.this, Pdf_View.class);
        Log.e("Web_Url",hashMap.get("weburl"));
        i.putExtra("Web_Url",hashMap.get("weburl"));
        startActivity(i);
    }
}