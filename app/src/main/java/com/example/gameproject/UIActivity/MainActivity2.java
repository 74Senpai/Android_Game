package com.example.gameproject.UIActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameproject.R;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void createGridButtonsDynamic(Context context, LinearLayout parentLayout, int itemsPerRow      // số phần tử mỗi hàng (<= 5)
    ) {
        parentLayout.removeAllViews();

        if (itemsPerRow > 5) itemsPerRow = 5;

        int marginPx = dpToPx(context, 10);

        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

        int totalMargin = marginPx * (itemsPerRow + 1);
        int buttonSize = (screenWidth - totalMargin) / itemsPerRow;

        LinearLayout currentRow = null;

        // Lấy danh sách game trực tiếp từ Enum
        ChoiceGame[] gameList = ChoiceGame.values();

        for (int i = 0; i < gameList.length; i++) {

            if (i % itemsPerRow == 0) {
                currentRow = new LinearLayout(context);
                currentRow.setOrientation(LinearLayout.HORIZONTAL);
                currentRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parentLayout.addView(currentRow);
            }

            // Lấy game hiện tại (cố định theo vòng lặp)
            ChoiceGame finalGame = gameList[i];

            Button btn = new Button(context);
            btn.setText(finalGame.getUIName());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonSize, buttonSize);

            params.setMargins(marginPx, marginPx, 0, marginPx);
            btn.setLayoutParams(params);

            // === EVENT CLICK -> Truyền Intent giống bạn làm ===
            btn.setOnClickListener(v -> {

                Intent gameIntent = new Intent(context, MainActivity3.class);

                Bundle bundle = new Bundle();
                bundle.putString("UIName", finalGame.getUIName());

                gameIntent.putExtra("GameCenter", bundle);

                context.startActivity(gameIntent);
            });

            currentRow.addView(btn);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        LinearLayout gameContainer = findViewById(R.id.grav);

        Button btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity2.this.finish();
            }
        });

        ArrayList<String> gameList = new ArrayList<>();
        gameList.add(ChoiceGame.GAME_CHIM_BAY.getUIName());
        gameList.add(ChoiceGame.GAME_HUNG_BIA.getUIName());

        createGridButtonsDynamic(this, gameContainer, 4);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
