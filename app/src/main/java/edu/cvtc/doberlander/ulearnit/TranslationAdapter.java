package edu.cvtc.doberlander.ulearnit;

import static edu.cvtc.doberlander.ulearnit.CategoryActivity.mSelectedItemID;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {

    private static final String TAG = "CategoryActivity";

    private final List<TranslationModel> mTranslationList;
    private final LayoutInflater mInflater;

    private int mPosition;
    private boolean mItemClicked = false;
    private boolean mAddToFavorites = false;


    // Constructor initializes the translation list from the data
    public TranslationAdapter(Context context, List<TranslationModel> translationList) {
        mInflater = LayoutInflater.from(context);
        this.mTranslationList = translationList;
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
            itemView.findViewById(R.id.favorite_imageView).setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked
            mPosition = getLayoutPosition();
            // Access the affected item in the list
            TranslationModel element = mTranslationList.get(mPosition);

            // Set itemClicked Global variable to true
            mItemClicked = true;

            // Check to see if the heart favorite imageView is what was clicked
            if(view.getId() == R.id.favorite_imageView) {
                // Get the current favorites value of that selected item
                // Formatting of this setting can be found in the onBindViewHolder
                if (element.getFavorite() == 1) {
                    // The previous value was 1, so unfavorite it by setting to 0
                    element.setFavorite(0);
                    // TODO: DELETE THIS COMMENT
                    //element.setFirstLanguageWord("Good Afternoon"); THIS WORKS
                } else {
                    // The previous value was 0, so favorite the item by setting it to one
                    element.setFavorite(1);
                }
            }

            // Pass the selected item to the CategoryActivity, so it can be used to pass to the
            // Modifier Activity
            CategoryActivity.mSelectedItem = element;

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
        // Get the list position
        TranslationModel mCurrentEntry = mTranslationList.get(position);

        // Display the translation information
        holder.firstLangTranslationItemView.setText(mCurrentEntry.getFirstLanguageWord());
        holder.secondLangTranslationItemView.setText(mCurrentEntry.getSecondLanguageWord());

        // Check to see if the item clicked is the Favorite Heart imageView
        if(holder.favoriteImageView.getId() == R.id.favorite_imageView) {
            // If the favorite heart is clicked, check the value of it
            if (mCurrentEntry.getFavorite() == 1) {
                // Display the filled in heart
                holder.favoriteImageView.setImageResource(R.drawable.ic_favorites);
            } else {
                // Display the outlined heart
                holder.favoriteImageView.setImageResource(R.drawable.ic_unfavorite);
            }
        }

        // Highlight the entry if clicked
        if(mPosition == position && mItemClicked) {

            // Highlight the entry
            holder.itemView.setBackgroundColor(Color.parseColor("#00FF00"));

            // Update the selected ID with the database ID for this record
            mSelectedItemID = (int)mCurrentEntry.getId();

            // Get the CategoryActivity Menu
            Menu modifyMenu = CategoryActivity.mModifyMenu;
            modifyMenu.findItem(R.id.action_editEntry).setVisible(true);

            // Save the updated favorite item to the database
            DataManager dm = new DataManager();
            dm.saveEntryToDatabase(mCurrentEntry.getId(), mCurrentEntry, mInflater);

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
        return mTranslationList.size();
    }
}
