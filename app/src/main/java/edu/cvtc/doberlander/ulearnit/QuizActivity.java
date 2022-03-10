package edu.cvtc.doberlander.ulearnit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

//        String category = "";
//        String categoryMessage;
//
//        // Retrieve information from the intent passed from the previous activity
//        Intent intent = getIntent();
//
//        // Retrieve information from the bundle passed from the previous activity
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            // Retrieve each piece of information
//            category = bundle.getString("category");
//            categoryMessage = bundle.getString("category_message");
//
//            // Set category header TextView
//            TextView textView = findViewById(R.id.categoryHeaderText);
//            textView.setText(categoryMessage);
//        }
//
//        // Check to see if the bundle was not used to populate the category
//        if (category == null) {
//            // Display the category
//            category = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//
//            // Hide the Category Message Layout
//            LinearLayout headerLayout = findViewById(R.id.category_Heading_LinearLayout);
//            headerLayout.setVisibility(View.GONE);
//        }
//
//        // Set the Activity Title with the Category that was selected
//        setTitle("Category: " + category);
//
//        // Get the translation List
//        TranslationList translationListObject = new TranslationList();
//        LinkedList<TranslationModel> translations = translationListObject.GetTranslations(category);
//
//        displayToast(translations.getFirst().toString());
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}