package com.example.mylibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.example.mylibrary.interfaces.LogEventCallback;

public  class LogOnLabs {
    Config  config_obj;
    Context ctx;
public static LogEventCallback callback_obj;
    private LogEventCallbackController dispatchController;

    public LogOnLabs(Context c, Config object)
    {
        this.config_obj = object;
        ctx= c;
        this.dispatchController = new LogEventCallbackController();
        init_method();

    }

   public  void  init_method(){
       Intent ientnt = new Intent(ctx,LogOnLabsFragment.class).putExtra("config_obj", config_obj);
       ctx.startActivity(ientnt);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public LogOnLabs initialize()
    { return this;
    }

    /**
     * Add an event callback to be called on checkout event dispatched
     * @param eventType {@Link String}
     * @param callback {@Link LogEventCallback}
     * @return {@Link LogOnLabs }
     */

    public LogOnLabs responseCallback(String eventType, final LogEventCallback callback) {
        this.dispatchController.on(eventType, callback);
        callback_obj=callback;
        return this;
    }


}


