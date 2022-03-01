package edu.cvtc.doberlander.ulearnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    // Create list used to store the list of data
    //private final LinkedList<TranslationModel> mTranslationList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private TranslationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String category = "";
        String categoryMessage = "";

        // Retrieve information from the intent passed from the previous activity
        Intent intent = getIntent();

        // Retrieve information from the bundle passed from the previous activity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            // Retrieve each piece of information
            category = bundle.getString("category");
            categoryMessage = bundle.getString("category_message");

            // Set category header TextView
            TextView textView = findViewById(R.id.categoryHeaderText);
            textView.setText(categoryMessage);
        }

        // Check to see if the bundle was not used to populate the category
        if(category == null) {
            // Display the category
            category = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

            // Hide the Category Message Layout
            LinearLayout headerLayout = findViewById(R.id.category_Heading_LinearLayout);
            headerLayout.setVisibility(View.GONE);
        }

        // Set the Activity Title with the Category that was selected
        setTitle("Category: " + category);

        // Get the translation List
        TranslationList translationListObject = new TranslationList();
        LinkedList<TranslationModel> translations = translationListObject.GetTranslations(category);

        // Create the RecyclerView and Connect the Adapter and Data
        mRecyclerView = findViewById(R.id.categoryRecyclerView);

        // Create the adapter and attach the data
        mAdapter = new TranslationAdapter(this,translations);

        // Connect the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // Give the RecyclerView a default Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}