package com.alctrain.android.rateapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements Runnable {

    EditText rmb;
    TextView show;
    Handler handler;
    public final String tag="rate";
    private float dollarrate=0.1f;
    private float eurorate=0.2f;
    private float wonrate=0.3f;
    private String updateDate="";

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
        updateDate=sp.getString("updateDate","");


        //获取当前系统时间
        Date today=Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr=sdf.format(today);




        Log.i(tag,"openone:dollarrate="+dollarrate);
        Log.i(tag,"openone:eurorate="+eurorate);
        Log.i(tag,"openone:wonrate="+wonrate);
        Log.i(tag,"openone:updateDate="+updateDate);
        Log.i(tag,"openone:todayStr="+todayStr);
        //判断时间
        if(!todayStr.equals((updateDate))){
            //open zixiancheng
            Log.i(tag,"需要更新");
            Thread t =new Thread(this);
            t.start();
        }else{
            Log.i(tag,"不需要更新");
        }



        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==5){
                    Bundle bd1=(Bundle) msg.obj;
                    dollarrate=bd1.getFloat("web-dollar");
                    eurorate=bd1.getFloat("web-euro");
                    wonrate=bd1.getFloat("web-won");
                    Log.i(tag,"openone:dollarrate="+dollarrate);
                    Log.i(tag,"openone:eurorate="+eurorate);
                    Log.i(tag,"openone:wonrate="+wonrate);
                    Log.i(tag,"openone:updateDate="+updateDate);


                    //保存已经更新的日期
                    SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor ed=sp.edit();
                    ed.putString("updateDate",todayStr);
                    ed.apply();



                    Toast.makeText(MainActivity.this,"汇率已更新",Toast.LENGTH_SHORT).show();
                }

                super.handleMessage(msg);
            }
        };
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

    public void open(View btn){
        Intent list=new Intent(this,MyList2.class);
        startActivity(list);
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
        Date today=Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr=sdf.format(today);
        ed.putString("updateDate",todayStr);
        ed.apply();

        super.onActivityResult(requestcode,resultcode,data);
    }
    public void run(){
        Log.i("str","zixiancheng yijing qi yong");
        for(int i=1;i<=3;i++){
            Log.i(tag,"i="+i);
            try{
                Thread.sleep(2000);}
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        Bundle bundle;

        bundle = getFromBOC();
        Message msg=handler.obtainMessage(5);
        msg.obj=bundle;
        handler.sendMessage(msg);


    }

    /**
     * 从网页获取数据
     * @return
     */
    private Bundle getFromBOC() {
        Bundle bundle =new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            Log.i(tag,doc.title());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements tables=doc.getElementsByTag("table");
        Element table6=tables.get(1);
        //get data from td
        Elements tds=table6.getElementsByTag("td");
        for(int i=0;i<tds.size();i=i+8){
            Element td1=tds.get(i);
            Element zhe=tds.get(i+5);
            Log.i(tag,td1.text()+" "+zhe.text());
            String name=td1.text();
            String rate=zhe.text();
            if("美元".equals(name)){
                bundle.putFloat("web-dollar",100f/Float.parseFloat(rate));
            }else if("欧元".equals(name)){
                bundle.putFloat("web-euro",100f/Float.parseFloat(rate));
            }else if("韩国元".equals(name)){
                bundle.putFloat("web-won",100f/Float.parseFloat(rate));
            }
        }
        return bundle;
    }
}

