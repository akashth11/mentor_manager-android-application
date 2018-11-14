package com.example.ashht.mentormanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMentor extends AppCompatActivity {

    private EditText mentoremail, mentorpassword, mentorname, mentorsepcialization;
    private Button addmentorbtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseMentor;
    String mail,pass,name,specialization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mentor);

        databaseMentor = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mentormanager-f2456.firebaseio.com/Mentors");
        firebaseAuth = FirebaseAuth.getInstance();

        mentoremail = (EditText)findViewById(R.id.menregemail);
        mentorpassword = (EditText)findViewById(R.id.menregpass);
        mentorname = (EditText)findViewById(R.id.menname);
        mentorsepcialization =(EditText)findViewById(R.id.menspecialization);
        addmentorbtn = (Button)findViewById(R.id.addmenbtn);
        progressDialog = new ProgressDialog(this);

        addmentorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getValues()){

                    progressDialog.setMessage("Registering Mentor...");
                    progressDialog.show();

                    String mentor_mail = mentoremail.getText().toString().trim();
                    String mentor_password = mentorpassword.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(mentor_mail, mentor_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                sendMentorData();
                                firebaseAuth.signOut();
                                Toast.makeText(AddMentor.this, "Registartion Successfull !", Toast.LENGTH_SHORT).show();
                                finish();

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(AddMentor.this, "Registartion Failed !", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                }

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private Boolean getValues(){
        Boolean result = false;
        mail = mentoremail.getText().toString();
        pass = mentorpassword.getText().toString();
        name = mentorname.getText().toString();
        specialization = mentorsepcialization.getText().toString();

        if(mail.isEmpty() || pass.isEmpty()){
            Toast.makeText(this,"Please enter the details first !!!",Toast.LENGTH_SHORT).show();

        }else{
            result = true;
        }

        return result;
    }

    public void sendMentorData(){
        mentordata mentordata = new mentordata(name, mail, specialization, pass);
        databaseMentor.child(firebaseAuth.getUid()).setValue(mentordata);

    }


}
