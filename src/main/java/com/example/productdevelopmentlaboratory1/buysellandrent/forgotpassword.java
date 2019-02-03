package com.example.productdevelopmentlaboratory1.buysellandrent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {
    private EditText email;
    private Button button_sbm;
    FirebaseAuth firebaseAuth;
    public void generateotp(){
                email=(EditText)findViewById(R.id.editText_email);
                button_sbm=(Button)findViewById(R.id.button_otp);
                firebaseAuth=FirebaseAuth.getInstance();
                button_sbm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                             firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                         if(task.isSuccessful()){
                                             Toast.makeText(forgotpassword.this,"Password sent to your registered email",Toast.LENGTH_SHORT).show();
                                         }else{
                                             Toast.makeText(forgotpassword.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                         }
                                 }
                             });
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        generateotp();
    }
}
