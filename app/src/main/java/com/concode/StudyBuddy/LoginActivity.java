package com.concode.StudyBuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.concode.StudyBuddy.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        Button LoginBTN = (Button) findViewById(R.id.LoginBTN);
        assert binding.LoginBTN != null;
        binding.LoginBTN.setOnClickListener(view -> {
                String email = binding.email.getText().toString().trim();
                String password = binding.password.getText().toString().trim();
                progressDialog.setTitle("Sending Mail...");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(authResult -> {
                            progressDialog.cancel();
                            Toast.makeText(LoginActivity.this, "Login Successful!",Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(e -> {
                            progressDialog.cancel();
                            Toast.makeText(LoginActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                        });
            });
        Button forgotPswdBtn = (Button) findViewById(R.id.forgotPswdBtn);
        binding.forgotPswdBtn.setOnClickListener(view -> {
            String email = binding.email.getText().toString();
            progressDialog.setTitle("Sending Mail...");
            progressDialog.show();
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(unused -> {
                        progressDialog.cancel();
                        Toast.makeText(LoginActivity.this, "Email Sent :)", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        progressDialog.cancel();
                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    });
        });


        Objects.requireNonNull(binding.goToSignUpBtn).setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,MainActivity.class)));
    }
}