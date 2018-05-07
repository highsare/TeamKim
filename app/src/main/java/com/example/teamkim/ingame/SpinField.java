package com.example.teamkim.ingame;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.teamkim.customview.PopUpView;
import com.example.teamkim.ingame.InGameActivity;

import java.util.ArrayList;

/**
 * Created by SCITMASTER on 2018-03-07.
 */

public class SpinField extends PopUpView {
    private float nextX;
    private ArrayList<Float> xList,yList;
    private String direcX;

    public SpinField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        xList = new ArrayList<>();
        yList = new ArrayList<>();
        direcX = "STAY";
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                xList.add(motionEvent.getX());
                yList.add(motionEvent.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                xList.add(motionEvent.getX());
                yList.add(motionEvent.getY());
                break;
            case MotionEvent.ACTION_UP:
                this.setX(0);
                this.setY(0);

                nextX = xList.get(0) - xList.get(xList.size()-1);

                if (nextX > 0){
                    direcX = "LEFT";
                    if (InGameActivity.spin > -2) {
                        InGameActivity.spin--;
                    }
                }else{
                    direcX="RIGHT";
                    if (InGameActivity.spin < 2) {
                        InGameActivity.spin++;
                    }
                }
                xList.removeAll(xList);
                yList.removeAll(yList);
                break;
        }
        return true;
    }
}
