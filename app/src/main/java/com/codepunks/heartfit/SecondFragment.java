package com.codepunks.heartfit;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 12/31/15.
 */
public class SecondFragment extends Fragment implements View.OnClickListener{

    View myView;
    FirebaseAuth mAuth;
    DatabaseReference cust_data;
    String userId;String mNames,mPhones;
    private EditText mName,mPhone;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        cust_data = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        getUserInfo();
        Button save = (Button) myView.findViewById(R.id.save);
        mName = (EditText) myView.findViewById(R.id.name);
        mPhone = (EditText) myView.findViewById(R.id.phone);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });
        Button mBack = (Button) myView.findViewById(R.id.back);
        mBack.setOnClickListener(this);
        return myView;
    }

    private void getUserInfo(){
        cust_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    Map<String, Object>  map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name")!=null){
                        mName.setText(map.get("name").toString());
                        mNames = map.get("name").toString();
                    }
                    if(map.get("phone")!=null){
                        mPhone.setText(map.get("phone").toString());
                        mPhones = map.get("phone").toString();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveUserInformation() {

        mNames = mName.getText().toString();
        mPhones = mPhone.getText().toString();
        Map userInfo = new HashMap();
        userInfo.put("name",mNames);
        userInfo.put("phone",mPhones);
        cust_data.updateChildren(userInfo);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new FirstFragment()).commit();
    }
}
