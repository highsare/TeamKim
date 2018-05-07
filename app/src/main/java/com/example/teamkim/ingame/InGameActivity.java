package com.example.teamkim.ingame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.teamkim.MainActivity;
import com.example.teamkim.R;
import com.example.teamkim.customview.Stone;

/**
 * Created by SCITMASTER on 2018-03-06.
 */

public class InGameActivity extends Activity {

    public static final int DEFAULT_VIEW = 1;
    public static final int SET_POSITION = 2;
    public static final int SET_SPIN = 3;
    public static final int SET_DRAW = 4;
    public static final int SWEEP = 5;
    public static final int COLLISION = 6;
    public static final int RESULT = 7;
    public static SoundPool soundPool;
    public static View view_default;
    public static View setPosition;
    public static View setSpin;
    public static View setDraw;
    public static View sweep;
    public static View collision;
    public static View result;
    public static View currentView;
    public static FrameLayout frame;
    public static boolean newGame;
    public static boolean isPosition;
    public static boolean isEnd;
    public static boolean isLoad;
    public static int viewNum;
    public static int endNum;
    public static int turn;
    public static int spin;
    public static int[] p1ScoreBoard;
    public static int[] p2ScoreBoard;
    public static String player1_Name;
    public static String player2_Name;
    public static float[] p1_sListX;
    public static float[] p1_sListY;
    public static float[] p2_sListX;
    public static float[] p2_sListY;
    public static float aimX;
    public static float aimY;
    public static String first;
    public static Stone currentStone;

    public boolean playing;
    private int[] soundIdx;
    private AudioAttributes audioAttributes;

    private View collision_test;

    private ImageView stone_spin;
    private ImageView stone_sweep;

    private com.example.teamkim.customview.NextButton next_default;
    private com.example.teamkim.customview.NextButton next_setPosition;
    private com.example.teamkim.customview.NextButton next_setSpin;
    private com.example.teamkim.customview.NextButton next_collision;
    private com.example.teamkim.customview.NextButton next_result;

    private SpinField drag_spin, drag_draw;

