package com.example.learning_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning_app.Model.Firebase_model;
import com.example.learning_app.R;
import com.example.learning_app.screen.SubjectTopicActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

    public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.MyClass> {
    Context context;
    ArrayList<Firebase_model> data;

    public UnitAdapter(Context context, ArrayList<Firebase_model> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public MyClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_itam, parent, false);
        MyClass myClass = new MyClass(view);

        return myClass;
    }

    @Override
    public void onBindViewHolder(@NonNull MyClass holder, @SuppressLint("RecyclerView") int position) {
        holder.text.setText(data.get(position).getName());
        final SwipeDetector swipeDetector = new SwipeDetector();
        holder.linear.setOnTouchListener(swipeDetector);
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("My Id", data.get(position).getId());
                if (swipeDetector.swipeDetected()){
                    // do the onSwipe action
                    Intent i = new Intent(context, SubjectTopicActivity.class);
                    i.putExtra("Unit_Id", data.get(position).getId());
                    i.putExtra("Topic", (ArrayList) data.get(position).getTopic());
                    view.getContext().startActivity(i);
                } else {
                    // do the onItemLongClick action
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyClass extends RecyclerView.ViewHolder {
        TextView text;
        LinearLayout linear;

        //  ImageView image;
        public MyClass(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textView);
            linear = itemView.findViewById(R.id.linear);
            // image = itemView.findViewById(R.id.folder);

        }
    }
}

