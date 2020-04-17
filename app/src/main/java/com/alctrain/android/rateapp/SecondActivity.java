package com.alctrain.android.rateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {


     EditText dollartext;
     EditText eurotext;
     EditText wontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent main=getIntent();
        float dollar=main.getFloatExtra("dollar_rate_key",0.0f);
        float euro=main.getFloatExtra("euro_rate_key",0.0f);
        float won=main.getFloatExtra("won_rate_key",0.0f);
        dollartext=(EditText) findViewById(R.id.dollar_rate);
        eurotext=(EditText) findViewById(R.id.euro_rate);
        wontext=(EditText) findViewById(R.id.won_rate);
        dollartext.setText(String.valueOf(dollar));
        eurotext.setText(String.valueOf(euro));
        wontext.setText(String.valueOf(won));
    }


    public void save(View btn){
        Log.i("cfg","save:rate");
        float newdollar=Float.parseFloat(dollartext.getText().toString());
        float neweuro=Float.parseFloat(eurotext.getText().toString());
        float newwon=Float.parseFloat(wontext.getText().toString());
        Intent first=getIntent();
        Bundle ball=new Bundle();
        ball.putFloat("new_dollar_key",newdollar);
        ball.putFloat("new_euro_key",neweuro);
        ball.putFloat("new_won_key",newwon);
        first.putExtras(ball);
        setResult(2,first);
        finish();
    }
}
