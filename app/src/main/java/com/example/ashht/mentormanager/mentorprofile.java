package com.example.ashht.mentormanager;

import android.content.Intent;
import android.provider.ContactsContract;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mentorprofile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private TextView mennam,menspec;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    request req;
    String retname, getreqdata;
    private Button accept,deny;
    String acc,den,getstudentpassword;
    userProfile student;
    //private  int count;
    //Integer count=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentorprofile);

        mennam = (TextView)findViewById(R.id.getmenname);
        menspec = (TextView)findViewById(R.id.getmenspec);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        accept =(Button)findViewById(R.id.acceptreq);
        deny = (Button)findViewById(R.id.rejectreq);

        acc="accepted";
        den="denied";

        listView = (ListView)findViewById(R.id.reqlist);

        req = new request();
        student = new userProfile();

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.reqinfo, R.id.requestinfo, list);

        final DatabaseReference databaseReference = firebaseDatabase.getReference("Mentors");
        DatabaseReference mchild = databaseReference.child(firebaseAuth.getUid());

        mchild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              mentordata mentor = dataSnapshot.getValue(mentordata.class);
              mennam.setText("Hello "+mentor.getMentorName()+" !!");
              menspec.setText("Your Specialization "+mentor.getMentorSpecialization());
              retname = mentor.getMentorName();
                Query query = FirebaseDatabase.getInstance().getReference("Request").orderByChild("mentorname").equalTo(retname);
                query.addListenerForSingleValueEvent(valueEventListener);
                accept.setEnabled(false);
                deny.setEnabled(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                getreqdata = (String) adapterView.getItemAtPosition(position).toString();
                Query query2 = FirebaseDatabase.getInstance().getReference("Request").orderByChild("studentname").equalTo(getreqdata);
                query2.addListenerForSingleValueEvent(valueEventListener1);
                accept.setEnabled(true);
                deny.setEnabled(true);
                Query query3 = FirebaseDatabase.getInstance().getReference("Students").orderByChild("userName").equalTo(getreqdata);
                query3.addListenerForSingleValueEvent(valueEventListener2);


            }
        });







        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptrequest();
                Toast.makeText(mentorprofile.this,"Accepted Request Successfully !!"+getstudentpassword,Toast.LENGTH_SHORT).show();
                accept.setEnabled(false);
                deny.setEnabled(false);
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectrequest();
                Toast.makeText(mentorprofile.this,"Accepted Request Successfully !!"+getstudentpassword,Toast.LENGTH_SHORT).show();
                deny.setEnabled(false);
                accept.setEnabled(false);

            }
        });








    }



    public void acceptrequest()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference("RequestResult");
        requestresult requestresult = new requestresult(acc,getreqdata,retname);
        databaseReference.child(getstudentpassword).setValue(requestresult);

    }

    public void rejectrequest()
    {
        DatabaseReference databaseReference = firebaseDatabase.getReference("RequestResult");
        requestresult requestresult = new requestresult(den,getreqdata,retname);
        databaseReference.child(getstudentpassword).setValue(requestresult);

    }


    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

           if(dataSnapshot.exists()){
               for(DataSnapshot ds : dataSnapshot.getChildren()){
                   student = ds.getValue(userProfile.class);
                   getstudentpassword = student.getUserPassword();

               }
           }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {


        }

        };





    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    req = ds.getValue(request.class);
                    list.add(req.getStudentname());

                }
                listView.setAdapter(adapter);

            }

            else{
                list.add("No Requests..");
                listView.setAdapter(adapter);
                listView.setEnabled(false);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists()) {

                list.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    req = ds.getValue(request.class);
                    list.add("Dear Mentor,\nMy name is "+req.studentname+" of branch "+req.getStudentbranch()+"\n"+req.getRequest()+ "\n\nPlease accept my request !");

                }
                listView.setAdapter(adapter);
                listView.setEnabled(false);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumentor, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutmenu:{
                Logout();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void Logout(){
        firebaseAuth.signOut();
        startActivity(new Intent(mentorprofile.this, LoginActivity.class));

    }





}
