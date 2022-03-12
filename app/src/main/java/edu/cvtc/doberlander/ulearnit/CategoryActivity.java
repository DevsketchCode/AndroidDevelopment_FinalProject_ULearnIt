package edu.cvtc.doberlander.ulearnit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    // Variable to access translations from the database
    private DbHelper mDbHelper;
    private RecyclerView mRecyclerItems;
    private LinearLayoutManager mTranslationsLayoutManager;
    private TranslationAdapter mTranslationsAdapter;
    public static final int ITEM_COURSES = 0;
    private String mCategory;

    // Member variable for translations
    public List<TranslationModel> mTranslations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

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
        setTitle("Category: " + mCategory);

        // Get the translation List
        //TranslationList translationListObject = new TranslationList();
        //LinkedList<TranslationModel> translations = translationListObject.GetTranslations(category);

        // Get the list from the database
        DataManager dm = new DataManager();
        List<TranslationModel> translations = dm.getTranslations();
        initializeDisplayContent();
        displayToast(String.valueOf(mTranslations.size()));
        // Only attach data if there is data
        if (translations.size() > 0) {
            // Create the RecyclerView and Connect the Adapter and Data
            // Create list used to store the list of data
            RecyclerView mRecyclerView = findViewById(R.id.categoryRecyclerView);

            // Create the adapter and attach the data
            TranslationAdapter mAdapter = new TranslationAdapter(this, translations);

            // Connect the adapter to the RecyclerView
            mRecyclerView.setAdapter(mAdapter);

            // Give the RecyclerView a default Layout Manager
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizIntent = new Intent(CategoryActivity.this, QuizActivity.class);
                quizIntent.putExtras(bundle);
                startActivity(quizIntent);
            }
        });
    }

    private void initializeDisplayContent() {
        // Retrieve the information from the database
        DataManager.loadFromDatabase(mDbHelper, mCategory);

        // Set a reference to the translations of the items layout
        mRecyclerItems = (RecyclerView) findViewById(R.id.categoryRecyclerView);
        mTranslationsLayoutManager = new LinearLayoutManager(this);

        // Get the translations and save it to the global variable
        mTranslations = DataManager.getInstance().getTranslations();

        // Fill the RecyclerAdapter with the translations
        mTranslationsAdapter = new TranslationAdapter(this, mTranslations);

        // Display the translations
        displayTranslations();
    }

    private void displayTranslations() {
        // Set the LayoutManager and Adapter for the RecyclerView
        mRecyclerItems.setLayoutManager((mTranslationsLayoutManager));
        mRecyclerItems.setAdapter(mTranslationsAdapter);
    }

    private void saveFavoritesToDatabase(int entryId, TranslationModel tEntry) {
        // Create selection criteria as constants
        final String selection = DbContract.TranslationEntry._ID + " = ?";
        final String[] selectionArgs = {Integer.toString(entryId)};

        // Use a ContentValues object to put our information into.
        final ContentValues values = new ContentValues();
        values.put(DbContract.TranslationEntry.COLUMN_FAVORITE, tEntry.getFavorite());

        AsyncTaskLoader<String> task = new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                // Get connection to the database. Use the writable method since we are changing the data.
                DbHelper dbHelper = new DbHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Call the update method
                db.update(DbContract.TranslationEntry.TABLE_NAME, values, selection, selectionArgs);
                return null;
            }
        };

        task.loadInBackground();
    }


    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}