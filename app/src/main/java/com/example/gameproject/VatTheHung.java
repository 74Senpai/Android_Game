package com.example.gameproject;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VatTheHung {
    TextView lbl_beHung;
    RelativeLayout layout;
    Context context;
    protected float CHIEU_CAO;
    protected float CHIEU_DAI;

    public VatTheHung(RelativeLayout layout_vungHung, Context context) {
        this.layout = layout_vungHung;
        this.context = context;
        this.CHIEU_CAO = Resources.getSystem().getDisplayMetrics().heightPixels;
        this.CHIEU_DAI = Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public void BatDau() {
        lbl_beHung = new TextView(this.context);
        lbl_beHung.setText("Be hung");
        lbl_beHung.setBackgroundColor(Color.RED);
        lbl_beHung.setTextSize(30);
        lbl_beHung.setX(CHIEU_DAI/2);
        lbl_beHung.setY(CHIEU_CAO-900);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void KeoTha() {
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
                            newX = (float) (CHIEU_DAI - viewWidth);
                        }

                        view.animate().x(newX).setDuration(0).start();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void themVaoView () {
        layout.addView(this.lbl_beHung);
    }
}