package com.example.teamkim.ingame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.teamkim.R;
import com.example.teamkim.customview.PopUpView;
import com.example.teamkim.ingame.InGameActivity;

/**
 * Created by SCITMASTER on 2018-03-08.
 */

public class SweepField extends PopUpView {
    private final float SPIN = 100;
    private final int SWEEP = 500;
    private int sweepCnt = 0;

    private boolean first = true;
    private boolean wait = true;

    private int cnt = 0;

    public SweepField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setImage(R.drawable.sweeper);
        this.setOnTouchListener(this);
        this.position = new Rect(0,0,0,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Sweeping image here
        canvas.drawBitmap(this.getImage(),null,this.getPosition(),null);
    }

    // TODO: 2018-03-09 [sweep]Insert Sound and Animation exc.

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        InGameActivity.soundPool.stop(2);
        if (!wait) {

            int pointX = (int) motionEvent.getX();
            int pointY = (int) motionEvent.getY();

            this.position = new Rect(pointX - 150, pointY - 800, pointX + 750, pointY + 100);
            sweepCnt++;
            if (sweepCnt >= SWEEP) {
                InGameActivity.currentStone.setCoordX(InGameActivity.aimX + InGameActivity.spin * SPIN);
                InGameActivity.currentStone.setCoordY(100);
                InGameActivity.soundPool.release();
                InGameActivity.viewChange(InGameActivity.COLLISION);
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                if ((sweepCnt % 50) == 0 || first) {
                    InGameActivity.soundPool.play(6, 1, 1, 2, 0, 1);
                    first = false;
                }
                if (sweepCnt % 100 == 0) {
                    InGameActivity.soundPool.play(7, 1, 1, 3, 0, 1);
                    cnt++;
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                InGameActivity.soundPool.stop(6);
                first = true;
            }
            invalidate();
        }else{
            InGameActivity.soundPool.stop(1);
            InGameActivity.soundPool.play(11,1,1,5,0,1);
            wait = false;
        }
        return true;
    }
}
