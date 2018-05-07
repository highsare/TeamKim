package com.example.teamkim.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by SCITMASTER on 2018-03-07.
 */

/*Class extended View Class for showing and animating images*/
public class PopUpView extends View implements View.OnTouchListener, View.OnClickListener, View.OnDragListener{

    protected Bitmap Image;
    protected Rect position;
    protected float x,y;

    public PopUpView(Context context) {
        super(context);
    }

    public PopUpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImage(int imageSourceId) {
        this.Image = BitmapFactory.decodeResource(getResources(),imageSourceId);
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setPosition(Rect position) {
        this.position = position;
        this.x = position.exactCenterX();
        this.y = position.exactCenterY();
    }

    public Rect getPosition() {
        return position;
    }

    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    /*Called in XML*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void hideView(){
        this.setVisibility(INVISIBLE);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        return false;
    }
}
