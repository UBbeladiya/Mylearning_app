package com.example.learning_app.Fragment_page;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.learning_app.R;

import io.github.kbiakov.codeview.CodeView;

public class XML_CodeView extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_xml_codeview, container, false);
        Bundle bundle = getArguments();
        CodeView codeView = (CodeView) view.findViewById(R.id.xml_code_view);


        String sTitle=getArguments().getString("title");


        Log.e("Code", sTitle);

        codeView.setCode(sTitle);


        return view;
    }
}
