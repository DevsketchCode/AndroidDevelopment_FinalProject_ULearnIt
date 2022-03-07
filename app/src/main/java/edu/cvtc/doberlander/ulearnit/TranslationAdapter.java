package edu.cvtc.doberlander.ulearnit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {

    private static final String TAG = "CategoryActivity";

    private final LinkedList<TranslationModel> mTranslationList;
    private final LayoutInflater mInflater;

    private int mPosition;
    private boolean mItemClicked = false;
    private boolean mAddToFavorites = false;

    // Constructor initializes the translation list from the data
    public TranslationAdapter(Context context, LinkedList<TranslationModel> translationList) {
        mInflater = LayoutInflater.from(context);
        this.mTranslationList = translationList;
    }

    // Create the View Holder
    public class TranslationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView firstLangTranslationItemView;
        public final TextView secondLangTranslationItemView;
        final TranslationAdapter mAdapter;

        public TranslationViewHolder(View itemView, TranslationAdapter adapter) {
            super(itemView);
            firstLangTranslationItemView = itemView.findViewById(R.id.firstLangTranslation);
            secondLangTranslationItemView = itemView.findViewById(R.id.secondLangTranslation);
            this.mAdapter = adapter;

            // Connect the onClickListener to the view
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

            // Check to see if the clicked element is a favorite or not
            if (!TranslationList.Favorites_List.contains(element)) {
                // Remove the translation from the Favorites List
                TranslationList.Favorites_List.add(element);

                // Set the mAddedToFavorites global variable to true
                mAddToFavorites = true;

            } else {

                // Add the translation to the Favorites List
                TranslationList.Favorites_List.remove(element);

                // Set the mAddedToFavorites global variable to false (removing from favorites)
                mAddToFavorites = false;
                //notifyItemRemoved(this.getLayoutPosition());
            }

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


        // Highlight the entry if clicked
        if(mPosition == position && mItemClicked) {

            if (mAddToFavorites) {
                // Add items to favorites and highlight
                holder.itemView.setBackgroundColor(Color.parseColor("#00FF00"));
                Toast.makeText(holder.itemView.getContext(), mCurrentEntry.getFirstLanguageWord() + " - Added to Favorites", Toast.LENGTH_SHORT).show();
            } else {
                // Item removed from favorites, and have red highlight
                holder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
                Toast.makeText(holder.itemView.getContext(), mCurrentEntry.getFirstLanguageWord() + " - Removed from Favorites", Toast.LENGTH_SHORT).show();
            }

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
