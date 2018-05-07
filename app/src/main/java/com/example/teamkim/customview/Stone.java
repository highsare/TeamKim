package com.example.teamkim.customview;

import android.graphics.Bitmap;
import android.graphics.Rect;


/**
 * Created by SCITMASTER on 2018-03-06.
 */

public class Stone {
    public static final int R = 20;
    public static final int MASS = 1;

    private Bitmap image;
    private Rect rect;
    private double veloX,veloY;
    private float coordX,coordY;

    public Stone(Bitmap image,double veloX, double veloY, float coordX, float coordY) {
        this.image = image;
        this.veloX = veloX;
        this.veloY = veloY;
        this.coordX = coordX;
        this.coordY = coordY;
        this.rect = new Rect((int)coordX-R,(int)coordY-R,(int)coordX+R,(int)coordY+R);
    }

    public float getDistance(){
        return Math.abs((float) Math.sqrt((double)(Math.getExponent(this.coordX - 400F)+Math.getExponent(this.coordY - 1200F))));
    }

    public double getAngle(){
        return Math.atan2(veloX,veloY);
    }

    public double getVelocity(){
        return Math.sqrt(Math.pow(this.veloX,2) +Math.pow(this.veloY,2));
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(float coordX, float coordY) {
        this.rect = new Rect((int)coordX-R,(int)coordY-R,(int)coordX+R,(int)coordY+R);
    }

    public double getVeloX() {
        return veloX;
    }

    public void setVeloX(double veloX) {
        this.veloX = veloX;
    }

    public double getVeloY() {
        return veloY;
    }

    public void setVeloY(double veloY) {
        this.veloY = veloY;
    }

    public float getCoordX() {
        return coordX;
    }

    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    public float getCoordY() {
        return coordY;
    }

    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "rect=" + rect +
                ", veloX=" + veloX +
                ", veloY=" + veloY +
                ", coordX=" + coordX +
                ", coordY=" + coordY +
                '}';
    }
}
