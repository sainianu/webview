package com.example.mylibrary;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import org.json.JSONObject;

import static com.example.mylibrary.LogOnLabs.callback_obj;

public class LogOnLabsFragment extends AppCompatActivity implements View.OnClickListener {
    WebView webV;
    Button btn;
     LogEventCallbackController dispatchController;
    Config config_obj;
    boolean alert_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);
        webV = findViewById(R.id.webV);
        btn = findViewById(R.id.cancel);
        btn.setOnClickListener(this);
        this.dispatchController = new LogEventCallbackController();
         config_obj = (Config) getIntent().getSerializableExtra("config_obj");
         alert_type =config_obj.getShowAlertOnCancel();
        final JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("appId", config_obj.getAppId());
            jsonObj.put("webUrl", config_obj.getWebURL());
            jsonObj.put("params", config_obj.getParams());
            jsonObj.put("origin", "android");
        } catch (Exception e) {
            e.printStackTrace();
        }


        webV.setWebChromeClient(new WebChromeClient() {
            public boolean onConsoleMessage(ConsoleMessage cmsg) {
                Log.e("response", cmsg.message());
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
                view.loadUrl("javascript:window.logonlabsConfig=" + jsonObj);
            }

            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Log.e("Load ", description);
            }
        };

        webV.getSettings().setJavaScriptEnabled(true);
        webV.getSettings().setSupportZoom(true);
        webV.getSettings().setBuiltInZoomControls(true);
        webV.setWebViewClient(yourWebClient);
        webV.loadUrl(config_obj.getWebURL());
        WebView.setWebContentsDebuggingEnabled(true);
        webV.addJavascriptInterface(new MyJavaScriptInterface(), "logonlabs");
    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void postMessage(String jsonResponse) {

            Log.e("ttth", jsonResponse);
            try {
          callback_obj.onResponse(jsonResponse);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btn){
            if(alert_type)
            showCustomDialog();
            else
                finish();
        }


    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(LogOnLabsFragment.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.cancel_dialog);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmlp.gravity =  Gravity.CENTER;;
        Button cancel = dialog.findViewById(R.id.cancel);
        Button ok = dialog.findViewById(R.id.ok);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback_obj=null;
                finish();
            }
        });
        dialog.show();
    }
}
