package com.example.gameproject;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class GameBase {

    protected AtomicInteger score = new AtomicInteger(0);
    protected AtomicInteger lives = new AtomicInteger(5);
    protected TextView lbl_Score, lbl_Life;
    protected Button btnStart;
    protected Context context;
    protected RelativeLayout layoutGame;
    protected volatile boolean isRuning = false;

    // === Progress Bar ===
    protected View progressBackground;
    protected View progressFill;
    protected int screenWidth;

    public GameBase(Context context, RelativeLayout layoutGame) {
        this.context = context;
        this.layoutGame = layoutGame;

        // Lấy kích thước màn hình (dùng để tính chiều rộng thanh progress)
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;

        initProgressBar();
        initScoreBar();
        initLifesBar();
        startGameButton();
    }

    /** ===================== PROGRESS BAR ===================== */
    protected void initProgressBar() {
        // Nền progress (màu xám)
        progressBackground = new View(context);
        progressBackground.setBackgroundColor(Color.GRAY);
        RelativeLayout.LayoutParams bgParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, 30);
        bgParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutGame.addView(progressBackground, bgParams);

        // Phần đã hoàn thành (màu xanh)
        progressFill = new View(context);
        progressFill.setBackgroundColor(Color.GREEN);
        RelativeLayout.LayoutParams fillParams = new RelativeLayout.LayoutParams(0, 30);
        fillParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutGame.addView(progressFill, fillParams);
    }

    /**
     * Cập nhật tiến trình (0 - 100%)
     */
    public void updateProgress(int percent) {
        if (percent < 0) percent = 0;
        if (percent > 100) percent = 100;

        int newWidth = (int) (screenWidth * (percent / 100.0));

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) progressFill.getLayoutParams();
        params.width = newWidth;
        progressFill.setLayoutParams(params);
    }

    /** ===================== SCORE ===================== */
    protected void initScoreBar() {
        lbl_Score = new TextView(context);
        lbl_Score.setText("Score: " + score.get());
        lbl_Score.setTextSize(20);
        lbl_Score.setTextColor(Color.WHITE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.topMargin = 40; // tránh đè lên progress bar
        lbl_Score.setLayoutParams(params);
        layoutGame.addView(lbl_Score);
    }

    public void updateScore(int delta) {
        score.addAndGet(delta);
        if (score.get() < 0) score.set(0);
        if (lbl_Score != null) {
            lbl_Score.setText("Score: " + score.get());
            updateProgress(score.get() * 100 / 4000);
        }
    }

    /** ===================== LIFE ===================== */
    protected void initLifesBar() {
        lbl_Life = new TextView(context);
        lbl_Life.setText("Lifes: " + lives.get());
        lbl_Life.setTextSize(20);
        lbl_Life.setTextColor(Color.WHITE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.topMargin = 40; // tránh đè lên progress bar
        lbl_Life.setLayoutParams(params);
        layoutGame.addView(lbl_Life);
    }

    public void updateLifes(int delta) {
        lives.addAndGet(delta);
        if (lives.get() < 0) {
            lives.getAndSet(0);
            gameOver();
        }
        if (lbl_Life != null) {
            lbl_Life.setText("Lifes: " + lives.get());
        }
    }

    /** ===================== START BUTTON ===================== */
    protected void startGameButton() {
        btnStart = new Button(context);
        btnStart.setText("Start Game");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        btnStart.setLayoutParams(params);
        layoutGame.addView(btnStart);

        score.getAndSet(0);
        lives.getAndSet(5);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                btnStart.setVisibility(View.INVISIBLE);
            }
        });
    }

    protected void startGame() {
        // Override ở lớp con
    }

    protected void stopGame() {
        // Override ở lớp con nếu cần
    }

    protected void gameOver() {
        stopGame();
        if (btnStart != null) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setEnabled(true);
        }
    }
}
