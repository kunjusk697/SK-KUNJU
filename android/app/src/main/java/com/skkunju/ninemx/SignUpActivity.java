package com.skkunju.ninemx;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText confirm;
    private EditText invitation;
    private TextView emailError;
    private TextView passwordError;
    private TextView confirmError;
    private TextView invitationError;
    private TextView submit;
    private CheckBox agree;
    private boolean invitationTouched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        invitation = findViewById(R.id.invitation);
        emailError = findViewById(R.id.emailError);
        passwordError = findViewById(R.id.passwordError);
        confirmError = findViewById(R.id.confirmError);
        invitationError = findViewById(R.id.invitationError);
        submit = findViewById(R.id.submit);
        agree = findViewById(R.id.agree);

        findViewById(R.id.back).setOnClickListener(v -> finish());
        findViewById(R.id.toLogin).setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));
        wireToggle(password, findViewById(R.id.togglePassword));
        wireToggle(confirm, findViewById(R.id.toggleConfirm));

        TextWatcher watcher = new SimpleWatcher(this::refresh);
        email.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);
        confirm.addTextChangedListener(watcher);
        invitation.addTextChangedListener(watcher);
        agree.setOnCheckedChangeListener((button, checked) -> refresh());
        invitation.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                invitationTouched = true;
                refresh();
            }
        });
        submit.setOnClickListener(v -> {
            if (!formReady()) {
                invitationTouched = true;
                refresh();
                return;
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        refresh();
    }

    private void refresh() {
        String emailText = email.getText().toString().trim();
        boolean emailValid = Patterns.EMAIL_ADDRESS.matcher(emailText).matches();
        boolean showEmailError = !emailText.isEmpty() && !emailValid;
        email.setBackgroundResource(showEmailError ? R.drawable.bg_input_error : R.drawable.bg_input);
        emailError.setVisibility(showEmailError ? TextView.VISIBLE : TextView.GONE);

        boolean shortPassword = !password.getText().toString().isEmpty() && password.length() < 6;
        passwordError.setVisibility(shortPassword ? TextView.VISIBLE : TextView.GONE);

        String confirmText = confirm.getText().toString();
        boolean mismatch = !confirmText.isEmpty() && !confirmText.equals(password.getText().toString());
        confirm.setBackgroundResource(mismatch ? R.drawable.bg_input_error : R.drawable.bg_input);
        confirmError.setVisibility(mismatch ? TextView.VISIBLE : TextView.GONE);

        boolean missingCode = invitation.getText().toString().trim().isEmpty();
        invitationError.setVisibility(invitationTouched && missingCode ? TextView.VISIBLE : TextView.GONE);

        boolean ready = formReady();
        submit.setBackgroundResource(ready ? R.drawable.bg_button_yellow : R.drawable.bg_button_disabled);
        submit.setTextColor(getColor(ready ? R.color.yellow_text : R.color.disabled_text));
    }

    private boolean formReady() {
        String emailText = email.getText().toString().trim();
        return Patterns.EMAIL_ADDRESS.matcher(emailText).matches()
                && password.length() >= 6
                && password.getText().toString().equals(confirm.getText().toString())
                && !invitation.getText().toString().trim().isEmpty()
                && agree.isChecked();
    }

    private void wireToggle(EditText field, ImageView icon) {
        icon.setOnClickListener(v -> {
            boolean show = !Boolean.TRUE.equals(icon.getTag());
            icon.setTag(show);
            int cursor = field.getSelectionStart();
            field.setInputType(EditorInfo.TYPE_CLASS_TEXT | (show
                    ? EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    : EditorInfo.TYPE_TEXT_VARIATION_PASSWORD));
            field.setTypeface(Typeface.SANS_SERIF);
            if (cursor >= 0) {
                field.setSelection(cursor);
            }
            icon.setImageResource(show ? R.drawable.ic_eye_off : R.drawable.ic_eye);
            icon.setContentDescription(getString(show ? R.string.hide_password : R.string.show_password));
        });
    }

    private static final class SimpleWatcher implements TextWatcher {
        private final Runnable after;

        private SimpleWatcher(Runnable after) {
            this.after = after;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            after.run();
        }
    }
}
