package com.example.vowvenue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    EditText emailET;
    EditText passET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);


            emailET = findViewById(R.id.editTextEmailAddress);
            passET = findViewById(R.id.editTextPassword);

            return insets;
        });
    }


    public void login(View view) {
        String printnameStr = emailET.getText().toString();
        String printpassStr = passET.getText().toString();

        Log.i(LOG_TAG, "Bejelentkezett " + printnameStr + " jelsz: " + printpassStr);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);
    }
}