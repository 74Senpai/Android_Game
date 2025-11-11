package com.example.gameproject;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lớp cơ sở quản lý game: score, lifes, start button
 * Các lớp khác kế thừa có thể gọi updateScore/updateLifes
 */
public class GameBase {

    protected AtomicInteger score = new AtomicInteger(0);
    protected AtomicInteger lifes = new AtomicInteger(5);
    protected TextView lbl_Score, lbl_Life;
    protected Button btnStart;
    protected Context context;
    protected RelativeLayout layoutGame;
    protected volatile boolean isRuning = false;

    public GameBase(Context context, RelativeLayout layoutGame) {
        this.context = context;
        this.layoutGame = layoutGame;

        initScoreBar();
        initLifesBar();
        startGameButton();
    }

    /**
     * Khởi tạo thanh điểm
     */
    protected void initScoreBar() {
        lbl_Score = new TextView(context);
        lbl_Score.setText("Score: " + score.get());
        lbl_Score.setTextSize(20);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lbl_Score.setLayoutParams(params);
        layoutGame.addView(lbl_Score);
    }

    /**
     * Cập nhật điểm
     */
    protected void updateScore(int delta) {
        score.addAndGet(delta);
        if (score.get() < 0) score.set(0);
        if (lbl_Score != null) {
            lbl_Score.setText("Score: " + score.get());
        }
    }

    /**
     * Khởi tạo thanh tim
     */
    protected void initLifesBar() {
        lbl_Life = new TextView(context);
        lbl_Life.setText("Lifes: " + lifes.get());
        lbl_Life.setTextSize(20);
        layoutGame.addView(lbl_Life);
    }

    /**
     * Cập nhật tim
     */
    protected void updateLifes(int delta) {
        lifes.addAndGet(delta);
        if (lifes.get() < 0) {
            lifes.getAndSet(0);
            gameOver();
        }
        if (lbl_Life != null) {
            lbl_Life.setText("Lifes: " + lifes.get());
        }
    }

    /**
     * Khởi tạo nút Start Game
     */
    protected void startGameButton() {
        btnStart = new Button(context);
        btnStart.setText("Start Game");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        btnStart.setLayoutParams(params);
        layoutGame.addView(btnStart);
        score.getAndSet(0);
        lifes.getAndSet(5);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                btnStart.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * Hàm bắt đầu game, lớp con override để thực hiện logic thả vật thể
     */
    protected void startGame() {
        // Lớp con override
    }

    protected void stopGame() {

    }

    /**
     * Hàm kết thúc game
     */
    protected void gameOver() {
        stopGame();
        // Lớp con override hoặc hiện thông báo
        if (btnStart != null) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setEnabled(true);
        }
    }
}