    NextButton nb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame_screen);

        Intent initializeIntent = getIntent();
        newGame = initializeIntent.getBooleanExtra("newGame", true);
        nb = new NextButton();
        initializing(initializeIntent, newGame);
        setFrame();
        connectViews();

        AnimatePopView animatePopView = new AnimatePopView();
        animatePopView.start();
    }

    public void initializing(Intent intent,boolean newGame){
        viewNum = 1;
        currentView = null;
        isPosition = false;
        spin = 0;

        player1_Name = intent.getStringExtra("player1_Name");
        player2_Name = intent.getStringExtra("player2_Name");

        endNum = intent.getIntExtra("endNum",1);
        turn = intent.getIntExtra("turn",1);
        first = intent.getStringExtra("first");

        //Insert code about Bitmap setting
        currentStone = MainActivity.currentStone;

        if (newGame) {
            p1ScoreBoard = new int[4];
            p2ScoreBoard = new int[4];
            p1_sListX = new float[4];
            p1_sListY = new float[4];
            p2_sListX = new float[4];
            p2_sListY = new float[4];

        }else{
            p1ScoreBoard = intent.getIntArrayExtra("p1ScoreBoard");
            p2ScoreBoard = intent.getIntArrayExtra("p2ScoreBoard");
            p1_sListX = intent.getFloatArrayExtra("p1_sListX");
            p1_sListY = intent.getFloatArrayExtra("p1_sListY");
            p2_sListX = intent.getFloatArrayExtra("p2_sListX");
            p2_sListY = intent.getFloatArrayExtra("p2_sListY");
        }
    }

    public void connectViews(){
        next_default = findViewById(R.id.next_default);
        next_setPosition = findViewById(R.id.next_setPosition);
        next_setSpin = findViewById(R.id.next_setSpin);
        next_collision = findViewById(R.id.next_collision);
        next_result = findViewById(R.id.next_result);

        next_default.setOnClickListener(nb);
        next_setPosition.setOnClickListener(nb);
        next_setSpin.setOnClickListener(nb);
        next_collision.setOnClickListener(nb);
        next_result.setOnClickListener(nb);

        drag_spin = findViewById(R.id.drag_spin);

        stone_spin = findViewById(R.id.stone_spin);
        stone_sweep = findViewById(R.id.stone_sweep);
    }

    public void setFrame(){
        frame = findViewById(R.id.ingame_screenFrame);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view_default = inflater.inflate(R.layout.ingame_default, frame, false);
        setPosition = inflater.inflate(R.layout.ingame_set_position, frame, false);
        setSpin = inflater.inflate(R.layout.ingame_set_spin, frame, false);
        setDraw = inflater.inflate(R.layout.ingame_set_draw, frame, false);
        sweep = inflater.inflate(R.layout.ingame_sweep, frame, false);
        collision = inflater.inflate(R.layout.ingame_collision, frame, false);
        result = inflater.inflate(R.layout.ingame_result, frame, false);

        frame.addView(result);
        frame.addView(collision);
        frame.addView(sweep);
        frame.addView(setDraw);
        frame.addView(setSpin);
        frame.addView(setPosition);
        frame.addView(view_default);

        view_default.setVisibility(View.VISIBLE);
        setPosition.setVisibility(View.GONE);
        setSpin.setVisibility(View.GONE);
        setDraw.setVisibility(View.GONE);
        sweep.setVisibility(View.GONE);
        collision.setVisibility(View.VISIBLE);
        result.setVisibility(View.GONE);
    }

    public static void viewChange(int viewId){

        view_default.setVisibility(View.GONE);
        setPosition.setVisibility(View.GONE);
        setSpin.setVisibility(View.GONE);
        setDraw.setVisibility(View.GONE);
        sweep.setVisibility(View.GONE);
        collision.setVisibility(View.GONE);
        result.setVisibility(View.GONE);

        //*It means view changed that viewNum is not 0*//
        //*Set currView*//
        switch (viewId) {
            case R.id.next_default:
                currentView = (setPosition == null ? null : setPosition);
                break;
            case R.id.next_setPosition:
                currentView = (setSpin == null ? null : setSpin);
                break;
            case R.id.next_setSpin:
                currentView = (setDraw == null ? null : setDraw);
                break;
            case R.id.next_collision:
                currentView = (result == null ? null : result);
                break;
            case R.id.next_result:
                currentView = (view_default == null ? null : view_default);
                break;
            case SWEEP:
                currentView = (sweep == null ? null : sweep);
                break;
            case COLLISION:
                currentView = (collision == null? null: collision);
                break;
            default:
                break;
        }
        currentView.setVisibility(View.VISIBLE);
    }

    public void toNextTurn() {
        Intent toNextTurn = new Intent(this, InGameActivity.class);
        finish();
        startActivity(toNextTurn);
    }

    class NextButton implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            viewChange(view.getId());
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        soundIdx = new int[12];
        soundIdx[0] = R.raw.bgm_main;
        soundIdx[1] = R.raw.bgm_ingame;
        soundIdx[2] = R.raw.fanfare;
        soundIdx[3] = R.raw.gliding;
        soundIdx[4] = R.raw.hit;
        soundIdx[5] = R.raw.sweep;
        soundIdx[6] = R.raw.ym_1;
        soundIdx[7] = R.raw.ym_2;
        soundIdx[8] = R.raw.ym_3;
        soundIdx[9] = R.raw.ym_up;
        soundIdx[10] = R.raw.ym_up2;

        audioAttributes =  new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(12).build();
        for (int i = 0; i < 11; i++) {
            soundPool.load(InGameActivity.this, soundIdx[i], 1);
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                isLoad = true;
                soundPool.play(2,1,1,1,-1,1);
            }
        });
    }


    class AnimatePopView extends Thread {
        @Override
        public void run() {
            int spinSync = spin;
            int cnt = 0;
            while (true) {
                if (currentView == setSpin) {
                    if (spinSync != spin || cnt == 4){
                        spinAnime();
                        cnt = 0;
                        spinSync = spin;
                    }
                    try {
                        cnt++;
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                }else if(currentView == sweep){
                    sweepAnime();
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }

                if (isEnd){
                    Log.i("isEND","be True");
                    finishHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000000);
                    } catch (InterruptedException e) {}
                }
            }
        }

        public void spinAnime(){
            Log.i("SPIN", spin+"");
            animateHandler.sendEmptyMessage(spin);
        }

        public void sweepAnime(){
            animateHandler.sendEmptyMessage(spin*10);
        }

        Handler animateHandler = new Handler() {
            Animation anim = null;
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case -2:
                        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_counter_clockwise_2);
                        stone_spin.startAnimation(anim);
                        break;
                    case -1:
                        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_counter_clockwise_1);
                        stone_spin.startAnimation(anim);
                        break;
                    case 1:
                        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise_1);
                        stone_spin.startAnimation(anim);
                        break;
                    case 2:
                        anim = anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise_2);
                        stone_spin.startAnimation(anim);
                        break;
                    case -20:
                        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_counter_clockwise_2);
                        stone_sweep.startAnimation(anim);
                        break;
                    case -10:
                        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_counter_clockwise_1);
                        stone_sweep.startAnimation(anim);
                        break;
                    case 10:
                        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise_1);
                        stone_sweep.startAnimation(anim);
                        break;
                    case 20:
                        anim = anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise_2);
                        stone_sweep.startAnimation(anim);
                        break;
                }
            }
        };

        Handler finishHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                MainActivity.isEnd = true;
                finish();
            }
        };
    }
}
