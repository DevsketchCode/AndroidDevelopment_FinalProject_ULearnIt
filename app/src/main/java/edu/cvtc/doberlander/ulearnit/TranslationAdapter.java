package edu.cvtc.doberlander.ulearnit;

import static edu.cvtc.doberlander.ulearnit.CategoryActivity.mSelectedItemID;

import android.content.Context;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {

    private final List<TranslationModel> mTranslationList;
    private final LayoutInflater mInflater;
    private final RecyclerViewInterface recyclerViewInterface;
    //private static final String TAG = "CategoryActivity";

    private int mPosition;
    public static boolean mItemClicked = false;

    // Constructor initializes the translation list from the data
    public TranslationAdapter(Context context, List<TranslationModel> translationList, RecyclerViewInterface recyclerViewInterface) {
        mInflater = LayoutInflater.from(context);
        this.mTranslationList = translationList;
        this.recyclerViewInterface = recyclerViewInterface;
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
            // https://developer.android.com/training/gestures/detector
            itemView.findViewById(R.id.favorite_imageView).setOnClickListener(this);

            // Create the long click listener to show the languages of the selected item
            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    if(recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        // Only do the onLongClick action if there is a position and it equals the one that is highlighted.
                        if (position != RecyclerView.NO_POSITION && mPosition == position) {
                            // Run the actions in the CategoryActivity
                            recyclerViewInterface.onItemLongTap(position);
                        }
                    }
                    return true;
                }
            });

            // Create an onTouch listener to handle some touch gestures
            itemView.setOnTouchListener(new View.OnTouchListener(){
                // Create the GestureDetector to be able to capture a double tap and long press
                GestureDetector gestureDetector = new GestureDetector(itemView.getContext(), new GestureDetector.SimpleOnGestureListener(){
                    // This is the confirm the click, in order for the web activity to activate
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        // Get the position of the item that was clicked
                         mPosition = getLayoutPosition();

                        // Set itemClicked Global variable to true
                        mItemClicked = true;

                        // Notify the adapter that data has changed and update the RecyclerView
                        mAdapter.notifyDataSetChanged();
                        return super.onSingleTapConfirmed(e);
                    }
                    // Double tap to open up the translation in the WebActivity
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {

                        if(recyclerViewInterface != null) {
                            int position = getAdapterPosition();
                                // Only do the onLongClick action if there is a position and it equals the one that is highlighted.
                                if (position != RecyclerView.NO_POSITION && mPosition == position) {
                                    // Run the actions in the CategoryActivity
                                    recyclerViewInterface.onItemDoubleTap(position);
                                }
                        }
                        return super.onDoubleTap(e);
                    }
                });
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    // Process the gesture
                    gestureDetector.onTouchEvent(motionEvent);
                    return false;
                }
            });
        }

        // Use this onClick method for selecting an entry to be added or removed from favorites
        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked
            mPosition = getLayoutPosition();
            // Access the affected item in the list
            TranslationModel element = mTranslationList.get(mPosition);

            // Check to see if the heart favorite imageView is what was clicked
            if(view.getId() == R.id.favorite_imageView) {
                // Get the current favorites value of that selected item
                // Formatting of this setting can be found in the onBindViewHolder
                if (element.getFavorite() == 1) {
                    // The previous value was 1, so un-favorite it by setting to 0
                    element.setFavorite(0);
                } else {
                    // The previous value was 0, so favorite the item by setting it to one
                    element.setFavorite(1);
                }
            }
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
    public void onBindViewHolder(@NonNull TranslationViewHolder holder, int position) {
        // Get the list position
        TranslationModel mCurrentEntry = mTranslationList.get(position);

        // Display the translation information
        holder.firstLangTranslationItemView.setText(mCurrentEntry.getFirstLanguageEntry());
        holder.secondLangTranslationItemView.setText(mCurrentEntry.getSecondLanguageEntry());

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

            // Set the Selected Item to the currently clicked item
            CategoryActivity.mSelectedItem = mCurrentEntry;

            // Update the selected ID with the database ID for this record
            mSelectedItemID = mCurrentEntry.getId();

            // Only run when using the Category Activity and the category is NOT favorites
            if(!mCurrentEntry.getCategory().equals("Favorites")) {
                // Get the CategoryActivity ModifyMenu
                Menu modifyMenu = CategoryActivity.mModifyMenu;
                // Display the edit option when entry is selected
                modifyMenu.findItem(R.id.action_editEntry).setVisible(true);
            }

            // Save the updated favorite item to the database
            DataManager dm = new DataManager();
            dm.saveEntryFromAdapterToDatabase(mCurrentEntry.getId(), mCurrentEntry, mInflater);

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
