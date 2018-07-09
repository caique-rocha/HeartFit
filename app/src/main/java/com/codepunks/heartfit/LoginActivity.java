package com.codepunks.heartfit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.Manifest;
public class LoginActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private Button mLogin, mRegistration;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        TextView btn =(TextView)findViewById(R.id.link_signup);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
        startService(new Intent(LoginActivity.this, onAppKilled.class));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            String uid = user.getUid().toString();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                        cwtype = dataSnapshot.child("ctype").getValue().toString();
                    if(cwtype.equals("0")){
                        Intent intentMain = new Intent(LoginActivity.this, AfterLoginActivity.class);
                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentMain);
                        finish();
                    }
                    else if(cwtype.equals("1")){
                        Intent intentMain = new Intent(LoginActivity.this, AmbulanceMapsActivity.class);
                        intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentMain);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            // No user is signed in
            mAuth = FirebaseAuth.getInstance();
            mEmail = (EditText) findViewById(R.id.email2);
            mPassword = (EditText) findViewById(R.id.password2);

            mLogin = (Button) findViewById(R.id.btn_login) ;
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String email1 = mEmail.getText().toString();
                    final String password1 = mPassword.getText().toString();
                    if(!email1.equals("") && !password1.equals("")) {
                        loginUser(email1, password1);
                    }else{
                        Toast.makeText(LoginActivity.this, "Failed Login: Empty Inputs are not allowed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }String cwtype;
    private void loginUser( String userLoginEmail, String userLoginPassword) {
        if(userLoginEmail!=null && userLoginPassword!=null)
            mAuth.signInWithEmailAndPassword(userLoginEmail, userLoginPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseUser user = mAuth.getCurrentUser();
                                String uid = user.getUid().toString();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists())
                                            cwtype = dataSnapshot.child("ctype").getValue().toString();
                                        if(cwtype.equals("0") ){
                                            Intent intentMain = new Intent(LoginActivity.this, AfterLoginActivity.class);
                                            intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intentMain);
                                            finish();
                                        }
                                        else if(cwtype.equals("1")){
                                            Intent intentMain = new Intent(LoginActivity.this, AmbulanceMapsActivity.class);
                                            intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intentMain);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    });
    }



    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }


}
