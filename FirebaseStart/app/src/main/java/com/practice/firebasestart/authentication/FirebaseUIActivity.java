package com.practice.firebasestart.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.practice.firebasestart.R;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUIActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_ui);

        Button firebaseuiauthbtn = findViewById(R.id.button);
        firebaseuiauthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
            else {

            }
        }
    }

    private void signIn() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(getSelectedTheme())
                        .setLogo(getSelectedLogo())
                        .setAvailableProviders(getSelectedProviders())
                        .setTosAndPrivacyPolicyUrls("http://naver.com", "https://google.com")
                        .setIsSmartLockEnabled(true)
                        .build(), RC_SIGN_IN);
    }

    private int getSelectedTheme() {
        return AuthUI.getDefaultTheme();
    }

    private int getSelectedLogo() { return R.drawable.firebase_logo; }

    private List<AuthUI.IdpConfig> getSelectedProviders() {
        List<AuthUI.IdpConfig> selectedProviders = new ArrayList<>();
        CheckBox googlechk = findViewById(R.id.checkBox);
        CheckBox facebookchk = findViewById(R.id.checkBox2);
        CheckBox githubchk = findViewById(R.id.checkBox3);

        if (googlechk.isChecked()) {
            selectedProviders.add(new AuthUI.IdpConfig.GoogleBuilder().build());
        }
        if (facebookchk.isChecked()) {
            selectedProviders.add(new AuthUI.IdpConfig.FacebookBuilder().build());
        }
        if (githubchk.isChecked()) {
            selectedProviders.add(new AuthUI.IdpConfig.EmailBuilder().build());
        }

        return selectedProviders;
    }
}
