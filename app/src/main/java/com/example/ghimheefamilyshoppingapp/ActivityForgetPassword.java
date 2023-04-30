package com.example.ghimheefamilyshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityForgetPassword extends AppCompatActivity implements View.OnClickListener{
    TextView tvReturn;
    EditText etEmailReset;
    Button btnReset;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        etEmailReset = findViewById(R.id.etEmailAddress);
        etEmailReset.setOnClickListener(this);

        tvReturn = findViewById(R.id.tvBackToLogin);
        tvReturn.setOnClickListener(this);

        btnReset = findViewById(R.id.btnResetPassword);
        btnReset.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnResetPassword:
                resetPassword();
                break;

            case R.id.tvBackToLogin:
                startActivity(new Intent(ActivityForgetPassword.this,LoginActivity.class));
        }

    }

    private void resetPassword() {
        String email = etEmailReset.getText().toString().trim();

        if (email.isEmpty()){
            etEmailReset.setError("Email is Required!");
            etEmailReset.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmailReset.setError("Please Provide valid email!");
            etEmailReset.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ActivityForgetPassword.this,"Check your email to reset your password!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ActivityForgetPassword.this,"Error, Something wrong happened. Please try again!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}