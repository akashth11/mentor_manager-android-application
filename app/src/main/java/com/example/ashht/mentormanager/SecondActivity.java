package com.example.ashht.mentormanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter <String> adapter;
    mentordata mentor;
    DatabaseReference databaseReference;
    String name, studentname, studentbranch;
    private TextView requestresult;
    private Button submit,response;
    String req,stdnaam,mennaam;
    requestresult reqres;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView = (ListView)findViewById(R.id.retrmentorlist);
        mentor = new mentordata();
        reqres = new requestresult();
        requestresult = (TextView)findViewById(R.id.reqresult);
        response = (Button)findViewById(R.id.responsebutton);



        req="I want to be in your mentorship !";

        submit = (Button)findViewById(R.id.subreq);

        submit.setEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Mentors");




        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.mentor_info, R.id.mentorinfo, list);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    mentor = ds.getValue(mentordata.class);
                    list.add(mentor.getMentorName());
                }
                listView.setAdapter(adapter);
                submit.setEnabled(false);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SecondActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }

        });

        final DatabaseReference databaseReference = database.getReference("Students");
        DatabaseReference mchild = databaseReference.child(firebaseAuth.getUid());

        mchild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userProfile userProfile = dataSnapshot.getValue(com.example.ashht.mentormanager.userProfile.class);
                studentname = userProfile.getUserName();
                studentbranch =userProfile.getUserBranch();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                name = (String) adapterView.getItemAtPosition(position).toString();
                Query query = FirebaseDatabase.getInstance().getReference("Mentors").orderByChild("mentorName").equalTo(name);
                query.addListenerForSingleValueEvent(valueEventListener);
                submit.setEnabled(true);


            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendrequest();
                Toast.makeText(SecondActivity.this,"Requested Successfully !!",Toast.LENGTH_SHORT).show();
                submit.setEnabled(false);

            }
        });

        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query resquery = FirebaseDatabase.getInstance().getReference("RequestResult").orderByChild("studentname").equalTo(studentname);
                resquery.addListenerForSingleValueEvent(valueEventListener1);

            }
        });





    }


    public void sendrequest(){
        DatabaseReference databaseReference = database.getReference("Request");
        request request = new request(studentname,studentbranch,name,req);
        databaseReference.child(firebaseAuth.getUid()).setValue(request);

    }


    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){

            for(DataSnapshot ds: dataSnapshot.getChildren())
            {
                reqres = ds.getValue(requestresult.class);
                requestresult.setText("Mentor "+reqres.getMentorname()+" has "+reqres.getResult()+" your Request !!");
                listView.setEnabled(false);


            }

        }
        else {

                requestresult.setText("No request response till now !");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(SecondActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

        }
    };




    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for(DataSnapshot ds: dataSnapshot.getChildren())
            {
                list.clear();
                mentor = ds.getValue(mentordata.class);
                list.add("\nName              : "+mentor.getMentorName()+"  \n\nSpecialization : "+mentor.getMentorSpecialization());

            }
            listView.setAdapter(adapter);
            listView.setEnabled(false);
            submit.setEnabled(true);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(SecondActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

        }
    };



    private void Logout(){
        firebaseAuth.signOut();
        finish();

        Toast.makeText(SecondActivity.this,"You are Logged out !",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SecondActivity.this, LoginActivity.class));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutmenu:{
                Logout();
                break;
            }

            case R.id.profilmenu:{
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
