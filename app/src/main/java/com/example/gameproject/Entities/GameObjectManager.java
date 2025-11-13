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
        int score = gameBase.score.get();
        int live = gameBase.score.get();
        initLyBia();
        if (maxX % 5 == 0 && (live < 3 && score > 100) || score > 2700) {
            initNuocLoc();
        }
        if (live < 4 && lbl_beHung.getWidth() <= 200 && score > 500) {
            initChanh();
        }
        if(score > 3000){
            initBom();
        }
    }

    private void initLyBia() {
        int randomX = random.nextInt(Math.max(maxX, 1));
        LyBia lyBia = new LyBia(
                gameBase.fallingSpeed(GameBase.EntityType.DEFAULT),
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
                gameBase.fallingSpeed(GameBase.EntityType.HYBRID),
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
                gameBase.fallingSpeed(GameBase.EntityType.GOOD),
                randomX,
                0,
                lbl_beHung,
                gameBase
        );
        quaChanh.khoiTaoVatThe();
        quaChanh.Roi();
    }

    private void initBom(){
        int randomX = random.nextInt(Math.max(maxX, 1));
        Bom quaBom = new Bom(
                gameBase.fallingSpeed(GameBase.EntityType.BAD),
                randomX,
                0,
                lbl_beHung,
                gameBase
        );
        quaBom.khoiTaoVatThe();
        quaBom.Roi();
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
