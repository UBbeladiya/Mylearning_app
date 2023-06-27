package com.example.learning_app.ui.gallery;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.learning_app.Model.WeekData;
import com.example.learning_app.R;
import com.example.learning_app.databinding.FragmentGalleryBinding;
import com.example.learning_app.screen.ExVideoPlyerActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private static final int MAX_X_VALUE = 7;
    private static final int MAX_Y_VALUE = 50;
    private static final int MIN_Y_VALUE = 5;
    private static final String SET_LABEL = "Hours";
    String[] DAYS = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelName;
    ArrayList<WeekData> weekDataArrayList = new ArrayList<>();

    BarChart chart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        chart = binding.fragmentVerticalbarchartChart;
        barEntryArrayList = new ArrayList<>();
        labelName = new ArrayList<>();
        fillWeekDay();




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void fillWeekDay(){
        weekDataArrayList.clear();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String my_id = mAuth.getCurrentUser().getUid();
        Log.e(" My id is ",my_id);
        db.collection("Student").document(my_id).collection("Report")
                .orderBy("Date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.e("Task",task.getResult().toString());
                        try {
                            if (task.isSuccessful()) {
                                int i=0;
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    if (7>task.getResult().size()){
                                        Log.e("size", String.valueOf(task.getResult().size()));
                                        String currentString = document.getData().get("Hours").toString();
                                        String[] sep_hours = currentString.split(":");
                                        String str_hours =  sep_hours[0]+"."+sep_hours[1];
                                        float hours = Float.parseFloat(str_hours);
                                        Log.e("hours", String.valueOf(hours));
                                        weekDataArrayList.add(0,new WeekData(document.getData().get("Date").toString(),hours));
                                    }

                                    if (7<task.getResult().size() ){
                                        Log.e("size", String.valueOf(task.getResult().size()));
                                        String currentString = document.getData().get("Hours").toString();
                                        String[] sep_hours = currentString.split(":");
                                        String str_hours =  sep_hours[0]+"."+sep_hours[1];
                                        float hours = Float.parseFloat(str_hours);
                                        Log.e("hours", String.valueOf(hours));
                                        weekDataArrayList.add(0,new WeekData(document.getData().get("Date").toString(),hours));
                                        i++;
                                        if (i == 7){
                                            break;
                                        }

                                    }


                                }
                                configureChartAppearance();

                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }catch (Exception e){
                            Log.e("Error getting documents", task.getException().toString());
                        }

                    }
                });

    }

    private void configureChartAppearance() {
        for (int i =0 ; i< weekDataArrayList.size(); i++){
            String week = weekDataArrayList.get(i).getWeekday();
            float hours = weekDataArrayList.get(i).getHours();
           float f_hours=  Float.parseFloat(new DecimalFormat("##.##").format(hours));
           Log.e("f_hours", String.valueOf(f_hours));
            barEntryArrayList.add(new BarEntry(i,f_hours));
            labelName.add(week);
        }
       /* BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);*/
        BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"Working Hours");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
     //   barDataSet.setBarBorderWidth(0.9f);

        Description description = new Description();
        description.setText("Week Day");
        chart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        chart.setData(barData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelName));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelName.size());
        xAxis.setLabelRotationAngle(270);
        chart.setFitBars(true);
        chart.animateY(4000);


        chart.invalidate();
    }

}
/*


 db.collection("Report")
         .orderBy("Date", Query.Direction.DESCENDING)
         .get()
         .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
@Override
public void onComplete(@NonNull Task<QuerySnapshot> task) {
        Log.e("Task",task.getResult().toString());
        try {
        if (task.isSuccessful()) {
        for (QueryDocumentSnapshot document : task.getResult()) {
        DAYS.
        Log.e("Date",document.getData().get("Date").toString());
        Log.e("Day",document.getData().get("Day").toString());
        }

        } else {
        Log.d(TAG, "Error getting documents: ", task.getException());
        }
        }catch (Exception e){
        Log.e("Error getting documents", task.getException().toString());
        }

        }
        });*/
