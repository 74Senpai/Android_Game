package com.example.gameproject.GameHungBia.Entities;

import static android.view.View.GONE;

import android.content.Context;
import android.widget.RelativeLayout;

import com.example.gameproject.GameHungBia.Entities.FallObjects.Bom;
import com.example.gameproject.GameHungBia.Entities.FallObjects.Chanh;
import com.example.gameproject.GameHungBia.Entities.FallObjects.LyBia;
import com.example.gameproject.GameHungBia.Entities.FallObjects.NuocLoc;
import com.example.gameproject.GameBase.GameBase;

import java.util.Random;

public class GameObjectManager {
    private final Context context;
    public final RelativeLayout layoutVungTha;
    private final Random random = new Random();
    private final GameBase gameBase;
    private final float diemTha;
    private final int maxX;
    private VatTheHung vatTheHung;

    public GameObjectManager(Context context, GameBase gameBase) {
        this.context = context;
        this.layoutVungTha = gameBase.layoutGame;
        this.gameBase = gameBase;
        this.diemTha = gameBase.getStartFallingPoint();
        this.maxX = layoutVungTha.getWidth() - 100;

        initVatTheHung();
    }

    public void createVatTheRoi() {
        int score = gameBase.score.get();
        int live = gameBase.lifes.get();
        initLyBia();
        if ((live < 3 && score > 100) || score > 2700) {
            initNuocLoc();
        }
        if (live < 4 && vatTheHung.lbl_beHung.getWidth() <= vatTheHung.maxWidth && score > 500) {
            initChanh();
        }
        if (score > 3000) {
            initBom();
        }
    }

    private void initLyBia() {
        int randomX = random.nextInt(Math.max(maxX, 1));
        LyBia lyBia = new LyBia(
                gameBase.fallingSpeed(GameBase.EntityType.DEFAULT),
                randomX,
                (int) diemTha,
                vatTheHung,
                gameBase
        );
        lyBia.khoiTaoVatThe();
        lyBia.Roi();
    }

    private void initNuocLoc() {
        int randomX = random.nextInt(Math.max(maxX, 1));
        NuocLoc lyNuoc = new NuocLoc(
                gameBase.fallingSpeed(GameBase.EntityType.HYBRID),
                randomX,
                (int) diemTha,
                vatTheHung,
                gameBase
        );
        lyNuoc.khoiTaoVatThe();
        lyNuoc.Roi();
    }

    private void initChanh() {
        int randomX = random.nextInt(Math.max(maxX, 1));
        Chanh quaChanh = new Chanh(
                gameBase.fallingSpeed(GameBase.EntityType.GOOD),
                randomX,
                0,
                vatTheHung,
                gameBase
        );
        quaChanh.khoiTaoVatThe();
        quaChanh.Roi();
    }

    private void initBom() {
        int randomX = random.nextInt(Math.max(maxX, 1));
        Bom quaBom = new Bom(
                gameBase.fallingSpeed(GameBase.EntityType.BAD),
                randomX,
                0,
                vatTheHung,
                gameBase
        );
        quaBom.khoiTaoVatThe();
        quaBom.Roi();
    }

    private void initVatTheHung() {
        this.vatTheHung = new VatTheHung(layoutVungTha, context);
        vatTheHung.init();
        vatTheHung.setDragEvent();
        vatTheHung.addToView();
    }

    public void anVatTheHung() {
        this.vatTheHung.lbl_beHung.setVisibility(GONE);
    }
}
