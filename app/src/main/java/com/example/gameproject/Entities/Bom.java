package com.example.gameproject.Entities;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gameproject.GameBase;

public class Bom extends VatTheRoi {

    private TextView lbl_vungHungVatThe;
    private final GameBase GAME;
    protected TextView lbl_vatTheRoi;

    public Bom(int tocDoRoi,
                 int diemBatDauRoiX,
                 int diemBatDauRoiY,
                 TextView lbl_vungHungVatThe,
                 GameBase game) {

        super(tocDoRoi,
                diemBatDauRoiX,
                diemBatDauRoiY,
                game.layoutGame);

        this.lbl_vungHungVatThe = lbl_vungHungVatThe;
        this.GAME = game;
    }

    @Override
    public void khoiTaoVatThe() {
        lbl_vatTheRoi = new TextView(this.GAME.context);
        lbl_vatTheRoi.setText("💣");
        lbl_vatTheRoi.setTextSize(36);
        lbl_vatTheRoi.setVisibility(TextView.VISIBLE);
        this.GAME.layoutGame.addView(lbl_vatTheRoi);
        this.themVatTheRoiVaoContex(lbl_vatTheRoi);
    }

    @Override
    public boolean chamBeHung(float x, float y) {
        float beHungX_left = this.lbl_vungHungVatThe.getX();
        float beHungY = this.lbl_vungHungVatThe.getY();
        float beHungWidth = this.lbl_vungHungVatThe.getWidth();
        float beHungX_right = beHungX_left + beHungWidth;

        if ((x >= beHungX_left && x <= beHungX_right) &&
                (y >= beHungY - 20 && y <= beHungY + lbl_vungHungVatThe.getHeight())) {
            this.lbl_vatTheRoi.setText("💥");
            this.GAME.updateLifes(Integer.MIN_VALUE);
            return true;
        }

        return false;
    }

    @Override
    public void xuLyChamBeHung() {
        this.ngungRoi();
        this.xoaVatThe();
        System.out.println("Vat the cham be hung");
    }
}
