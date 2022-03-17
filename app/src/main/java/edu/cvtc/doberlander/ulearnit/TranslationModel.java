package edu.cvtc.doberlander.ulearnit;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TranslationModel implements Parcelable {
    // Declare Variables
    private int mId;
    private String mCategory;
    private String mFirstLanguage;
    private String mFirstLanguageWord;
    private String mSecondLanguage;
    private String mSecondLanguageWord;
    private int mFavorite;

    // Default constructor
    public TranslationModel() {
    }

    // Empty Overloaded Constructor with predefined category and favorites set
    public TranslationModel(String category, int favorite) {
        this.mCategory = category;
        this.mFirstLanguage = "";
        this.mFirstLanguageWord = "";
        this.mSecondLanguage = "";
        this.mSecondLanguageWord = "";
        this.mFavorite = favorite;
    }

    // Overloaded Constructor to create entire object, including Id
    public TranslationModel(int id, String category, String firstLanguage, String firstLanguageWord, String secondLanguage, String secondLanguageWord, int favorite) {
        this.mId = id;
        this.mCategory = category;
        this.mFirstLanguage = firstLanguage;
        this.mFirstLanguageWord = firstLanguageWord;
        this.mSecondLanguage = secondLanguage;
        this.mSecondLanguageWord = secondLanguageWord;
        this.mFavorite = favorite;
    }


    // Getters and Setters;
    public int getId() { return mId; }

    public void setId(int id) { this.mId = id; }

    public String getCategory() { return mCategory; }

    public void setCategory(String category) { this.mCategory = category; }

    public String getFirstLanguage() {
        return mFirstLanguage;
    }

    public void setFirstLanguage(String firstLanguage) {
        this.mFirstLanguage = firstLanguage;
    }

    public String getFirstLanguageWord() {
        return mFirstLanguageWord;
    }

    public void setFirstLanguageWord(String firstLanguageWord) {
        this.mFirstLanguageWord = firstLanguageWord;
    }

    public String getSecondLanguage() {
        return mSecondLanguage;
    }

    public void setSecondLanguage(String secondLanguage) {
        this.mSecondLanguage = secondLanguage;
    }

    public String getSecondLanguageWord() {
        return mSecondLanguageWord;
    }

    public void setSecondLanguageWord(String secondLanguageWord) {
        this.mSecondLanguageWord = secondLanguageWord;
    }

    public int getFavorite() {
        return mFavorite;
    }

    public void setFavorite(int favorite) {
        this.mFavorite = favorite;
    }

    // Compare key that concatenates the firstLangWord and secondLangWord
    private String getCompareKey() { return mFirstLanguageWord + "|" + mSecondLanguageWord; }

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
        setFirstLanguage(parcel.readString());
        setFirstLanguageWord(parcel.readString());
        setSecondLanguage(parcel.readString());
        setSecondLanguageWord(parcel.readString());
        setFavorite(parcel.readInt());
    }

    // Override the hashCode and toString to pull out rows for comparison.
    @Override
    public int hashCode() { return getCompareKey().hashCode(); }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mCategory);
        parcel.writeString(mFirstLanguage);
        parcel.writeString(mFirstLanguageWord);
        parcel.writeString(mSecondLanguage);
        parcel.writeString(mSecondLanguageWord);
        parcel.writeInt(mFavorite);
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
