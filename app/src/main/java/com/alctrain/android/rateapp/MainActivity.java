package com.alctrain.android.rateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText rmb;
    TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rmb=(EditText) findViewById(R.id.rmb);
        show=(TextView) findViewById(R.id.show_out);
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
        float val=r*(1/6.7f);
        show.setText(String.valueOf(val));
    }else if(btn.getId()==R.id.button_euro){
        float val=r*(1/11.0f);
        show.setText(String.valueOf(val));
    }else if(btn.getId()==R.id.button_won){
        float val=r*500;
        show.setText(String.valueOf(val));
    }

    }

    public void openone(View btn){
        Intent hello=new Intent(this,SecondActivity.class);
        startActivity(hello);
    }
}
