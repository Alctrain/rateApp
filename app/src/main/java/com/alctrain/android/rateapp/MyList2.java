package com.alctrain.android.rateapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MyList2 extends ListActivity {



    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        this.setListAdapter(listItemadapter);

    }
    private void initListView(){
        listItems=new ArrayList<HashMap<String, String>>();
        for(int i=0;i<10;i++) {
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("ItemTitle", "rate " + i);
            map.put("ItemDetail","Detail"+i);
            listItems.add(map);
        }
        listItemadapter=new SimpleAdapter(this,listItems,R.layout.list_item,//此布局文件用于描述一行数据的表示方式
                new String[]{"ItemTitle","Itemdetails"},
                new int[]{R.id.itemTitle,R.id.itemDetail});
    }
}
