package com.example.learning_app.Adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning_app.Model.material_Model;
import com.example.learning_app.R;

import com.example.learning_app.screen.ExVideoPlyerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyClass>  implements GestureDetector.OnGestureListener{
    Context context;
    ArrayList topicList;
    String unit_Id;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static ArrayList<material_Model> data = new ArrayList<material_Model>();



    public TopicAdapter(Context context, ArrayList topicList, String unit_Id) {
        this.topicList = topicList;
        this.context = context;
        this.unit_Id = unit_Id;

    }

    @NonNull
    @Override
    public TopicAdapter.MyClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_itam, parent, false);
        TopicAdapter.MyClass myClass = new TopicAdapter.MyClass(view);

        return myClass;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicAdapter.MyClass holder, @SuppressLint("RecyclerView") int position) {
        holder.text.setText(topicList.get(position).toString());

        final SwipeDetector swipeDetector = new SwipeDetector();
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Pos",topicList.get(position).toString()+"= " + unit_Id);
                db.collection("material")
                        .whereEqualTo("Unit", unit_Id)
                        .whereEqualTo("Topic",topicList.get(position).toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                Log.e("Task",task.getResult().toString());
                                try {
                                    if (task.isSuccessful()) {
                                        Log.e("Task=2",task.getResult().getDocuments().toString());
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.e("Data Type", document.getData().get("PDF").getClass().getSimpleName());
                                            Log.e("Data", document.getData().get("Code").toString());
                                            HashMap<String,String> detail = new HashMap<String, String>();
                                            detail.put("Code",document.getData().get("Code").toString());
                                            detail.put("PDF",document.getData().get("PDF").toString());
                                            detail.put("Topic",document.getData().get("Topic").toString());
                                            detail.put("Unit",document.getData().get("Unit").toString());
                                            detail.put("description",document.getData().get("description").toString());
                                            detail.put("weburl",document.getData().get("weburl").toString());
                                            detail.put("XML",document.getData().get("XML").toString());


                                            Intent i = new Intent(context, ExVideoPlyerActivity.class);
                                            i.putExtra("hashMap",detail);
                                            view.getContext().startActivity(i);
                                            Log.e("Code", String.valueOf(data.size()));

                                        }


                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }catch (Exception e){
                                    Log.e("Error getting documents", task.getException().toString());
                                }

                            }
                        });

            }
        });

    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    public class MyClass extends RecyclerView.ViewHolder {
        TextView text;
        LinearLayout linear;

        public MyClass(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textView);
            linear = itemView.findViewById(R.id.linear);
        }
    }
}
