package com.example.teamkim.ingame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.teamkim.R;
import com.example.teamkim.customview.PopUpView;
import com.example.teamkim.customview.Stone;
import com.example.teamkim.ingame.InGameActivity;

/**
 * Created by SCITMASTER on 2018-03-06.
 */


// TODO: 2018-03-10 Get image or situation info
// TODO: 2018-03-10 Show info about situation
/*Show Macth's Situation*/
public class DefaultField extends PopUpView {
    Bitmap redStone,yelStone;

    @SuppressLint("ClickableViewAccessibility")
    public DefaultField(Context context, @Nullable AttributeSet attributeSet){
        super(context, attributeSet);

        redStone = BitmapFactory.decodeResource(getResources(), R.drawable.red_stone);
        yelStone = BitmapFactory.decodeResource(getResources(), R.drawable.yel_stone);

        //Cood of Stones
        //Score
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0 ; i < 4; i++){
            if (InGameActivity.p1_sListX[i] > 0) {
                float x = InGameActivity.p1_sListX[i];
                float y = InGameActivity.p1_sListY[i];
                Rect rect = new Rect((int)x- Stone.R,(int)y-Stone.R,(int)x+Stone.R,(int)y+Stone.R);
                canvas.drawBitmap(redStone,null,rect,null);
            }
            if (InGameActivity.p2_sListX[i] > 0) {
                float x = InGameActivity.p2_sListX[i];
                float y = InGameActivity.p2_sListY[i];
                Rect rect = new Rect((int)x-Stone.R,(int)y-Stone.R,(int)x+Stone.R,(int)y+Stone.R);
                canvas.drawBitmap(yelStone,null,rect,null);
            }
        }
    }



}