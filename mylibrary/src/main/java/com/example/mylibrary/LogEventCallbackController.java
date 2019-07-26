/*
 * Copyright (c) 2018 Bambora ( https://bambora.com/ )
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.example.mylibrary;

import android.webkit.JavascriptInterface;
import com.example.mylibrary.interfaces.LogEventCallback;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * The controller that handles all attached events to be called on event dispatch
 */
public class LogEventCallbackController {

    private Map<String, ArrayList<LogEventCallback>> callbackEventCollection;

    /**
     * The default constructor
     */
    public LogEventCallbackController()
    {
        this.callbackEventCollection = new TreeMap<>();
    }

    /**
     * Add an event callback to be called on checkout event dispatched
     * @param eventType {@Link String}
     * @param callbackEvent {@Link LogEventCallback}
     * @return void
     */
    public void on(String eventType, final LogEventCallback callbackEvent) {
        eventType = eventType.toLowerCase(Locale.getDefault());
        if(this.callbackEventCollection.get(eventType) == null) {
            ArrayList<LogEventCallback> callbackEvents = new ArrayList<>();
            callbackEvents.add(callbackEvent);
            this.callbackEventCollection.put(eventType, callbackEvents);
        } else {
            ArrayList<LogEventCallback> callbackEvents = this.callbackEventCollection.get(eventType);
            callbackEvents.add(callbackEvent);
            this.callbackEventCollection.put(eventType, callbackEvents);
        }
    }

    /**
     * Add an event callback to be called on checkout event dispatched
     * @param callbackEvents {@Link Map<String, ArrayList<LogEventCallback>>}
     * @return {@Link BamboraCheckout }
     */
    public void on(Map<String, ArrayList<LogEventCallback>> callbackEvents) {
        this.callbackEventCollection.putAll(callbackEvents);
    }

    /**
     * Remove an event callback from beeing called on checkout event dispatched
     * @param eventType {@Link String}
     * @return {@Link BamboraCheckout }
     */
    public void off(String eventType) {
        this.callbackEventCollection.remove(eventType);
    }

    /**
     * Get the collection og callbackEvents
     * @return {@Link Map<String, ArrayList<LogEventCallback>>}
     */
    public Map<String, ArrayList<LogEventCallback>> getCallbackEventCollection()
    {
        return this.callbackEventCollection;
    }

    /**
     * Get an array of eventCallbacks based on eventType.
     * @param eventType {@Link String}
     * @return {@Link ArrayList<LogEventCallback>}
     */
    public ArrayList<LogEventCallback> getEventCallback(String eventType)
    {
        eventType = eventType.toLowerCase(Locale.getDefault());
        return callbackEventCollection.get(eventType);
    }

    /**
     * The JavaScript interface for handling dispatched events from the WebView
     * @param eventType {@Link String}
     * @param jsonPayload {@Link String}
     * @return void
     */
    @JavascriptInterface
    @SuppressWarnings("unused")
    public void dispatchEvent(String eventType, String jsonPayload) {

        eventType = eventType.toLowerCase(Locale.getDefault());
        if (this.callbackEventCollection.size() > 0) {
            ArrayList<LogEventCallback> callbackEvents = this.callbackEventCollection.get(eventType);
            if (callbackEvents != null && callbackEvents.size() > 0) {
                for (LogEventCallback callback : callbackEvents) {
                    callback.onResponse(jsonPayload);
                }
            }
            ArrayList<LogEventCallback> anyCallbackEvents = this.callbackEventCollection.get("*");
            if (anyCallbackEvents != null && anyCallbackEvents.size() > 0) {
                for (LogEventCallback anyCallback : anyCallbackEvents) {
                    anyCallback.onResponse( jsonPayload);
                }
            }
        }
    }
}
