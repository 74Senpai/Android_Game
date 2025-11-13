package com.example.gameproject.GameHungBia.Entities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VatTheHung {
    public TextView lbl_beHung;
    protected RelativeLayout layout;
    protected Context context;
    protected float CHIEU_CAO;
    protected float CHIEU_DAI;
    private final float layoutWidth;
    private final int maxWidth;
    private final int minWidth;

    public VatTheHung(RelativeLayout layout_vungHung, Context context,
                      int maxWidth, int minWidth) {
        this.layout = layout_vungHung;
        this.context = context;
        this.CHIEU_CAO = Resources.getSystem().getDisplayMetrics().heightPixels;
        this.CHIEU_DAI = Resources.getSystem().getDisplayMetrics().widthPixels;
        this.layoutWidth = this.layout.getWidth();
        this.maxWidth = maxWidth;
        this.minWidth = minWidth;
    }

    public void init() {
        lbl_beHung = new TextView(this.context);
        lbl_beHung.setBackgroundColor(Color.GREEN);
        lbl_beHung.setWidth((int)(maxWidth + minWidth) / 2);
        lbl_beHung.setX(CHIEU_DAI / 2);
        lbl_beHung.setY(CHIEU_CAO - 500);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setDragEvent() {
        lbl_beHung.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float newX = event.getRawX() + dX;
                        float viewWidth = view.getWidth();
                        if (newX < 0) {
                            newX = 0;
                        }
                        if (newX + viewWidth > CHIEU_DAI) {
                            newX = CHIEU_DAI - viewWidth;
                        }

                        view.animate().x(newX).setDuration(0).start();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void addToView() {
        layout.addView(this.lbl_beHung);
    }
    public void updateWidth(int newWidth){
        if (newWidth > maxWidth) newWidth = maxWidth;
        else if(newWidth < minWidth) newWidth = minWidth;
        lbl_beHung.setWidth(newWidth);
    }
}