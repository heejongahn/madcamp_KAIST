package com.example.nobell.madagora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AgoraActivity extends AppCompatActivity {
    public final static String EXTRA_USERNAME = "com.example.nobell.madagora.userid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agora);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String username = extras.getString(EXTRA_USERNAME);

        TextView textView = (TextView) findViewById(R.id.username_panel);
        textView.setText(String.format("You're logged in as %s", username));
    }
}
