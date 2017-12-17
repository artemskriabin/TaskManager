package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private ArrayAdapter<String> adapter1,adapter2,adapter3;
    private ListView listView1,listView2,listView3;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.personal);
        tabSpec.setIndicator("Personal");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.home);
        tabSpec.setIndicator("Home");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.work);
        tabSpec.setIndicator("Work");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
        listView1 = (ListView) findViewById(R.id.listView1);
        listView2 = (ListView) findViewById(R.id.listView2);
        listView3 = (ListView) findViewById(R.id.listView3);
        updateData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Intent intent = new Intent(this, AddTask.class);
                startActivity(intent);
                break;
            case R.id.listView1:
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "Delete task");
    }

    @Override// опции у таксов, поля в таске, приоритет, календарь, дата, заголовок заметки, теги - категории, дома - учеба - работа, фильтровать по ним, фильтр по датам
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (tabHost.getCurrentTab() == 0){
            String str = Model.text_to_do1.get(acmi.position);
            for (int i=0; i<Model.tasks.size(); i++){
                if(Model.tasks.get(i).getTask_to_do().equals(str)){
                    Model.tasks.remove(i);
                }
            }
            Model.text_to_do1.remove(acmi.position);
            adapter1.notifyDataSetChanged();
        } else if(tabHost.getCurrentTab() == 1){
            String str = Model.text_to_do2.get(acmi.position);
            for (int i=0; i<Model.tasks.size(); i++){
                if(Model.tasks.get(i).getTask_to_do().equals(str)){
                    Model.tasks.remove(i);
                }
            }
            Model.text_to_do2.remove(acmi.position);
            adapter2.notifyDataSetChanged();
        } else {
            String str = Model.text_to_do3.get(acmi.position);
            for (int i=0; i<Model.tasks.size(); i++){
                if(Model.tasks.get(i).getTask_to_do().equals(str)){
                    Model.tasks.remove(i);
                }
            }
            Model.text_to_do3.remove(acmi.position);
            adapter3.notifyDataSetChanged();
        }
        Task.clearFile(this);
        for (int i = 0; i < Model.tasks.size(); i++) {
            Model.tasks.get(i).appendInFile(this);
        }
        return true;
    }
    private void updateData(){
        Model.text_to_do1 = new ArrayList();
        Model.text_to_do2 = new ArrayList();
        Model.text_to_do3 = new ArrayList();
        Model.priority1 = new ArrayList<>();
        Model.priority2 = new ArrayList<>();
        Model.priority3 = new ArrayList<>();
        Model.tasks = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(openFileInput("data.txt"));
            BufferedReader br = new BufferedReader(isr);
            String tmp = "";
            String text = "";
            while ((tmp = br.readLine()) != null) {
                if (tmp.isEmpty()) {
                    Task tasktmp = new Task(text);
                    Model.tasks.add(tasktmp);
                    text = "";
                } else {
                    if (text.isEmpty()) {
                        text = tmp;
                    } else {
                        text = text + "\n" + tmp;
                    }
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        try {
            InputStreamReader isr = new InputStreamReader(openFileInput("category.txt"));
            BufferedReader br = new BufferedReader(isr);
            String text;
            Category cat;
            int i = 0;
            while ((text = br.readLine()) != null) {
                if (text.isEmpty() == false) {
                    cat = Category.valueOf(text);
                    Task tmp = Model.tasks.get(i);
                    tmp.setCategory(cat);
                    Model.tasks.set(i, tmp);
                    if (cat == Category.HOME){
                        Model.text_to_do2.add(tmp.getTask_to_do());
                    } else if (cat == Category.WORK){
                        Model.text_to_do3.add(tmp.getTask_to_do());
                    } else {
                        Model.text_to_do1.add(tmp.getTask_to_do());
                    }
                    i++;
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        try {
            InputStreamReader isr = new InputStreamReader(openFileInput("priority.txt"));
            BufferedReader br = new BufferedReader(isr);
            String text;
            Boolean prior;
            int i = 0;
            while ((text = br.readLine()) != null) {
                if (text.isEmpty() == false) {
                    prior = Boolean.valueOf(text);
                    Task tmp = Model.tasks.get(i);
                    tmp.setPriority(prior);
                    Model.tasks.set(i, tmp);
                    Category cat = tmp.getCategory();
                    if (cat == Category.HOME){
                        Model.priority2.add(prior);
                    } else if (cat == Category.WORK){
                        Model.priority3.add(prior);
                    } else {
                        Model.priority1.add(prior);
                    }
                    i++;
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        adapter1 = new ArrayAdapter(this, R.layout.item_with_image,R.id.text1,Model.text_to_do1);
        listView1.setAdapter(adapter1);
        registerForContextMenu(listView1);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Model.text_to_do2);
        listView2.setAdapter(adapter2);
        registerForContextMenu(listView2);
        adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Model.text_to_do3);
        listView3.setAdapter(adapter3);
        registerForContextMenu(listView3);
    }
}

