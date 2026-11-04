package com.skkunju.ninemx;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private TextView emailError;
    private TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        emailError = findViewById(R.id.emailError);
        submit = findViewById(R.id.submit);

        findViewById(R.id.back).setOnClickListener(v -> finish());
        findViewById(R.id.toSignUp).setOnClickListener(v -> finish());
        ImageView toggle = findViewById(R.id.togglePassword);
        toggle.setOnClickListener(v -> {
            boolean show = !Boolean.TRUE.equals(toggle.getTag());
            toggle.setTag(show);
            int cursor = password.getSelectionStart();
            password.setInputType(EditorInfo.TYPE_CLASS_TEXT | (show
                    ? EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    : EditorInfo.TYPE_TEXT_VARIATION_PASSWORD));
            password.setTypeface(Typeface.SANS_SERIF);
            if (cursor >= 0) {
                password.setSelection(cursor);
            }
            toggle.setImageResource(show ? R.drawable.ic_eye_off : R.drawable.ic_eye);
        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                refresh();
            }
        };
        email.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
        submit.setOnClickListener(v -> {
            if (!ready()) {
                refresh();
                return;
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        refresh();
    }

    private boolean ready() {
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()
                && password.length() > 0;
    }

    private void refresh() {
        String emailText = email.getText().toString().trim();
        boolean showError = !emailText.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(emailText).matches();
        email.setBackgroundResource(showError ? R.drawable.bg_input_error : R.drawable.bg_input);
        emailError.setVisibility(showError ? TextView.VISIBLE : TextView.GONE);
        boolean enabled = ready();
        submit.setBackgroundResource(enabled ? R.drawable.bg_button_yellow : R.drawable.bg_button_disabled);
        submit.setTextColor(getColor(enabled ? R.color.yellow_text : R.color.disabled_text));
    }
}
