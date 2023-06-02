package com.example.learning_app.screen;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.learning_app.Adapter.UnitAdapter;
import com.example.learning_app.Model.Firebase_model;
import com.example.learning_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UnitListActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView Unit_recyclerView;
    ArrayList<Firebase_model> data = new ArrayList<Firebase_model>();
    ArrayList<String>  doc_name = new ArrayList<>();
    UnitAdapter firebade_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);
        Unit_recyclerView =findViewById(R.id.Unit);
        db.collection("Unit")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Map> listItems =new ArrayList<Map>();
                        Map<String, String> mapItems = new HashMap<String, String>();

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("Data Type",document.getData().get("Topic").getClass().getSimpleName());
                                Firebase_model firebase_model = new Firebase_model(document.getId(), (String) document.getData().get("Name"), (ArrayList) document.getData().get("Topic"));
                                data.add(firebase_model);
                            }
                            firebade_recyclerView =new UnitAdapter(UnitListActivity.this,data);
                            Unit_recyclerView.setAdapter(firebade_recyclerView);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }




}