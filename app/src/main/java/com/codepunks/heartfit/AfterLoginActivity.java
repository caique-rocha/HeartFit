package com.codepunks.heartfit;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


import com.firebase.geofire.core.GeoHashQuery;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import android.Manifest;
import android.widget.Toast;

public class AfterLoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,com.google.android.gms.location.LocationListener {

    public void cl(){
        startActivity(new Intent(AfterLoginActivity.this,ClientMapsActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Button p = (Button) findViewById(R.id.push_button);
//        p.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AfterLoginActivity.this, ClientMapsActivity.class));
//            }
//        });
//        mPush = (Button) findViewById(R.id.push_button);
//        mPush.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        setContentView(R.layout.activity_after_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("HeartFit");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AfterLoginActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.nav_first_layout);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame,new FirstFragment()).commit();
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.after_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case 1:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //mapFragment.getMapAsync();
                }else{
                    Toast.makeText(getApplicationContext(), "Please Provide the Permision", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();


        if (id == R.id.nav_first_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new FirstFragment())
                    .commit();
        } else if (id == R.id.nav_second_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new SecondFragment())
                    .commit();
        } else if (id == R.id.nav_third_layout) {
//            fragmentManager.beginTransaction()
//                    .replace(R.id.content_frame
//                            , new ThirdFragment())
//                    .commit();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cl(View view) {
    startActivity(new Intent(AfterLoginActivity.this,ClientMapsActivity.class));


    }


    public void cl2(View view) {
//


        String uri = "http://maps.google.com/maps?saddr=" +latitude+","+longitude;

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String ShareSub = "Here is my location";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }
    LatLng latLng;
    Double latitude;
    Double longitude;
    @Override
    public void onLocationChanged(Location location) {
        if(getApplicationContext()!=null){


            latLng = new LatLng(location.getLatitude(),location.getLongitude());
            latitude = location.getLatitude();
            longitude = location.getLongitude();


        }
    }

    private EditText mName,mPhone;
    private Button mSave,mBack;
    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;



}
