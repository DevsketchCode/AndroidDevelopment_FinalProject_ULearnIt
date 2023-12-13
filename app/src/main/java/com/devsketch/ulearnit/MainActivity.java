package com.devsketch.ulearnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.devsketch.ulearnit.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView = null;
    public static final String EXTRA_MESSAGE = "com.devsketch.ulearnit.extra.MESSAGE";
    public static final int LAUNCH_ENTRY_MODIFIER_ACTIVITY = 1;
    private DbHelper mDbHelper;

    // Initialize default category
    private static String mCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);



        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        // Show the text below each icon
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.categories_menuItem:
                        //Intent categoryIntent = new Intent(getApplicationContext(), MainActivity.class);
                        //startActivity(categoryIntent);
                        //overridePendingTransition(0,0);
                        return false;
                    case R.id.flashcards_menuItem:
                        displayToast("Review is unavailable at this time.");
                        return false;
                    case R.id.search_menuItem:
                        displayToast("Search is unavailable at this time.");
                        return false;
                    case R.id.quiz_menuItem:
                        displayToast("Quiz is unavailable at this time.");
                        return false;
                    case R.id.profile_menuItem:
                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(profileIntent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // Retrieve Preferences
        TextView categoriesHeader = findViewById(R.id.categories_TextView);
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences prefs = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        if (prefs.getString("FirstLanguage", "") != "" && prefs.getString("SecondLanguage", "") != "") {
            categoriesHeader.setText(prefs.getString("FirstLanguage", "") + " to " +
                    prefs.getString("SecondLanguage", "") + "\nChoose Your Category");
        }


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
        Preferences preferences = new Preferences();
        preferences.setCategory(mCategory);
        DataManager.loadFromDatabase(mDbHelper,preferences);
        SharedPreferences sharedPrefs = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        populateEntryCountPerCategory(mDbHelper, sharedPrefs);
        hideEmptyCategories();
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

        Intent modifierIntent = new Intent(this, EntryModifierActivity.class);
        Bundle modifierBundle = new Bundle();
        String modifyType;

        switch (item.getItemId()) {
            case R.id.action_newEntry:
                // Bundle up the data
                modifyType = getString(R.string.action_new);
                modifierBundle.putString("category", "");
                modifierBundle.putString("message", getString(R.string.new_entry_modifier_message));
                modifierBundle.putString("modifyType", modifyType);
                // Put the bundle in an intent
                modifierIntent.putExtras(modifierBundle);
                // Start the activity and pass the bundled intent, expecting success result
                startActivityForResult(modifierIntent,LAUNCH_ENTRY_MODIFIER_ACTIVITY);
                return true;
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
            case R.id.action_ai:
                // Run the AI Web Activity
                Intent aiIntent = new Intent(this, WebAIActivity.class);
                startActivity(aiIntent);
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

    private void populateEntryCountPerCategory (DbHelper mDbHelper, SharedPreferences sharedPrefs) {
        int count = 0;

        TextView anatomyCount = findViewById(R.id.anatomyCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Anatomy", "", "", "", "");
        anatomyCount.setText(Integer.toString(count));

        TextView animalsCount = findViewById(R.id.animalsCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Animals", "", "", "", "");
        animalsCount.setText(Integer.toString(count));

        TextView beveragesCount = findViewById(R.id.beveragesCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Beverages", "", "", "", "");
        beveragesCount.setText(Integer.toString(count));

        TextView colorsCount = findViewById(R.id.colorsCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Colors", "", "", "", "");
        colorsCount.setText(Integer.toString(count));

        TextView conversationCount = findViewById(R.id.conversationCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Conversation", "", "", "", "");
        conversationCount.setText(Integer.toString(count));

        TextView countriesCount = findViewById(R.id.countriesCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Countries", "", "", "", "");
        countriesCount.setText(Integer.toString(count));

        TextView directionsCount = findViewById(R.id.directionsCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Directions", "", "", "", "");
        directionsCount.setText(Integer.toString(count));

        TextView emotionsCount = findViewById(R.id.emotionsCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Emotions", "", "", "", "");
        emotionsCount.setText(Integer.toString(count));

        TextView foodCount = findViewById(R.id.foodCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Food", "", "", "", "");
        foodCount.setText(Integer.toString(count));

        TextView generalCount = findViewById(R.id.generalCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "General", "", "", "", "");
        generalCount.setText(Integer.toString(count));

        TextView grammaticalCount = findViewById(R.id.grammaticalCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Grammatical", "", "", "", "");
        grammaticalCount.setText(Integer.toString(count));

        TextView greetingsCount = findViewById(R.id.greetingsCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Greetings", "", "", "", "");
        greetingsCount.setText(Integer.toString(count));

        TextView homeCount = findViewById(R.id.homeCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Home", "", "", "", "");
        homeCount.setText(Integer.toString(count));

        TextView medicalCount = findViewById(R.id.medicalCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Medical", "", "", "", "");
        medicalCount.setText(Integer.toString(count));

        TextView numbersCount = findViewById(R.id.numbersCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Numbers", "", "", "", "");
        numbersCount.setText(Integer.toString(count));

        TextView occupationsCount = findViewById(R.id.occupationsCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Occupations", "", "", "", "");
        occupationsCount.setText(Integer.toString(count));

        TextView peopleCount = findViewById(R.id.peopleCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "People", "", "", "", "");
        peopleCount.setText(Integer.toString(count));

        TextView placesCount = findViewById(R.id.placesCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Places", "", "", "", "");
        placesCount.setText(Integer.toString(count));

        TextView plantsCount = findViewById(R.id.plantsCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Plants", "", "", "", "");
        plantsCount.setText(Integer.toString(count));

        TextView relationshipCount = findViewById(R.id.relationshipCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Relationship", "", "", "", "");
        relationshipCount.setText(Integer.toString(count));

        TextView shoppingCount = findViewById(R.id.shoppingCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Shopping", "", "", "", "");
        shoppingCount.setText(Integer.toString(count));

        TextView theocraticCount = findViewById(R.id.theocraticCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Theocratic", "", "", "", "");
        theocraticCount.setText(Integer.toString(count));

        TextView timeCount = findViewById(R.id.timeCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Time", "", "", "", "");
        timeCount.setText(Integer.toString(count));

        TextView transportationCount = findViewById(R.id.transportationCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Transportation", "", "", "", "");
        transportationCount.setText(Integer.toString(count));

        TextView travelCount = findViewById(R.id.travelCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Travel", "", "", "", "");
        travelCount.setText(Integer.toString(count));

        TextView verbsCount = findViewById(R.id.verbsCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Verbs", "", "", "", "");
        verbsCount.setText(Integer.toString(count));

        TextView weatherCount = findViewById(R.id.weatherCount_TextView);
        count = DataManager.getEntryCountFromDatabase(mDbHelper, sharedPrefs, "category", "Weather", "", "", "", "");
        weatherCount.setText(Integer.toString(count));

    }

    private void hideEmptyCategories() {
        ArrayList<View> categoryViewList = new ArrayList<>();
        categoryViewList.add(findViewById(R.id.anatomy_CardView));
        categoryViewList.add(findViewById(R.id.animals_CardView));
        categoryViewList.add(findViewById(R.id.beverages_CardView));
        categoryViewList.add(findViewById(R.id.colors_CardView));
        categoryViewList.add(findViewById(R.id.conversation_CardView));
        categoryViewList.add(findViewById(R.id.countries_CardView));
        categoryViewList.add(findViewById(R.id.directions_CardView));
        categoryViewList.add(findViewById(R.id.emotions_CardView));
        categoryViewList.add(findViewById(R.id.food_CardView));
        categoryViewList.add(findViewById(R.id.general_CardView));
        categoryViewList.add(findViewById(R.id.grammatical_CardView));
        categoryViewList.add(findViewById(R.id.greetings_CardView));
        categoryViewList.add(findViewById(R.id.home_CardView));
        categoryViewList.add(findViewById(R.id.medical_CardView));
        categoryViewList.add(findViewById(R.id.numbers_CardView));
        categoryViewList.add(findViewById(R.id.occupations_CardView));
        categoryViewList.add(findViewById(R.id.people_CardView));
        categoryViewList.add(findViewById(R.id.places_CardView));
        categoryViewList.add(findViewById(R.id.plants_CardView));
        categoryViewList.add(findViewById(R.id.relationship_CardView));
        categoryViewList.add(findViewById(R.id.shopping_CardView));
        categoryViewList.add(findViewById(R.id.theocratic_CardView));
        categoryViewList.add(findViewById(R.id.time_CardView));
        categoryViewList.add(findViewById(R.id.transportation_CardView));
        categoryViewList.add(findViewById(R.id.travel_CardView));
        categoryViewList.add(findViewById(R.id.verbs_CardView));
        categoryViewList.add(findViewById(R.id.weather_CardView));

        ArrayList<View> categoryEntryCountList = new ArrayList<>();
        categoryEntryCountList.add(findViewById(R.id.anatomyCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.animalsCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.beveragesCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.colorsCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.conversationCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.countriesCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.directionsCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.emotionsCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.foodCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.generalCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.grammaticalCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.greetingsCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.homeCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.medicalCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.numbersCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.occupationsCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.peopleCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.placesCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.plantsCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.relationshipCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.shoppingCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.theocraticCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.timeCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.transportationCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.travelCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.verbsCount_TextView));
        categoryEntryCountList.add(findViewById(R.id.weatherCount_TextView));

        if(categoryViewList.size() == categoryEntryCountList.size()) {
            for (int i = 0; i < categoryViewList.size(); i++) {
                View layout = categoryViewList.get(i);
                TextView countTextView = (TextView) categoryEntryCountList.get(i);

                if (countTextView.getText().toString().equals("0")) {
                    layout.setVisibility(View.GONE);
                }
            }
        }


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
            case R.id.anatomy_ImageBtn:
                // Set Category
                categoryName = getString(R.string.anatomy_category_text);
                break;
            case R.id.animals_ImageBtn:
                // Set Category
                categoryName = getString(R.string.animals_category_text);
                break;
            case R.id.beverages_ImageBtn:
                // Set Category
                categoryName = getString(R.string.beverages_category_text);
                break;
            case R.id.colors_ImageBtn:
                // Set Category
                categoryName = getString(R.string.colors_category_text);
                break;
            case R.id.conversation_ImageBtn:
                // Set Category
                categoryName = getString(R.string.conversation_category_text);
                break;
            case R.id.countries_ImageBtn:
                // Set Category
                categoryName = getString(R.string.countries_category_text);
                break;
            case R.id.directions_ImageBtn:
                // Set Category
                categoryName = getString(R.string.directions_category_text);
                break;
            case R.id.emotions_ImageBtn:
                // Set Category
                categoryName = getString(R.string.emotions_category_text);
                break;
            case R.id.food_ImageBtn:
                // Set Food Category
                categoryName = getString(R.string.food_category_text);
                break;
            case R.id.general_ImageBtn:
                // Set General Category
                categoryName = getString(R.string.general_category_text);
                break;
            case R.id.grammatical_ImageBtn:
                // Set Category
                categoryName = getString(R.string.grammatical_category_text);
                break;
            case R.id.greetings_ImageBtn:
                // Set Greetings Category
                categoryName = getString(R.string.greetings_category_text);
                break;
            case R.id.home_ImageBtn:
                // Set Category
                categoryName = getString(R.string.home_category_text);
                break;
            case R.id.medical_ImageBtn:
                // Set Category
                categoryName = getString(R.string.medical_category_text);
                break;
            case R.id.numbers_ImageBtn:
                // Set Numbers Category
                categoryName = getString(R.string.numbers_category_text);
                break;
            case R.id.occupations_ImageBtn:
                // Set Category
                categoryName = getString(R.string.occupations_category_text);
                break;
            case R.id.people_ImageBtn:
                // Set People Category
                categoryName = getString(R.string.people_category_text);
                break;
            case R.id.places_ImageBtn:
                // Set Category
                categoryName = getString(R.string.places_category_text);
                break;
            case R.id.plants_ImageBtn:
                // Set Category
                categoryName = getString(R.string.plants_category_text);
                break;
            case R.id.relationship_ImageBtn:
                // Set Relationship Category
                categoryName = getString(R.string.relationship_category_text);
                break;
            case R.id.shopping_ImageBtn:
                // Set Category
                categoryName = getString(R.string.shopping_category_text);
                break;
            case R.id.theocratic_ImageBtn:
                // Set Theocratic Category
                categoryName = getString(R.string.theocratic_category_text);
                break;
            case R.id.time_ImageBtn:
                // Set Category
                categoryName = getString(R.string.time_category_text);
                break;
            case R.id.transportation_ImageBtn:
                // Set Category
                categoryName = getString(R.string.transportation_category_text);
                break;
            case R.id.travel_ImageBtn:
                // Set Category
                categoryName = getString(R.string.travel_category_text);
                break;
            case R.id.verbs_ImageBtn:
                // Set Category
                categoryName = getString(R.string.verbs_category_text);
                break;
            case R.id.weather_ImageBtn:
                // Set Category
                categoryName = getString(R.string.weather_category_text);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If came from the EntryModifierActivity then update the page
        if (requestCode == LAUNCH_ENTRY_MODIFIER_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK) {
                // Update data from the database and update the recycler
                initializeDisplayContent();
                // Display toast of successful entry
                displayToast(data.getStringExtra("Result"));
            }
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}