package com.example.productdevelopmentlaboratory1.buysellandrent;

import android.content.Intent;
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

public class rentactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentactivity);
        final ArrayList<String> arraylist = new ArrayList<String>();
        final ListView productslist = (ListView)findViewById(R.id.listview_details);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("products").child("rentitems");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rentactivity.this,android.R.layout.simple_list_item_1,arraylist);
        productslist.setAdapter(arrayAdapter);
        String s;
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                s = getIntent().getStringExtra("Id1");
                if(!s.equalsIgnoreCase(dataSnapshot.child("contact").getValue(String.class))) {
                    String value = "Name : " + dataSnapshot.child("productname").getValue(String.class) + "\n"
                            + "Cost : ₹ " + dataSnapshot.child("cost").getValue(String.class) + "\n"
                            + "Available for : " + dataSnapshot.child("flag").getValue(String.class) + "\n"
                            + "Description :" + dataSnapshot.child("description").getValue(String.class);
                    arraylist.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }
                productslist.setClickable(true);
                productslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // to be continued

                        Toast.makeText(rentactivity.this,"hello bro",Toast.LENGTH_SHORT).show();
                        Object o = productslist.getItemAtPosition(position);
                        String str=(String)o;
                        String postercontact="a",sendcontact;
                        for(int i=0;i<str.length();i++){
                            if(str.charAt(i)!='.'){
                                postercontact+=str.charAt(i);
                            }else{
                                break;
                            }
                        }
                        sendcontact = postercontact.substring(7,postercontact.length());
                        Intent in = new Intent(rentactivity.this,agreementpage.class);
                        in.putExtra("Id1",str);
                        in.putExtra("send",sendcontact);
                        startActivity(in);
                    }
                });
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
