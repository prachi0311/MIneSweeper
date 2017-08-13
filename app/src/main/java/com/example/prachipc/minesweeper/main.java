package com.example.prachipc.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class main extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button startgame=(Button) findViewById(R.id.startgame);
        startgame.setOnClickListener(this);
        TextView highscoredis=(TextView)findViewById(R.id.score);


        SharedPreferences sp = getSharedPreferences("MineSweeper", MODE_PRIVATE);
        Integer score = sp.getInt("highscore",0);

        highscoredis.setText(score+"");


    }

    @Override
    public void onClick(View v) {
        Intent i=new Intent();
        i.setClass(this,MainActivity.class);
        startActivity(i);
    }
}
