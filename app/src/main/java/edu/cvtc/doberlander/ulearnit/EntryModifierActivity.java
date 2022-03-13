package edu.cvtc.doberlander.ulearnit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class EntryModifierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_modifier);

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

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}