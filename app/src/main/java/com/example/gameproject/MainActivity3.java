package com.example.gameproject;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.graphics.Path;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        RelativeLayout layout = findViewById(R.id.layout_main);

        ThaVatTheRoi thaVatTheRoi = new ThaVatTheRoi(layout, this);

        thaVatTheRoi.BatDauTha();

        TextView lbl_beHung = new TextView(this);
        lbl_beHung.setText("Be hung");
        lbl_beHung.setBackgroundColor(Color.RED);
        lbl_beHung.setTextSize(30);
        lbl_beHung.setX(60);
        lbl_beHung.setY(1300f);
        lbl_beHung.setOnTouchListener(new View.OnTouchListener() {
            float dX, dY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Lưu vị trí ban đầu khi chạm
                        dX = view.getX() - event.getRawX();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        // Cập nhật vị trí khi kéo
                        view.animate()
                                .x(event.getRawX() + dX)
                                .setDuration(0)
                                .start();
                        return true;

                    default:
                        return false;
                }
            }
        });

        layout.addView(lbl_beHung);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}