package edu.cvtc.doberlander.ulearnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    // Keep track of items already used in a question
    private ArrayList<Integer> questionsAsked = new ArrayList<>();

    // Store correct answer
    private String correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Call the action bar
        ActionBar actionBar = getSupportActionBar();
        // Show the back button in the Action Bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        String category = "";
        String categoryMessage;

        // Retrieve information from the intent passed from the previous activity
        Intent intent = getIntent();

        // Retrieve information from the bundle passed from the previous activity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Retrieve each piece of information
            category = bundle.getString("category");
            categoryMessage = bundle.getString("category_message");

            // Set category header TextView
            TextView textView = findViewById(R.id.quizHeaderText);
            textView.setText(categoryMessage);
        }

        // Check to see if the bundle was not used to populate the category
        if (category == null) {
            // Display the category
            category = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

            // Hide the Category Message Layout
            LinearLayout headerLayout = findViewById(R.id.quiz_Heading_LinearLayout);
            headerLayout.setVisibility(View.GONE);
        }

        // Set the Activity Title with the Category that was selected
        setTitle("Category: " + category);

        // Get the translation List
        TranslationList translationListObject = new TranslationList();

        // Convert the LinkedList to an ArrayList to allow for randomization
        ArrayList<TranslationModel> translations = new ArrayList<>(translationListObject.GetTranslations(category));

        // Only populate the quiz if there are enough items in the list for a quiz
        if(translations.size() > 3) {

            // Randomize Translations List
            Collections.shuffle(translations);

            // Get random item from array
            int index = (int) (Math.random() * translations.size());

            // Add Index to Keep track of questions already asked in the quiz
            questionsAsked.add(index);

            // Get Quiz Views
            TextView quiz_Question = findViewById(R.id.quiz_QuestionText);
            Button quiz_Option1 = findViewById(R.id.btn_AnswerOption1);
            Button quiz_Option2 = findViewById(R.id.btn_AnswerOption2);
            Button quiz_Option3 = findViewById(R.id.btn_AnswerOption3);
            Button quiz_Option4 = findViewById(R.id.btn_AnswerOption4);

            // Add the options to an Array so they can be looped through for random entries
            ArrayList<Button> options = new ArrayList<>();
            options.add(quiz_Option1);
            options.add(quiz_Option2);
            options.add(quiz_Option3);
            options.add(quiz_Option4);

            ArrayList<String> quizAnswersPopulated = new ArrayList<>();


            // Populate the quiz Question
            quiz_Question.setText(translations.get(index).getFirstLanguageWord());

            // Get the correct answer
            correctAnswer = translations.get(index).getSecondLanguageWord();

            // Determine which Option will include the correct answer.
            Random random = new Random();
            int randomOptionForCorrectAnswer = random.nextInt(3);

            options.get(randomOptionForCorrectAnswer).setText(correctAnswer);
            // Add correct answer to the populated answers list
            quizAnswersPopulated.add(correctAnswer);

            String test = "";
            displayToast(correctAnswer);

            // Variable to adjust the entry if there is a duplicate correct answer in the list
            int entry = 0;
            // Fill in the rest of the options
            for (int i = 0; i < options.size(); i++) {
                if (i != randomOptionForCorrectAnswer) {

                    // Increment to next in list if the option is already the correct answer
                    if (translations.get(entry).getSecondLanguageWord() == correctAnswer) {
                        entry++;
                    }
                    // Populate the button text
                    options.get(i).setText(translations.get(entry).getSecondLanguageWord());
                    entry++;
                }
            }
        } else {
            displayToast("You must have at least 4 items in the list for a quiz.");
            finish();
        }
    }

    // Event that is enabled when pressing the back button in the ActionBar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void checkAnswer(View view) {
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        if(buttonText.equals(correctAnswer)) {
            displayToast("Great Job");
        } else {
            displayToast("Sorry, Wrong Answer!");

            // Make the button shake if the answer was wrong
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);

            // Reset the animation to prepare to run again if needed
            animation.reset();
            b.clearAnimation();
            b.startAnimation(animation);
        }
    }
}