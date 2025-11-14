package com.example.gameproject.UIActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameproject.R;
import com.example.gameproject.GameHungBia.GameHungBia;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        RelativeLayout layout = findViewById(R.id.layout_main);

        Bundle bundle = getIntent().getBundleExtra("GameCenter");
        String uiName = bundle.getString("UIName");
        ChoiceGame selectedGame = ChoiceGame.findByUIName(uiName);

        switch (selectedGame) {
            case GAME_HUNG_BIA:
                new GameHungBia(this, layout);
                break;
            case GAME_CHIM_BAY:
                TextView notifi = new TextView(this);
                notifi.setText("Game chưa phát hành");
                notifi.setTextSize(36);
                notifi.setGravity(Gravity.CENTER);
                layout.addView(notifi);
                Toast.makeText(MainActivity3.this, "Game chưa phát hành", Toast.LENGTH_LONG).show();
                break;
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}