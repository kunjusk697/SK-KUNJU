package com.skkunju.ninemx;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView languageLabel = findViewById(R.id.languageLabel);
        findViewById(R.id.language).setOnClickListener(v -> {
            String[] languages = {"English", "中文", "Español"};
            new AlertDialog.Builder(this)
                    .setTitle(R.string.language_title)
                    .setItems(languages, (dialog, which) -> languageLabel.setText(languages[which]))
                    .show();
        });

        findViewById(R.id.startNow).setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class)));
    }
}
