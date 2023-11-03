package com.example.buyproducts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class IngredientsSTocks extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapterIngredientsStocks mainAdapter;
    FloatingActionButton floatingActionButton;
    Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spinnerCategory = findViewById(R.id.category);

        // Create an ArrayAdapter for the category spinner
        String[] categoryOptions = {"All", "Material", "Ingredients"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryOptions);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        FirebaseRecyclerOptions<MainModelIngridients> options = new FirebaseRecyclerOptions.Builder<MainModelIngridients>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Ingredients"), MainModelIngridients.class)
                .build();

        mainAdapter = new MainAdapterIngredientsStocks(options);
        recyclerView.setAdapter(mainAdapter);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddIngredientsActivity.class));
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected category
                String selectedCategory = spinnerCategory.getSelectedItem().toString();
                filterItemsByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle nothing selected if needed
            }
        });
    }

    private void filterItemsByCategory(String selectedCategory) {
        Query query;
        if ("All".equals(selectedCategory)) {
            query = FirebaseDatabase.getInstance().getReference().child("Ingredients");
        } else {
            query = FirebaseDatabase.getInstance().getReference().child("Ingredients").orderByChild("category").equalTo(selectedCategory);
        }

        FirebaseRecyclerOptions<MainModelIngridients> filteredOptions = new FirebaseRecyclerOptions.Builder<MainModelIngridients>()
                .setQuery(query, MainModelIngridients.class)
                .build();

        mainAdapter.updateOptions(filteredOptions);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}
