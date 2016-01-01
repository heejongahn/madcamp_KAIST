package com.example.nobell.madagora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class AgoraActivity extends AppCompatActivity {
    private final static String EXTRA_USERNAME = "com.example.nobell.madagora.userid";
    private static String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_agora);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        mUsername = extras.getString(EXTRA_USERNAME);
        }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseFragment firebaseFrag = (FirebaseFragment)
                getSupportFragmentManager().findFragmentById(R.id.firebase_fragment);

        firebaseFrag.setUsername(mUsername);

    }
}