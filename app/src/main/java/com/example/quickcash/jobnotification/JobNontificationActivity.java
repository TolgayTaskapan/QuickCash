package com.example.quickcash.jobnotification;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.messaging.FirebaseMessagingService;

public class JobNontificationActivity extends FirebaseMessagingService
{
    private Context mcontext;

    Activity activity;

    public JobNontificationActivity(Context mcontext, Activity activity) {
        this.mcontext = mcontext;
        this.activity = activity;
    }

    public JobNontificationActivity() {
    }
}
