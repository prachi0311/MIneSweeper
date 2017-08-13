package com.example.prachipc.minesweeper;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static android.view.ViewDebug.HierarchyTraceType.DRAW;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    MyButton buttons[][];
    LinearLayout rows[];
    TextView score;
    int high_score;
    int scoreCount;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    LinearLayout mainLayout;
    int actualbombcount=0;
    int flagcount=0;
    int n = 8;
    int flag=0;
    int bombCount=10;
    int win=1;
    int loose=0;
    int x[]={-1,-1,-1,0,1,1,1,0};
    int y[]={-1,0,1,1,1,0,-1,-1};
    boolean gameover;
    Random rand = new Random();
    int bomb = 1;
    int number = 2;
    int zero = 3;
    int m = 5;
    int arr_row[] = new int[m];
    int arr_col[] = new int[m];
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.boardLayout);
        //mainLayout.setBackgroundResource(R.drawable.sideborder);
        score=(TextView) findViewById(R.id.score);
         sp = getSharedPreferences("MineSweeper", MODE_PRIVATE);
        mp=MediaPlayer.create(this,R.raw.sound);
         high_score = sp.getInt("highscore",0);
         editor = sp.edit();
        setUpBoard();
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                if (buttons[i][j].getstatus() == bomb) {
//                    buttons[i][j].setText("*");
//                } else
//                    buttons[i][j].setText(buttons[i][j].count + "");
//            }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.newGame){
            resetBoard();
        }else if(id == R.id.easy){

                n=8;
                bombCount=10;
                setUpBoard();

        }else if(id == R.id.medium){
            n = 10;
            bombCount=25;
            setUpBoard();
        }else if(id == R.id.hard){
            n = 13;
            bombCount=35;
            setUpBoard();
        }
        return true;
    }

    private void resetBoard() {

       setUpBoard();
    }



    private void setUpBoard() {
        buttons = new MyButton[n][n];
        rows = new LinearLayout[n];
        gameover = false;
        mainLayout.removeAllViews();
        score.setText("SCORE : 0");
        scoreCount=0;

        for (int i = 0; i < n; i++) {
            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            params.setMargins(0,0, 0, 0);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rows[i]);
        }
          //  rows[0].addView(score);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buttons[i][j] = new MyButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(0, 0, 0, 0);
                buttons[i][j].row=i;
                buttons[i][j].col=j;
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setText("");
                buttons[i][j].setPadding(10,10,10,10);
          //      buttons[i][j].setBackgroundResource(R.drawable.bluetile);
                //buttons[i][j].setBackgroundColor(Color.DKGRAY);
                buttons[i][j].setstatus(9);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setOnLongClickListener(this);
                buttons[i][j].longclick=false;
                rows[i].addView(buttons[i][j]);
            }
        }
     //   buttons[0][0].setClickable(false);
        for (int i = 0; i < bombCount; i++) {
            int k = rand.nextInt(n) ;
            int l = rand.nextInt(n) ;
            buttons[k][l].setstatus(bomb);
           // addadjacent(k, l,buttons[k][l]);
           // adjacentincrement(buttons[k][l]);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (buttons[i][j].getstatus()==bomb)
                actualbombcount++;

            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
            if(buttons[i][j].getstatus()==bomb){
                adjacentincrement(buttons[i][j]);
            }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (buttons[i][j].getstatus() != bomb && buttons[i][j].getstatus() != number) {
                    buttons[i][j].setstatus(zero);
                   // buttons[i][j].zero=false;
                }
            }
        }


    }

//    private void addzeroadj(int row,int col,MyButton but) {
//        int a,b;
//       but.initialise();
////        if (row-1 >= 0 && col-1 >= 0 ) {
////            if(buttons[row - 1][col - 1].zero==false)
////            buttons[row][col].adj.add(buttons[row - 1][col - 1]);
////
////        }
////        if(row-1 >= 0 ) {
////            buttons[row][col].adj.add(buttons[row - 1][col]);
////        }
////        if (row-1 >= 0 &&  col+1 <= n-1) {
////            buttons[row][col].adj.add(buttons[row - 1][col + 1]);
////        }
////        if ( col-1 >= 0 ) {
////            buttons[row][col].adj.add(buttons[row][col - 1]);
////        }
////        if ( col+1 <= n-1) {
////            buttons[row][col].adj.add(buttons[row][col + 1]);
////        }
////        if ( row+1 <= n-1 && col-1 >= 0 ) {
////            buttons[row][col].adj.add(buttons[row + 1][col - 1]);
////        }
////        if ( row+1 <= n-1 ) {
////            buttons[row][col].adj.add(buttons[row + 1][col]);
////        }
////        if ( row +1 <= n-1  && col+1 <= n-1) {
////            buttons[row][col].adj.add(buttons[row + 1][col + 1]);
////        }
//        for(int i=0;i<8;i++){
//
//              a=row+x[i] ;
//              b=col+y[i];
//            if(a>=0 && a<n && b>=0 && b<n ) {
//
//                if(buttons[a][b].zero==false || buttons[a][b].getstatus()==number)
//                buttons[row][col].adj.add(buttons[a][b]);
//            }
//        }
//
//    }

