package com.example.gameproject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gameproject.Entities.GameObjectManager;

import java.util.Random;

public class ThaVatTheRoi extends GameBase {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Random random = new Random();
    private final RelativeLayout layoutVungTha;
    private final GameObjectManager gameObjectManager;
    private Thread threadThaVatThe;
    private boolean isRunning = false;
    private TextView lbl_beHung;

    public ThaVatTheRoi(Context context, RelativeLayout layoutVungTha) {
        super(context, layoutVungTha);
        this.layoutVungTha = layoutVungTha;
        this.gameObjectManager = new GameObjectManager(context, layoutVungTha, this);
    }

    /**
     * Override startGame() từ GameBase
     */
    @Override
    protected void startGame() {
        if (isRunning) return;

        // Có thể reset score và lifes nếu cần
        score.getAndSet(0);
        lives.getAndSet(5);

        if (lbl_beHung == null) {
            lbl_beHung = gameObjectManager.createVatTheHung();
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
                        gameObjectManager.createVatTheRoi(maxX, lbl_beHung);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    stopGame();
                }
            }
            stopGame();
        });
    }

    /**
     * Dừng game
     */
    @Override
    protected void stopGame() {
        isRunning = false;
        if (lbl_beHung != null) {
            lbl_beHung.setVisibility(GONE);
        }
        if (threadThaVatThe != null && threadThaVatThe.isAlive()) {
            threadThaVatThe.interrupt();
        }
    }
}
