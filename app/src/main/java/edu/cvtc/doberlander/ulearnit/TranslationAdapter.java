package edu.cvtc.doberlander.ulearnit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.cvtc.doberlander.ulearnit.DbContract.TranslationEntry;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;
import java.util.List;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {

    private static final String TAG = "CategoryActivity";

    private final Context mContext;
    //private final List<TranslationModel> mTranslationList;
    private final LayoutInflater mInflater;
    private Cursor mCursor;

    // Handle Column Positions
    private int mEntryCategoryPosition;
    private int mEntryFirstLangPosition;
    private int mEntryFirstLangWordPosition;
    private int mEntrySecondLangPosition;
    private int mEntrySecondLangWordPosition;
    private int mEntryFavoritePosition;
    private int mIdPosition;

    // Favorite variables
    private boolean mItemClicked = false;
    private boolean mAddToFavorites = false;


    // Constructor initializes the translation list from the data
    public TranslationAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mInflater = LayoutInflater.from(context);

        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if(mCursor != null) {
            // Get the column indexes from the mCursor
            mEntryCategoryPosition = mCursor.getColumnIndex(TranslationEntry.COLUMN_CATEGORY);
            mEntryFirstLangPosition = mCursor.getColumnIndex(TranslationEntry.COLUMN_FIRST_LANGUAGE);
            mEntryFirstLangWordPosition = mCursor.getColumnIndex(TranslationEntry.COLUMN_FIRST_LANGUAGE_WORD);
            mEntrySecondLangPosition = mCursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE);
            mEntrySecondLangWordPosition = mCursor.getColumnIndex(TranslationEntry.COLUMN_SECOND_LANGUAGE_WORD);
            mEntryFavoritePosition = mCursor.getColumnIndex(TranslationEntry.COLUMN_FAVORITE);
            mIdPosition = mCursor.getColumnIndex(TranslationEntry._ID);
        }
    }

    // Check to see if you are using the most up to date cursor
    public void changeCursor(Cursor cursor) {
        // If the cursor is open, then close it
        if(mCursor != null) {
            mCursor.close();
        }

        // Create a new cursor based upon the object passed into it.
        mCursor = cursor;

        // Get the positions of the columns in your cursor.
        populateColumnPositions();

        // Tell the activity that the data set has changed.
        notifyDataSetChanged();
    }

    // Create the View Holder
    public class TranslationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView firstLangTranslationItemView;
        public final TextView secondLangTranslationItemView;
        public final ImageView favoriteImageView;
        final TranslationAdapter mAdapter;

        public TranslationViewHolder(View itemView, TranslationAdapter adapter) {
            super(itemView);
            firstLangTranslationItemView = itemView.findViewById(R.id.firstLangTranslation);
            secondLangTranslationItemView = itemView.findViewById(R.id.secondLangTranslation);
            favoriteImageView = itemView.findViewById(R.id.favorite_imageView);
            this.mAdapter = adapter;

            // Connect the onClickListener to the view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked
            mIdPosition = getLayoutPosition();

            // Set itemClicked Global variable to true
            mItemClicked = true;

            // Notify the adapter that data has changed and update the RecyclerView
            mAdapter.notifyDataSetChanged();

        }
    }

    // Inflate the item layout and return a ViewHolder with the layout and the adapter
    @NonNull
    @Override
    public TranslationAdapter.TranslationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        View mItemView = mInflater.inflate(R.layout.translation_item, parent, false);
        return new TranslationViewHolder(mItemView, this);
    }

    // Connect the data to the view holder
    @Override
    public void onBindViewHolder(@NonNull TranslationAdapter.TranslationViewHolder holder, int position) {
        // Move the cursor to the correct row
        mCursor.moveToPosition(position);

        // Get the actual values and pass them into an TranslationModel object
        TranslationModel tm = new TranslationModel();
        tm.setCategory(mCursor.getString(mEntryCategoryPosition));
        tm.setFirstLanguage(mCursor.getString(mEntryFirstLangPosition));
        tm.setFirstLanguageWord(mCursor.getString(mEntryFirstLangWordPosition));
        tm.setSecondLanguage(mCursor.getString(mEntrySecondLangPosition));
        tm.setSecondLanguageWord(mCursor.getString(mEntrySecondLangWordPosition));
        tm.setFavorite(mCursor.getInt(mEntryFavoritePosition));
        tm.setId(mCursor.getInt(mIdPosition));


        // Display the translation information
        holder.firstLangTranslationItemView.setText(tm.getFirstLanguageWord());
        holder.secondLangTranslationItemView.setText(tm.getSecondLanguageWord());
        if(tm.getFavorite() == 1) {
            Log.d(TAG, "Favorite Is Here");
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorites);
        } else {
            holder.favoriteImageView.setImageResource(R.drawable.ic_unfavorite);
        }


        // Highlight the entry if clicked
        if(mIdPosition == position && mItemClicked) {

            // Highlight the entry
            holder.itemView.setBackgroundColor(Color.parseColor("#00FF00"));

            // Get the CategoryActivity Menu
            Menu modifyMenu = CategoryActivity.mModifyMenu;
            modifyMenu.findItem(R.id.action_editEntry).setVisible(true);



            //TODO: Get Entry and save, so can be edited if Edit is clicked next

//            if (mAddToFavorites) {
//                // Add items to favorites and highlight
//                holder.itemView.setBackgroundColor(Color.parseColor("#00FF00"));
//                Toast.makeText(holder.itemView.getContext(), mCurrentEntry.getFirstLanguageWord() + " - Added to Favorites", Toast.LENGTH_SHORT).show();
//            } else {
//                // Item removed from favorites, and have red highlight
//                holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
//                Toast.makeText(holder.itemView.getContext(), mCurrentEntry.getFirstLanguageWord() + " - Removed from Favorites", Toast.LENGTH_SHORT).show();
//            }

            // Reset Item Clicked variable
            mItemClicked = false;

        } else {
            // Set all the entries to a default background color
            holder.itemView.setBackgroundColor(16777215);
        }

    }

    // Return the size of the translation list
    @Override
    public int getItemCount() {
        // If the cursor is null, return 0.  otherwise return the count of records in it.
        return mCursor == null ? 0 : mCursor.getCount();
    }
}
