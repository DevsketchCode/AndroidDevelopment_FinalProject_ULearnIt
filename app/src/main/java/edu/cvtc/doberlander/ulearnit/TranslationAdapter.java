package edu.cvtc.doberlander.ulearnit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {

    private static final String TAG = "CategoryActivity";

    private final LinkedList<TranslationModel> mTranslationList;
    private final LayoutInflater mInflater;

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
            int mPosition = getLayoutPosition();
            // Access the affected item in the list
            TranslationModel element = mTranslationList.get(mPosition);

            // Check to see if the clicked element is a favorite or not
            if (!TranslationList.Favorites_List.contains(element)) {
                // Remove the translation from the Favorites List
                TranslationList.Favorites_List.add(element);
            } else {
                // Add the translation to the Favorites List
                TranslationList.Favorites_List.remove(element);
            }
            Log.d(TAG, "Pretest: " + ((ColorDrawable)view.getBackground()).getColor());

            // Check to see if the element was highlighted or not
            //if ( == Color.parseColor("#00FFFFFF")) {
            //view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            Log.d(TAG, "Posttest: " + ((ColorDrawable)view.getBackground()).getColor());
            int color = ((ColorDrawable)view.getBackground()).getColor();
            // If Color is the default color, highlight it, else reset
            if (color == 16777215) {
                view.setBackgroundColor(Color.parseColor("#00FF00"));
            } else {
                view.setBackgroundColor(16777215);
            }

            //}

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
    }

    // Return the size of the translation list
    @Override
    public int getItemCount() {
        return mTranslationList.size();
    }

}
