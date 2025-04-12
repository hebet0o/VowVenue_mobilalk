package com.example.vowvenue;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisterActivity.class.getName();

    EditText nameET;
    EditText emailET;
    EditText passET;
    EditText passConfirmET;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        emailET = findViewById(R.id.editTextText2);
        nameET = findViewById(R.id.editTextText);
        passET = findViewById(R.id.editTextTextPassword);
        passConfirmET = findViewById(R.id.editTextTextPassword2);

        mAuth = FirebaseAuth.getInstance();
    }

    public void backToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void StartShopping(){
        Intent inti = new Intent(this, VenueActivity.class);
        startActivity(inti);
    }

    public void firebaseReg(View view) {
        String email = emailET.getText().toString();
        String pass = passET.getText().toString();
        String passConfirm = passConfirmET.getText().toString();
        String name = nameET.getText().toString();

        if(!pass.equals(passConfirm)){
            Log.e(LOG_TAG, "Nem egyezik a két jelszó");
            Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
        if (email.isEmpty() || pass.isEmpty() || passConfirm.isEmpty()  ||name.isEmpty() ){
            Toast.makeText(RegisterActivity.this, "Fill all of the input fields!", Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "User created");
                    StartShopping();
                } else{
                    Log.d(LOG_TAG, "User cannot be created");
                    Toast.makeText(RegisterActivity.this, "User cannot be registered: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Log.i(LOG_TAG, "Regisztrált email:" + email + " pass: " + pass + " confirmPass: " + passConfirm + " name: " + name);

    }
}