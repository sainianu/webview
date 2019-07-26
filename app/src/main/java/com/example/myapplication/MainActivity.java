package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mylibrary.Config;
import com.example.mylibrary.LogOnLabs;
import com.example.mylibrary.interfaces.LogEventCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn) {
            openWebView_SingleProvider();
        }
    }

    private void openWebView_SingleProvider() {
        Config config_obj = new Config();
        config_obj.setWebURL("https://logonlabs.netlify.com");     //user can enter any web url
        config_obj.setAppId("38573579347");
        config_obj.setShowAlertOnCancel(false);
       // config_obj.setParams("facebook");     // user can set any custom parameters as object

        new LogOnLabs(getApplicationContext(), config_obj)
                .responseCallback("anytype", new LogEventCallback() {
                    @Override
                    public void onResponse(String jsonPayload) {
                        Log.e("jsonPayload", jsonPayload);

                    }
                })
                .initialize();

    }

    private void openWebView_MultipleProvider() {
        Config config_obj = new Config();
        config_obj.setWebURL("https://logonlabs.netlify.com");     //user can enter any web url
        config_obj.setAppId("38573579347");
        config_obj.setShowAlertOnCancel(true);

        new LogOnLabs(getApplicationContext(), config_obj)
                .responseCallback("*", new LogEventCallback() {
                    @Override
                    public void onResponse(String jsonPayload) {
                        Log.e("jsonPayload", jsonPayload);

                    }
                })
                .initialize();
    }
}
