package com.example.productdevelopmentlaboratory1.buysellandrent;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class solditems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solditems);
        final ArrayList<String> arraylist = new ArrayList<String>();
        final ListView productslist = (ListView)findViewById(R.id.listview_details);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("products").child("sellitems");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(solditems.this,android.R.layout.simple_list_item_1,arraylist);
        productslist.setAdapter(arrayAdapter);
        String s;
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //checking that items posted by same user should not be displayed
                // retrieving content from previous intent
                s = getIntent().getStringExtra("Id1");
                if(s.equalsIgnoreCase(dataSnapshot.child("contact").getValue(String.class))) {
                    String value = "Name : " + dataSnapshot.child("productname").getValue(String.class) + "\n"
                            + "Cost : ₹ " + dataSnapshot.child("cost").getValue(String.class) + "\n"
                            + "Description :" + dataSnapshot.child("description").getValue(String.class);
                    arraylist.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
