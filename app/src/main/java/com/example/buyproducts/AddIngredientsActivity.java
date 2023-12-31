package com.example.buyproducts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddIngredientsActivity extends AppCompatActivity {

    Spinner measurement;
    Spinner spinnerCategory; // Spinner for selecting the category

    EditText name, turl, qty;
    TextView autoGeneratedId;
    Button btnAdd, BtnBack;
    private ImageView productImageView;
    private Button addImageButton;
    private Uri imageUri;
    private int idCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.name);
        spinnerCategory = findViewById(R.id.spinnerCategory); // Initialize the spinnerCategory
        autoGeneratedId = findViewById(R.id.autoGeneratedId);
        qty = findViewById(R.id.qty);
        productImageView = findViewById(R.id.productImageView);

        // Create an ArrayAdapter for the category spinner
        String[] categoryOptions = {"Material", "Ingredients"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryOptions);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        String[] measurementOptions = {" grams", " ml", " Shots", " Pieces"};
        ArrayAdapter<String> measurementAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, measurementOptions);
        measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurement = findViewById(R.id.spinnerMeasurement);
        measurement.setAdapter(measurementAdapter);

        qty.setFilters(new InputFilter[] { new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        }});
        qty.setInputType(InputType.TYPE_CLASS_NUMBER);

        autoGeneratedId.setText(String.valueOf(idCounter));

        btnAdd = findViewById(R.id.add);
        BtnBack = findViewById(R.id.back);

        addImageButton = findViewById(R.id.btnChooseImage);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddIngredientsActivity.this, IngredientsSTocks.class);
                startActivity(intent);
            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void insertData() {
        String enteredName = name.getText().toString();
        String selectedCategory = spinnerCategory.getSelectedItem().toString(); // Get selected category
        String selectedMeasurement = measurement.getSelectedItem().toString();
        String enteredQty = qty.getText().toString();
        productImageView.setImageResource(R.drawable.ic_menu_gallery); // Reset the image view

        if (enteredName.isEmpty() || selectedCategory.isEmpty() || selectedMeasurement.isEmpty() || enteredQty.isEmpty() || imageUri == null) {
            // Check if any of the fields are empty and display an error message
            Toast.makeText(AddIngredientsActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            // Check for existing IDs in the database
            checkExistingId(enteredName, selectedCategory, selectedMeasurement, imageUri, enteredQty);
        }
    }

    private void checkExistingId(final String name, final String category, final String measurement, Uri imageUri, final String qty) {
        final String autoId = String.valueOf(idCounter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Ingredients");

        // Query to check if an item with the same ID already exists
        Query query = databaseReference.orderByChild("id").equalTo(autoId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // An item with the same ID already exists, find a new ID
                    findNewId(databaseReference, name, category, measurement, imageUri, qty);
                } else {
                    // No item with the same ID exists, proceed with the insertion
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", autoId);
                    map.put("name", name);
                    map.put("category", category);
                    map.put("measurement", measurement);
                    map.put("qty", qty);
                    if (imageUri != null) {
                        map.put("turl", imageUri.toString());
                    }
                    idCounter++;
                    autoGeneratedId.setText(String.valueOf(idCounter));
                    insertData(map);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddIngredientsActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertData(Map<String, Object> data) {
        FirebaseDatabase.getInstance().getReference().child("Ingredients").push()
                .setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddIngredientsActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddIngredientsActivity.this, "No data inserted", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void findNewId(final DatabaseReference databaseReference, final String name, final String category, final String measurement, Uri imageUri, final String qty) {
        DatabaseReference idReference = FirebaseDatabase.getInstance().getReference().child("Ingredients");

        idReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> existingIds = new ArrayList<>();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String existingId = itemSnapshot.child("id").getValue(String.class);
                    existingIds.add(existingId);
                }

                // Find a new unique ID
                while (existingIds.contains(String.valueOf(idCounter))) {
                    idCounter++;
                }

                // Proceed with the insertion using the new ID
                checkExistingId(name, category, measurement, imageUri, qty);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddIngredientsActivity.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            productImageView.setImageURI(imageUri);
        }
    }
}
