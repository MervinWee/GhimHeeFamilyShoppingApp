package com.example.ghimheefamilyshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private String[] products = {"Bread", "Milk", "Eggs", "Butter", "Cheese", "Yogurt", "Orange Juice", "Apple Juice", "Soda", "Water"};
    private int[] images = {R.drawable.chickenbreastbone, R.drawable.appleredfujichinapacketof5, R.drawable.chickenbreastbone, R.drawable.anchovysambal, R.drawable.beansprout, R.drawable.arrowroot, R.drawable.australiabroccoli, R.drawable.beancurdsik1pc, R.drawable.babychyesim, R.drawable.babykailan};
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setting up the search bar
        // Setting up the horizontal scroll view
        // Find the search view by ID
        // Find the search view by ID
        SearchView searchView = findViewById(R.id.search_view);



        // Find the linear layout and horizontal scroll view
        // Find the linear layout and horizontal scroll view
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_layout);
        LinearLayout horizontalLayout = horizontalScrollView.findViewById(R.id.horizontal_layout_container);

        for (int i = 0; i < 3; i++) {
            // Inflate the card view layout
            View cardViewLayout = getLayoutInflater().inflate(R.layout.card_view_layout, null);

            // Find the image view and set the image resource
            ImageView imageView = cardViewLayout.findViewById(R.id.product_image);
            imageView.setImageResource(R.drawable.babyshanghaigreen);

            // Find the text view and set the text
            TextView textView = cardViewLayout.findViewById(R.id.product_name);
            textView.setText("Product " + (i + 1));

            // Add the card view to the linear layout
            horizontalLayout.addView(cardViewLayout);
        }

        // Add the linear layout to the horizontal scroll view

        // Find the vertical layout and add card views to it
        ScrollView verticalLayout = findViewById(R.id.vertical_layout);
        for (int i = 0; i < 3; i++) {
            // Inflate the card view layout
            View cardViewLayout = getLayoutInflater().inflate(R.layout.card_view_layout, null);

            // Find the image view and set the image resource
            ImageView imageView = cardViewLayout.findViewById(R.id.product_image);
            imageView.setImageResource(Product.getImage());

            // Find the text view and set the text
            TextView textView = cardViewLayout.findViewById(R.id.product_name);
            textView.setText("Product " + (i + 1));

            // Add the card view to the vertical layout
            verticalLayout.addView(cardViewLayout);
        }

        // Initialize Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Save products and images to the database
        for (int i = 0; i < products.length; i++) {
            Product product = new Product(products[i], images[i]);
            mDatabase.child("products").push().setValue(product);
        }

        // Retrieve products from the database
        mDatabase.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    // Create a new card view
                    View cardViewLayout = getLayoutInflater().inflate(R.layout.card_view_layout, null);
                    // Find the image view and set the image resource
                    ImageView imageView = cardViewLayout.findViewById(R.id.product_image);
                    int imageResourceId = product.getImage(); // get the image resource ID from the Product instance
                    imageView.setImageResource(imageResourceId);
                    // Find the text view and set the text
                    TextView textView = cardViewLayout.findViewById(R.id.product_name);
                    textView.setText((CharSequence) product.getTvName());
                    // Add the card view to the vertical layout
                    verticalLayout.addView(cardViewLayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    };
        }