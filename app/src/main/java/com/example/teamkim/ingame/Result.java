package com.example.teamkim.ingame;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.teamkim.MainActivity;
import com.example.teamkim.customview.PopUpView;

/**
 * Created by SCITMASTER on 2018-03-06.
 */

// TODO: 2018-03-10 Show result and set info
// TODO: 2018-03-10 Intent parameters
// TODO: 2018-03-10 Finish this Intent
public class Result extends PopUpView {
    public Result(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        for (int i = 0;i < 11; i++){
            InGameActivity.soundPool.stop(i);
        }
        InGameActivity.soundPool.play(4,1,1,1,0,1);

        if(InGameActivity.first.equals("PLAYER1")){
            MainActivity.p1_sListX[MainActivity.turn-1] = MainActivity.currentStone.getCoordX();
            MainActivity.p1_sListY[MainActivity.turn-1] = MainActivity.currentStone.getCoordY();
        }else{
            MainActivity.p2_sListX[MainActivity.turn-1] = MainActivity.currentStone.getCoordX();
            MainActivity.p2_sListY[MainActivity.turn-1] = MainActivity.currentStone.getCoordY();
        }

        MainActivity.turn++;

        InGameActivity.isEnd = true;
        return true;
    }
}
