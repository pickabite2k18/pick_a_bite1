package com.example.vishnuk.pick_a_bite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void login(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
