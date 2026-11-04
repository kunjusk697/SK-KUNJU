package com.skkunju.ninemx;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {
    private LinearLayout transcript;
    private ScrollView transcriptScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        transcript = findViewById(R.id.transcript);
        transcriptScroll = findViewById(R.id.transcriptScroll);
        EditText message = findViewById(R.id.message);
        findViewById(R.id.back).setOnClickListener(v -> finish());
        addBubble(getString(R.string.chat_intro), false);
        findViewById(R.id.send).setOnClickListener(v -> {
            String text = message.getText().toString().trim();
            if (text.isEmpty()) {
                return;
            }
            addBubble(text, true);
            message.setText("");
            addBubble(getString(R.string.chat_reply), false);
        });
    }

    private void addBubble(String text, boolean fromUser) {
        TextView bubble = new TextView(this);
        bubble.setText(text);
        bubble.setTextColor(0xFFFFFFFF);
        bubble.setTextSize(15);
        int pad = (int) (12 * getResources().getDisplayMetrics().density);
        bubble.setPadding(pad, pad, pad, pad);
        bubble.setBackgroundResource(fromUser ? R.drawable.bg_bubble_user : R.drawable.bg_bubble);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = pad;
        params.gravity = fromUser ? Gravity.END : Gravity.START;
        int side = (int) (48 * getResources().getDisplayMetrics().density);
        if (fromUser) {
            params.leftMargin = side;
        } else {
            params.rightMargin = side;
        }
        transcript.addView(bubble, params);
        transcriptScroll.post(() -> transcriptScroll.fullScroll(ScrollView.FOCUS_DOWN));
    }
}
