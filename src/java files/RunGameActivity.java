package com.example.antivenom.rby;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RunGameActivity extends AppCompatActivity {
    //View Objects
    Button redButton;
    Button blueButton;
    Button yellowButton;
    TextView mainStuff;
    TextView temp;
    TextView scoreText;
    TextView lifeText;
    TextView timerText;
    public static  int count;
    private CountDownTimer countDownTimer;
    public static int life;
    MediaPlayer player;


    /////////////////////////////////////////////////////For random//////////////////////


    public void randomizeTextAndColor(){

        final int min =1;
        final int max=7;


        final Random randomColor = new Random();
        final Random randomText = new Random();
        int rc;
        int rt;
        rc=randomColor.nextInt(max - min + 1) + min;
        rt=randomText.nextInt(max-min+1)+min;

        if(rc==1||rc==5){
            mainStuff.setTextColor(getResources().getColor(R.color.Red));
        }
        else if(rc==2||rc==4){
            mainStuff.setTextColor(getResources().getColor(R.color.Blue));
        }
        else{
            mainStuff.setTextColor(getResources().getColor(R.color.Yellow));
        }


        if(rt==1||rt==6){
            mainStuff.setText("Red");

        }
        else if(rt==2||rt==3){
            mainStuff.setText("Blue");
        }
        else{
            mainStuff.setText("Yellow");
        }
    }
    /////////////////////////////////////////////////////Wrong Button Pressed//////////////////////////////////////////////////////////////////

    public void wrongButtonPressed(){

        life = life-1;
        lifeText.setText("Life:" + life);
        if(count<0)
        {
            count=0;
            gameOver();
        }
        if(life==0){
            gameOver();
        }
    }

///////////////////////////////////On Create ////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_game);

        ///////////////////////////////////Reference objects and initialization/////////////////////////////////////////////////////////////
        redButton = (Button) findViewById(R.id.redButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        yellowButton =( Button) findViewById(R.id.yellowButton);
        mainStuff = (TextView) findViewById(R.id.mainStuff);
        scoreText = (TextView) findViewById(R.id.scoreText);
        lifeText = (TextView) findViewById(R.id.lifeText);
        timerText = (TextView) findViewById(R.id.timeText);
        start();
        countDownTimer.start();
        count =0;
        life = 2;
        lifeText.setText("Life: "+ life);
        randomizeTextAndColor();

        player=MediaPlayer.create(RunGameActivity.this,R.raw.abcd);
        player.start();


        /////////////////////////////////////////Listener list///////////////////////////////////////////////////////////////////////////
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = (TextView) findViewById(R.id.mainStuff);
                int i = temp.getCurrentTextColor();
                if (i == -65536) {
                    count = count + 1;
                    scoreText.setText("Score: " + count);
                }
                else
                {
                    count= count -1;
                    scoreText.setText("Score: " +count );
                    wrongButtonPressed();
                }
                randomizeTextAndColor();
            }
        });



        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = (TextView) findViewById(R.id.mainStuff);
                int i = temp.getCurrentTextColor();
                if(i == -16776961) {
                    count = count + 1;
                    scoreText.setText("Score: " + count);
                }

                else
                {
                    count= count -1;
                    scoreText.setText("Score: " +count);
                    wrongButtonPressed();
                }
                randomizeTextAndColor();
            }
        });



        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = (TextView) findViewById(R.id.mainStuff);
                int i = temp.getCurrentTextColor();
                if (i == -256) {
                    count = count + 1;
                    scoreText.setText("Score: " + count);
                }

                else
                {
                    count= count -1;
                    scoreText.setText("Score: " + count);
                    wrongButtonPressed();
                }
                randomizeTextAndColor();
            }

        });
        /////////////////////////////////////////////////////////////////////////////////////////////


    }

    /////////////////////////////////Finish of on create method/////////////////////////////////////


    //////////////////////////////////On Back Press////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RunGameActivity.this);

        builder.setTitle("Quit Alert ???").setMessage("Do you really want to quit"
        ).setPositiveButton("OK ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(RunGameActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }).setNegativeButton("Cancel",null);

        AlertDialog alert = builder.create();
        alert.show();

    }
    ////////////////////////////////////////Menu///////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.run_game_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case(R.id.settings):
                Toast.makeText(this, "You can now edit settings", Toast.LENGTH_SHORT).show();

            case(R.id.quit):
                RunGameActivity.super.onBackPressed();

            case(R.id.about):
                Toast.makeText(this, "Contact me at: aavashkhatri@gmail.com \n" +
                        "Call me at 9842875805", Toast.LENGTH_SHORT).show();


        }
        return true;
    }


    /////////////////////////////////////Getting Out /////////////////////////////////////////////////////////////////
    public void gameOver(){


        Intent intent = new Intent(RunGameActivity.this,GameOverActivity.class);
        intent.putExtra("score",Integer.toString(count));
        startActivity(intent);
        player.stop();



    }


    //////////////////////////////////// For CountDown Timer///////////////////////////////////////////////////
    public void start(){
        countDownTimer = new CountDownTimer(60*1000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText("Timer : "+ millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                timerText.setText("Finished ");
                gameOver();
            }
        };}

}




