package com.example.census2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    public void  signuphere(View view)
    {
        TextInputLayout textEmail=(TextInputLayout)findViewById(R.id.email);
        TextInputLayout textPwd=(TextInputLayout)findViewById(R.id.password);
        ProgressBar bar=(ProgressBar)findViewById(R.id.progressBar2);
       String email=textEmail.getEditText().getText().toString();
       String password=textPwd.getEditText().getText().toString();
         mAuth = FirebaseAuth.getInstance();
        if(email.length()==0 ||password.length()==0 )
        {
            Toast.makeText(MainActivity.this, "Enter valid Data..", Toast.LENGTH_SHORT).show();
            textPwd.getEditText().setText("");
            textEmail.getEditText().setText("");
        }
        else if(password.length()<6)
        {
            Toast.makeText(MainActivity.this, "Password Must be greater than 6 characters..", Toast.LENGTH_SHORT).show();
            textPwd.getEditText().setText("");

        }
        else {
                  bar.setVisibility(View.VISIBLE);
                   mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                textEmail.getEditText().setText("");
                                textPwd.getEditText().setText("");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                FirebaseUser user= mAuth.getCurrentUser();
                                DatabaseReference myRef = database.getReference();
                                myRef =database.getReference().child("User");
                                HashMap userMap=new HashMap();
                                userMap.put("Username",email);
                                userMap.put("Enrollment Number",password);
                                myRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful())
                                        {

                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),task.getException().toString(),Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                    }
                                });

                                bar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Registered Successfully..", Toast.LENGTH_SHORT).show();

                            } else {
                                bar.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Error In Registration..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}