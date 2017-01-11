package com.yjk.eventdemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.yjk.eventdemo.EventManager;

public class EventCollectService extends IntentService {

    private final String TAG = EventCollectService.class.getSimpleName();

    public static final String INTENT_KEY_CONTENT = "INTENT_KEY_CONTENT";

    public static final String INTENT_KEY_ACTIVITY = "INTENT_KEY_ACTIVITY";

    private EventManager mEventManager = EventManager.getInstance();

    public EventCollectService() {
        super("EventCollectService");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart " + Thread.currentThread().getName());
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand " + Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy " + Thread.currentThread().getName());
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String content = intent.getStringExtra(INTENT_KEY_CONTENT);
        final String activityName = intent.getStringExtra(INTENT_KEY_ACTIVITY);

        Log.d(TAG, "content = " + content + " | activityName = " + activityName + " | " + Thread.currentThread().getName());
        mEventManager.addClick(activityName, content);
    }
}
