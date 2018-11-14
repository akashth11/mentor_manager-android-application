package com.example.ashht.mentormanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileReader;

public class PasswordActivity extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        passwordEmail = (EditText)findViewById(R.id.forgemail);
        resetPassword = (Button) findViewById(R.id.forgbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                String useremail = passwordEmail.getText().toString().trim();

                if(useremail.equals("")){
                    progressDialog.dismiss();
                    Toast.makeText(PasswordActivity.this, "Please enter your registered Email", Toast.LENGTH_SHORT).show();

                }else {

                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                             finish();
                             progressDialog.dismiss();
                             Toast.makeText(PasswordActivity.this, "Password reset email sent !",Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                         }
                         else {
                             progressDialog.dismiss();
                             Toast.makeText(PasswordActivity.this, "Wrong Credentials !",Toast.LENGTH_SHORT).show();

                         }
                        }
                    });
                }

            }
        });

    }
}
