package com.example.ghimheefamilyshoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView TvRegister,TvForgetPassword;

    EditText etemail;
    EditText etpassword;

    Button btnLogin;


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TvForgetPassword = findViewById(R.id.tvForgetPassword);
        TvForgetPassword.setOnClickListener(this);

        TvRegister = findViewById(R.id.tvRegisterAccount);
        TvRegister.setOnClickListener(this);

        String text = TvRegister.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        int startIndex = text.indexOf("Sign Up");
        int endIndex = startIndex + "Sign Up".length();
        ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(foregroundSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TvRegister.setText(spannableString);

        etemail = findViewById(R.id.etEmailAddress);
        etpassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvRegisterAccount:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.btnLogin:
                userLogin();
                break;
            case R.id.tvForgetPassword:
                startActivity(new Intent(this,ActivityForgetPassword.class));
                break;
        }

    }

    private void userLogin() {
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();

        if (email.isEmpty()){
            etemail.setError("Email is Required!");
            etemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etemail.setError("Please Provide valid email!");
            etemail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            etpassword.setError("Password is Required!");
            etpassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            etpassword.setError("Min Password length should be 6 characters!");
            etpassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete( Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Check your email to verify your account! ", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}