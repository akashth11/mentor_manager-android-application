package com.example.ashht.mentormanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MentorLogin extends AppCompatActivity {

    private TextView mentoradd;
    private Button mntlog;
    private EditText mentormail;
    private EditText mentorpass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_login);

        mentoradd = (TextView) findViewById(R.id.addmentor);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        mntlog = (Button) findViewById(R.id.mentorlogbtn);
        mentormail = (EditText) findViewById(R.id.mentorid);
        mentorpass = (EditText) findViewById(R.id.mentorpassword);

        mentoradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MentorLogin.this, AddMentor.class));
            }
        });


        mntlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mentormail.getText().toString().trim();
                String password = mentorpass.getText().toString().trim();

                if (email.equals("") || password.equals("")) {

                    Toast.makeText(MentorLogin.this, "Please enter your credentials first !", Toast.LENGTH_SHORT).show();

                } else {
                    validate(mentormail.getText().toString().trim(), mentorpass.getText().toString().trim());
                }
            }
        });
    }


        private void validate(String mail, String password){

            progressDialog.setMessage("Loggin in...");
            progressDialog.show();


            firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        startActivity(new Intent(MentorLogin.this, mentorprofile.class));
                        Toast.makeText(MentorLogin.this, "Hello Mentor !!", Toast.LENGTH_SHORT).show();
                        //firebaseAuth.signOut();

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(MentorLogin.this, "Login Failed !!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

}
