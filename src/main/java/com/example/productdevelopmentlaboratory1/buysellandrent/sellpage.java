package com.example.productdevelopmentlaboratory1.buysellandrent;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;

public class sellpage extends AppCompatActivity {
    Button button_upload;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("products");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellpage);
        button_upload = (Button) findViewById(R.id.button_submit);
        button_upload.setOnClickListener(new View.OnClickListener(){
        //reference variables

            private EditText product_name;
            private EditText price;
            private EditText description;
            private EditText timeperiod;
            private RadioButton sell_button;
            private RadioButton rent_button;

            @Override
            public void onClick(View v) {
                     product_name = (EditText)findViewById(R.id.editText_name);
                     price = (EditText)findViewById(R.id.editText_price);
                     description = (EditText)findViewById(R.id.editText_desc);
                     timeperiod = (EditText)findViewById(R.id.editText_time);
                     sell_button = (RadioButton)findViewById(R.id.radioButton_sell);
                     rent_button = (RadioButton)findViewById(R.id.radioButton_rent);
                     String pname,cost,desc,time,contact;
                     pname = product_name.getText().toString();
                     cost = price.getText().toString();
                     time = timeperiod.getText().toString();
                     desc = description.getText().toString();
                     Intent in = getIntent();
                     contact=in.getStringExtra("Id1");
                     if(!pname.isEmpty()&&!cost.isEmpty()&&!desc.isEmpty()){
                             if(sell_button.isChecked()){
                                      ref=ref.child("sellitems");
                                 HashMap<String,String> datamap = new HashMap<String,String>();
                                 datamap.put("productname",pname);
                                 datamap.put("cost",cost);
                                 datamap.put("description",desc);
                                 datamap.put("time",time);
                                 datamap.put("flag","sell");
                                 datamap.put("contact",contact);
                                 ref.push().setValue(datamap);
                                 Toast.makeText(sellpage.this,"entered item is posted successfully", Toast.LENGTH_SHORT).show();
                             }else if(rent_button.isChecked()){
                                 ref=ref.child("rentitems");
                                 HashMap<String,String> datamap = new HashMap<String,String>();
                                 datamap.put("productname",pname);
                                 datamap.put("cost",cost);
                                 datamap.put("description",desc);
                                 datamap.put("time",time);
                                 datamap.put("flag","rent");
                                 datamap.put("contact",contact);
                                 ref.push().setValue(datamap);
                                 Toast.makeText(sellpage.this,"entered item is posted successfully", Toast.LENGTH_SHORT).show();
                             }else{
                                 Toast.makeText(sellpage.this,"Please fill in required details", Toast.LENGTH_SHORT).show();
                             }
                     }else{
                         Toast.makeText(sellpage.this,"Please fill in basic details", Toast.LENGTH_SHORT).show();
                     }
            }
        });
    }
}