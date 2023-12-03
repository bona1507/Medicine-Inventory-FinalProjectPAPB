package com.example.stokbat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private FirebaseAuth fireBaseAuth;
    private TextView callAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.loginButton);
        callAdmin = findViewById(R.id.register);

        TextView registerTextView = findViewById(R.id.register);
        TextView tcTextView = findViewById(R.id.tc);

        SpannableString span1 = new SpannableString(registerTextView.getText());
        SpannableString span2 = new SpannableString(tcTextView.getText());

        ForegroundColorSpan ColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.buttonn));
        UnderlineSpan underlineSpan = new UnderlineSpan();

        span1.setSpan(ColorSpan, 18, span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span1.setSpan(underlineSpan, 18, span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        span2.setSpan(ColorSpan, 45, 65, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        span2.setSpan(underlineSpan, 45, 65, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        registerTextView.setText(span1);
        tcTextView.setText(span2);

        fireBaseAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                login(email, password);
            }
        });

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:+6285155070589"));
                startActivity(smsIntent);
            }
        };

        span1.setSpan(clickableSpan, 18, span1.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        callAdmin.setText(span1);
        callAdmin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void login(String email, String password) {
        fireBaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Welcome " + email + ". You have successfully logged in.", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle login failure
                    Toast.makeText(LoginActivity.this, "Sorry, your email and password are incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}