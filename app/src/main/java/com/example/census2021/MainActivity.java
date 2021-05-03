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

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    public void  signuphere(View view)
    {
        TextInputLayout textEmail=(TextInputLayout)findViewById(R.id.email);
        TextInputLayout textPwd=(TextInputLayout)findViewById(R.id.password);
        ProgressBar bar=(ProgressBar)findViewById(R.id.progressBar2);
        bar.setVisibility(View.VISIBLE);
       String email=textEmail.getEditText().getText().toString();
       String password=textPwd.getEditText().getText().toString();
         mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            textEmail.getEditText().setText("");
                            textPwd.getEditText().setText("");
                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Registered Successfully..", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Error In Registration..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}