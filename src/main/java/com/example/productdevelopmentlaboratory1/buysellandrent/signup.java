package com.example.productdevelopmentlaboratory1.buysellandrent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class signup extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button button_sbm;
    private Button button_forgot;
    public static SharedPreferences sp;
    private String s;

    FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Users");
    public void onBackPressed(View view) {
        Intent setIntent = new Intent(this,homepage.class);
        startActivity(setIntent);
        finish();
    }
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

    public void signin(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                username = (EditText) findViewById(R.id.editText_username);
                password = (EditText) findViewById(R.id.editText_password);
                button_sbm = (Button) findViewById(R.id.sign_in_button);
                button_forgot = (Button) findViewById(R.id.forgot_password_button);
                button_forgot.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         Intent in = new Intent(signup.this, forgotpassword.class);
                                                         startActivity(in);
                                                         finish();
                                                     }
                                                 }
                );
                button_sbm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(signup.this,"inside onclick",Toast.LENGTH_SHORT).show();

                        int flag = 0;
                        mAuth=FirebaseAuth.getInstance();
                        mAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if (mAuth.getCurrentUser().isEmailVerified()) {
                                        final Intent in = new Intent(signup.this, martpage.class);
                                        in.putExtra("uemail",username.getText().toString());
                                        startActivity(in);
                                    }else{
                                        Toast.makeText(signup.this,"please verify email",Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(signup.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        }

            });
        }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}
    @Override
    public void onBackPressed() {

        Intent in=new Intent(signup.this,homepage.class);
        startActivity(in);
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
        //    Toast.makeText(signup.this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (isOnline()) {
            //do whatever you want to do
            signin();
        } else {
            try {
                Intent in=new Intent(signup.this,NoInternet.class);
                startActivity(in);
               // finish();
            } catch (Exception e) {
              //  Log.d(Constants.TAG, "Show Dialog: " + e.getMessage());
            }
          }
        }

    }