package edu.cvtc.doberlander.ulearnit;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.cvtc.doberlander.ulearnit.DbContract.TranslationEntry;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // Constants
    public static final String EXTRA_MESSAGE = "edu.cvtc.doberlander.ulearnit.extra.MESSAGE";
    public static final int LOADER_COURSES = 0;

    // Variable to access translations from the database
    private DbHelper mDbHelper;
    private RecyclerView mRecyclerItems;
    private LinearLayoutManager mTranslationsLayoutManager;
    private TranslationAdapter mTranslationsAdapter;
    private String mCategory;

    // Public variables
    public static final int ITEM_COURSES = 0;
    public static Menu mModifyMenu;

    // Member variable for translations
    public List<TranslationModel> mTranslations;

    // Boolean to check if the 'onCreateLoader' method has run
    private boolean mIsCreated = false;

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
        //DataManager dm = new DataManager();
        //List<TranslationModel> translations = dm.getTranslations();

        // Only attach data if there is data
//        if (translations.size() > 0) {
//            // Create the RecyclerView and Connect the Adapter and Data
//            // Create list used to store the list of data
//            RecyclerView mRecyclerView = findViewById(R.id.categoryRecyclerView);
//
//            // Create the adapter and attach the data, no cursor yet, so pass null
//            TranslationAdapter mAdapter = new TranslationAdapter(this, null);
//
//            // Connect the adapter to the RecyclerView
//            mRecyclerView.setAdapter(mAdapter);
//
//            // Give the RecyclerView a default Layout Manager
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    // Allow the menu to be accessed from the adapter
    public Menu getMenu() {
        return mModifyMenu;
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
        String category = "";

        switch (item.getItemId()) {
            case R.id.action_newEntry:
                category = getString(R.string.action_new);
                modifierIntent.putExtra(EXTRA_MESSAGE, category);
                startActivity(modifierIntent);
                return true;
            case R.id.action_editEntry:
                category = getString(R.string.action_edit);
                modifierIntent.putExtra(EXTRA_MESSAGE, category);
                startActivity(modifierIntent);
                return true;
            case R.id.action_favorites:
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
        // Retrieve the information from the database
        DataManager.loadFromDatabase(mDbHelper, mCategory);

        // Set a reference to the translations of the items layout
        mRecyclerItems = (RecyclerView) findViewById(R.id.categoryRecyclerView);
        mTranslationsLayoutManager = new LinearLayoutManager(this);

        // Get the translations and save it to the global variable
        mTranslations = DataManager.getInstance().getTranslations();

        // Fill the RecyclerAdapter with the translations
        mTranslationsAdapter = new TranslationAdapter(this, null);

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

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get the latest set of date from the database.
        // Use the restartLoader instead of initLoader to make sure it re-queries the database each time
        // the activity is loaded in the app. initLoader only runs the first time.
        LoaderManager.getInstance(this).restartLoader(LOADER_COURSES, null, null);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Create new cursor loader
        CursorLoader loader = null;

        if(id == LOADER_COURSES) {
            loader = new CursorLoader(this) {
                @Override
                public Cursor loadInBackground() {
                    mIsCreated = true;

                    // Open the database in read mode
                    SQLiteDatabase db = mDbHelper.getReadableDatabase();

                    // Create the list of columns to return
                    String[] entryColumns = {
                            TranslationEntry.COLUMN_CATEGORY,
                            TranslationEntry.COLUMN_FIRST_LANGUAGE,
                            TranslationEntry.COLUMN_FIRST_LANGUAGE_WORD,
                            TranslationEntry.COLUMN_SECOND_LANGUAGE,
                            TranslationEntry.COLUMN_SECOND_LANGUAGE_WORD,
                            TranslationEntry.COLUMN_FAVORITE,
                            TranslationEntry._ID
                    };

                    // Create an order by field for sorting purposes
                    String entryOrderBy = TranslationEntry._ID;

                    // Populate your cursor with the results of the query.
                    return db.query(TranslationEntry.TABLE_NAME, entryColumns,
                            null, null, null, null, entryOrderBy);
                }
            };
        }

        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == LOADER_COURSES && mIsCreated) {
            // Associate the cursor with the RecyclerAdapter
            mTranslationsAdapter.changeCursor(data);
            mIsCreated = false;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if(loader.getId() == LOADER_COURSES) {
            // Change the cursor to null (cleanup)
            mTranslationsAdapter.changeCursor(null);
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}