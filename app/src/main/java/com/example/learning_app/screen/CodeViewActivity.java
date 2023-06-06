package com.example.learning_app.screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;

import com.example.learning_app.Fragment_page.XML_CodeView;
import com.example.learning_app.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.kbiakov.codeview.CodeView;

public class CodeViewActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar1;
    String java_code;
    String xml_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_view);
        Intent intent = getIntent();
        java_code = intent.getStringExtra("Code");
        xml_code = intent.getStringExtra("XML");

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        toolbar1=findViewById(R.id.toolbar1);
        toolbar1.setTitle("Code View");
        toolbar1.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar1);

        // Initialize array list
        ArrayList<String> arrayList=new ArrayList<>(0);

        // Add title in array list
        arrayList.add("XML");
        arrayList.add("JAVA");


        // Setup tab layout
        tabLayout.setupWithViewPager(viewPager);

        // Prepare view pager
        prepareViewPager(viewPager,arrayList);

    }

    private void prepareViewPager(ViewPager viewPager, ArrayList<String> arrayList) {
        // Initialize main adapter
        MainAdapter adapter=new MainAdapter(getSupportFragmentManager());

        // Initialize main fragment
        XML_CodeView mainFragment=new XML_CodeView();

        // Use for loop
        for(int i=0;i<arrayList.size();i++)
        {
            // Initialize bundle
            Bundle bundle=new Bundle();

           if (i == 0){
               bundle.putString("title",xml_code);
           }
           if (i == 1){
               bundle.putString("title",java_code);
           }


            // set argument
            mainFragment.setArguments(bundle);

            // Add fragment
            adapter.addFragment(mainFragment,arrayList.get(i));
            mainFragment=new XML_CodeView();
        }
        // set adapter
        viewPager.setAdapter(adapter);
    }

    private class MainAdapter extends FragmentPagerAdapter {
        // Initialize arrayList
        ArrayList<Fragment> fragmentArrayList= new ArrayList<>();
        ArrayList<String> stringArrayList=new ArrayList<>();


        // Create constructor
        public void addFragment(Fragment fragment,String s)
        {
            // Add fragment
            fragmentArrayList.add(fragment);
            // Add title
            stringArrayList.add(s);
        }

        public MainAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // return fragment position
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            // Return fragment array list size
            return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            // Initialize drawable


            // set bound

            // Initialize spannable image
            SpannableString spannableString=new SpannableString(""+stringArrayList.get(position));

            // Initialize image span

            // Set span

            // return spannable string
            return spannableString;
        }
    }
}