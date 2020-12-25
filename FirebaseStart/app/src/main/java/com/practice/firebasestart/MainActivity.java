package com.practice.firebasestart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.practice.firebasestart.authentication.AuthActivity;
import com.practice.firebasestart.firestore.FirestoreActivity;
import com.practice.firebasestart.realtimedb.MemoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button firebaseuibtn = findViewById(R.id.button);
        firebaseuibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_LONG).show();
        else Toast.makeText(getApplicationContext(), "이미 로그인된 상태입니다.", Toast.LENGTH_LONG).show();

        Button firebaserealdbbtn = findViewById(R.id.button2);
        firebaserealdbbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MemoActivity.class);
                startActivity(intent);
            }
        });

        Button firebasecloudfirestorebtn = findViewById(R.id.button3);
        firebasecloudfirestorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FirestoreActivity.class);
                startActivity(intent);
            }
        });
    }
}
