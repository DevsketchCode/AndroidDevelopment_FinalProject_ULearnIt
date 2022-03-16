package edu.cvtc.doberlander.ulearnit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.cvtc.doberlander.ulearnit.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "edu.cvtc.doberlander.ulearnit.extra.MESSAGE";
    private DbHelper mDbHelper;
    // Initialize default category
    private static String mCategory = "";
    private static String mFirstLanguage;
    private static String mSecondLanguage;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        // Establish the database
        mDbHelper = new DbHelper(this);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToast("Tests are not available at this time");
            }
        });

        // Initialize variables
        mFirstLanguage = getString(R.string.first_language_text);
        mSecondLanguage = getString(R.string.second_language_text);

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        // Retrieve information from the database
        DataManager.loadFromDatabase(mDbHelper,mCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_favorites:
                Intent intent = new Intent(this, CategoryActivity.class);
                String category = getString(R.string.action_favorites);
                intent.putExtra(EXTRA_MESSAGE, category);
                startActivity(intent);
                return true;
            case R.id.action_contact:
                displayToast(getString(R.string.action_contact_message));
                return true;
            default:
                // Do Nothing
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Launch the Category Activity along with the Greetings text
     */
    public void launchCategoryActivity(View view) {

        // Initialize Category Variables
        String categoryName;
        String categoryMessage = mFirstLanguage + " - " + mSecondLanguage;

        // Determine which Category button was clicked
        switch (view.getId()) {
            case R.id.greetings_ImageBtn:
                // Set Greetings Category
                categoryName = getString(R.string.greetings_category_text);
                categoryMessage = categoryMessage;
                break;
            case R.id.numbers_ImageBtn:
                // Set Numbers Category
                categoryName = getString(R.string.numbers_category_text);
                categoryMessage = categoryMessage;
                break;
            case R.id.action_favorites:
                // Set the Favorites Category
                categoryName = getString(R.string.favorites_category_text);
                categoryMessage = "";
                break;
            default:
                // Set Default Value
                categoryName = getString(R.string.default_category_name);
                categoryMessage = getString(R.string.default_category_text);
        }

        mCategory = categoryName;

        // Bundle the information depending what button was clicked.
        Bundle bundle = new Bundle();
        bundle.putString("category", categoryName);
        bundle.putString("category_message", categoryMessage);

        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}