package com.example.teamkim.ingame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.teamkim.R;
import com.example.teamkim.customview.PopUpView;

/**
 * Created by SCITMASTER on 2018-03-06.
 */

/*Clickable View*/
public class SetPosition extends PopUpView {
    public SetPosition(Context context) {

        super(context);
    }

    public SetPosition(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.setImage(R.drawable.position_flag);
        this.setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (InGameActivity.isPosition){
            canvas.drawBitmap(this.getImage(),null,this.getPosition(),null);
        }
    }

    /*When touched screen, Set x and y*/
    /*After set x and y, draw arc_line and flag*/

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int)motionEvent.getX();
        int y = (int)motionEvent.getY();
        this.setPosition(new Rect(x-25,y-25,x+25,y+25));
        invalidate();

        //send Data (x,y)
        InGameActivity.aimX = x;
        InGameActivity.aimY = y;
        InGameActivity.isPosition = true;
        return false;
    }
}
