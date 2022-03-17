package edu.cvtc.doberlander.ulearnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ResultsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Call the action bar
        ActionBar actionBar = getSupportActionBar();
        // Show the back button in the Action Bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set the title of the page
        setTitle("Your Results");

        // Declare variables
        int correctAnswers = 0;
        int totalQuestions = 0;
        double percentGrade = 0.0;

        // Retrieve information from teh bundle
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            correctAnswers = bundle.getInt("correctAnswers");
            totalQuestions = bundle.getInt("totalQuestions");
            percentGrade = (((double) correctAnswers / (double) totalQuestions) * 100);

            // format the grade to have a max of 2 decimals
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(2);
            percentGrade = Double.parseDouble(decimalFormat.format(percentGrade));
        } else {
            System.out.println("Bundle came back null");
        }

        // Display the header for a Quiz
        TextView resultsCompleteTextView = findViewById(R.id.results_CompleteText);
        resultsCompleteTextView.setText(R.string.quiz_completed_message);

        // Get the Results Grade textview and place the results percentage
        TextView resultsTextView = findViewById(R.id.results_Grade);
        String resultsPercentage = percentGrade + "%";
        resultsTextView.setText(resultsPercentage);

        // Get the Ratio Grade textview and place the ratio of correct answers
        TextView resultsGradeRatioTextView = findViewById(R.id.results_GradeRatio);
        String resultsRatioText = correctAnswers + " out of " + totalQuestions + " Correct";
        resultsGradeRatioTextView.setText(resultsRatioText);

        // Get the results Message textview and display the appropriate message
        TextView resultsMessageTextView = findViewById(R.id.results_Message);
        String resultsMessage;

        if(percentGrade > 90) {
            resultsMessage = getString(R.string.resultMessage_Great);
        } else if (percentGrade > 70) {
            resultsMessage = getString(R.string.resultMessage_Good);
        } else if (percentGrade > 50) {
            resultsMessage = getString(R.string.resultMessage_Okay);
        } else {
            resultsMessage = getString(R.string.resultMessage_Bad);
        }

        // Display the results message
        resultsMessageTextView.setText(resultsMessage);


    }

    // Event that is enabled when pressing the back button in the ActionBar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}