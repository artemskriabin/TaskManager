package com.example.taskmanager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.*;


import android.content.Context;

public class Task{
    private static int counter = 0;
    private String task_to_do;
    private int num_of_task;
    private boolean priority = false;
    private Category category;
    public Task(String s){
        num_of_task = counter;
        counter++;
        task_to_do = s;
        category = Category.PERSONAL;
    }
    public Task(String s, boolean p){
        this(s);
        priority = p;
    }
    public Task(String s, Category c){
        this(s);
        category = c;
    }
    public Task(String s, boolean p, Category c){
        this(s,p);
        category = c;
    }
    public String getTask_to_do(){
        return  task_to_do;
    }
    public void setPriority(Boolean b){
        priority = b;
    }
    public Category getCategory(){
        return category;
    }
    public static void clearFile(Context ctx){
        try {
            OutputStreamWriter fos = new OutputStreamWriter(ctx.openFileOutput("data.txt", MODE_PRIVATE));
            BufferedWriter bw = new BufferedWriter(fos);
            String str = "";
            bw.write(str);
            bw.flush();
            bw.close();
            fos = new OutputStreamWriter(ctx.openFileOutput("category.txt", MODE_PRIVATE));
            bw = new BufferedWriter(fos);
            str = "";
            bw.write(str);
            bw.flush();
            bw.close();
            fos = new OutputStreamWriter(ctx.openFileOutput("important.txt", MODE_PRIVATE));
            bw = new BufferedWriter(fos);
            str = "";
            bw.write(str);
            bw.flush();
            bw.close();
        } catch (IOException e) {

        }
    }
    public void appendInFile(Context ctx){
        try {
            OutputStreamWriter fos = new OutputStreamWriter(ctx.openFileOutput("data.txt", MODE_APPEND));
            BufferedWriter bw = new BufferedWriter(fos);
                bw.append(task_to_do + "\n");
                bw.newLine();
            bw.flush();
            bw.close();
            fos = new OutputStreamWriter(ctx.openFileOutput("category.txt", MODE_APPEND));
            bw = new BufferedWriter(fos);
            bw.append(category.toString());
            bw.newLine();
            bw.flush();
            bw.close();
            fos = new OutputStreamWriter(ctx.openFileOutput("important.txt", MODE_APPEND));
            bw = new BufferedWriter(fos);
            bw.append(String.valueOf(priority));
            bw.newLine();
            bw.flush();
            bw.close();
        } catch (IOException e) {

        }
    }
    public void setCategory(Category c){
        category = c;
    }
}