package com.example.productdevelopmentlaboratory1.buysellandrent;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class agreementpage extends AppCompatActivity {
    String sendercontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreementpage);
        TextView details = findViewById(R.id.textView_details);
        Button button_agree = findViewById(R.id.button_agree);
        final String value = getIntent().getStringExtra("Id1");
        final String productname = getIntent().getStringExtra("send");
        details.setText(value);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("products").child("sellitems");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //  String sendercontact;
                          for(DataSnapshot i : dataSnapshot.getChildren()){
                                  String pname=i.child("productname").getValue(String.class);
                                  if(pname!=null) {
                                      if (pname.equals(productname)) {
                                          Toast.makeText(getApplicationContext(), i.child("productname").getValue(String.class), Toast.LENGTH_SHORT).show();
                                          sendercontact =pname;
                                          break;
                                      }
                                  }else{
                                      Toast.makeText(getApplicationContext(),"sorry bro", Toast.LENGTH_SHORT).show();
                                  }
                          }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        button_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject="regarding buying of posted product";
                String message ="Iam interested in buying the product "+value;
                Intent intent = new Intent(Intent.ACTION_SEND);
                Toast.makeText(getApplicationContext(),sendercontact,Toast.LENGTH_SHORT).show();
                intent.putExtra(Intent.EXTRA_EMAIL ,sendercontact);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });
    }
}
