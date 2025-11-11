package com.example.gameproject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class ThaVatTheRoi extends GameBase {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Random random = new Random();
    private final RelativeLayout layoutVungTha;
    private Thread threadThaVatThe;
    private boolean isRunning = false;
    private TextView lbl_beHung;

    public ThaVatTheRoi(Context context, RelativeLayout layoutVungTha) {
        super(context, layoutVungTha);
        this.layoutVungTha = layoutVungTha;
    }

    /**
     * Override startGame() từ GameBase
     */
    @Override
    protected void startGame() {
        if (isRunning) return;

        // Có thể reset score và lifes nếu cần
        score.getAndSet(0);
        lifes.getAndSet(5);

        if (lbl_beHung == null) {
            lbl_beHung = initVatTheHung();
        }
        lbl_beHung.setVisibility(VISIBLE);
        isRunning = true;
        initGameThread();
        threadThaVatThe.start();
        this.btnStart.setEnabled(false);
    }

    protected void initGameThread() {
        threadThaVatThe = new Thread(() -> {
            int maxX = layoutVungTha.getWidth() - 100;
            while (isRunning) {
                try {
                    long sleepTime = 500 + random.nextInt(500);
                    Thread.sleep(sleepTime);
                    handler.post(() -> {
                        initVatTheRoi(maxX, lbl_beHung);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    stopGame();
                }
            }
            stopGame();
        });
    }

    private void initVatTheRoi(int maxX, TextView lbl_beHung) {
        int randomX = random.nextInt(Math.max(maxX, 1));
        TextView lblVatThe = new TextView(context);
        lblVatThe.setText("🍺");
        lblVatThe.setTextSize(30);
        lblVatThe.setX(randomX);
        lblVatThe.setY(0);
        layoutVungTha.addView(lblVatThe);

        LyBia lyBia = new LyBia(
                4000,
                1,
                0,
                randomX,
                0,
                lblVatThe,
                layoutVungTha,
                lbl_beHung,
                this
        );

        lyBia.khoiTaoVatThe();
        lyBia.Roi();
    }

    private TextView initVatTheHung() {
        VatTheHung vatTheHung = new VatTheHung(layoutVungTha, context);
        vatTheHung.init();
        vatTheHung.setDragEvent();
        vatTheHung.addToView();
        return vatTheHung.lbl_beHung;
    }

    /**
     * Dừng game
     */
    @Override
    protected void stopGame() {
        isRunning = false;
        lbl_beHung.setVisibility(GONE);
        if (threadThaVatThe != null && threadThaVatThe.isAlive()) {
            threadThaVatThe.interrupt();
        }
    }
}
