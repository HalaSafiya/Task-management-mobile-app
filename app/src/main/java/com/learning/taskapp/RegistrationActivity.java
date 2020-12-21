package com.learning.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email ;
    private EditText pass;
    private Button btnReg;
    private TextView login_txt;

    private FirebaseAuth mAuth;

    //ProgressDialog is a modal dialog, which prevents the user from interacting with the app
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        email = findViewById(R.id.email_reg);
        pass = findViewById(R.id.password_reg);

        btnReg = findViewById(R.id.reg_btn);

        login_txt = findViewById(R.id.login_txt);

        login_txt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //trim method eliminates spaces and returns the omitted string.
                String mEmail = email.getText().toString().trim();
                String mPass = pass.getText().toString().trim();


                //if email field is empty
                if(TextUtils.isEmpty(mEmail)){
                    email.setError("Required Field!");
                    return;
                }
                if(TextUtils.isEmpty(mPass)){
                    pass.setError("Required Field");
                    return;
                }


            mDialog.setMessage("Processing..");
            mDialog.show();

            //To handle success and failure in the same listener, attach an OnCompleteListener
            mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //getApplicationContext() returns the context for the entire application (the process all the Activities are running inside of). Use this instead of the current Activity context if you need a context tied to the lifecycle of the entire application, not just the current Activity
                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        mDialog.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(),"Problem occured", Toast.LENGTH_LONG).show();
                        mDialog.dismiss();

                    }
                }
            });
            }
        });
    }
}