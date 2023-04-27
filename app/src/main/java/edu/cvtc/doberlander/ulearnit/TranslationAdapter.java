package edu.cvtc.doberlander.ulearnit;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;
import static edu.cvtc.doberlander.ulearnit.CategoryActivity.mSelectedItem;
import static edu.cvtc.doberlander.ulearnit.CategoryActivity.mSelectedItemID;
import static edu.cvtc.doberlander.ulearnit.CategoryActivity.mSelectedItemPosition;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Layout;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.TranslationViewHolder> {

    private final List<TranslationModel> mTranslationList;
    private final LayoutInflater mInflater;
    private final RecyclerViewInterface recyclerViewInterface;

    //private static final String TAG = "CategoryActivity";
    private TranslationModel mSelectedElement;
    private ConstraintLayout mainCategoryActivity;
    private ConstraintLayout entryDetailsContainer;

    private Context adapterContext;
    private Button closeDetailsButton;
    private int mPosition;
    private int mPreviousPosition = -1;
    private int clickedViewId;
    public static boolean mItemClicked = false;

    // Constructor initializes the translation list from the data
    public TranslationAdapter(Context context, List<TranslationModel> translationList, RecyclerViewInterface recyclerViewInterface) {
        mInflater = LayoutInflater.from(context);
        this.mTranslationList = translationList;
        this.recyclerViewInterface = recyclerViewInterface;
        mainCategoryActivity = ((CategoryActivity)context).findViewById(R.id.activity_category);
        entryDetailsContainer = ((CategoryActivity)context).findViewById(R.id.cardDetailsPopupContainer);
        closeDetailsButton = ((CategoryActivity)context).findViewById(R.id.btn_closeDetailsPopup);
    }

    // Create the View Holder
    public class TranslationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView firstLangTranslationItemView;
        public final TextView secondLangTranslationItemView;
        public final TextView entryTypeView;
        public final TextView tenseView;
        public final TextView notesView;
        public final ImageView favoriteImageView;
        public final CardView moreDetailsView;
        public final CardView audioTTSView;
        public final ImageView moreDetailsImageView;
        private final TranslationAdapter mAdapter;

        public TranslationViewHolder(View itemView, TranslationAdapter adapter) {
            super(itemView);
            firstLangTranslationItemView = itemView.findViewById(R.id.firstLangTranslation);
            secondLangTranslationItemView = itemView.findViewById(R.id.secondLangTranslation);
            entryTypeView = itemView.findViewById(R.id.langTranslationEntryType);
            tenseView = itemView.findViewById(R.id.langTranslationTense);
            notesView = itemView.findViewById(R.id.langTranslationNotes);
            favoriteImageView = itemView.findViewById(R.id.favorite_imageView);
            moreDetailsView = itemView.findViewById(R.id.translationEntryCardMore);
            audioTTSView = itemView.findViewById(R.id.translationEntryCardTTS);
            moreDetailsImageView = itemView.findViewById(R.id.more_imageView);
            this.mAdapter = adapter;
            adapterContext = itemView.getContext();

            // Connect the onClickListener to the view
            // https://developer.android.com/training/gestures/detector
            itemView.findViewById(R.id.translationEntryCardLayout).setOnClickListener(this);
            itemView.findViewById(R.id.favorite_imageView).setOnClickListener(this);
            itemView.findViewById(R.id.translationEntryCardMore).setOnClickListener(this);
            itemView.findViewById(R.id.translationEntryCardTTS).setOnClickListener(this);

            closeDetailsButton.setOnClickListener(this);

            // Create the long click listener to show the languages of the selected item
            itemView.findViewById(R.id.translationEntryCardLayout).setOnLongClickListener(new View.OnLongClickListener(){
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
            itemView.findViewById(R.id.translationEntryCardLayout).setOnTouchListener(new View.OnTouchListener(){
                // Create the GestureDetector to be able to capture a double tap and long press
                GestureDetector gestureDetector = new GestureDetector(itemView.getContext(), new GestureDetector.SimpleOnGestureListener(){
                    // This is the confirm the click, in order for the web activity to activate
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        // Get the position of the item that was clicked
                         // mPosition = getLayoutPosition();

                        // Set itemClicked Global variable to true
                        mItemClicked = true;

                        // Notify the adapter that data has changed and update the RecyclerView
                        // mAdapter.notifyDataSetChanged();
                        return super.onSingleTapConfirmed(e);
                    }
                    // Double tap to open up the translation in the WebActivity
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if(recyclerViewInterface != null) {
                            int position = getLayoutPosition();
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
            clickedViewId = -1;
            // Get the position of the item that was clicked
            mPosition = getAdapterPosition();
            // Access the affected item in the list
            mSelectedElement = mTranslationList.get(mPosition);

            // Check to see if the heart favorite imageView is what was clicked
            if(view.getId() == R.id.favorite_imageView) {
                // Set the view id that is clicked so it can be referenced easier later
                clickedViewId = view.getId();
                // Get the current favorites value of that selected item
                // Formatting of this setting can be found in the onBindViewHolder
                if (mSelectedElement.getFavorite() == 1) {
                    // The previous value was 1, so un-favorite it by setting to 0
                    mSelectedElement.setFavorite(0);
                } else {
                    // The previous value was 0, so favorite the item by setting it to one
                    mSelectedElement.setFavorite(1);
                }
            } else if (view.getId() == R.id.translationEntryCardMore) {
                // toggle visibility of entryDetailsContainer (this is ultimately what fixed this issue to show on the first click)
                if(entryDetailsContainer.getVisibility() == View.VISIBLE) {
                    entryDetailsContainer.setVisibility(View.INVISIBLE);
                } else {
                    entryDetailsContainer.setVisibility(View.VISIBLE);
                }
                clickedViewId = view.getId();
                mPreviousPosition = mPosition;
            } else if (view.getId() == R.id.btn_closeDetailsPopup) {
                clickedViewId = view.getId();
            } else if (view.getId() == R.id.translationEntryCardLayout) {
                clickedViewId = view.getId();
            } else if (view.getId() == R.id.translationEntryCardTTS) {
                clickedViewId = view.getId();
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
        holder.entryTypeView.setText(mCurrentEntry.getEntryType());
        holder.tenseView.setText(mCurrentEntry.getTense());
        holder.notesView.setText(mCurrentEntry.getNotes().replace("\\n", Objects.requireNonNull(System.getProperty("line.separator"))));

        View detailsContainer = holder.itemView.findViewById(R.id.cardDetailsPopupContainer);
        // Check to see if the item clicked is the Favorite Heart imageView
        if(clickedViewId == R.id.favorite_imageView) {
            // If the favorite heart is clicked, check the value of it
            if (mCurrentEntry.getFavorite() == 1) {
                // Display the filled in heart
                holder.favoriteImageView.setImageResource(R.drawable.ic_favorites);
            } else {
                // Display the outlined heart
                holder.favoriteImageView.setImageResource(R.drawable.ic_unfavorite);
            }
        } else if (clickedViewId == R.id.translationEntryCardMore) {
            // Show details container that exists outside of the recyclerview
            entryDetailsContainer.setVisibility(View.VISIBLE);

        } else if (clickedViewId == R.id.btn_closeDetailsPopup) {
            // Hide details container that exists outside of the recyclerview
            entryDetailsContainer.setVisibility(View.INVISIBLE);
        }

        // Highlight favorite upon recyclerview loading if it is checked in the database
        if(mCurrentEntry.getFavorite() == 1) {
            holder.favoriteImageView.setImageResource(R.drawable.ic_favorites);
        } else {
            holder.favoriteImageView.setImageResource(R.drawable.ic_unfavorite);
        }

        if(clickedViewId == R.id.btn_closeDetailsPopup) {
            mPosition = mPreviousPosition;
        }

        // Toast.makeText(holder.itemView.getContext(), "mPosition: " + mPosition + "//position: " + position, Toast.LENGTH_SHORT).show();

        // Highlight the entry if clicked
        if(mPosition == position && mItemClicked) {
            // Highlight the entry
            holder.itemView.setBackgroundColor(Color.parseColor("#F1D1F6"));
            // CardView entryCard = holder.itemView.findViewById(R.id.translationEntryCardLayout);
            // entryCard.setCardBackgroundColor(Color.parseColor("#F1D1F6"));

            if(clickedViewId == R.id.translationEntryCardTTS) {
                // Play TextToSpeech Audio
                // Create an instance of the AudioManager class
                AudioManager audioManager = new AudioManager();

                String firstLanguage = mCurrentEntry.getFirstLanguage().toString();
                String firstLanguageEntry = mCurrentEntry.getFirstLanguageEntry().toString();
                String secondLanguage = mCurrentEntry.getSecondLanguage().toString();
                String secondLanguageEntry = mCurrentEntry.getSecondLanguageEntry().toString();

                // Prepare the second language to play
                UtteranceProgressListener secondLanguageListener = new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {
                        audioManager.isSpeakingFlag();
                    }

                    @Override
                    public void onDone(String utteranceId) {
                        Log.d(TAG, "TranslationAdapter: TTS is done: SecondLanguage");
                        // Speech synthesis has completed
                        audioManager.resetSpeakingFlag();
                    }
                    @Override
                    public void onError(String utteranceId) {
                        Log.d(TAG, "TranslationAdapter: TTS had an error: SecondLanguage");
                        // There was an error in speech synthesis
                        audioManager.resetSpeakingFlag();
                    }
                };

                // Prepare the first language to play
                UtteranceProgressListener firstLanguageListener = new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {
                        audioManager.isSpeakingFlag();
                    }

                    @Override
                    public void onDone(String utteranceId) {
                        // If there is a second language and text to speak, play it immediately after the first one
                        if (!secondLanguage.isEmpty() && !secondLanguageEntry.isEmpty()) {
                            Log.d(TAG, "TranslationAdapter: TTS is done: FirstLanguage");
                            // Get the first string to play in the first language
                            audioManager.textToSpeech(holder.itemView.getContext(), secondLanguage, secondLanguageEntry, secondLanguageListener);
                        }
                    }
                    @Override
                    public void onError(String utteranceId) {
                        Log.d(TAG, "TranslationAdapter: TTS had an error: FirstLanguage");
                    }
                };

                // Get the first string to play in the first language
                audioManager.textToSpeech(holder.itemView.getContext(), firstLanguage, firstLanguageEntry + "... ", firstLanguageListener);
            }

            // Display Popup
            if(entryDetailsContainer.getVisibility() == View.VISIBLE) {
                populateDetailsPopup(mCurrentEntry);
            }

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
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    // Return the size of the translation list
    @Override
    public int getItemCount() {
        return mTranslationList.size();
    }

    private void populateDetailsPopup(TranslationModel tm) {
        TextView tmpFirstLang = entryDetailsContainer.findViewById(R.id.textView_Details_Lang1);
        TextView tmpFirstLangEntry = entryDetailsContainer.findViewById(R.id.textView_Details_Lang1Entry);
        TextView tmpFirstLangEntryRomanized = entryDetailsContainer.findViewById(R.id.textView_Details_Lang1EntryRomanized);
        TextView tmpFirstLangEntryRomanizedLabel = entryDetailsContainer.findViewById(R.id.textView_Label_1stLangRomanized);
        TextView tmpSecondLang = entryDetailsContainer.findViewById(R.id.textView_Details_Lang2);
        TextView tmpSecondLangEntry = entryDetailsContainer.findViewById(R.id.textView_Details_Lang2Entry);
        TextView tmpSecondLangEntryRomanized = entryDetailsContainer.findViewById(R.id.textView_Details_Lang2EntryRomanized);
        TextView tmpSecondLangEntryRomanizedLabel = entryDetailsContainer.findViewById(R.id.textView_Label_2ndLangRomanized);
        TextView tmpEntryType = entryDetailsContainer.findViewById(R.id.textView_Details_EntryType);
        TextView tmpGender = entryDetailsContainer.findViewById(R.id.textView_Details_Gender);
        TextView tmpTense = entryDetailsContainer.findViewById(R.id.textView_Details_Tense);
        TextView tmpFormality = entryDetailsContainer.findViewById(R.id.textView_Details_Formality);
        TextView tmpIsPlural = entryDetailsContainer.findViewById(R.id.textView_Details_IsPlural);
        TextView tmpPercentLearned = entryDetailsContainer.findViewById(R.id.textView_Details_PercentLearned);
        TextView tmpNotes = entryDetailsContainer.findViewById(R.id.textView_Details_Notes);
        TextView tmpLang1Example = entryDetailsContainer.findViewById(R.id.textView_Details_Lang1EntryExample);
        TextView tmpLang2Example = entryDetailsContainer.findViewById(R.id.textView_Details_Lang2EntryExample);

        // Set OnClick Listener to copy text
        tmpFirstLangEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyText(tmpFirstLang.getText().toString(), tmpFirstLangEntry.getText().toString());
            }
        });

        tmpSecondLangEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyText(tmpSecondLang.getText().toString(), tmpSecondLangEntry.getText().toString());
            }
        });

        String tmpPercentLearnedString = "";
        tmpFirstLang.setText(tm.getFirstLanguage());
        tmpFirstLangEntry.setText(tm.getFirstLanguageEntry());
        tmpFirstLangEntryRomanized.setText(tm.getFirstLanguageEntryRomanized());
        if (tmpFirstLangEntryRomanized.getText().equals("")) {
            tmpFirstLangEntryRomanizedLabel.setVisibility(View.INVISIBLE);
            //tmpFirstLangEntryRomanized.setHeight(0);
            tmpFirstLangEntryRomanized.setVisibility(View.INVISIBLE);
        } else {
            tmpFirstLangEntryRomanizedLabel.setVisibility(View.VISIBLE);
            tmpFirstLangEntryRomanized.setVisibility(View.VISIBLE);
        }
        tmpSecondLang.setText(tm.getSecondLanguage());
        tmpSecondLangEntry.setText(tm.getSecondLanguageEntry());
        tmpSecondLangEntryRomanized.setText(tm.getSecondLanguageEntryRomanized());

        if (tmpSecondLangEntryRomanized.getText().equals("")) {
            tmpSecondLangEntryRomanizedLabel.setVisibility(View.INVISIBLE);
            tmpSecondLangEntryRomanized.setVisibility(View.INVISIBLE);
        } else
        {
            tmpSecondLangEntryRomanizedLabel.setVisibility(View.VISIBLE);
            tmpSecondLangEntryRomanized.setVisibility(View.VISIBLE);
        }

        tmpEntryType.setText(tm.getEntryType());
        tmpGender.setText(tm.getGender());
        tmpTense.setText(tm.getTense());
        tmpFormality.setText(tm.getFormality());
        if((tm.getEntryType().equals("Noun") || tm.getEntryType().equals("Verb") )) {
            if(tm.getIsPlural()) {
                tmpIsPlural.setText("Plural");
            } else {
                tmpIsPlural.setText("Singular");
            }
        } else {
            tmpIsPlural.setText("N/A");
        }


        tmpPercentLearnedString = tm.getPercentLearned() + "%";
        tmpPercentLearned.setText(tmpPercentLearnedString);

        tmpNotes.setText(tm.getNotes().replace("\\n", Objects.requireNonNull(System.getProperty("line.separator"))));
        tmpLang1Example.setText(tm.getFirstLanguageExample());
        tmpLang2Example.setText(tm.getSecondLanguageExample());

    }

    private void copyText(String lang, String txt) {
        ClipboardManager clipboard = (ClipboardManager) adapterContext.getSystemService(adapterContext.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copy Entry", txt);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(adapterContext, lang + " Entry Copied", Toast.LENGTH_SHORT).show();
    }
}
