package com.example.taskmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AddTask extends AppCompatActivity {
    private CheckBox checkBox;
    private RadioButton radioButton1, radioButton2, radioButton3;
    private Category category = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText editText = (EditText)findViewById(R.id.editText);
        String str = editText.getText().toString();
        if (radioButton2.isChecked()){
            category = Category.HOME;
            Model.priority2.add(checkBox.isChecked());
            Model.text_to_do2.add(str);
        } else if (radioButton3.isChecked()){
            category = Category.WORK;
            Model.priority3.add(checkBox.isChecked());
            Model.text_to_do3.add(str);
        } else {
            category = Category.PERSONAL;
            Model.priority1.add(checkBox.isChecked());
            Model.text_to_do1.add(str);
        }
        Task tmp = new Task(str, checkBox.isChecked(),category);
        tmp.appendInFile(this);
        Model.tasks.add(tmp);
        finish();
        return true;
    }
}