//    private void addadjacent(int row, int col,MyButton but) {
//        but.initialise();
//        if (row-1 >= 0 && col-1 >= 0 ) {
//            buttons[row][col].adj.add(buttons[row - 1][col - 1]);
//        }
//        if(row-1 >= 0 ) {
//            buttons[row][col].adj.add(buttons[row - 1][col]);
//        }
//        if (row-1 >= 0 &&  col+1 <= n-1) {
//            buttons[row][col].adj.add(buttons[row - 1][col + 1]);
//        }
//        if ( col-1 >= 0 ) {
//            buttons[row][col].adj.add(buttons[row][col - 1]);
//        }
//        if ( col+1 <= n-1) {
//            buttons[row][col].adj.add(buttons[row][col + 1]);
//        }
//        if ( row+1 <= n-1 && col-1 >= 0 ) {
//            buttons[row][col].adj.add(buttons[row + 1][col - 1]);
//        }
//        if ( row+1 <= n-1 ) {
//            buttons[row][col].adj.add(buttons[row + 1][col]);
//        }
//        if ( row +1 <= n-1  && col+1 <= n-1) {
//            buttons[row][col].adj.add(buttons[row + 1][col + 1]);
//        }
//    }

    private void  adjacentincrement(MyButton b) {
        int a,c;
      //  b.zero=true;
        for(int i=0;i<8;i++){

            a=b.row+x[i] ;
            c=b.col+y[i];
            if(a>=0 && a<n && c>=0 && c<n && buttons[a][c].getstatus()!=bomb) {
                buttons[a][c].count++;
                buttons[a][c].setstatus(number);
            }
       }
    }


    @Override
    public void onClick(View v) {
        MyButton b = (MyButton) v;
       mp.start();

        if(gameover || b.longclick ){
            Log.i("gameover","gameover");
         //   Toast.makeText(this, "game over", Toast.LENGTH_SHORT).show();


            return;
        }

        if (b.getstatus() == zero) {
            b.setText("");
            b.setBackgroundColor(Color.WHITE);
            reveal(b);

        }
        else if(b.getstatus()==number){
            b.setText(b.count+"");
            b.setBackgroundColor(Color.WHITE);
            updateScore(b.count);
        }
        else if(b.getstatus()==bomb) {

               // b.setText("*");
                b.setBackgroundResource(R.drawable.bomb);
                gameover = true;
                Toast.makeText(this, "GAMEOVER!!!", Toast.LENGTH_SHORT).show();
            if(scoreCount>high_score)
                Log.i("score",String.valueOf(scoreCount));
            editor.putInt("highscore",scoreCount);
            editor.commit();
        }

           if(result()){
               Toast.makeText(this,"YOU WIN!!!!",Toast.LENGTH_SHORT).show();
           }


    }

    private void updateScore(int count) {
        scoreCount+=count;
        score.setText("SCORE : "+scoreCount);

    }

    private boolean result() {
//        int flagcount=0;
//        for(int i=0;i<n;i++){
//            for(int j=0;j<n;j++){
////                if(!buttons[i][j].getBackground().equals(Color.WHITE) && (buttons[i][j].getstatus()==number || buttons[i][j].getstatus()==zero)){
////                    bleh++;
////                    break;
//                  flagcount++;
//
//                }
//            }
        if(flagcount==actualbombcount)
            return true;
        else return false;
        }
//        if(bleh==0){
//            return true;
//        }
       // else return false;


    private void reveal(MyButton b) {
        int a,c;
        b.zero=true;

        for(int i=0;i<8;i++){

            a=b.row+x[i] ;
            c=b.col+y[i];
            if(a>=0 && a<n && c>=0 && c<n ) {

                if( buttons[a][c].getstatus()==number){
                    buttons[a][c].setText(buttons[a][c].count+"");
                    buttons[a][c].setBackgroundColor(Color.WHITE);
                    if(!buttons[a][c].counted) {
                        updateScore(buttons[a][c].count);
                        buttons[a][c].counted=true;
                    }

                }
                else if(buttons[a][c].getstatus()==zero && buttons[a][c].zero==false){

                    buttons[a][c].setText("");
                    buttons[a][c].setBackgroundColor(Color.WHITE);
                    reveal(buttons[a][c]);
                }
            }
        }

//      for(int i=0;i<b.adj.size();i++){
//          if(b.adj.get(i).getstatus()==number){
//              b.adj.get(i).setText(b.adj.get(i).count+"");
//              b.adj.get(i).setBackgroundColor(Color.WHITE);
//          }
//          else if(b.adj.get(i).getstatus()==bomb){
//              b.adj.get(i).setText("*");
//              b.adj.get(i).setBackgroundColor(Color.WHITE);
//          }
//          else if(b.adj.get(i).getstatus()==zero){
//              b.adj.get(i).setText("0");
//              b.adj.get(i).setBackgroundColor(Color.WHITE);
//                reveal(b.adj.get(i));
//          }
//
//          }
      }

    @Override
    public boolean onLongClick(View v) {
        MyButton b=(MyButton)v;
        if(b.longclick==false) {
            //b.setText("F");
            b.setBackgroundResource(R.drawable.flag);
         //   b.setTextColor(Color.BLACK);
         //  b.setLongClickable(true);
          //  b.setClickable(false);
            flagcount++;
            b.longclick=true;

        }

          else {
            b.setText("");
           // b.setClickable(true);
            //b.setLongClickable(true);

            b.longclick=false;
            flagcount--;

        }
        return true;
    }
}







