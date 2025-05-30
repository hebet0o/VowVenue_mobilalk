package com.example.vowvenue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    EditText emailET;
    EditText passET;

    Button loginBtn;
    Button registerBtn;
    Button guestLoginBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
        emailET = findViewById(R.id.editTextEmailAddress);
        passET = findViewById(R.id.editTextPassword);

        mAuth = FirebaseAuth.getInstance();

        loginBtn = findViewById(R.id.loginButton);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        loginBtn.startAnimation(fadeIn);

        registerBtn = findViewById(R.id.registerButton);
        registerBtn.startAnimation(fadeIn);

        guestLoginBtn = findViewById(R.id.guestButton);
        guestLoginBtn.startAnimation(fadeIn);


    }

    public void StartShopping(){
        Intent intent = new Intent(MainActivity.this, VenueActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.fade_in);

    }

    public void login(View view) {
        String printnameStr = emailET.getText().toString();
        String printpassStr = passET.getText().toString();

        if (printnameStr.isEmpty() || printpassStr.isEmpty() ){
            Toast.makeText(MainActivity.this, "Fill all of the input fields!", Toast.LENGTH_SHORT).show();
        }

        mAuth.signInWithEmailAndPassword(printnameStr, printpassStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "User logged in.");
                    StartShopping();
                } else{
                    Log.d(LOG_TAG, "User can't be logged in.");
                    Toast.makeText(MainActivity.this, "Something went wrong" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Log.i(LOG_TAG, "Bejelentkezett " + printnameStr + " jelsz: " + printpassStr);
    }

    public void register(View view) {
        Intent inti = new Intent(this, RegisterActivity.class);
        startActivity(inti);
    }

    public void guestLogin(View view) {
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "User logged in Anonymously.");
                    StartShopping();
                } else{
                    Log.d(LOG_TAG, "User can't be logged in Anonymously");
                    Toast.makeText(MainActivity.this, "Something went wrong" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}