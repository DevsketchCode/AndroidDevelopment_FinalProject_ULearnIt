package edu.cvtc.doberlander.ulearnit;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TranslationModel implements Parcelable {
    // Declare Variables
    private int mId;
    private String mCategory;
    private String mSubCategory;
    private String mFirstLanguage;
    private String mFirstLanguageEntry;
    private String mFirstLanguageExample;
    private String mSecondLanguage;
    private String mSecondLanguageEntry;
    private String mSecondLanguageExample;
    private String mEntryType;
    private String mTense;
    private String mSingularOrPlural;
    private String mMasculineOrFeminine;
    private int mPercentLearned;
    private String mNotes;
    private int mFavorite;
    private String mTags;

    // Default constructor
    public TranslationModel() {
    }

    // Empty Overloaded Constructor with predefined category and favorites set
    public TranslationModel(String category, int favorite) {
        this.mCategory = category;
        this.mSubCategory = "";
        this.mFirstLanguage = "";
        this.mFirstLanguageEntry = "";
        this.mFirstLanguageExample = "";
        this.mSecondLanguage = "";
        this.mSecondLanguageEntry = "";
        this.mSecondLanguageExample = "";
        this.mEntryType = "";
        this.mTense = "";
        this.mSingularOrPlural = "";
        this.mMasculineOrFeminine = "";
        this.mPercentLearned = 0;
        this.mNotes = "";
        this.mFavorite = favorite;
        this.mTags = "";
    }

    // Overloaded Constructor to create entire object, including Id
    public TranslationModel(int id, String category, String subCategory, String firstLanguage, String firstLanguageEntry, String firstLanguageExample, String secondLanguage, String secondLanguageEntry, String secondLanguageExample, String entryType, String tense, String singularOrPlural, String masculineOrFeminine, int percentLearned, String notes, int favorite, String tags) {
        this.mId = id;
        this.mCategory = category;
        this.mSubCategory = subCategory;
        this.mFirstLanguage = firstLanguage;
        this.mFirstLanguageEntry = firstLanguageEntry;
        this.mFirstLanguageExample = firstLanguageExample;
        this.mSecondLanguage = secondLanguage;
        this.mSecondLanguageEntry = secondLanguageEntry;
        this.mSecondLanguageExample = secondLanguageExample;
        this.mEntryType = entryType;
        this.mTense = tense;
        this.mSingularOrPlural = singularOrPlural;
        this.mMasculineOrFeminine = masculineOrFeminine;
        this.mPercentLearned = percentLearned;
        this.mNotes = notes;
        this.mFavorite = favorite;
        this.mTags = tags;
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

    public String getSecondLanguageExample() { return mSecondLanguageExample; }

    public void setSecondLanguageExample(String secondLanguageExample) {
        this.mSecondLanguageExample = secondLanguageExample;
    }

    public String getEntryType() { return mEntryType; }

    public void setEntryType(String entryType) { this.mEntryType = entryType; }

    public String getTense() { return mTense; }

    public void setTense(String tense) { this.mTense = tense; }

    public String getSingularOrPlural() { return mSingularOrPlural; }
    public void setSingularOrPlural(String singularOrPlural) { this.mSingularOrPlural = singularOrPlural; }

    public String getMasculineOrFeminine() { return mMasculineOrFeminine; }

    public void setMasculineOrFeminine(String masculineOrFeminine) { this.mMasculineOrFeminine = masculineOrFeminine; }

    public int getPercentLearned() { return mPercentLearned; }

    public void setPercentLearned(int percentLearned) { this.mPercentLearned = percentLearned; }

    public String getNotes() { return mNotes; }

    public void setNotes(String notes) { this.mNotes = notes; }

    public int getFavorite() {
        return mFavorite;
    }

    public void setFavorite(int favorite) {
        this.mFavorite = favorite;
    }

    public String getTags() { return mTags; }

    public void setTags(String tags) { this.mTags = tags; }

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
        setFirstLanguageExample(parcel.readString());
        setSecondLanguage(parcel.readString());
        setSecondLanguageEntry(parcel.readString());
        setSecondLanguageExample(parcel.readString());
        setEntryType(parcel.readString());
        setTense(parcel.readString());
        setSingularOrPlural(parcel.readString());
        setMasculineOrFeminine(parcel.readString());
        setPercentLearned(parcel.readInt());
        setNotes(parcel.readString());
        setFavorite(parcel.readInt());
        setTags(parcel.readString());
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
        parcel.writeString(mFirstLanguageExample);
        parcel.writeString(mSecondLanguage);
        parcel.writeString(mSecondLanguageEntry);
        parcel.writeString(mSecondLanguageExample);
        parcel.writeString(mEntryType);
        parcel.writeString(mTense);
        parcel.writeString(mSingularOrPlural);
        parcel.writeString(mMasculineOrFeminine);
        parcel.writeInt(mPercentLearned);
        parcel.writeString(mNotes);
        parcel.writeInt(mFavorite);
        parcel.writeString(mTags);
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
