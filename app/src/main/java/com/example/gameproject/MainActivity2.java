package com.example.gameproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        GridView gridView_gameCenter = findViewById(R.id.gridview_gameCenter);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                gameList
        );

        gridView_gameCenter.setAdapter(adapter);

        gridView_gameCenter.setOnItemClickListener((parent, view, position, id) -> {
            String selectedGame = gameList.get(position);

            Intent gameIntent = new Intent(MainActivity2.this, MainActivity3.class);

            Bundle bundle = new Bundle();
            bundle.putString("UIName", selectedGame);

            gameIntent.putExtra("GameCenter", bundle);

            startActivity(gameIntent);

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
