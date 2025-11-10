package com.example.gameproject;

import android.widget.RelativeLayout;
import android.widget.TextView;

public class LyBia extends VatTheRoi {

    private TextView lbl_vungHungVatThe;

    public LyBia(int tocDoRoi,
                 int heSoTangToc,
                 int doDichChuyenTrucNgang,
                 int diemBatDauRoiX,
                 int diemBatDauRoiY,
                 TextView lbl_vatTheRoi,
                 RelativeLayout layout_vungTha,
                 TextView lbl_vungHungVatThe) {

        super(tocDoRoi,
                heSoTangToc,
                doDichChuyenTrucNgang,
                diemBatDauRoiX,
                diemBatDauRoiY,
                lbl_vatTheRoi,
                layout_vungTha);

        this.lbl_vungHungVatThe = lbl_vungHungVatThe;
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

