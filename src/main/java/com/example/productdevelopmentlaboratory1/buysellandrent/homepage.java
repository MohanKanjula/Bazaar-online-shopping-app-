package com.example.productdevelopmentlaboratory1.buysellandrent;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class homepage extends AppCompatActivity {
    private static Button button_login;
    private static Button button_signup;
    private static Button button_skipsignup;
    private VideoView mvideoview;
    public void onClickinglogin(){
        button_login=(Button)findViewById(R.id.Loginbutton);
        button_login.setOnClickListener(new View.OnClickListener(){
                                            @Override
                                            public void onClick(View v){
                                                        Intent sign=new Intent(homepage.this,signup.class);
                                                        startActivity(sign);
                                                    }
                                                }
        );
    }
    public void onskipping(){
        button_skipsignup=(Button)findViewById(R.id.skipbutton);
        button_skipsignup.setOnClickListener(new View.OnClickListener(){
                                                 @Override
                                                 public void onClick(View view) {
                                              //       Toast.makeText(homepage.this,"hello you clicked skip", Toast.LENGTH_LONG).show();
                                                     //   Intent in=new Intent("com.example.productdevelopmentlaboratory1.buysellandrent");
                                                     //   startActivity(in);
                                                 }
                                             }
        );
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void signup() {
        button_signup = (Button) findViewById(R.id.signupbutton);
        button_signup.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View view) {
                                              //   Toast.makeText(homepage.this,"hello you clicked signup", Toast.LENGTH_LONG).show();
                                                 Intent sgnup=new Intent(homepage.this,sign_up.class);
                                                 startActivity(sgnup);
                                             }
                                         }
        );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mvideoview=(VideoView)findViewById(R.id.videoView1);
        Uri uri =Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid);
        mvideoview.setVideoURI(uri);
        mvideoview.start();
        mvideoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

                                             @Override
                                             public void onPrepared(MediaPlayer mp) {
                                                  mp.setLooping(true);
                                                  mp.seekTo(0);
                                                  mp.start();
                                             }
                                         }
        );
        onClickinglogin();
        onskipping();
        signup();
    }
}
