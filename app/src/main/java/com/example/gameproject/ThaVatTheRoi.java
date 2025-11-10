package com.example.gameproject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class ThaVatTheRoi {
    private RelativeLayout VUNG_THA_VAT_THE;
    private Context context;
    private boolean isRunning = false;
    private Thread threadThaVatThe;
    private final Random random = new Random();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public ThaVatTheRoi(RelativeLayout layout_vungTha, Context context) {
        this.VUNG_THA_VAT_THE = layout_vungTha;
        this.context = context;
    }

    /** Bắt đầu thả vật thể */
    public void BatDauTha() {
        if (isRunning) return; // tránh gọi nhiều lần
        VatTheHung vatTheHung = new VatTheHung(this.VUNG_THA_VAT_THE,this.context);
        vatTheHung.init();
        vatTheHung.setDragEvent();
        vatTheHung.addToView();

        isRunning = true;
        threadThaVatThe = new Thread(() -> {
            while (isRunning) {
                try {
                    // Random thời gian chờ giữa 0.5 - 1s
                    long sleepTime = 500 + random.nextInt(500);
                    Thread.sleep(sleepTime);

                    // Random vị trí ngang trong vùng thả
                    int maxX = VUNG_THA_VAT_THE.getWidth() - 100; // trừ kích thước vật thể
                    int randomX = random.nextInt(Math.max(maxX, 1));

                    // Thực hiện trên UI thread
                    handler.post(() -> {
                        TextView lbl_lyBia = new TextView(context);
                        lbl_lyBia.setText("🍺");
                        lbl_lyBia.setTextSize(30);
                        lbl_lyBia.setX(randomX);
                        lbl_lyBia.setY(0);

                        VUNG_THA_VAT_THE.addView(lbl_lyBia);

                        LyBia lyBia = new LyBia(
                                4000, // tốc độ rơi (ms)
                                1,    // hệ số tăng tốc
                                0,    // độ dịch ngang
                                randomX, // điểm bắt đầu X
                                0,       // điểm bắt đầu Y
                                lbl_lyBia,
                                VUNG_THA_VAT_THE,
                                vatTheHung.lbl_beHung
                        );
                        lyBia.khoiTaoVatThe();
                        lyBia.Roi();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadThaVatThe.start();
    }

    /** Dừng thả vật thể */
    public void NgungTha() {
        isRunning = false;
        if (threadThaVatThe != null && threadThaVatThe.isAlive()) {
            threadThaVatThe.interrupt();
        }
    }
}
