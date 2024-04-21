package com.wiz.wizmart;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived ( message );
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken ( token );
    }
}
