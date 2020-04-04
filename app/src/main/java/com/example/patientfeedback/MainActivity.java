package com.example.patientfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    EditText et_login, et_passward;
    Button bt_login;

    String LOGIN_ID = "1", PASSWARD = "1";

    private Executor executor;
    private androidx.biometric.BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_login = findViewById(R.id.edit_text_id);
        et_passward = findViewById(R.id.edit_text_password);

        bt_login = findViewById(R.id.button_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_login.getText().toString().equals(LOGIN_ID) && et_passward.getText().toString().trim().equals(PASSWARD)){
                    //Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                    Intent i = new Intent(getApplicationContext(), DashBoard.class);
                    startActivity(i);
                }
                else{
                    //popup_toast("Wrong Credentials");
                    //Intent i = new Intent(getApplicationContext(), DashBoard.class);
                    //startActivity(i);
                    if(et_login.getText().toString().length() == 0){
                        et_login.setError("Empty Field");
                        et_login.requestFocus();
                        return;
                    }
                    else{
                        if(et_passward.getText().toString().length() == 0){
                            et_passward.setError("Empty Field");
                            et_passward.requestFocus();
                            return;
                        }
                        else{
                            if(!et_login.getText().toString().equals(LOGIN_ID)) {
                                et_login.setError("Incorrect Login ID");
                                et_login.requestFocus();
                                return;
                            }
                            else{
                                if(!et_passward.getText().toString().equals(PASSWARD)){
                                    et_passward.setError("Incorrect Password");
                                    et_passward.requestFocus();
                                    return;
                                }
                                else {
                                    popup_toast("Something Unexpected");
                                }
                            }
                        }
                    }
                }
            }
        });

        /*
        * Code Added for biometric
        * */
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new androidx.biometric.BiometricPrompt(MainActivity.this,
                executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DashBoard.class));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login")
                .setSubtitle("Log in using your biometric credential")
                //.setNegativeButtonText("Use account password")
                .setDeviceCredentialAllowed(true)
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        ImageButton biometricLoginButton = findViewById(R.id.imageButton_fingerprint);
        biometricLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

    }

    private void popup_toast(String msg) {
        Toast.makeText(getApplicationContext(), msg+"",Toast.LENGTH_LONG).show();
    }
}
