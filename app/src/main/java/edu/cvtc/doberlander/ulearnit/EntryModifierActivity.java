package edu.cvtc.doberlander.ulearnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EntryModifierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_modifier);

        // Call the action bar
        ActionBar actionBar = getSupportActionBar();
        // Show the back button in the Action Bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        String modifyType = getIntent().getExtras().getString("modifyType");
        if(modifyType.equals("Edit")) {
            TranslationModel tm = getIntent().getExtras().getParcelable("SelectedItem");
            if(tm != null) {
                // fill the entry fields to edit
                EditText firstLangWord = findViewById(R.id.editText_FirstLangWord);
                EditText secondLangWord = findViewById(R.id.editText_SecondLangWord);
                firstLangWord.setText(tm.getFirstLanguageWord());
                secondLangWord.setText(tm.getSecondLanguageWord());
            }
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
}