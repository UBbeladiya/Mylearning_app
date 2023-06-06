package com.example.learning_app.ui.home;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.learning_app.MainActivity;
import com.example.learning_app.databinding.FragmentHomeBinding;
import com.example.learning_app.screen.Registration;
import com.example.learning_app.screen.UnitListActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Button btn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CardView cardview = binding.cardview;
     //   btn = binding.btn;
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UnitListActivity.class);
                startActivity(intent);
            }
        });
        //TextView, EditText, ImageView, ListView, ScrollView, CardView, GridView, VideoView
       /*  btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseFirestore db = FirebaseFirestore.getInstance();

                 Map<String, Object> Data = new HashMap<>();
              //   Data.put("Code","");
                 Data.put("Code","<?xml version=\"1.0\" encoding=\"utf-8\"?><!-- XML implementation of Card Layout --> <androidx.cardview.widget.CardView \txmlns:android=\"http://schemas.android.com/apk/res/android\" \txmlns:app=\"http://schemas.android.com/apk/res-auto\" \tandroid:layout_width=\"match_parent\" \tandroid:layout_height=\"120dp\" \tandroid:layout_gravity=\"center\" \tandroid:layout_margin=\"5dp\" \tapp:cardCornerRadius=\"5dp\" \tapp:cardElevation=\"5dp\">  \t<LinearLayout \t\tandroid:layout_width=\"match_parent\" \t\tandroid:layout_height=\"wrap_content\" \t\tandroid:orientation=\"vertical\">  \t\t<ImageView \t\t\tandroid:id=\"@+id/idIVcourse\" \t\t\tandroid:layout_width=\"100dp\" \t\t\tandroid:layout_height=\"100dp\" \t\t\tandroid:layout_gravity=\"center\" \t\t\tandroid:src=\"@mipmap/ic_launcher\" />  \t\t<TextView \t\t\tandroid:id=\"@+id/idTVCourse\" \t\t\tandroid:layout_width=\"match_parent\" \t\t\tandroid:layout_height=\"wrap_content\" \t\t\tandroid:text=\"@string/app_name\" \t\t\tandroid:textAlignment=\"center\" /> \t</LinearLayout> </androidx.cardview.widget.CardView>");
                 Data.put("PDF", "https://www.tutorialspoint.com/android/android_tutorial.pdf");
                 Data.put("Topic", "GridView");
                 Data.put("Unit", "bMXXZtwLkkQwEpur43pd");
                 Data.put("description",  "A GridView is a type of AdapterView that displays items in a two-dimensional scrolling grid. Items are inserted into this grid layout from a database or from an array. The adapter is used for displaying this data, setAdapter() method is used to join the adapter with GridView. The main function of the adapter in GridView is to fetch data from a database or array and insert each piece of data in an appropriate item that will be displayed in GridView. This is what the GridView structure looks like.");
                 Data.put("weburl", "https://www.geeksforgeeks.org/gridview-in-android-with-example/");

                 db.collection("material")
                         .add(Data)
                         .addOnSuccessListener(new OnSuccessListener() {
                             @Override
                             public void onSuccess(Object o) {
                                 Log.d(TAG, "DocumentSnapshot successfully written!");
                                 Toast.makeText(getActivity(), "successfully", Toast.LENGTH_SHORT).show();
                             }
                         })
                         .addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 Log.d(TAG, "Error writing document", e);
                             }
                         });
             }
         });*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}