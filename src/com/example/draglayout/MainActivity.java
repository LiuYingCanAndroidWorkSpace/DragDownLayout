package com.example.draglayout;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.draglayout.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = View.inflate(MainActivity.this, R.layout.header, null);                                        //头部View

        View content = View.inflate(MainActivity.this, R.layout.content_view, null);
        ListView listView = new ListView(MainActivity.this);
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this,  
                android.R.layout.simple_list_item_activated_1,  
                android.R.id.text1, list));

        DragDownListViewLayout aboveLayout = new DragDownListViewLayout(MainActivity.this, 200, 1280, listView);              //Context, 头部高度， content高度
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        aboveLayout.setLayoutParams(params);

        WrapLayout wrapLayout = new WrapLayout(MainActivity.this, view, 200, aboveLayout);
        setContentView(wrapLayout);
    }

}
