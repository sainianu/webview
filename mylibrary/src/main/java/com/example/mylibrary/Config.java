package com.example.mylibrary;

import java.io.Serializable;

public class Config implements Serializable {

    public static String webURL="";
    public static String sdkURL = "https://logonlabs.netlify.com";
    public  static  String appId ="";
    public static Boolean showAlertOnCancel;
    public static Object getParams() {
        return params;
    }

    public static void setParams(Object params) {
        Config.params = params;
    }

    public static Object params;

    public static String getAppId() {
        return appId;
    }

    public static void setAppId(String appId) {
        Config.appId = appId;
    }

    public static String getWebURL() {
        return webURL;
    }

    public static void setWebURL(String webURL) {
        Config.webURL = webURL;
    }

    public static String getSdkURL() {
        return sdkURL;
    }

    public static void setSdkURL(String sdkURL) {
        Config.sdkURL = sdkURL;
    }

    public static Boolean getShowAlertOnCancel() {
        return showAlertOnCancel;
    }

    public static void setShowAlertOnCancel(Boolean showAlertOnCancel) {
        Config.showAlertOnCancel = showAlertOnCancel;
    }
}
