package com.example.gameproject;

import static android.view.View.TEXT_ALIGNMENT_TEXT_END;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lớp cơ sở quản lý game: score, lifes, start button
 * Các lớp khác kế thừa có thể gọi updateScore/updateLifes
 */
public class GameBase {

    private final Random random = new Random();
    private final String heart = "💖";
    public AtomicInteger score = new AtomicInteger(0);
    public AtomicInteger lifes = new AtomicInteger(5);
    public Context context;
    public RelativeLayout layoutGame;
    protected TextView lbl_Score, lbl_Life, lbl_Process;
    protected Button btnStart;
    protected GameDBHelper dbHelper;
    private float layoutWidth;
    enum GAME_STATE {
        STOP,
        RUNNING,
        OVER,
        WAIT,
    }

    private GAME_STATE currentState = GAME_STATE.WAIT;

    public GameBase(Context context, RelativeLayout layoutGame) {
        this.context = context;
        this.layoutGame = layoutGame;
        this.dbHelper = new GameDBHelper(context);

        //Đợi vẽ xong layout rồi mới tạo
        layoutGame.post(() -> {
            initProcessBar();
            initLifesBar();
            initScoreBar();
            startGameButton();
        });

    }

    public float getStartFallingPoint() {
        return lbl_Score.getY();
    }

    protected void initProcessBar() {
        layoutWidth = layoutGame.getWidth();

        lbl_Process = new TextView(context);
        lbl_Process.setId(View.generateViewId());
        lbl_Process.setBackgroundColor(Color.GREEN);
        lbl_Process.setText("🔥");
        lbl_Process.setTextSize(20);
        lbl_Process.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
        lbl_Process.setGravity(Gravity.END);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                50,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lbl_Process.setLayoutParams(params);

        layoutGame.addView(lbl_Process);
    }

    protected void initLifesBar() {
        lbl_Life = new TextView(context);
        lbl_Life.setId(View.generateViewId());
        lbl_Life.setText(heart.repeat(lifes.get()));
        lbl_Life.setTextSize(20);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, lbl_Process.getId());
        lbl_Life.setLayoutParams(params);

        layoutGame.addView(lbl_Life);
    }

    protected void initScoreBar() {
        lbl_Score = new TextView(context);
        lbl_Score.setId(View.generateViewId());
        lbl_Score.setText("Score : " + score.get());
        lbl_Score.setTextSize(20);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, lbl_Life.getId());
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lbl_Score.setLayoutParams(params);

        layoutGame.addView(lbl_Score);
    }


    /**
     * Cập nhật điểm
     */
    public void updateScore(int delta) {
        if(this.currentState != GAME_STATE.RUNNING) return;
        score.addAndGet(delta);
        if (score.get() < 0) score.set(0);
        if (lbl_Score != null) {
            lbl_Score.setText("Score : " + score.get());
            updateProcessBar(score.get() * 100 / 4000);
        }
    }

    /**
     * Cập nhật tim
     */
    public void updateLifes(int delta) {
        if(this.currentState != GAME_STATE.RUNNING) return;
        int heartCount = lifes.addAndGet(delta);
        if (heartCount < 1) {
            heartCount = 0;
            lifes.getAndSet(0);
            gameOver();
        }
        if (lbl_Life != null) {
            if (heartCount > 5) {
                lbl_Life.setText(heart.repeat(5) + "💛".repeat(heartCount - 5));
            } else {
                lbl_Life.setText(heart.repeat(heartCount));
            }
        }
    }

    protected void updateProcessBar(int delta) {
        if(this.currentState != GAME_STATE.RUNNING) return;
        if (lbl_Process != null) {
            int newWidth = Math.max((int) (layoutWidth * delta / 100.0), 50);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) lbl_Process.getLayoutParams();
            params.width = newWidth;
            lbl_Process.setLayoutParams(params);
        }
    }

    /**
     * Khởi tạo nút Start Game
     */
    protected void startGameButton() {
        if(this.currentState == GAME_STATE.RUNNING) return;
        this.currentState = GAME_STATE.RUNNING;
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
        if (this.currentState == GAME_STATE.OVER) return;
        this.currentState = GAME_STATE.STOP;
        stopGame();
        saveScore(score.get());
        showGameOverDialog(score.get());
//        if (btnStart != null) {
//            btnStart.setVisibility(View.VISIBLE);
//            btnStart.setEnabled(true);
//        }
        this.currentState = GAME_STATE.OVER;
    }

    protected void resetGame() {
        if(this.currentState == GAME_STATE.RUNNING) return;
        this.currentState = GAME_STATE.RUNNING;
        score.set(0);
        lifes.set(5);

        // Update UI
        updateLifes(0);
        updateScore(0);
        updateProcessBar(0);

//        if (btnStart != null) {
//            btnStart.setVisibility(View.INVISIBLE);
//        }
        startGame();
    }

    protected void showGameOverDialog(int currentScore) {
        if(this.currentState != GAME_STATE.STOP) return;
        int highScore = dbHelper.getHighScore();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Game Over");
        builder.setCancelable(false);
        builder.setMessage("Điểm của bạn: " + currentScore + "\nKỉ lục: " + highScore);
        builder.setPositiveButton("Chơi lại", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                resetGame();
            }
        });
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Lưu điểm vào database
     */
    protected void saveScore(int score) {
        dbHelper.addScore(score);
    }


    /**
     * Random số nguyên trong khoảng [min, max]
     */
    protected int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * Random số thực trong khoảng [min, max]
     */
    protected float randomFloat(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }
}
