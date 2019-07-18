package com.example.mylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public  class LogOnLabs extends AppCompatActivity {
    WebView webV;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
             webV = findViewById(R.id.webV);

                displaView("https://logonlabs.netlify.com");

        }

    public   void displaView(String url) {
        webV.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cmsg)
            {
                Log.e("response",cmsg.message());
                return true;
            }
        });


        WebViewClient yourWebClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Log.e("Load ", description);
                Toast.makeText(
                        getApplicationContext(),
                        "Problem loading. Make sure internet connection is available.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        webV.getSettings().setJavaScriptEnabled(true);
        webV.getSettings().setSupportZoom(true);
        webV.getSettings().setBuiltInZoomControls(true);
        webV.setWebViewClient(yourWebClient);
        webV.loadUrl(url);
        webV.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
    }

    class MyJavaScriptInterface
        {
            @JavascriptInterface
            @SuppressWarnings("unused")
            public void processHTML(String html)
            {
                Log.e("wdwdw",html);
            }
        }

    }


