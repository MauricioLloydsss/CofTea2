package com.example.buyproducts;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

public class cashiersHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashiers_home);

        Button Stocks = findViewById(R.id.Stocks);
        Button orderProductsBTN = findViewById(R.id.orderProductsBTN);
        Button QueueBTN = findViewById(R.id.Queue);


        orderProductsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the SecondActivity
                Intent intent = new Intent(cashiersHome.this, ProductListActivity.class);
                startActivity(intent);
            }
        });


        Stocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the SecondActivity
                Intent intent = new Intent(cashiersHome.this, ManageProductActivitiyList.class);
                startActivity(intent);
            }
        });

        QueueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the SecondActivity
                Intent intent = new Intent(cashiersHome.this, ManageQueueActivityList.class);
                startActivity(intent);
            }
        });
    }
}