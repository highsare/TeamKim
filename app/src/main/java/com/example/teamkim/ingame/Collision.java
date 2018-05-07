package com.example.teamkim.ingame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.teamkim.R;
import com.example.teamkim.customview.PopUpView;
import com.example.teamkim.customview.Stone;

import java.util.ArrayList;

/**
 * Created by SCITMASTER on 2018-03-06.
 */

// TODO: 2018-03-10 Make animating Button
/*Animate stones with physical losic*/
public class Collision extends PopUpView {

    private final double CURVE_1 = 1.07;
    private final double CURVE_2 = 1.1;

    private final float FPS = 24F;
    private final float FIRST_LINE = 175;
    private final float END_LINE = 890;
    private final float HOUSE_RADIOUS = 90000;
    private final float HOUSE_END = 1330;
    private final double FRICTION = 0.99;
    private final double FRICTION_CUT = 4;

    private float[] p1_StonesX,p1_StonesY,p2_StonesX,p2_StonesY;
    private ArrayList<Stone> p1_sList,p2_sList;
    private Bitmap red_stone,yel_stone;

    public Collision(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //get Stones by using Static variable p1_sList, p2_sList
        p1_StonesX = InGameActivity.p1_sListX;
        p1_StonesY = InGameActivity.p1_sListY;
        p2_StonesX = InGameActivity.p2_sListX;
        p2_StonesY = InGameActivity.p2_sListY;

        p1_sList = new ArrayList<>();
        p2_sList = new ArrayList<>();

        red_stone = BitmapFactory.decodeResource(getResources(),R.drawable.red_stone);
        yel_stone = BitmapFactory.decodeResource(getResources(),R.drawable.yel_stone);

        for (int i = 0; i < 2; i++) {
            p1_StonesX[i] = i*200 + 450;
            p1_StonesY[i] = 1000;
            p2_StonesX[i] = i*100 + 500;
            p2_StonesY[i] = 1100;
        }

        for (int i = 0 ; i < p1_StonesX.length; i++){
            Stone stone = new Stone(red_stone,0,0,p1_StonesX[i],p1_StonesY[i]);
            p1_sList.add(stone);
        }
        for (int i = 0 ; i < p2_StonesX.length; i++){
            Stone stone = new Stone(yel_stone,0,0,p2_StonesX[i],p2_StonesY[i]);
            p2_sList.add(stone);
        }

        //get variable about thrown stone
        this.setOnTouchListener(this);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Test Stones
        for (Stone stone:p1_sList){
            if (stone.getCoordX() != 0 && stone.getCoordY() != 0) {
                canvas.drawBitmap(stone.getImage(), null, stone.getRect(), null);
            }
        }
        for (Stone stone:p2_sList){
            if (stone.getCoordX() != 0 && stone.getCoordY() != 0) {
                canvas.drawBitmap(stone.getImage(), null, stone.getRect(), null);
            }
        }

        //draw stones here
        canvas.drawBitmap(InGameActivity.currentStone.getImage(),null, InGameActivity.currentStone.getRect(),null);
    }

    protected void move(Stone stone){
        double veloX = stone.getVeloX();
        double veloY = stone.getVeloY();

        if (veloX != 0 || veloY != 0) {

            float x = (float) (stone.getCoordX() + (veloX / FPS));
            float y = (float) (stone.getCoordY() + (veloY / FPS));
            firiction(stone);

            stone.setCoordX(x);
            stone.setCoordY(y);
            stone.setRect(stone.getCoordX(), stone.getCoordY());

            if (stone.getCoordX() < FIRST_LINE || stone.getCoordX() > END_LINE) {
                stone.setCoordX(0);
                stone.setCoordY(0);
                stone.setVeloX(0);
                stone.setVeloY(0);
                return;
            }
            if (stone.getDistance() > this.HOUSE_RADIOUS && stone.getCoordY() > this.HOUSE_END){
                stone.setCoordX(0);
                stone.setCoordY(0);
                stone.setVeloX(0);
                stone.setVeloY(0);
                return;
            }
        }
    }

    protected void crash(Stone stone_1, Stone stone_2){
        float distanceX = Math.abs(stone_1.getCoordX() - stone_2.getCoordX());
        float distanceY = Math.abs(stone_1.getCoordY() - stone_2.getCoordY());
        double distance = Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2));

        if (distance <= Stone.R * 2){

            InGameActivity.soundPool.play(5,1,1,2,0,1);

            //Crashed
            //Calculate each stone's velocity after crashed
            double angle_stone1 = stone_1.getAngle();
            double angle_stone2 = calculateAngle(stone_1,stone_2);
            double angle_Btw = angle_stone1 - angle_stone2;

            double velocityBeforeCrash = stone_1.getVelocity();
            double velocity_stone2 = velocityBeforeCrash * Math.cos(angle_Btw);

            stone_2.setVeloY(velocity_stone2 * Math.cos(angle_stone2));
            stone_2.setVeloX(velocity_stone2 * Math.sin(angle_stone2));
            stone_1.setVeloY(stone_1.getVeloY() - stone_2.getVeloY());
            stone_1.setVeloX(stone_1.getVeloX() - stone_2.getVeloX());
        }
    }

    protected double calculateAngle(Stone stone_1,Stone stone_2){
        double differenceX,differenceY;

        differenceX = stone_1.getCoordX() - stone_2.getCoordX();
        differenceY = stone_1.getCoordY() - stone_2.getCoordY();

        return Math.atan2(differenceX,differenceY)-Math.PI;
    }

    protected void firiction (Stone stone){
        double forcedX = stone.getVeloX()*FRICTION;
        double forcedY = stone.getVeloY()*FRICTION;

        if (Math.abs(forcedY) > FRICTION_CUT){
            double curve = 1.0;
            switch (Math.abs(InGameActivity.spin)) {
                case 0:
                    curve = 1;
                    break;
                case 1:
                    curve = CURVE_1;
                    break;
                case 2:
                    curve = CURVE_2;
                    break;
            }
            stone.setVeloX((Math.abs(forcedX * curve) >= 1 ? forcedX : (forcedX * curve)));
            stone.setVeloY((Math.abs(forcedY) < FRICTION_CUT ? 0 : forcedY));
        }else{
            stone.setVeloX(0);
            stone.setVeloY(0);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            InGameActivity.soundPool.play(4,1,1,2,-1,1);
        }
        move(InGameActivity.currentStone);
        for (Stone stone1:p1_sList){
            move(stone1);
            crash(InGameActivity.currentStone,stone1);
        }
        for (Stone stone2:p2_sList){
            move(stone2);
            crash(InGameActivity.currentStone,stone2);
        }
        invalidate();
        return true;
    }
}
