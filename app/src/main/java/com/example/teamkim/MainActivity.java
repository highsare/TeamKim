package com.example.teamkim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.teamkim.customview.Stone;
import com.example.teamkim.ingame.InGameActivity;

// TODO: 2018-03-10 [Main] replace Views

public class MainActivity extends AppCompatActivity {

    public static boolean newGame,isPosition,isEnd,isLoad;
    public static int viewNum,endNum,turn,spin;
    public static int[] p1ScoreBoard,p2ScoreBoard;
    public static String player1_Name,player2_Name;
    public static float[] p1_sListX,p1_sListY,p2_sListX,p2_sListY;
    public static float aimX,aimY;
    public static String first;
    public static Stone currentStone;

    AudioAttributes audioAttributes;
    SoundPool soundPool;
    Button btn_toSetting;

    Bitmap redStone,yelStone;

    Intent toInGameActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        toInGameActivity = new Intent(this,InGameActivity.class);

        audioAttributes =  new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(1).build();
        soundPool.load(MainActivity.this,R.raw.bgm_main,1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                soundPool.play(1, 1, 1, 1, -1, 1);
            }
        });
        btn_toSetting = findViewById(R.id.btn_toSetting);

        newGame = true;
        isPosition = false;
        isEnd = false;
        isLoad = false;

        viewNum = 1;
        endNum = 1;
        turn = 1;
        spin = 0;

        p1ScoreBoard = new int[4];
        p2ScoreBoard = new int[4];

        for (int i = 0 ; i < 4; i++){
            p1ScoreBoard[i] = 0;
            p2ScoreBoard[i] = 0;
        }

        player1_Name = "Team Kim";
        player2_Name = "Team Fuj";

        p1_sListX = new float[4];
        p1_sListY = new float[4];
        p2_sListX = new float[4];
        p2_sListY = new float[4];

        for (int i = 0; i < 4; i++){
            p1_sListX[i] = -50;
            p1_sListY[i] = -50;
            p2_sListX[i] = -50;
            p2_sListY[i] = -50;
        }

        aimX = 0;
        aimY = 0;

        first = "PLAYER1";

        redStone = BitmapFactory.decodeResource(getResources(),R.drawable.red_stone);
        yelStone = BitmapFactory.decodeResource(getResources(),R.drawable.yel_stone);

        currentStone = new Stone(redStone,0,0,-50,-50);
    }

    public void startGame(@Nullable View view){
        soundPool.stop(1);

        toInGameActivity.putExtra("newGame", newGame);
        toInGameActivity.putExtra("isEnd", isEnd);
        toInGameActivity.putExtra("isPosition", isPosition);
        toInGameActivity.putExtra("isLoad", isLoad);

        toInGameActivity.putExtra("viewNum", viewNum);
        toInGameActivity.putExtra("endNum", endNum);
        toInGameActivity.putExtra("turn",turn);
        toInGameActivity.putExtra("spin", spin);

        toInGameActivity.putExtra("player1_Name", player1_Name);
        toInGameActivity.putExtra("player2_Name", player2_Name);

        toInGameActivity.putExtra("p1_sListX", p1_sListX);
        toInGameActivity.putExtra("p1_sListY", p1_sListY);
        toInGameActivity.putExtra("p2_sListX", p2_sListX);
        toInGameActivity.putExtra("p2_sListY",p2_sListY);

        toInGameActivity.putExtra("aimX", aimX);
        toInGameActivity.putExtra("aimY", aimY);

        toInGameActivity.putExtra("first",first);

        startActivity(toInGameActivity);
    }

    Handler turnHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            toInGameActivity.putExtra("newGame", newGame);
            toInGameActivity.putExtra("isEnd", isEnd);
            toInGameActivity.putExtra("isPosition", isPosition);
            toInGameActivity.putExtra("isLoad", isLoad);

            toInGameActivity.putExtra("viewNum", viewNum);
            toInGameActivity.putExtra("endNum", endNum);
            toInGameActivity.putExtra("turn",turn);
            toInGameActivity.putExtra("spin", spin);

            toInGameActivity.putExtra("player1_Name", player1_Name);
            toInGameActivity.putExtra("player2_Name", player2_Name);

            toInGameActivity.putExtra("p1_sListX", p1_sListX);
            toInGameActivity.putExtra("p1_sListY", p1_sListY);
            toInGameActivity.putExtra("p2_sListX", p2_sListX);
            toInGameActivity.putExtra("p2_sListY",p2_sListY);

            toInGameActivity.putExtra("aimX", aimX);
            toInGameActivity.putExtra("aimY", aimY);

            toInGameActivity.putExtra("first",first);
        }
    };

    class NextTurn extends Thread {
        @Override
        public void run() {
            while (isEnd){
                turnHandler.sendEmptyMessage(0);
                isEnd = false;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {}
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }
}
