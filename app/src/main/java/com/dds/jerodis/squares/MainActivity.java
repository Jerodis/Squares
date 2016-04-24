package com.dds.jerodis.squares;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.BoolRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private TextView timeView, scoreView;
    private int time, score, theSquare, theSquareID;
    private Boolean running;
    private String groupColor, differentColor, finalMessage;
    private String[] colorVals;
    private ArrayList<Button> squaresList = new ArrayList<Button>();
    private Random r = new Random();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setSquares();
        colorVals = getResources().getStringArray(R.array.color_array);

    }

    public void gameOver(String s){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_title)
                .setMessage("You scored " + Integer.toString(score) + "! Can you beat it?")
                .setCancelable(false)
                .setPositiveButton("Finished", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    dialog.cancel();
                }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setSquares(){
        squaresList.add((Button) findViewById(R.id.square0));
        squaresList.add((Button) findViewById(R.id.square1));
        squaresList.add((Button) findViewById(R.id.square2));
        squaresList.add((Button) findViewById(R.id.square3));
        squaresList.add((Button) findViewById(R.id.square4));
        squaresList.add((Button) findViewById(R.id.square5));
        squaresList.add((Button) findViewById(R.id.square6));
        squaresList.add((Button) findViewById(R.id.square7));
        squaresList.add((Button) findViewById(R.id.square8));
        squaresList.add((Button) findViewById(R.id.square9));
        squaresList.add((Button) findViewById(R.id.square10));
        squaresList.add((Button) findViewById(R.id.square11));
        squaresList.add((Button) findViewById(R.id.square12));
        squaresList.add((Button) findViewById(R.id.square13));
        squaresList.add((Button) findViewById(R.id.square14));
        squaresList.add((Button) findViewById(R.id.square15));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private CountDownTimer cdt = new CountDownTimer(10000, 1000) {
        public void onTick(long millisUntilFinished) {
            timeView.setText("" + millisUntilFinished / 1000);
        }

        public void onFinish() {
            timeView.setText(Integer.toString(0));
            running = false;
        }
    };

    public void reInit(){
        time = Integer.parseInt(timeView.getText().toString());
        if(time == 0){
            timeView.setText("10");
        }
        time = Integer.parseInt(timeView.getText().toString());
        cdt = new CountDownTimer(time*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeView.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timeView.setText(Integer.toString(0));
                btnStart.setText("Restart");
                running = false;
                gameOver(Integer.toString(score));
            }
        };
    }

    public void setGroupColor(){
        groupColor = "#";
        for(int i = 6; i > 0; i--){
            groupColor = groupColor + colorVals[new Random().nextInt(colorVals.length)];
        }
    }

    public void setDifferentColor(){
        differentColor = "#";
        for(int i = 6; i > 0; i--){
            differentColor = differentColor + colorVals[new Random().nextInt(colorVals.length)];
        }
    }

    public void initiateGame(String groupC, String differentC){

        theSquare = r.nextInt(15-0);
        theSquareID = squaresList.get(theSquare).getId();

        for(int i = squaresList.size()-1; i>=0; i--){
            if(i == theSquare) {
                squaresList.get(i).setBackgroundColor(Color.parseColor(differentC));
            }else{
                squaresList.get(i).setBackgroundColor(Color.parseColor(groupC));
            }
        }
    }

    public void init() {
        timeView = (TextView)findViewById(R.id.timerView);
        scoreView = (TextView)findViewById(R.id.scoreView);
        btnStart = (Button)findViewById(R.id.startGameButton);
        running = false;
        time = Integer.parseInt(timeView.getText().toString());
        score = 0;
        btnStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                setGroupColor();
                setDifferentColor();
                initiateGame(groupColor, differentColor);
                cdt.cancel();
                reInit();
                if(!running) {
                    running = true;
                    score = 0;
                    scoreView.setText(Integer.toString(score));
                    btnStart.setText("Stop Game");
                    cdt.start();
                }else{
                    running = false;
                    btnStart.setText("Start Game");
                    cdt.cancel();
                }

            }
        });

    }

    public void changeColors(){
        setGroupColor();
        setDifferentColor();

        theSquare = r.nextInt(15-0);
        theSquareID = squaresList.get(theSquare).getId();

        for(int i = squaresList.size()-1; i>=0; i--){
            if(i == theSquare) {
                squaresList.get(i).setBackgroundColor(Color.parseColor(differentColor));
            }else{
                squaresList.get(i).setBackgroundColor(Color.parseColor(groupColor));
            }
        }
    }

    public void isTheSquare(View view){
        if(running) {
            if (view.getId() == theSquareID) {
                score = score + 1;
                scoreView.setText(Integer.toString(score));
                changeColors();
            }
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
