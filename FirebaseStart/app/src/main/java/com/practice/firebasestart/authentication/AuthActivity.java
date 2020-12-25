package com.practice.firebasestart.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.practice.firebasestart.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Button firebaseuibtn = findViewById(R.id.button);
        firebaseuibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FirebaseUIActivity.class);
                startActivity(intent);
            }
        });

        Button firebaselogoutbtn = findViewById(R.id.button2);
        firebaselogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                logout();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            firebaseuibtn.setEnabled(true);
            firebaselogoutbtn.setEnabled(false);
        }
        else {
            firebaseuibtn.setEnabled(false);
            firebaselogoutbtn.setEnabled(true);
        }
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) finish();
                    }
                });
    }
}
