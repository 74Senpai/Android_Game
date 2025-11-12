package com.example.gameproject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gameproject.Entities.Chanh;
import com.example.gameproject.Entities.GameObjectManager;
import com.example.gameproject.Entities.LyBia;
import com.example.gameproject.Entities.NuocLoc;
import com.example.gameproject.Entities.VatTheHung;

import java.util.Random;

public class ThaVatTheRoi extends GameBase {

    private boolean isRunning = false;
    private final android.os.Handler handler = new Handler(Looper.getMainLooper());
    private GameObjectManager objMg;

    public ThaVatTheRoi(Context context, RelativeLayout layoutVungTha) {
        super(context, layoutVungTha);
    }

    /**
     * Override startGame() từ GameBase
     */
    @Override
    protected void startGame() {
        if (isRunning) return;
        // Có thể reset score và lifes nếu cần
        this.objMg = new GameObjectManager(context, this);
        score.getAndSet(0);
        lifes.getAndSet(5);
        updateLifes(0);
        updateScore(0);
        isRunning = true;
        initGameThread();
        this.btnStart.setEnabled(false);
    }

    protected void initGameThread() {
        Runnable dropLoop = new Runnable() {
            @Override
            public void run() {
                if (!isRunning) return;
                long sleepTime = randomInt(500, 1000);
                objMg.createVatTheRoi();
                handler.postDelayed(this, sleepTime);
            }
        };

        handler.post(dropLoop);
    }

    /**
     * Dừng game
     */
    @Override
    protected void stopGame() {
        isRunning = false;
        objMg.anVatTheHung();
        isRunning = false;
        handler.removeCallbacksAndMessages(null);
    }

}
