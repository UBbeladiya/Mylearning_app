package com.example.learning_app.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.learning_app.R;

public class Pdf_View extends AppCompatActivity {
     WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        webView = findViewById(R.id.webView);

        // Enable JavaScript (optional)
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);

        // Set a WebViewClient to handle the PDF file loading
        webView.setWebViewClient(new WebViewClient());
        // Load the PDF file
        webView.loadUrl("https://www.javatpoint.com/android-working-with-button");

    }
}
