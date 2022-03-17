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
                displayToast(getString(R.string.test_not_available));
            }
        });

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
                // Run the CategoryActivity, passing the Favorites Category
                Intent favoritesIntent = new Intent(this, CategoryActivity.class);
                String category = getString(R.string.action_favorites);
                favoritesIntent.putExtra(EXTRA_MESSAGE, category);
                startActivity(favoritesIntent);
                return true;
            case R.id.action_translate:
                // Run the AboutActivity
                Intent translateIntent = new Intent(this, WebTranslateActivity.class);
                startActivity(translateIntent);
                return true;
            case R.id.action_help:
                // Run the AboutActivity
                Intent helpIntent = new Intent(this, HelpActivity.class);
                startActivity(helpIntent);
                return true;
            case R.id.action_about:
                // Run the AboutActivity
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
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
        String categoryMessage = getString(R.string.category_message_learn);

        // Determine which Category button was clicked
        switch (view.getId()) {
            case R.id.greetings_ImageBtn:
                // Set Greetings Category
                categoryName = getString(R.string.greetings_category_text);
                break;
            case R.id.numbers_ImageBtn:
                // Set Numbers Category
                categoryName = getString(R.string.numbers_category_text);
                break;
            case R.id.people_ImageBtn:
                // Set Numbers Category
                categoryName = getString(R.string.people_category_text);
                break;
            case R.id.food_ImageBtn:
                // Set Numbers Category
                categoryName = getString(R.string.food_category_text);
                break;
            case R.id.action_favorites:
                // Set the Favorites Category
                categoryName = getString(R.string.favorites_category_text);
                // No category message needed for Favorites
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