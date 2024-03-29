package com.devsketch.ulearnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class CategoryActivity extends AppCompatActivity implements RecyclerViewInterface{

    public static final String EXTRA_MESSAGE = "com.devsketch.ulearnit.extra.MESSAGE";

    // Variable to access translations from the database
    private DbHelper mDbHelper;
    private RecyclerView mRecyclerItems;
    private LinearLayoutManager mTranslationsLayoutManager;
    private TranslationAdapter mTranslationsAdapter;
    private String mCategory;

    // Public variables
    public static final int LAUNCH_ENTRY_MODIFIER_ACTIVITY = 1;
    public static Menu mModifyMenu;
    public static TranslationModel mSelectedItem = null;
    public static int mSelectedItemID = 0;
    public static int mSelectedItemPosition = 0;
    public ActionBar categoryActionBar = null;

    // Member variable for translations
    public static List<TranslationModel> mTranslations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view
        setContentView(R.layout.activity_category);

        // Hide the back button on the ActionBar
        categoryActionBar = getSupportActionBar();
        if (categoryActionBar != null) {
            categoryActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.flashcards_menuItem);
        // Show the text below each icon
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.categories_menuItem:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
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
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // Instantiate variables
        mCategory = "";
        String categoryMessage;

        // Create DbHelper to get access to the database
        mDbHelper = new DbHelper(this);

        // Retrieve information from the intent passed from the previous activity
        Intent intent = getIntent();

        // Retrieve information from the bundle passed from the previous activity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Retrieve each piece of information
            mCategory = bundle.getString("category");
            categoryMessage = bundle.getString("category_message");

            // Retrieve Preferences
            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences prefs = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
            if (prefs.getString("FirstLanguage", "") != "" && prefs.getString("SecondLanguage", "") != "") {
                categoryMessage = prefs.getString("FirstLanguage", "") + " to " +
                        prefs.getString("SecondLanguage", "") + "\n" + categoryMessage;
            }

            // Set category header TextView
            TextView textView = findViewById(R.id.categoryHeaderText);
            textView.setText(categoryMessage);
        }

        // Check to see if the bundle was not used to populate the category
        if (mCategory == null) {
            // Display the category
            mCategory = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

            // Hide the Category Message Layout
            LinearLayout headerLayout = findViewById(R.id.category_Heading_LinearLayout);
            headerLayout.setVisibility(View.GONE);
        }

        // Set the Activity Title with the Category that was selected
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        setTitle(mCategory);

        // Create an extended floating action button, an icon with text
        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the Quiz activity with previously passed intent data
                Intent quizIntent = new Intent(CategoryActivity.this, QuizActivity.class);
                quizIntent.putExtras(bundle);
                startActivity(quizIntent);
            }
        });

        initializeDisplayContent();
    }

    // Prepare the options menu to be accessed from the Adapter
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mModifyMenu = menu;
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        // Set the edit item so that it can be visible when an item is highlighted.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Initialize Intent
        Intent categoryIntent = new Intent(this, CategoryActivity.class);
        Intent modifierIntent = new Intent(this, EntryModifierActivity.class);
        Bundle modifierBundle = new Bundle();
        String modifyType;
        String category;

        // Determine which Intent combination to pass and activity to launch
        switch (item.getItemId()) {
            case R.id.action_newEntry:
                // Bundle up the data
                modifyType = getString(R.string.action_new);
                modifierBundle.putString("category", mCategory);
                modifierBundle.putString("message", getString(R.string.new_entry_modifier_message));
                modifierBundle.putString("modifyType", modifyType);
                // Put the bundle in an intent
                modifierIntent.putExtras(modifierBundle);
                // Start the activity and pass the bundled intent, expecting success result
                startActivityForResult(modifierIntent,LAUNCH_ENTRY_MODIFIER_ACTIVITY);
                return true;
            case R.id.action_editEntry:
                // Bundle up the data
                modifyType = getString(R.string.action_edit);
                modifierBundle.putInt("selectedId", mSelectedItemID);
                modifierBundle.putString("category", mCategory);
                modifierBundle.putString("message", getString(R.string.edit_entry_modifier_message));
                modifierBundle.putString("modifyType", modifyType);
                modifierBundle.putParcelable("SelectedItem", mSelectedItem);
                // Put the bundle in an intent
                modifierIntent.putExtras(modifierBundle);
                // Start the activity and pass the bundled intent, expecting success result
                startActivityForResult(modifierIntent,LAUNCH_ENTRY_MODIFIER_ACTIVITY);
                return true;
            case R.id.action_favorites:
                // Favorites are separate so the category is set manually here
                category = getString(R.string.action_favorites);
                categoryIntent.putExtra(EXTRA_MESSAGE, category);
                startActivity(categoryIntent);
                return true;
            default:
                // Do Nothing
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeDisplayContent() {

        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences prefs = this.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        Preferences preferences = new Preferences();
        preferences.setFirstLanguage(prefs.getString("FirstLanguage", ""));
        preferences.setSecondLanguage(prefs.getString("SecondLanguage", ""));
        preferences.setCategory(mCategory);

        // Retrieve the information from the database
        DataManager.loadFromDatabase(mDbHelper, preferences);

        // Set a reference to the translations of the items layout
        mRecyclerItems = findViewById(R.id.categoryRecyclerView);
        mTranslationsLayoutManager = new LinearLayoutManager(this);

        // Get the translations and save it to the global variable
        mTranslations = DataManager.getInstance().getTranslations();

        // Fill the RecyclerAdapter with the translations
        mTranslationsAdapter = new TranslationAdapter(this, this, mTranslations, this);

        // Display the translations
        displayTranslations();
    }

    private void displayTranslations() {
        // Set the LayoutManager and Adapter for the RecyclerView
        mRecyclerItems.setLayoutManager((mTranslationsLayoutManager));
        mRecyclerItems.setAdapter(mTranslationsAdapter);

        // Add extra space at bottom of list to allow higher scrolling
        int bottomMargin = 150; // change this value as needed
        BottomMarginDecoration bottomMarginDecoration = new BottomMarginDecoration(bottomMargin);
        mRecyclerItems.addItemDecoration(bottomMarginDecoration);
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

    @Override
    public void onItemClick(int position) {
        //mSelectedItem = mTranslations.get(position);
        mSelectedItemPosition = position;
    }

    @Override
    public void onItemDoubleTap(int position) {
        // Pass the selected item to the CategoryActivity, so it can be used to pass to the
        // Modifier Activity
        CategoryActivity.mSelectedItem = mTranslations.get(position);
        // Toast.makeText(this, "DoubleTapped", Toast.LENGTH_SHORT).show();

        // Open the WebActivity Intent
        Intent webActivityIntent = new Intent(this, WebTranslateActivity.class);
        Bundle webActivityBundle = new Bundle();
        // Put the parcelable selected item object in the bundle
        webActivityBundle.putParcelable("SelectedItem", mSelectedItem);
        // Put the bundle in the Intent
        webActivityIntent.putExtras(webActivityBundle);
        // Pass the selected item
        startActivity(webActivityIntent);
    }

    @Override
    public void onItemLongTap(int position) {
        // On Long Tap of an Entry Item, display the languages of that translation entry
        if (mSelectedItem != null) {
            displayToast(mSelectedItem.getFirstLanguage() + " to " + mSelectedItem.getSecondLanguage() + ".\nEntry Type: " + mSelectedItem.getEntryType() +
                    "\n Last Modified: " + mSelectedItem.getModifiedDate());
        }
        // Doubletap isn't working yet, so using it her in LongTap
        CategoryActivity.mSelectedItem = mTranslations.get(position);
        // Toast.makeText(this, "DoubleTapped", Toast.LENGTH_SHORT).show();

        // Open the WebActivity Intent
        Intent webActivityIntent = new Intent(this, WebTranslateActivity.class);
        Bundle webActivityBundle = new Bundle();
        // Put the parcelable selected item object in the bundle
        webActivityBundle.putParcelable("SelectedItem", mSelectedItem);
        // Put the bundle in the Intent
        webActivityIntent.putExtras(webActivityBundle);
        // Pass the selected item
        startActivity(webActivityIntent);
    }
}