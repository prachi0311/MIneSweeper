package com.example.prachipc.minesweeper;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PRACHI PC on 1/28/2017.
 */

public class MyButton extends Button {

    public MyButton(Context context) {
        super(context);
        count=0;
    }

    private int status;
    boolean counted;
     int count;
     int row,col;
    boolean longclick;
     boolean zero;
    ArrayList<MyButton> adj;
    public void initialise(){
        adj=new ArrayList<>();
    }

    public int getstatus() {
        return status;
    }

    public void setstatus(int status) {
        this.status = status;

    }



}


