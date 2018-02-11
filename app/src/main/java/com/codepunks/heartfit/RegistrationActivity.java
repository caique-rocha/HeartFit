package com.codepunks.heartfit;

//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//public class RegistrationActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//    }
//}
import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.HashMap;
        import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private Spinner mType;
    private EditText mEmail,mPassword,mPhone,mName,mcPassword;
    private Button mRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private int ctype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle("Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        mType = (Spinner) findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.c_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mType.setAdapter(adapter);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mPhone = (EditText) findViewById(R.id.phone);
        mName = (EditText) findViewById(R.id.name);
        mcPassword = (EditText) findViewById(R.id.cpassword);

        mRegister = (Button) findViewById(R.id.register);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String mname = mName.getText().toString();
                final String mphone = mPhone.getText().toString();
                final String mtype = mType.getSelectedItem().toString();
                if(mtype.equals("Client")){
                    ctype = 0;
                }
                else if(mtype.equals("Ambulance")){
                    ctype = 1;
                }
                if(password.equals(mcPassword.getText().toString())){
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(RegistrationActivity.this, "Sign up error",Toast.LENGTH_SHORT).show();
                            }else{
                                if(ctype==0){
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                                    current_user_db.setValue(true);
                                    Map map = new HashMap();
                                    map.put("name",mname);
                                    map.put("phone",mphone);
                                    map.put("email",email);
                                    map.put("ctype",ctype);
                                    current_user_db.updateChildren(map);
                                }
                                else if(ctype==1){
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                                    current_user_db.setValue(true);
                                    Map map = new HashMap();
                                    map.put("name",mname);
                                    map.put("phone",mphone);
                                    map.put("email",email);
                                    map.put("ctype",ctype);
                                    current_user_db.updateChildren(map);
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegistrationActivity.this, "Password Do not Match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}