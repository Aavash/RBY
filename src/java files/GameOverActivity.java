package com.example.antivenom.rby;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverActivity extends AppCompatActivity {
    Button restartButton;
    TextView highScoreText;
    TextView yourScoreText;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        ///////////////////////////////////Receiving and setting scores//////////////////////////////////
        Intent intent = getIntent();
        String score = intent.getStringExtra("score");
        int count = Integer.parseInt(score); // int form of score

        int highscore = getScore();  //getting previous highscore

        if(count > highscore )   // if score is higher than previous highscore then new ones
        {
            saveScore(count);           // save count as new highscore
            highscore= count;           // highscore updated to count
        }


        highScoreText = (TextView) findViewById(R.id.highScoreText);
        yourScoreText = (TextView) findViewById(R.id.yourScoreText);
        highScoreText.setText("Highscore: " + highscore );
        yourScoreText.setText("Your Score:" + count);

        ///////////////////////////////////Restart Button///////////////////////////////////////////////////
        restartButton = (Button) findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this,RunGameActivity.class);
                startActivity(intent);
            }
        });
    }

    ///////////////////////////////ON BACK PRESS//////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameOverActivity.this);

        builder.setTitle("Quit Alert ???").setMessage("Do you really want to quit"
        ).setPositiveButton("OK ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(intent);

            }
        }).setNegativeButton("Cancel",null);

        AlertDialog alert = builder.create();
        alert.show();
        super.onBackPressed();

    }


    public void saveScore(int count ){
        SharedPreferences sharedPref= getSharedPreferences("HighScoreInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("Highscore",count);
        editor.apply();

        Toast.makeText(this, "New Highscore! Congrats!!!!", Toast.LENGTH_SHORT).show();
    }


    public int getScore(){
        SharedPreferences sharedPref = getSharedPreferences("HighScoreInfo",Context.MODE_PRIVATE);
        int highscore = sharedPref.getInt("Highscore",0);
        return highscore;
    }

}
