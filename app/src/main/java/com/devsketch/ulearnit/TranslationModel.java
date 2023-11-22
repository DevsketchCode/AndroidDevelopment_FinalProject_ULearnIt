package com.devsketch.ulearnit;

import static android.content.ContentValues.TAG;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TranslationModel implements Parcelable {
    // Declare Variables
    private int mId;
    private String mCategory;
    private String mSubCategory;
    private String mFirstLanguage;
    private String mFirstLanguageEntry;
    private String mFirstLanguageEntryRomanized;
    private String mFirstLanguageExample;
    private String mSecondLanguage;
    private String mSecondLanguageEntry;
    private String mSecondLanguageEntryRomanized;
    private String mSecondLanguageExample;
    private String mEntryType;
    private String mTense;
    private int mLinkEntryIdTenseBase;
    private int mLinkEntryIdTensePast;
    private int mLinkEntryIdTensePresent;
    private int mLinkEntryIdTenseFuture;
    private Boolean mIsPlural;
    private String mGender;
    private String mFormality;
    private int mPercentLearned;
    private String mPercentLearnedModifiedDate;
    private int mFailedAttempts;
    private Boolean mMemorized;
    private String mNotes;
    private String mSummaryNotes;
    private String mImage;
    private String mAudio;
    private String mUserAudio;
    private String mUserAudioModifiedDate;
    private int mFavorite;
    private String mTags;
    private String mModifiedDate;
    private Boolean mOnQuickList;
    private Boolean mArchived;

    // Default constructor
    public TranslationModel() {
    }

    // Empty Overloaded Constructor with predefined category and favorites set
    public TranslationModel(String category, int favorite) {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault());
        this.mCategory = category;
        this.mSubCategory = "";
        this.mFirstLanguage = "";
        this.mFirstLanguageEntry = "";
        this.mFirstLanguageEntryRomanized = "";
        this.mFirstLanguageExample = "";
        this.mSecondLanguage = "";
        this.mSecondLanguageEntry = "";
        this.mSecondLanguageEntryRomanized = "";
        this.mSecondLanguageExample = "";
        this.mEntryType = "";
        this.mTense = "N/A"; // N/A, Base, Past, Present, or Future
        this.mLinkEntryIdTenseBase = 0;
        this.mLinkEntryIdTensePast = 0;
        this.mLinkEntryIdTensePresent = 0;
        this.mLinkEntryIdTenseFuture = 0;
        this.mIsPlural = false; // False for NA or Singular, True for Plural
        this.mGender = "N/A"; //N/A, Masculine, Feminine
        this.mFormality = "N/A"; // N/A, Casual, Formal, Informal, Polite, Slang
        this.mPercentLearned = 0;
        this.mPercentLearnedModifiedDate = sdf.format(new Date());
        this.mFailedAttempts = 0;
        this.mMemorized = false;
        this.mNotes = "";
        this.mSummaryNotes = "";
        this.mImage = "";
        this.mAudio = "";
        this.mUserAudio = "";
        this.mUserAudioModifiedDate = sdf.format(new Date());
        this.mFavorite = favorite;
        this.mTags = "";
        this.mModifiedDate = sdf.format(new Date());
        this.mOnQuickList = false;
        this.mArchived = false;
    }

    // Overloaded Constructor to create entire object, including Id
    public TranslationModel(int id, String category, String subCategory, String firstLanguage, String firstLanguageEntry, String mFirstLanguageEntryRomanized, String firstLanguageExample, String secondLanguage, String secondLanguageEntry, String mSecondLanguageEntryRomanized, String secondLanguageExample, String entryType, String tense, int linkEntryIdTenseBase, int linkEntryIdTensePast, int linkEntryIdTensePresent, int linkEntryIdTenseFuture, Boolean isPlural, String gender, String formality, int percentLearned, String percentLearnedModifiedDate, int failedAttempts, Boolean memorized, String notes, String summaryNotes, String image, String audio, String userAudio, String userAudioModifiedDate, int favorite, String tags, String modifiedDate, Boolean onQuickList, Boolean archived) {
        this.mId = id;
        this.mCategory = category;
        this.mSubCategory = subCategory;
        this.mFirstLanguage = firstLanguage;
        this.mFirstLanguageEntry = firstLanguageEntry;
        this.mFirstLanguageEntryRomanized = mFirstLanguageEntryRomanized;
        this.mFirstLanguageExample = firstLanguageExample;
        this.mSecondLanguage = secondLanguage;
        this.mSecondLanguageEntry = secondLanguageEntry;
        this.mSecondLanguageEntryRomanized = mSecondLanguageEntryRomanized;
        this.mSecondLanguageExample = secondLanguageExample;
        this.mEntryType = entryType;
        this.mTense = tense;
        this.mLinkEntryIdTenseBase = linkEntryIdTenseBase;
        this.mLinkEntryIdTensePast = linkEntryIdTensePast;
        this.mLinkEntryIdTensePresent = linkEntryIdTensePresent;
        this.mLinkEntryIdTenseFuture = linkEntryIdTenseFuture;
        this.mIsPlural = isPlural;
        this.mGender = gender;
        this.mFormality = formality;
        this.mPercentLearned = percentLearned;
        this.mPercentLearnedModifiedDate = percentLearnedModifiedDate;
        this.mFailedAttempts = failedAttempts;
        this.mMemorized = memorized;
        this.mNotes = notes;
        this.mSummaryNotes = summaryNotes;
        this.mImage = image;
        this.mAudio = audio;
        this.mUserAudio = userAudio;
        this.mUserAudioModifiedDate = userAudioModifiedDate;
        this.mFavorite = favorite;
        this.mTags = tags;
        this.mModifiedDate = modifiedDate;
        this.mOnQuickList = onQuickList;
        this.mArchived = archived;
    }


    // Getters and Setters;
    public int getId() { return mId; }

    public void setId(int id) { this.mId = id; }

    public String getCategory() { return mCategory; }

    public void setCategory(String category) { this.mCategory = category; }

    public String getSubCategory() { return mSubCategory; }

    public void setSubCategory(String subCategory) { this.mSubCategory = subCategory; }

    public String getFirstLanguage() {
        return mFirstLanguage;
    }

    public void setFirstLanguage(String firstLanguage) {
        this.mFirstLanguage = firstLanguage;
    }

    public String getFirstLanguageEntry() {
        return mFirstLanguageEntry;
    }

    public void setFirstLanguageEntry(String firstLanguageEntry) {
        this.mFirstLanguageEntry = firstLanguageEntry;
    }

    public String getFirstLanguageEntryRomanized() { return mFirstLanguageEntryRomanized; }

    public void setFirstLanguageEntryRomanized(String firstLanguageEntryRomanized) {
        this.mFirstLanguageEntryRomanized = firstLanguageEntryRomanized;
    }

    public String getFirstLanguageExample() { return mFirstLanguageExample; }

    public void setFirstLanguageExample(String firstLanguageExample) {
        this.mFirstLanguageExample = firstLanguageExample;
    }

    public String getSecondLanguage() {
        return mSecondLanguage;
    }

    public void setSecondLanguage(String secondLanguage) {
        this.mSecondLanguage = secondLanguage;
    }

    public String getSecondLanguageEntry() {
        return mSecondLanguageEntry;
    }

    public void setSecondLanguageEntry(String secondLanguageEntry) {
        this.mSecondLanguageEntry = secondLanguageEntry;
    }

    public String getSecondLanguageEntryRomanized() { return mSecondLanguageEntryRomanized; }

    public void setSecondLanguageEntryRomanized(String secondLanguageEntryRomanized) {
        this.mSecondLanguageEntryRomanized = secondLanguageEntryRomanized;
    }

    public String getSecondLanguageExample() { return mSecondLanguageExample; }

    public void setSecondLanguageExample(String secondLanguageExample) {
        this.mSecondLanguageExample = secondLanguageExample;
    }

    public String getEntryType() { return mEntryType; }

    public void setEntryType(String entryType) { this.mEntryType = entryType; }

    public String getTense() { return mTense; }

    public void setTense(String tense) { this.mTense = tense; }

    public int getLinkEntryIdTenseBase() {
        return mLinkEntryIdTenseBase;
    }

    public void setLinkEntryIdTenseBase(int linkEntryIdTenseBase) {
        this.mLinkEntryIdTenseBase = linkEntryIdTenseBase;
    }

    public int getLinkEntryIdTensePast() {
        return mLinkEntryIdTensePast;
    }

    public void setLinkEntryIdTensePast(int linkEntryIdTensePast) {
        this.mLinkEntryIdTensePast = linkEntryIdTensePast;
    }

    public int getLinkEntryIdTensePresent() {
        return mLinkEntryIdTensePresent;
    }

    public void setLinkEntryIdTensePresent(int linkEntryIdTensePresent) {
        this.mLinkEntryIdTensePresent = linkEntryIdTensePresent;
    }

    public int getLinkEntryIdTenseFuture() {
        return mLinkEntryIdTenseFuture;
    }

    public void setLinkEntryIdTenseFuture(int linkEntryIdTenseFuture) {
        this.mLinkEntryIdTenseFuture = linkEntryIdTenseFuture;
    }

    public Boolean getIsPlural() { return mIsPlural; }
    public void setIsPlural(Boolean isPlural) { this.mIsPlural = isPlural; }

    public String getGender() { return mGender; }

    public void setGender(String gender) { this.mGender = gender; }

    public String getFormality() { return mFormality; }

    public void setFormality(String formality) { this.mFormality = formality; }

    public int getPercentLearned() { return mPercentLearned; }

    public void setPercentLearned(int percentLearned) { this.mPercentLearned = percentLearned; }

    public String getPercentLearnedModifiedDate() {
        return mPercentLearnedModifiedDate;
    }

    public void setPercentLearnedModifiedDate(String newDate) {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault());
        // Always set a new date and time to become the modified date/time stamp
        newDate = sdf.format(new Date());
        this.mPercentLearnedModifiedDate = newDate;
    }

    public int getFailedAttempts() { return mFailedAttempts; }

    public void setFailedAttempts(int failedAttempts) {
        this.mFailedAttempts = failedAttempts;
    }

    public Boolean getMemorized() {
        return mMemorized;
    }

    public void setMemorized(Boolean memorized) {
        this.mMemorized = memorized;
    }

    public String getNotes() {
        mNotes = String.valueOf(Html.fromHtml(mNotes, Html.FROM_HTML_MODE_COMPACT));
        return mNotes;
    }

    public void setNotes(String notes) {
        this.mNotes = String.valueOf(Html.toHtml(new SpannableString(notes), Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
    }

    public String getSummaryNotes() {
        return mSummaryNotes;
    }

    public void setSummaryNotes(String summaryNotes) {
        this.mSummaryNotes = summaryNotes;
    }

    public String getImage() { return mImage; }

    public void setImage(String image) { this.mImage = image; }

    public String getAudio() { return mAudio; }

    public void setAudio(String audio) { this.mAudio = audio; }

    public String getUserAudio() { return mUserAudio; }

    public void setUserAudio(String userAudio) { this.mUserAudio = userAudio; }

    public String getUserAudioModifiedDate() {
        return mUserAudioModifiedDate;
    }

    public void setUserAudioModifiedDate(String newDate) {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault());
        // Always set a new date and time to become the modified date/time stamp
        newDate = sdf.format(new Date());
        this.mUserAudioModifiedDate = newDate;
    }

    public int getFavorite() {
        return mFavorite;
    }

    public void setFavorite(int favorite) {
        this.mFavorite = favorite;
    }

    public String getTags() { return mTags; }

    public void setTags(String tags) { this.mTags = tags; }

    public String getModifiedDate() {
        Log.d(TAG, "TranslationModel: get modifiedDate: " + mModifiedDate);
        return mModifiedDate;
    }

    public void setModifiedDate(String newDate) {
            this.mModifiedDate = newDate;
    }

    public Boolean getOnQuickList() { return mOnQuickList; }

    public void setOnQuickList(Boolean onQuickList) { this.mOnQuickList = onQuickList; }

    public Boolean getArchived() { return mArchived; }

    public void setArchived(Boolean archived) { this.mArchived = archived; }

    // Compare key that concatenates the firstLangEntry and secondLangEntry
    private String getCompareKey() { return mFirstLanguageEntry + "|" + mSecondLanguageEntry; }

    // Override the equals method so it will not add duplicate entries to the database
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationModel that = (TranslationModel) o;
        return getCompareKey().equals((that.getCompareKey()));
    }

    @NonNull
    @Override
    public String toString() { return getCompareKey(); }

    protected TranslationModel(Parcel parcel) {
        setCategory(parcel.readString());
        setSubCategory(parcel.readString());
        setFirstLanguage(parcel.readString());
        setFirstLanguageEntry(parcel.readString());
        setFirstLanguageEntryRomanized(parcel.readString());
        setFirstLanguageExample(parcel.readString());
        setSecondLanguage(parcel.readString());
        setSecondLanguageEntry(parcel.readString());
        setSecondLanguageEntryRomanized(parcel.readString());
        setSecondLanguageExample(parcel.readString());
        setEntryType(parcel.readString());
        setTense(parcel.readString());
        setLinkEntryIdTenseBase(parcel.readInt());
        setLinkEntryIdTensePast(parcel.readInt());
        setLinkEntryIdTensePresent(parcel.readInt());
        setLinkEntryIdTenseFuture(parcel.readInt());
        setIsPlural(parcel.readBoolean());
        setGender(parcel.readString());
        setFormality(parcel.readString());
        setPercentLearned(parcel.readInt());
        setPercentLearnedModifiedDate(parcel.readString());
        setFailedAttempts(parcel.readInt());
        setMemorized(parcel.readBoolean());
        setNotes(parcel.readString());
        setSummaryNotes(parcel.readString());
        setImage(parcel.readString());
        setAudio(parcel.readString());
        setUserAudio(parcel.readString());
        setUserAudioModifiedDate(parcel.readString());
        setFavorite(parcel.readInt());
        setTags(parcel.readString());
        setModifiedDate(parcel.readString());
        setOnQuickList(parcel.readBoolean());
        setArchived(parcel.readBoolean());
    }

    // Override the hashCode and toString to pull out rows for comparison.
    @Override
    public int hashCode() { return getCompareKey().hashCode(); }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mCategory);
        parcel.writeString(mSubCategory);
        parcel.writeString(mFirstLanguage);
        parcel.writeString(mFirstLanguageEntry);
        parcel.writeString(mFirstLanguageEntryRomanized);
        parcel.writeString(mFirstLanguageExample);
        parcel.writeString(mSecondLanguage);
        parcel.writeString(mSecondLanguageEntry);
        parcel.writeString(mSecondLanguageEntryRomanized);
        parcel.writeString(mSecondLanguageExample);
        parcel.writeString(mEntryType);
        parcel.writeString(mTense);
        parcel.writeInt(mLinkEntryIdTenseBase);
        parcel.writeInt(mLinkEntryIdTensePast);
        parcel.writeInt(mLinkEntryIdTensePresent);
        parcel.writeInt(mLinkEntryIdTenseFuture);
        parcel.writeBoolean(mIsPlural);
        parcel.writeString(mGender);
        parcel.writeString(mFormality);
        parcel.writeInt(mPercentLearned);
        parcel.writeString(mPercentLearnedModifiedDate);
        parcel.writeInt(mFailedAttempts);
        parcel.writeBoolean(mMemorized);
        parcel.writeString(mNotes);
        parcel.writeString(mSummaryNotes);
        parcel.writeString(mImage);
        parcel.writeString(mAudio);
        parcel.writeString(mUserAudio);
        parcel.writeString(mUserAudioModifiedDate);
        parcel.writeInt(mFavorite);
        parcel.writeString(mTags);
        parcel.writeString(mModifiedDate);
        parcel.writeBoolean(mOnQuickList);
        parcel.writeBoolean(mArchived);
    }

    @Override
    public int describeContents() { return 0; }

    // Required creator field for implementing Parcelable
    public static final Creator<TranslationModel> CREATOR = new Creator<TranslationModel>() {
        @Override
        public TranslationModel createFromParcel(Parcel parcel) {
            return new TranslationModel(parcel);
        }

        @Override
        public TranslationModel[] newArray(int size) {
            return new TranslationModel[size];
        }
    };

}
