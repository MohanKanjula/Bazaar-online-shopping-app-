package com.example.productdevelopmentlaboratory1.buysellandrent;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class sign_up extends AppCompatActivity {
    private  EditText user_name;
    private  EditText user_email;
    private  EditText user_password;
    private  EditText user_phonenumber;
    private  Button button_sbm;
    private DatabaseReference Database;
    private ImageView imageview;
    private FirebaseAuth mAuth;
     ArrayList<String> alist;
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public void storingdata(){
          DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();

          firebase.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(DataSnapshot dataSnapshot) {

              }

              @Override
              public void onCancelled(DatabaseError databaseError) {

              }
          });
    }
    // UI function


    public void onclickbutton(){
           // int exi
           button_sbm=(Button)findViewById(R.id.registerbutton);

           imageview = (ImageView)findViewById(R.id.imageview_backbutton);
           mAuth = FirebaseAuth.getInstance();
           imageview.setOnClickListener(new View.OnClickListener(){

                                            @Override
                                            public void onClick(View v) {
                                                Intent in=new Intent(sign_up.this,homepage.class);
                                                startActivity(in);
                                                finish();
                                            }
                                        }
           );
           button_sbm.setOnClickListener(new View.OnClickListener(){
                             @Override
                             public void onClick(View v) {


                                 final String name, email, password, phonenumber;
                                 name = user_name.getText().toString();
                                 email = user_email.getText().toString();
                                 password = user_password.getText().toString();
                                 phonenumber = user_phonenumber.getText().toString();
                                 int y=0,phone_flag=0,exist_flag=0,z=0;
                                 String str="@gmail.com";
                                 if(!email.isEmpty()) {
                                     if (!email.substring(email.length() - str.length(), email.length()).equals(str))
                                         y = 1;
                                 }
                                 if(phonenumber.length()!=10)
                                        phone_flag=1;
                                 for(int i=0;i<alist.size();i++){
                                        if(alist.get(i).equals(email))
                                               exist_flag=1;
                                 }
                                 if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phonenumber.isEmpty()) {
                                     if(password.length()>=6&&y==0&&phone_flag==0&&exist_flag==0&&z==0) {

                                         // email validation
                                         mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                             @Override
                                             public void onComplete(@NonNull Task<AuthResult> task) {
                                                     if(task.isSuccessful()){
                                                           mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                       if(task.isSuccessful()){
                                                                           String userid=mAuth.getCurrentUser().getUid();
                                                                           Toast.makeText(sign_up.this,"mail has been sent to your email id for conformation. Please verify it",Toast.LENGTH_SHORT).show();
                                                                           Database = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                                                                           HashMap<String, String> datamap = new HashMap<>();
                                                                           datamap.put("username", name);
                                                                           datamap.put("email", email);
                                                                           datamap.put("password", password);
                                                                           datamap.put("phone number", phonenumber);
                                                                           Database.setValue(datamap);
                                                                           Toast.makeText(sign_up.this, "User successfully registered", Toast.LENGTH_SHORT).show();
                                                                           Intent in=new Intent(sign_up.this,signup.class);
                                                                           startActivity(in);
                                                                       }else{
                                                                           Toast.makeText(sign_up.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                       }
                                                               }
                                                           });
                                                     }else{
                                                         Toast.makeText(sign_up.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                     }
                                             }
                                         });
                                     }else{
                                         if(exist_flag==1)
                                             Toast.makeText(sign_up.this, "email already taken register with different email" , Toast.LENGTH_SHORT).show();
                                         if(phone_flag==1)
                                             Toast.makeText(sign_up.this, "Enter valid phone number" , Toast.LENGTH_SHORT).show();
                                         if(y==1)
                                             Toast.makeText(sign_up.this, "Enter valid email" , Toast.LENGTH_SHORT).show();
                                         if(password.length()<6)
                                             Toast.makeText(sign_up.this, "password should be atleast 6 characters length", Toast.LENGTH_SHORT).show();
                                     }
                                 } else{
                                     if(password.isEmpty())
                                     Toast.makeText(sign_up.this, "password is mandatory", Toast.LENGTH_LONG).show();
                                     if(email.isEmpty())
                                     Toast.makeText(sign_up.this, "email is mandatory", Toast.LENGTH_LONG).show();
                                     if(name.isEmpty())
                                     Toast.makeText(sign_up.this, "User Name is mandatory", Toast.LENGTH_LONG).show();
                                     if(phonenumber.isEmpty())
                                     Toast.makeText(sign_up.this, "phone number is mandatory", Toast.LENGTH_LONG).show();
                                 }
                             }
                             }
           );
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
          //  Toast.makeText(sign_up.this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user_email=findViewById(R.id.editText_email);
        user_name=findViewById(R.id.editText_name);
        user_password=findViewById(R.id.editText_password);
        user_phonenumber=findViewById(R.id.editText_phonenumber);
        if (isOnline()) {
            alist = new ArrayList<String>();
            storingdata();
           onclickbutton();
        } else {
            try {
                Intent in=new Intent(sign_up.this,NoInternet.class);
                startActivity(in);
              finish();
            } catch (Exception e) {
            }
        }
    }
}
