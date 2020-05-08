package com.alctrain.android.rateapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{

    String data[]={"one","two","three"};
    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemadapter;
    private int msgWhat=7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);因为父类里有布局
        List<String> list1=new ArrayList<String>();
        for(int i =1;i<100;i++){
            list1.add("item"+i);
        }


        ListAdapter adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);
        setListAdapter(adapter);//只有当前类继承于ListActivity才能调用这个方法，这个方法早已写好




       


    }

    @Override
    public void run(){
        List<String> retlist = new ArrayList<String>();

//获取网络数据
    Message msg=handler.obtainMessage(7);
    msg.obj=retlist;
    handler.sendMessage(msg);


    }

}
