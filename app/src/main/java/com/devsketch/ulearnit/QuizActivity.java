package com.devsketch.ulearnit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    // Constants
    private final static int MAX_QUIZ_SIZE = 10;

    // Keep track of items already used in a question
    private final ArrayList<Integer> mQuestionsAsked = new ArrayList<>();
    // Keep track of index
    private int mIndex = 0;
    // Instantiate the quiz size with the lowest possible size
    private int mQuizSize = 4;

    private String dbResult = "";

    // Member variables for populating the quiz on each click
    List<TranslationModel> mQuizTranslations;

    TranslationModel tmQuestionEntry;
    TextView mQuiz_Question;
    ArrayList<Button> mOptions;
    Button mCorrectAnswerOptionButton;
    ArrayList<String> mQuizAnswersPopulated;

    // Store correct answer
    private String mCorrectAnswer;
    private Boolean mIsCorrect = false;

    // Store the quizResults
    private int mCorrectAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Call the action bar
        ActionBar actionBar = getSupportActionBar();
        // Show the back button in the Action Bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
            TextView quizHeaderText = findViewById(R.id.quizHeaderText);
            quizHeaderText.setText(categoryMessage);
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

        // Get the translation List from the database via Data Manager
        mQuizTranslations = CategoryActivity.mTranslations;

        // Only populate the quiz if there are enough items in the list for a quiz
        if(mQuizTranslations.size() > 3) {

            // Randomize Translations List
            Collections.shuffle(mQuizTranslations);

            // Get Quiz Views
            mQuiz_Question = findViewById(R.id.quiz_QuestionText);
            Button quiz_Option1 = findViewById(R.id.btn_AnswerOption1);
            Button quiz_Option2 = findViewById(R.id.btn_AnswerOption2);
            Button quiz_Option3 = findViewById(R.id.btn_AnswerOption3);
            Button quiz_Option4 = findViewById(R.id.btn_AnswerOption4);

            // Add the options to an Array so they can be looped through for random entries
            mOptions = new ArrayList<>();
            mOptions.add(quiz_Option1);
            mOptions.add(quiz_Option2);
            mOptions.add(quiz_Option3);
            mOptions.add(quiz_Option4);

            mQuizAnswersPopulated = new ArrayList<>();

            // Set buttons to reset color for consistency
            resetButtonFormatting();

            // Populate the quiz
            populateQuiz();

        } else {
            displayToast("You must have at least 4 items in the list for a quiz.");
            finish();
        }
    }

    // Event that is enabled when pressing the back button in the ActionBar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Sort the translations alphabetically before returning
        mQuizTranslations.sort(Comparator.comparing(TranslationModel::getFirstLanguageEntry));
        // Return back to the previous activity
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void populateQuiz() {
        // Get random item from array for the Quiz Question
        // Keep randomizing the index until there is one that is not already in the list
        do {
            // Randomize the index to determine the next quiz question
            mIndex = (int) (Math.random() * mQuizTranslations.size());
        } while (mQuestionsAsked.contains(mIndex));

        // Add Index to Keep track of questions already asked in the quiz
        mQuestionsAsked.add(mIndex);

        // Populate the quiz question number text
        TextView textView_QuestionNumbers = findViewById(R.id.textView_QuestionNumbers);

        // Determine the size limits of the quiz depending on the translation list size
        if(mQuizTranslations.size() > MAX_QUIZ_SIZE) {
            // Limit the quiz to the Max Size
            mQuizSize = MAX_QUIZ_SIZE;
        } else if (mQuizTranslations.size() > 3){
            // Use all the entries in the quiz, since it is less than 10
            // This must be greater than 4
            mQuizSize = mQuizTranslations.size();
        } else {
            // If it gets this far, quizzes are not allowed with items less than 4
            displayToast("The category must have at least 4 items in it for a quiz");
            return;
        }
        // Display what question you are on of how many questions there are total
        String questionNumberMessage = mQuestionsAsked.size() + " of " + mQuizSize;
        textView_QuestionNumbers.setText(questionNumberMessage);

        // Populate the quiz Question
        mQuiz_Question.setText(mQuizTranslations.get(mIndex).getFirstLanguageEntry());

        // Prepare the Quiz Question Entry to have it's learned percentage updated depending on the users response
        tmQuestionEntry = mQuizTranslations.get(mIndex);

        // Get the correct answer
        mCorrectAnswer = mQuizTranslations.get(mIndex).getSecondLanguageEntry();

        // Determine which Option will include the correct answer.
        Random random = new Random();
        int randomOptionForCorrectAnswer = random.nextInt(4);

        // Get the option button that matches the randomNumber that generated.
        mCorrectAnswerOptionButton = mOptions.get(randomOptionForCorrectAnswer);
        mCorrectAnswerOptionButton.setText(mCorrectAnswer);
        // Add correct answer to the populated answers list
        mQuizAnswersPopulated.add(mCorrectAnswer);

        // Variable to adjust the entry if there is a duplicate correct answer in the list
        int entry = 0;
        // Fill in the rest of the options
        for (int i = 0; i < mOptions.size(); i++) {
            if (i != randomOptionForCorrectAnswer) {
                // Increment to next in list if the option is already the correct answer
                if (mQuizTranslations.get(entry).getSecondLanguageEntry().equals(mCorrectAnswer)) {
                    // If next entry is (4), then reset to 0 (if entry > 3, then reset to 0)
                    entry++;
                    if(entry == mOptions.size()) {
                        entry = 0;
                    }
                }
                // Populate the button text
                mOptions.get(i).setText(mQuizTranslations.get(entry).getSecondLanguageEntry());
                // If next entry is (4), then reset to 0 (if entry > 3, then reset to 0)
                entry++;
                if(entry == mOptions.size()) {
                    entry = 0;
                }
            }
        }
    }

    public void checkAnswer(View view) {
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        int tmpPercentLearned = 0;
        int tmpPercentLearnedIncrement = 5;
        int tmpFailedAttempts = 0;

        formatButtonsOnGuess();
        // Get ImageView to show an X or Checkmark depending on if the user got the answer right or wrong
        ImageView answerResponseImageView = findViewById(R.id.question_ResponseImageView);

        // Determine if the answer was correct and score accordingly
        if(buttonText.equals(mCorrectAnswer)) {
            // ANSWER IS CORRECT
            // Increment the correct answers variable
            mCorrectAnswers++;
            mIsCorrect = true;

            //TODO: SETUP Percentage Learned for Entry and need to set the Percenage Learned Modified Date too, and update the database here.
            // Prepare the db to update if the learning percentage changes
            Boolean dbChangesMade = false;
            Boolean percentageChanged = false;
            DbHelper dbHelper = new DbHelper(QuizActivity.this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            DbWorker dbWorker = new DbWorker(db);

            // Increase Learned Percentage;
            tmpPercentLearned = tmQuestionEntry.getPercentLearned();
            if(tmQuestionEntry != null) {
                if(tmpPercentLearned <= 90) {
                    tmQuestionEntry.setPercentLearned(tmpPercentLearned + tmpPercentLearnedIncrement);
                    tmQuestionEntry.setPercentLearnedModifiedDate("");
                    percentageChanged = true;
                } else if (tmpPercentLearned <= 100 && tmpPercentLearned > 90) {
                    // The word has been learned very well.  Give it 10 more times before it has been learned completely.
                    // TODO: During this time, eventually make it so this word does not show up as often as the others.
                    tmpPercentLearnedIncrement = 1;
                    tmQuestionEntry.setPercentLearned(tmpPercentLearned + tmpPercentLearnedIncrement);
                    tmQuestionEntry.setPercentLearnedModifiedDate("");
                    percentageChanged = true;
                }

                // TODO: Push this out to its own function
                if (percentageChanged) {
                    // Update the database with the new learned percentage
                    try {
                        // Run the Save to DB function in the Data Manager
                        dbWorker.UpdateOrDeleteEntries(tmQuestionEntry.getId(), tmQuestionEntry,
                                QuizActivity.this, "update");
                        dbResult = "Successfully Updated";
                        dbChangesMade = true;
                    } catch (Exception ex) {
                        // Display error for the user to see
                        dbResult = "Failed Update";
                        // Print Error to Console
                        System.out.println("Error updating record: " + ex.getMessage());
                    }
                }
            } else {
                dbResult = "Failed: No data passed";
            }

            if(dbChangesMade)
            {
                // Close the database
                db.close();

                // Create intent to display result
                // TODO: Intent is likely not needed here.
                //Intent intent = new Intent();
                //intent.putExtra("Result", dbResult);
                // Set the result so the the Activity can be updated
                //setResult(QuizActivity.RESULT_OK, intent);
                Toast.makeText(this, "You got it! Progress Updated", Toast.LENGTH_SHORT).show();
                // Close the activity and go back
                // We do not want to close the activity, because they should be able to continue the quiz
                // finish();
            }


            // Display an Check Mark over the question
            answerResponseImageView.setImageResource(R.drawable.ic_correct);
            // Change the color of the image
            answerResponseImageView.setColorFilter(ContextCompat.getColor(this, R.color.correct));
            // Display the response image
            answerResponseImageView.setVisibility(View.VISIBLE);

        } else {
            // ANSWER IS INCORRECT
            mIsCorrect = false;

            //TODO: MOVE this and the one in correct answer to its own function
            Boolean dbChangesMade = false;
            Boolean entryChanged = false;
            DbHelper dbHelper = new DbHelper(QuizActivity.this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            DbWorker dbWorker = new DbWorker(db);

            // Increase Learned Percentage;
            tmpFailedAttempts = tmQuestionEntry.getFailedAttempts();
            if(tmQuestionEntry != null) {
                if(tmpPercentLearned != 100) {
                    tmQuestionEntry.setFailedAttempts(tmpFailedAttempts + 1);
                    entryChanged = true;
                }

                // TODO: Push this out to its own function
                if (entryChanged) {
                    // Update the database with the new learned percentage
                    try {
                        // Run the Save to DB function in the Data Manager
                        dbWorker.UpdateOrDeleteEntries(tmQuestionEntry.getId(), tmQuestionEntry,
                                QuizActivity.this, "update");
                        dbResult = "Successfully Updated";
                        dbChangesMade = true;
                    } catch (Exception ex) {
                        // Display error for the user to see
                        dbResult = "Failed Update";
                        // Print Error to Console
                        System.out.println("Error updating record: " + ex.getMessage());
                    }
                }
            } else {
                dbResult = "Failed: No data passed";
            }

            if(dbChangesMade)
            {
                // Close the database
                db.close();
            }

            // Display an X over the question
            answerResponseImageView.setImageResource(R.drawable.ic_incorrect);
            // Change the color of the image
            answerResponseImageView.setColorFilter(ContextCompat.getColor(this, R.color.incorrect));
            // Display the response image
            answerResponseImageView.setVisibility(View.VISIBLE);

            // Make the button shake if the answer was wrong
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);

            // Reset the animation to prepare to run again if needed
            animation.reset();
            b.clearAnimation();
            b.startAnimation(animation);
        }

        // Create a handler to delay the next actions before populating the next question
        final Handler handler = new Handler();

        // TODO: Allow Custom Preferences to adjust delay values here, or have a Continue button option
        int delayTime = 2500;
        if (!mIsCorrect) {
            delayTime = 5000;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resetButtonFormatting();

                // Only populate if the Quiz is within the designated size parameters
                if (mQuestionsAsked.size() < mQuizSize) {
                    // Increment to next question
                    mIndex++;
                    // Populate the next question
                    populateQuiz();
                } else {
                    // Sort the translations list back to it's alphabetical order
                    // This is used to display the list appropriately when going back to the Category Activity,
                    // After the ResultsActivity
                    mQuizTranslations.sort(Comparator.comparing(TranslationModel::getFirstLanguageEntry));

                    // Set up the intent to be sent to the ResultsActivity
                    Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("correctAnswers", mCorrectAnswers);
                    bundle.putInt("totalQuestions", mQuestionsAsked.size());
                    intent.putExtras(bundle);
                    // Start the results Activity with the bundled intent
                    startActivity(intent);
                    // close quiz activity so the results page goes back to the categories instead of this activity
                    finish();
                }
            } // Delay in milliseconds
        }, delayTime);
    }

    private void formatButtonsOnGuess() {
        // Iterate through the option buttons
        for (Button button : mOptions) {
            if(button != mCorrectAnswerOptionButton) {
                // if the option button was incorrect, turn it red
                button.setBackgroundColor(Color.RED);
            } else {
                // if the option button was correct, turn it green
                mCorrectAnswerOptionButton.setBackgroundColor(Color.GREEN);
            }
            // disable the buttons until they are reset on the next questions
            button.setEnabled(false);
        }
    }

    private void resetButtonFormatting() {
        // Iterate through the option buttons and reset the color
        for (Button button : mOptions) {
            button.setBackgroundColor(Color.parseColor("#FF6200EE"));
            // re-enable the buttons
            button.setEnabled(true);
        }

        // Get ImageView to show an X or Checkmark depending on if the user got the answer right or wrong
        ImageView answerResponseImageView = findViewById(R.id.question_ResponseImageView);
        // hide the imageView
        answerResponseImageView.setVisibility(View.GONE);
    }

}