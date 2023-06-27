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
   // Button btn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CardView cardview = binding.cardview;
      //  btn = binding.btn;
        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UnitListActivity.class);
                startActivity(intent);
            }
        });
        //TextView, EditText, ImageView, ListView, ScrollView, CardView, GridView, VideoView
        /* btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseFirestore db = FirebaseFirestore.getInstance();

                 Map<String, Object> Data = new HashMap<>();
              //   Data.put("Code","");
                Data.put("Code","package com.gtappdevelopers.kotlingfgproject;\n" +
                        "\n" +
                        "import android.net.Uri;\n" +
                        "import android.os.Bundle;\n" +
                        "import android.widget.MediaController;\n" +
                        "import android.widget.VideoView;\n" +
                        "import androidx.appcompat.app.AppCompatActivity;\n" +
                        "\n" +
                        "public class MainActivity extends AppCompatActivity {\n" +
                        "\n" +
                        "\t// on below line we are creating variables.\n" +
                        "\tprivate VideoView videoView;\n" +
                        "\t\n" +
                        "\t// Your Video URL\n" +
                        "\tString videoUrl = \"Paste Your Video URL Here\";\n" +
                        "\n" +
                        "\t@Override\n" +
                        "\tprotected void onCreate(Bundle savedInstanceState) {\n" +
                        "\t\tsuper.onCreate(savedInstanceState);\n" +
                        "\t\tsetContentView(R.layout.activity_main);\n" +
                        "\n" +
                        "\t\t// on below line we are initializing our variables.\n" +
                        "\t\tvideoView = findViewById(R.id.idVideoView);\n" +
                        "\n" +
                        "\t\t// Uri object to refer the\n" +
                        "\t\t// resource from the videoUrl\n" +
                        "\t\tUri uri = Uri.parse(videoUrl);\n" +
                        "\n" +
                        "\t\t// sets the resource from the\n" +
                        "\t\t// videoUrl to the videoView\n" +
                        "\t\tvideoView.setVideoURI(uri);\n" +
                        "\n" +
                        "\t\t// creating object of\n" +
                        "\t\t// media controller class\n" +
                        "\t\tMediaController mediaController = new MediaController(this);\n" +
                        "\n" +
                        "\t\t// sets the anchor view\n" +
                        "\t\t// anchor view for the videoView\n" +
                        "\t\tmediaController.setAnchorView(videoView);\n" +
                        "\n" +
                        "\t\t// sets the media player to the videoView\n" +
                        "\t\tmediaController.setMediaPlayer(videoView);\n" +
                        "\n" +
                        "\t\t// sets the media controller to the videoView\n" +
                        "\t\tvideoView.setMediaController(mediaController);\n" +
                        "\n" +
                        "\t\t// starts the video\n" +
                        "\t\tvideoView.start();\n" +
                        "\n" +
                        "\t}\n" +
                        "}\n"+"\n" );

                 Data.put("PDF", "https://firebasestorage.googleapis.com/v0/b/learning-d3962.appspot.com/o/video%2Fvideoplayback.mp4?alt=media&token=bb39d544-5a67-49e2-afc6-7ea4cae40011");
                 Data.put("Topic", "VideoView");
                 Data.put("Unit", "WgZMwLp2ecnR0J6FZLYB");
                // Data.put("XML", "");
                 Data.put("XML","<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                         "<RelativeLayout\n" +
                         "\txmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                         "\txmlns:tools=\"http://schemas.android.com/tools\"\n" +
                         "\tandroid:id=\"@+id/idRLContainer\"\n" +
                         "\tandroid:layout_width=\"match_parent\"\n" +
                         "\tandroid:layout_height=\"match_parent\"\n" +
                         "\tandroid:orientation=\"vertical\"\n" +
                         "\ttools:context=\".MainActivity\">\n" +
                         "\n" +
                         "\t<!--on below line we are creating a simple text view-->\n" +
                         "\t<TextView\n" +
                         "\t\tandroid:id=\"@+id/idTVHeading\"\n" +
                         "\t\tandroid:layout_width=\"match_parent\"\n" +
                         "\t\tandroid:layout_height=\"wrap_content\"\n" +
                         "\t\tandroid:layout_margin=\"20dp\"\n" +
                         "\t\tandroid:gravity=\"center\"\n" +
                         "\t\tandroid:padding=\"10dp\"\n" +
                         "\t\tandroid:text=\"Video View in Android\"\n" +
                         "\t\tandroid:textAlignment=\"center\"\n" +
                         "\t\tandroid:textColor=\"@color/black\"\n" +
                         "\t\tandroid:textSize=\"20sp\"\n" +
                         "\t\tandroid:textStyle=\"bold\" />\n" +
                         "\n" +
                         "\t<!-- adding VideoView to the layout -->\n" +
                         "\t<VideoView\n" +
                         "\t\tandroid:id=\"@+id/idVideoView\"\n" +
                         "\t\tandroid:layout_width=\"match_parent\"\n" +
                         "\t\tandroid:layout_height=\"wrap_content\"\n" +
                         "\t\tandroid:layout_below=\"@id/idTVHeading\"\n" +
                         "\t\tandroid:layout_centerInParent=\"true\" />\n" +
                         "\t\n" +
                         "</RelativeLayout>\n"

                 );

                 Data.put("description", "VideoView is a UI widget that is used to display video content to the users within android applications. We can add video in this video view from different resources such as a video stored on the user device, or a video from a server. In this article, we will take a look at How to use Video View in the android application. A sample video is given below to get an idea about what we are going to do in this article.");
                 Data.put("weburl", "https://www.geeksforgeeks.org/videoview-in-android/");

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