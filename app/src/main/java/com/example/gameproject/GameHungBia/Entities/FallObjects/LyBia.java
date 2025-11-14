package com.example.gameproject.GameHungBia.Entities.FallObjects;

import android.widget.TextView;

import com.example.gameproject.GameHungBia.Entities.VatTheHung;
import com.example.gameproject.GameBase.GameBase;

public class LyBia extends VatTheRoi {

    private final TextView lbl_vungHungVatThe;
    private final GameBase GAME;

    public LyBia(int tocDoRoi,
                 int heSoTangToc,
                 int doDichChuyenTrucNgang,
                 VatTheHung vatTheHung,
                 GameBase game) {

        super(tocDoRoi,
                heSoTangToc,
                doDichChuyenTrucNgang,
                game.layoutGame);

        this.lbl_vungHungVatThe = vatTheHung.lbl_beHung;
        this.GAME = game;
    }

    @Override
    public void khoiTaoVatThe() {
        lbl_vatTheRoi = new TextView(this.GAME.context);
        lbl_vatTheRoi.setText("🍺");
        lbl_vatTheRoi.setTextSize(36);
        lbl_vatTheRoi.setVisibility(TextView.VISIBLE);
        this.GAME.layoutGame.addView(lbl_vatTheRoi);
        this.themVatTheRoiVaoContex(lbl_vatTheRoi);
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

