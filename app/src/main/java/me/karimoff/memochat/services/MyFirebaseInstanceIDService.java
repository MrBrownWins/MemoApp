package me.karimoff.memochat.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public MyFirebaseInstanceIDService() {
    }

    @Override
    public void onTokenRefresh() {
        // Get new Firebase FCM token
        String newToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("token", "Refreshed token:" + newToken);
        
    }
}
