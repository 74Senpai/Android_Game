package com.example.gameproject.Entities;

import static android.view.View.GONE;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gameproject.GameBase;

import java.util.Random;

public class GameObjectManager {
    private final Context context;
    public final RelativeLayout layoutVungTha;
    private final Random random = new Random();
    private final GameBase gameBase;
    private final float diemTha;
    private final int maxX;
    private final TextView lbl_beHung;

    public GameObjectManager(Context context, GameBase gameBase) {
        this.context = context;
        this.layoutVungTha = gameBase.layoutGame;
        this.gameBase = gameBase;
        this.diemTha = gameBase.getStartFallingPoint();
        this.lbl_beHung = initVatTheHung();
        this.maxX = layoutVungTha.getWidth() - 100;
    }

    public void createVatTheRoi() {
        initLyBia();
        if (maxX % 5 == 0
                && (gameBase.lifes.get() < 3 && gameBase.score.get() > 100)
                || gameBase.score.get() > 2700) {
            initNuocLoc();
        }
        if (gameBase.lifes.get() < 4
                && lbl_beHung.getWidth() <= 200
                && gameBase.score.get() > 500) {
            initChanh();
        }
    }

    private void initLyBia() {
        int randomX = random.nextInt(Math.max(maxX, 1));
        LyBia lyBia = new LyBia(
                4000 - gameBase.score.get(),
                randomX,
                (int)diemTha,
                lbl_beHung,
                gameBase
        );
        lyBia.khoiTaoVatThe();
        lyBia.Roi();
    }

    private void initNuocLoc(){
        int randomX = random.nextInt(Math.max(maxX, 1));
        NuocLoc lyNuoc = new NuocLoc(
                2500 + gameBase.score.get(),
                randomX,
                (int)diemTha,
                lbl_beHung,
                gameBase
        );
        lyNuoc.khoiTaoVatThe();
        lyNuoc.Roi();
    }

    private void initChanh(){
        int randomX = random.nextInt(Math.max(maxX, 1));
        Chanh quaChanh = new Chanh(
                2000,
                randomX,
                0,
                lbl_beHung,
                gameBase
        );
        quaChanh.khoiTaoVatThe();
        quaChanh.Roi();
    }
    private TextView initVatTheHung() {
        VatTheHung vatTheHung = new VatTheHung(layoutVungTha, context);
        vatTheHung.init();
        vatTheHung.setDragEvent();
        vatTheHung.addToView();
        return vatTheHung.lbl_beHung;
    }

    public void anVatTheHung(){
        this.lbl_beHung.setVisibility(GONE);
    }
}
