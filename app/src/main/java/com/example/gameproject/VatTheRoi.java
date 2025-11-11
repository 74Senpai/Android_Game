package com.example.gameproject;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public abstract class VatTheRoi {
    protected int TOC_DO_ROI;
    protected int HE_SO_TANG_TOC;
    protected int DO_DICH_CHUYEN_TRUC_NGANG;
    protected int DIEM_BAT_DAU_ROI_X, DIEM_BAT_DAU_ROI_Y;
    protected TextView lbl_vatTheRoi;
    protected ObjectAnimator animX, animY;
    protected RelativeLayout VUNG_THA;
    protected int CHIEU_CAO;

    public VatTheRoi(int tocDoRoi,
                     int heSoTangToc,
                     int doDichChuyenTrucNgang,
                     int diemBatDauRoiX,
                     int diemBatDauRoiY,
                     TextView lbl_vatTheRoi,
                     RelativeLayout layout_vungTha) {
        this.TOC_DO_ROI = tocDoRoi;
        this.HE_SO_TANG_TOC = heSoTangToc;
        this.DO_DICH_CHUYEN_TRUC_NGANG = doDichChuyenTrucNgang;
        this.DIEM_BAT_DAU_ROI_X = diemBatDauRoiX;
        this.DIEM_BAT_DAU_ROI_Y = diemBatDauRoiY;
        this.lbl_vatTheRoi = lbl_vatTheRoi;
        this.VUNG_THA = layout_vungTha;
        this.CHIEU_CAO = Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * Khởi tạo vật thể (vị trí, hiển thị ban đầu)
     */
    public abstract void khoiTaoVatThe();

    /**
     * Cho vật thể rơi
     */
    public void Roi() {
        lbl_vatTheRoi.setX(DIEM_BAT_DAU_ROI_X);
        lbl_vatTheRoi.setY(DIEM_BAT_DAU_ROI_Y);

        // Tạo chuyển động rơi
        animX = ObjectAnimator.ofFloat(lbl_vatTheRoi, "translationX",
                DIEM_BAT_DAU_ROI_X + DO_DICH_CHUYEN_TRUC_NGANG);
        animY = ObjectAnimator.ofFloat(lbl_vatTheRoi, "translationY",
                CHIEU_CAO-50); // điểm kết thúc rơi (tùy độ cao màn hình)

        animX.setDuration(TOC_DO_ROI);
        animY.setDuration(TOC_DO_ROI);
        animX.setInterpolator(new LinearInterpolator());
        animY.setInterpolator(new LinearInterpolator());

        animY.addUpdateListener(animation -> {
            float chieuDai = lbl_vatTheRoi.getWidth();
            float chieuCao = lbl_vatTheRoi.getHeight();
            float currentX = lbl_vatTheRoi.getX();
            float currentY = lbl_vatTheRoi.getY();
            boolean isDown = this.chamBeHung(currentX + chieuDai / 2, currentY + chieuCao);
            if(isDown) {
                this.xuLyChamBeHung();
            }else{
                this.missing(lbl_vatTheRoi, currentY);
            }
        });

        animY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                xoaVatThe(); // ✅ gọi hàm xóa sẵn có
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }
        });

        animX.start();
        animY.start();

    }

    /**
     * Ngừng rơi
     */
    public void ngungRoi() {
        if (animX != null) animX.cancel();
        if (animY != null) animY.cancel();
    }

    /**
     * Xóa vật thể khỏi màn hình
     */
    public void xoaVatThe() {
        if (VUNG_THA != null && lbl_vatTheRoi != null) {
            VUNG_THA.removeView(lbl_vatTheRoi);
            lbl_vatTheRoi = null; // giải phóng tham chiếu để tránh leak
        }
    }

    public boolean chamBeHung(float x, float y){
      return true;
    };
    public void xuLyChamBeHung(){
        //Do notinhg
    };

    public boolean missing(TextView lbl_vatTheRoi, float y){
        //Do nothing
        return true;
    }

}
