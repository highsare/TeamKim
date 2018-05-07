package com.example.teamkim.ingame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.teamkim.R;
import com.example.teamkim.customview.PopUpView;
import com.example.teamkim.ingame.InGameActivity;

import java.util.ArrayList;

/**
 * Created by SCITMASTER on 2018-03-07.
 */

// TODO: 2018-03-10 Make Velocity Guide parameter bar

public class DrawField extends PopUpView {
    private final int LEFT = 500;
    private final int TOP = 1200;
    private final int SIZE = 300;
    private float veloX,veloY;
    private ArrayList<Float> xList,yList;

    private Bitmap progressBar,progressGauge;
    private Rect progressRect,progressGaugeRect;

    public DrawField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        progressRect = new Rect(200,300,400,1400);
        progressGaugeRect = new Rect(200,310,400,1390);

        xList = new ArrayList<>();
        yList = new ArrayList<>();

        this.position = new Rect(LEFT,TOP,LEFT+SIZE,TOP+SIZE);
        this.setImage(R.drawable.red_stone);
        this.progressBar = BitmapFactory.decodeResource(getResources(),R.drawable.progrees_bar);
        this.progressGauge = BitmapFactory.decodeResource(getResources(),R.drawable.progress_over);
        this.setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(progressBar,null,progressRect,null);
        canvas.drawBitmap(progressGauge,null,progressGaugeRect,null);
        canvas.drawBitmap(this.getImage(),null,this.position,null);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float moX = motionEvent.getX();
        float moY = motionEvent.getY();
        Rect moRect = new Rect((int)moX-10,(int)moY-10,(int)moX+10,(int)moY+10);
        if (Rect.intersects(moRect,this.getPosition())) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xList.add(moX);
                    yList.add(moY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    xList.add(moX);
                    yList.add(moY);
                    veloY = 30 *Math.abs((yList.get(0) - yList.get(yList.size() - 1)) / yList.size());

                    progressGaugeRect = new Rect(200,310,400,1390-(3*(int)veloY));

                    break;
                case MotionEvent.ACTION_UP:
                    //to Sweep phase
                    veloX = -1 * InGameActivity.spin * 30;
                    InGameActivity.currentStone.setVeloX(veloX);
                    InGameActivity.currentStone.setVeloY(veloY);

                    InGameActivity.viewChange(InGameActivity.SWEEP);
                    break;
            }

            if (yList.size() >= 2) {
                int recentY = (int)Math.ceil(yList.get(yList.size()-1));
                int moveY = (int)(recentY - yList.get(0));
                this.position = new Rect(LEFT,TOP+moveY,LEFT+SIZE,TOP+SIZE+moveY);
            }
            invalidate();
        }
        return true;
    }
}
