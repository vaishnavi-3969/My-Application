package com.concode.StudyBuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.concode.StudyBuddy.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button SignUpBTN, goToLoginBtn;
    ActivityMainBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        SignUpBTN = findViewById(R.id.signupBtn);
        binding.SignUpBTN.setOnClickListener(view -> {
            String name = binding.name.getText().toString();
            String number = binding.phoneNo.getText().toString();
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString();
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(authResult -> {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        progressDialog.cancel();
                        firebaseFirestore.collection("User")
                                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                .set(new UserModel(name,email,number,email));
                    }).addOnFailureListener(e -> {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    });
        });


        binding.goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }
}


/*
package com.concode.StudyBuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.concode.StudyBuddy.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button SignUpBTN, goToLoginBtn;
    ActivityMainBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    UserModel userModel;
    FirebaseFirestore firebaseFirestore;
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);
        //get instance of our firebase database
//        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
//        databaseReference = firebaseDatabase.getReference("UserInfo");
        firebaseAuth = FirebaseAuth.getInstance();
        //get reference of our database
        firebaseFirestore = FirebaseFirestore.getInstance();
//        userModel = new UserModel();

        progressDialog = new ProgressDialog(this);
        SignUpBTN = findViewById(R.id.signupBtn);
        SignUpBTN.setOnClickListener(view -> {
            String name = findViewById(R.id.name).toString().trim();
            String number = findViewById(R.id.phoneNo).toString();
            String email = findViewById(R.id.email).toString();
            String password = findViewById(R.id.password).toString();
            userModel = new UserModel(name,number,email,password);
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(authResult -> {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        progressDialog.cancel();
                        firebaseFirestore.collection("User")
                                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                .set(new UserModel(name,email,number,email));
                    }).addOnFailureListener(e -> {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    });
//            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(number) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
//
//                Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
//            } else {
//
//                addDatatoFirebase(email,name, number, password);
//            }


        });


        goToLoginBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

    }
//    private void addDatatoFirebase(String email,String name, String number, String password){
//        userModel.setName(name);
//        userModel.setEmail(email);
//        userModel.setNumber(number);
//        userModel.setPassword(password);
//
//        // we are use add value event listener method
//        // which is called with database reference.
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                databaseReference.setValue(userModel);
//                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}*/
