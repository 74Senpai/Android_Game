package com.example.gameproject.Entities;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gameproject.GameBase;

import java.util.Random;

public class GameObjectManager {
    private final Context context;
    private final RelativeLayout layoutVungTha;
    private final Random random = new Random();
    private final GameBase gameBase;

    public GameObjectManager(Context context, RelativeLayout layoutVungTha, GameBase gameBase) {
        this.context = context;
        this.layoutVungTha = layoutVungTha;
        this.gameBase = gameBase;
    }

    public TextView createVatTheHung() {
        VatTheHung vatTheHung = new VatTheHung(layoutVungTha, context);
        vatTheHung.init();
        vatTheHung.setDragEvent();
        vatTheHung.addToView();
        return vatTheHung.lbl_beHung;
    }

    public void createVatTheRoi(int maxX, TextView lbl_beHung) {
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
                gameBase
        );

        lyBia.khoiTaoVatThe();
        lyBia.Roi();
    }
}
