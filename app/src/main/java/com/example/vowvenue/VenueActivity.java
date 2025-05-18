package com.example.vowvenue;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class VenueActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VenueAdapter venueAdapter;
    private ArrayList<Venue> venueList;
    private FirebaseFirestore db;
    private Button buttonAddVenue;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);

        recyclerView = findViewById(R.id.recyclerViewVenues);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        venueList = new ArrayList<>();
        venueAdapter = new VenueAdapter(venueList);
        recyclerView.setAdapter(venueAdapter);

        db = FirebaseFirestore.getInstance();

        buttonAddVenue = findViewById(R.id.buttonAddVenue);
        buttonAddVenue.setOnClickListener(v -> {
            Intent intent = new Intent(VenueActivity.this, AddVenueActivity.class);
            startActivity(intent);
        });

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(VenueActivity.this, MainActivity.class));
            finish();
        });

        Button buttonQuery1 = findViewById(R.id.buttonQuery1);
        buttonQuery1.setOnClickListener(v -> query1());

        Button buttonQuery2 = findViewById(R.id.buttonQuery2);
        buttonQuery2.setOnClickListener(v -> query2());

        Button buttonQuery3 = findViewById(R.id.buttonQuery3);
        buttonQuery3.setOnClickListener(v -> query3());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }

        loadVenues();
    }

    private void loadVenues() {
        db.collection("venues")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    venueList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Venue venue = doc.toObject(Venue.class);
                        venueList.add(venue);
                    }
                    venueAdapter.notifyDataSetChanged();

                    NotificationHelper.sendNotification(this, "Új helyszín", "Sikeresen betöltötted a helyszíneket.", 1);
                    scheduleDelayedNotification();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba a helyszínek betöltésekor: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void scheduleDelayedNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long triggerAtMillis = SystemClock.elapsedRealtime() + 10 * 1000; // 10 másodperc

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                Toast.makeText(VenueActivity.this, "Aktuális helyzet: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Helymeghatározás engedély megtagadva", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVenues();
    }


    // query1: city = "Gyomaendrőd" és capacity > 500, rendezes capacity szerint
    private void query1() {
        db.collection("venues")
                .whereEqualTo("city", "Gyomaendrőd")
                .whereGreaterThan("capacity", 500)
                .orderBy("capacity", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    venueList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Venue venue = doc.toObject(Venue.class);
                        venueList.add(venue);
                    }
                    venueAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Query 1 eredmény", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba a lekérdezésnél: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // query2: city = "Gyomaendrőd", rendezes capacity szerint, limit 3
    private void query2() {
        db.collection("venues")
                .whereEqualTo("city", "Gyomaendrőd")
                .orderBy("capacity", com.google.firebase.firestore.Query.Direction.ASCENDING)
                .limit(3)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    venueList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Venue venue = doc.toObject(Venue.class);
                        venueList.add(venue);
                    }
                    venueAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Query 2 eredmény", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba a lekérdezésnél: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // query3:  capacity < 800, rendezes city szerint
    private void query3() {
        db.collection("venues")
                .whereLessThan("capacity", 800)
                .orderBy("city", com.google.firebase.firestore.Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    venueList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Venue venue = doc.toObject(Venue.class);
                        venueList.add(venue);
                    }
                    venueAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "Query 3 eredmény", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba a lekérdezésnél: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
