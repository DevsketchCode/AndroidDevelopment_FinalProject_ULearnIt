package edu.cvtc.doberlander.ulearnit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {

    private static final String TAG = "CategoryActivity";

    private final LinkedList<TranslationModel> mTranslationList;
    private LayoutInflater mInflater;

    // Constructor initializes the translation list from the data
    public TranslationAdapter(Context context, LinkedList<TranslationModel> translationList) {
        mInflater = LayoutInflater.from(context);
        this.mTranslationList = translationList;
    }

    // Create the View Holder
    public class TranslationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView translationItemView;
        final TranslationAdapter mAdapter;

        public TranslationViewHolder(View itemView, TranslationAdapter adapter) {
            super(itemView);
            translationItemView = itemView.findViewById(R.id.translation);
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

            // Add translation as Favorite
            mTranslationList.set(mPosition, element);

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
        TranslationModel mCurrentEntry = (TranslationModel) mTranslationList.get(position);
        Log.d(TAG, "tester: " + mTranslationList.get(position).toString());
        // Set the display text
        String translationText = mCurrentEntry.getFirstLanguageWord() + " : " + mCurrentEntry.getSecondLanguageWord();
        // Display the translation information
        holder.translationItemView.setText(translationText);
    }

    // Return the size of the translation list
    @Override
    public int getItemCount() { return mTranslationList.size(); }

}
