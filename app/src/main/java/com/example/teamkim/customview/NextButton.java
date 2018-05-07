package com.example.teamkim.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.teamkim.R;
import com.example.teamkim.ingame.InGameActivity;

/**
 * Created by SCITMASTER on 2018-03-06.
 */

public class NextButton extends PopUpView {

    Bitmap icon;

    public NextButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        icon = BitmapFactory.decodeResource(getResources(), R.drawable.next_btn);
        this.setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect(0,0,200,100);
        canvas.drawBitmap(icon,null,rect,null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next_default: InGameActivity.viewNum = InGameActivity.SET_POSITION; break;
            case R.id.next_setPosition: InGameActivity.viewNum = InGameActivity.SET_SPIN; break;
            case R.id.next_setSpin: InGameActivity.viewNum = InGameActivity.COLLISION; break;
            case R.id.next_collision: InGameActivity.viewNum = InGameActivity.RESULT; break;
            case R.id.next_result: InGameActivity.viewNum = InGameActivity.DEFAULT_VIEW; break;
            default:break;
        }
    }
}
