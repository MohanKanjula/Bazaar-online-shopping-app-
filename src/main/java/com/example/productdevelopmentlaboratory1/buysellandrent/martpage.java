package com.example.productdevelopmentlaboratory1.buysellandrent;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class martpage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView email;
    private TextView name;
    private Button buy_button,sell_button,rent_button;
    String usermail,username,uname,userid;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;
    DatabaseReference myRef=firebaseDatabase.getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_martpage);
      //  details();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        name=(TextView)findViewById(R.id.textView_name);
        email=(TextView)findViewById(R.id.textView_email);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView1.getHeaderView(0);
        email = (TextView)hView.findViewById(R.id.textView_email);
        name = (TextView)hView.findViewById(R.id.textView_name);
        Intent in=getIntent();
        usermail=in.getStringExtra("uemail");
        details();
        email.setText(usermail);
        buy_button=(Button)findViewById(R.id.button_buy);
        sell_button=(Button)findViewById(R.id.button_sell);
        rent_button=(Button)findViewById(R.id.button_rent);
        buy_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              //   Intent in=new Intent(martpage.this,buy.class);
              //   startActivity(in);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (email.getText().toString().equalsIgnoreCase(data.child("email").getValue(String.class))) {
                                //do ur stuff
                                Toast.makeText(martpage.this, data.child("username").getValue(String.class), Toast.LENGTH_SHORT).show();
                            } else {
                                //do something
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent in=new Intent(martpage.this,buypage.class);
                in.putExtra("Id1",email.getText().toString());
                startActivity(in);
            }
        });
        sell_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(martpage.this,sellpage.class);
                in.putExtra("Id1",email.getText().toString());
                startActivity(in);
            }
        });
        rent_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent in=new Intent(martpage.this,rentactivity.class);
                in.putExtra("Id1",email.getText().toString());
                startActivity(in);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Intent in=new Intent(martpage.this,signup.class);
        startActivity(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.martpage, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action

        } else if (id == R.id.nav_gallery) {
                Intent in = new Intent(martpage.this,solditems.class);
                in.putExtra("Id1",email.getText().toString());
                startActivity(in);
        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_logout) {
                Intent in=new Intent(martpage.this,signup.class);
                startActivity(in);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void details(){
        mAuth=FirebaseAuth.getInstance();
        userid=mAuth.getCurrentUser().getUid();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i:dataSnapshot.child("Users").child(userid).getChildren()){
                    if(i.child("email").equals(usermail)){
                       uname=i.child("username").getValue(String.class);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        name.setText(uname);
    }
}
