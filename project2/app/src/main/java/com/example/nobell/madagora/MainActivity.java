package com.example.nobell.madagora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_USERNAME = "com.example.nobell.madagora.userid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        Intent intent = new Intent(this, AgoraActivity.class);
        EditText editText = (EditText) findViewById(R.id.edittext_name);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_USERNAME, message);
        startActivity(intent);
    }
}
