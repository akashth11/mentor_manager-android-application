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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail, userBranch;
    private Button regButton;
    private TextView userlogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ImageView userProfilePic;
    private DatabaseReference databaseStudent;
    String email, name, password, branch;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        databaseStudent = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mentormanager-f2456.firebaseio.com/Students");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){

                    progressDialog.setMessage("Registering...");
                    progressDialog.show();

                    //upload data to the database
                    String user_mail = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(user_mail, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                sendEmailVerification();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "Registartion Failed !", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            }
        });

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

    }

    private  void setupUIViews(){
        userName = (EditText)findViewById(R.id.etusername);
        userPassword = (EditText)findViewById(R.id.etpassword);
        userEmail = (EditText)findViewById(R.id.etemail);
        regButton = (Button)findViewById(R.id.regbtn);
        userlogin = (TextView)findViewById(R.id.gobacklogin);
        userBranch = (EditText)findViewById(R.id.etbranch);
        userProfilePic = (ImageView)findViewById(R.id.userprofilepic);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private Boolean validate(){
        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        branch = userBranch.getText().toString();


        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || branch.isEmpty()){
            Toast.makeText(this,"Please enter the details first !!!",Toast.LENGTH_SHORT).show();

        }else{
            result = true;
        }

        return result;

    }


    public void sendUserData(){
        userProfile userprofile = new userProfile(name, email, branch,password);
        databaseStudent.child(firebaseAuth.getUid()).setValue(userprofile);
    }



    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        firebaseAuth.signOut();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        Toast.makeText(RegistrationActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RegistrationActivity.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
