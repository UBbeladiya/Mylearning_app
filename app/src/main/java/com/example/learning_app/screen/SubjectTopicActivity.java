package com.example.learning_app.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learning_app.Adapter.TopicAdapter;
import com.example.learning_app.Adapter.UnitAdapter;
import com.example.learning_app.Model.Firebase_model;
import com.example.learning_app.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SubjectTopicActivity extends AppCompatActivity {
    ArrayList TopicList;
    String Unit_Id;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView Topic_recyclerView;
    TopicAdapter topicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_topic);
        Intent intent = getIntent();
        Topic_recyclerView = findViewById(R.id.Topic_Unit);

        TopicList= intent.getStringArrayListExtra( "Topic" );
        Unit_Id = intent.getStringExtra( "Unit_Id" );

        Log.e("My Array List", TopicList.toString());
        topicAdapter =new TopicAdapter(SubjectTopicActivity.this,TopicList,Unit_Id);
        Topic_recyclerView.setAdapter(topicAdapter);


    }

}