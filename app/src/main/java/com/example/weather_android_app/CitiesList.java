package com.example.weather_android_app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class CitiesList extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> catNamesList;
    private Button btnDelete;
    private ArrayList<Integer> selectedForDeletion; // Список индексов выбранных для удаления
    private ArrayList<Integer> selectedForMove; // Список индексов выбранных для перемещения

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cities_list);

        ListView listView = findViewById(R.id.listView);
        btnDelete = findViewById(R.id.btnDelete);
        selectedForDeletion = new ArrayList<>(); // Инициализируем список выбранных для удаления
        selectedForMove = new ArrayList<>(); // Инициализируем список выбранных для перемещения

        catNamesList = new ArrayList<>(Arrays.asList(
                "Sydney", "New-York", "Moscow", "Minsk", "London",
                "Kiev", "Vladivostok", "Omsk", "Yakutsk", "Washington"
        ));

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, catNamesList) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                // Подсвечиваем элемент для удаления голубым или для перемещения желтым
                if (selectedForDeletion.contains(position)) {
                    view.setBackgroundColor(Color.CYAN); // Цвет для выделенных для удаления
                } else if (selectedForMove.contains(position)) {
                    view.setBackgroundColor(Color.YELLOW); // Цвет для выделенных для перемещения
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT); // Обычный цвет
                }
                return view;
            }
        };

        listView.setAdapter(adapter);

        // Обработчик касаний для определения двойного нажатия
        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // Получаем позицию элемента списка, по которому был выполнен двойной клик
                int position = listView.pointToPosition((int) e.getX(), (int) e.getY());
                if (position >= 0) {
                    // Перемещаем элемент на верх списка
                    String selectedCity = catNamesList.remove(position);
                    catNamesList.add(0, selectedCity);

                    // Добавляем элемент в список выбранных для перемещения
                    if (!selectedForMove.contains(0)) {
                        selectedForMove.add(0);
                    }

                    adapter.notifyDataSetChanged(); // Обновляем адаптер
                }
                return true;
            }
        });

        // Устанавливаем обработчик касаний на ListView
        listView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

        // Обработчик долгого нажатия на элемент списка для выделения
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (!selectedForDeletion.contains(position)) {
                selectedForDeletion.add(position); // Запоминаем выбранный индекс для удаления
            }
            adapter.notifyDataSetChanged(); // Обновляем адаптер
            return true; // Возвращаем true, чтобы показать, что событие обработано
        });

        // Обработчик нажатия на кнопку удаления
        btnDelete.setOnClickListener(v -> {
            if (!selectedForDeletion.isEmpty()) {
                // Удаляем выбранные элементы, которые не выделены для перемещения
                for (int i = selectedForDeletion.size() - 1; i >= 0; i--) {
                    int index = selectedForDeletion.get(i);
                    if (!selectedForMove.contains(index)) { // Проверяем, не выделен ли элемент для перемещения
                        catNamesList.remove(index);
                    }
                }
                adapter.notifyDataSetChanged(); // Обновляем адаптер
                selectedForDeletion.clear(); // Очищаем список выбранных индексов для удаления
                selectedForMove.clear(); // Очищаем список выбранных индексов для перемещения
                Toast.makeText(CitiesList.this, "Cities removed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CitiesList.this, "No city selected for deletion!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}