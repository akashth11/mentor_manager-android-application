package com.example.ashht.mentormanager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilepic;
    private TextView profilename, profilemail, profilebranch;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilepic = (ImageView)findViewById(R.id.actpropic);
        profilename = (TextView)findViewById(R.id.actprofilename);
        profilemail = (TextView)findViewById(R.id.actprofilemail);
        profilebranch= (TextView)findViewById(R.id.actprofilebranch);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://mentormanager-f2456.firebaseio.com/Students");
        DatabaseReference mchild = databaseReference.child(firebaseAuth.getUid());

        mchild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userProfile userProfile = dataSnapshot.getValue(com.example.ashht.mentormanager.userProfile.class);
                profilename.setText("Name : " + userProfile.getUserName());
                profilemail.setText("Email : " +userProfile.getUserEmail());
                profilebranch.setText("Branch : " +userProfile.getUserBranch());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}
