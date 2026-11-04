package com.skkunju.ninemx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PageActivity extends AppCompatActivity {
    public static void open(Context context, String title, String body) {
        Intent intent = new Intent(context, PageActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("body", body);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        ((TextView) findViewById(R.id.pageTitle)).setText(getIntent().getStringExtra("title"));
        ((TextView) findViewById(R.id.pageBody)).setText(getIntent().getStringExtra("body"));
        findViewById(R.id.back).setOnClickListener(v -> finish());
    }
}
