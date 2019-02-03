package com.example.productdevelopmentlaboratory1.buysellandrent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class shoppage extends AppCompatActivity {

    public void onsigningin(){
        String s= getIntent().getStringExtra("Id1");
      //  TextView txtview = (TextView) findViewById(R.id.textView);
        //    txtview.setText("hi you are in shopping page "+s);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppage);
        onsigningin();
    }
}
