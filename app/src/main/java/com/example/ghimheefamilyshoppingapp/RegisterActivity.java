package com.example.ghimheefamilyshoppingapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etEmail,etPassword,etUsername,etHomeAddress;
    Button btnSignup;
    TextView tvBanner,tvBack;
    String userID;


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        etHomeAddress = findViewById(R.id.etAddress);
        btnSignup = findViewById(R.id.btnCreateAccount);
        btnSignup.setOnClickListener(this);
        tvBack = findViewById(R.id.tvBack);
        tvBanner = findViewById(R.id.tvSignIn);

        tvBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    };
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCreateAccount:
                registerUser();
                break;
        }

    }


    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String HomeAddress = etHomeAddress.getText().toString();

        if (username.isEmpty()){
            etUsername.setError("Full Name is Required!");
            etUsername.requestFocus();
            return;
        }
        if (email.isEmpty()){
            etEmail.setError("Email is Required!");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please Provide valid email!");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            etPassword.setError("Password is Required!");
            etPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            etPassword.setError("Min Password length should be 6 characters!");
            etPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Registration created successfully",Toast.LENGTH_LONG).show();
                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("Username",username);
                    user.put("Email",email);
                    user.put("Password",password);
                    user.put("Home Address", HomeAddress);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"onSuccess: User profile is created for " + userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {
                            Log.d(TAG,"OnFailure: " + e.toString());
                        }
                    });
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }else{
                    Toast.makeText(RegisterActivity.this,"Registration is Unsuccessful. Please Try Again",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}


