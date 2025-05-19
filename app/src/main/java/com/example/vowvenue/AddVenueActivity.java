package com.example.vowvenue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddVenueActivity extends AppCompatActivity {

    private EditText editTextName, editTextLocation, editTextCapacity;
    private Button buttonSave, buttonBack;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);

        editTextName = findViewById(R.id.editTextVenueName);
        editTextLocation = findViewById(R.id.editTextVenueLocation);
        editTextCapacity = findViewById(R.id.editTextVenueCapacity);
        buttonSave = findViewById(R.id.buttonSaveVenue);
        buttonBack = findViewById(R.id.buttonBack);

        db = FirebaseFirestore.getInstance();

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String location = editTextLocation.getText().toString().trim();
            String capacityStr = editTextCapacity.getText().toString().trim();

            if (name.isEmpty() || location.isEmpty() || capacityStr.isEmpty()) {
                Toast.makeText(this, "Kérlek, tölts ki minden mezőt!", Toast.LENGTH_SHORT).show();
                return;
            }

            int capacity;
            try {
                capacity = Integer.parseInt(capacityStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "A kapacitásnak számnak kell lennie!", Toast.LENGTH_SHORT).show();
                return;
            }

            Venue venue = new Venue(name, location, capacity, "");
            db.collection("venues")
                    .add(venue)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Helyszín hozzáadva!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Hiba történt: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        buttonBack.setOnClickListener(v -> {
            finish();
        });
    }
}
