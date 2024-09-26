package com.example.weather_android_app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CitiesList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cities_list);

        ListView listView = findViewById(R.id.listView);

        final String[] catNames = new String[] {
                "Sydney", "New-York", "Moscow", "Minsk", "London",
                "Kiev", "Vladivostok", "Omsk", "Yakutsk", "Washington",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, catNames);

        listView.setAdapter(adapter);

    }
}