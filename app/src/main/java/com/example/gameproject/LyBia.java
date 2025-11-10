package com.example.gameproject;

import android.widget.RelativeLayout;
import android.widget.TextView;

public class LyBia extends VatTheRoi {
    public LyBia(int tocDoRoi,
                 int heSoTangToc,
                 int doDichChuyenTrucNgang,
                 int diemBatDauRoiX,
                 int diemBatDauRoiY,
                 TextView lbl_vatTheRoi,
                 RelativeLayout layout_vungTha) {

        super(tocDoRoi,
                heSoTangToc,
                doDichChuyenTrucNgang,
                diemBatDauRoiX,
                diemBatDauRoiY,
                lbl_vatTheRoi,
                layout_vungTha);
    }

    @Override
    public void khoiTaoVatThe() {
        lbl_vatTheRoi.setText("🍺");
        lbl_vatTheRoi.setTextSize(36);
        lbl_vatTheRoi.setVisibility(TextView.VISIBLE);
    }
}

