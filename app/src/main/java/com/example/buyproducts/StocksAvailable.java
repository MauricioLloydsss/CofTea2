package com.example.buyproducts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StocksAvailable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks_available);

        Button IngredientsBTN = findViewById(R.id.IngredientsBTN);
        Button addNewProductBTN = findViewById(R.id.addNewProductBTN);


        IngredientsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StocksAvailable.this, IngredientsSTocks.class);
                startActivity(intent);
            }
        });

        addNewProductBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StocksAvailable.this, ManageProductActivitiyList.class);
                startActivity(intent);
            }
        });
    }
}