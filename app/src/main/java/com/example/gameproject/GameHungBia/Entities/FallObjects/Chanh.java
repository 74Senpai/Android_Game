package com.example.gameproject.GameHungBia.Entities.FallObjects;

import android.widget.TextView;

import com.example.gameproject.GameHungBia.Entities.VatTheHung;
import com.example.gameproject.GameBase.GameBase;

public class Chanh extends VatTheRoi {

    private TextView lbl_vungHungVatThe;
    private final GameBase GAME;
    protected TextView lbl_vatTheRoi;
    protected final VatTheHung vatTheHung;

    public Chanh(int tocDoRoi,
                 int diemBatDauRoiX,
                 int diemBatDauRoiY,
                 VatTheHung vatTheHung,
                 GameBase game) {

        super(tocDoRoi,
                diemBatDauRoiX,
                diemBatDauRoiY,
                game.layoutGame);

        this.vatTheHung = vatTheHung;
        this.lbl_vungHungVatThe = vatTheHung.lbl_beHung;
        this.GAME = game;
    }

    @Override
    public void khoiTaoVatThe() {
        lbl_vatTheRoi = new TextView(this.GAME.context);
        lbl_vatTheRoi.setText("🍋");
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
            int newWidth = (int)beHungWidth + 10;
            vatTheHung.updateWidth(newWidth);
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
