package com.example.gameproject;

import android.widget.RelativeLayout;
import android.widget.TextView;

public class LyBia extends VatTheRoi {

    private TextView lbl_vungHungVatThe;
    private GameBase GAME;

    public LyBia(int tocDoRoi,
                 int heSoTangToc,
                 int doDichChuyenTrucNgang,
                 int diemBatDauRoiX,
                 int diemBatDauRoiY,
                 TextView lbl_vatTheRoi,
                 RelativeLayout layout_vungTha,
                 TextView lbl_vungHungVatThe,
                 GameBase game) {

        super(tocDoRoi,
                heSoTangToc,
                doDichChuyenTrucNgang,
                diemBatDauRoiX,
                diemBatDauRoiY,
                lbl_vatTheRoi,
                layout_vungTha);

        this.lbl_vungHungVatThe = lbl_vungHungVatThe;
        this.GAME = game;
    }

    @Override
    public void khoiTaoVatThe() {
        lbl_vatTheRoi.setText("🍺");
        lbl_vatTheRoi.setTextSize(36);
        lbl_vatTheRoi.setVisibility(TextView.VISIBLE);
    }

    @Override
    public boolean chamBeHung(float x, float y) {
        float beHungX_left = this.lbl_vungHungVatThe.getX();
        float beHungY = this.lbl_vungHungVatThe.getY();
        float beHungX_right = beHungX_left + this.lbl_vungHungVatThe.getWidth();

        if ((x >= beHungX_left && x <= beHungX_right) &&
                (y >= beHungY - 20 && y <= beHungY + lbl_vungHungVatThe.getHeight())) {
            this.GAME.updateScore(10);
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

    @Override
    public boolean missing(TextView lbl_vatTheRoi, float y) {
        float beHungY = this.lbl_vungHungVatThe.getY();
        float beHungHeight = this.lbl_vungHungVatThe.getHeight();
        float paddingMiss = 20;
        if ((y > (beHungY + beHungHeight + paddingMiss))
                && lbl_vatTheRoi.isEnabled()) {
            this.GAME.updateLifes(-1);
            lbl_vatTheRoi.setEnabled(false);
            return true;
        }
        return false;
    }
}

