package com.alctrain.android.rateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    EditText rmb;
    TextView show;
    public final String tag="rate";
    private float dollarrate=0.1f;
    private float eurorate=0.2f;
    private float wonrate=0.3f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rmb=(EditText) findViewById(R.id.rmb);
        show=(TextView) findViewById(R.id.show_out);
        //get the new rate from sp
        SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollarrate=sp.getFloat("dollar_rate",0.0f);
        eurorate=sp.getFloat("euro_rate",0.0f);
        wonrate=sp.getFloat("won_rate",0.0f);
    }

    public  void onclick(View btn){
        String str=rmb.getText().toString();
        float r=0;
        if(str.length()>0){
            r=Float.parseFloat(str);
        }else {
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }
        if(btn.getId()==R.id.button_dollar){
            show.setText(String.format("%.2f",r*dollarrate));
        }else if(btn.getId()==R.id.button_euro){
            show.setText(String.format("%.2f",r*eurorate));
        }else if(btn.getId()==R.id.button_won){
            show.setText(String.format("%.2f",r*wonrate));
        }

    }

    public void openone(View btn){
        Intent hello=new Intent(this,SecondActivity.class);
        hello.putExtra("dollar_rate_key",dollarrate);
        hello.putExtra("euro_rate_key",eurorate);
        hello.putExtra("won_rate_key",wonrate);
        Log.i(tag,"openone:dollarrate="+dollarrate);
        Log.i(tag,"openone:eurorate="+eurorate);
        Log.i(tag,"openone:wonrate="+wonrate);
        startActivityForResult(hello,1);
    }

    protected void onActivityResult(int requestcode,int resultcode,Intent data){

        Bundle bun=data.getExtras();
        dollarrate=bun.getFloat("new_dollar_key",0.1f);
        eurorate=bun.getFloat("new_euro_key",0.1f);
        wonrate=bun.getFloat("new_won_key",0.1f);
        SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        ed.putFloat("dollar_rate",dollarrate);
        ed.putFloat("euro_rate",eurorate);
        ed.putFloat("won_rate",wonrate);
        ed.apply();

        super.onActivityResult(requestcode,resultcode,data);
    }
}
