package edu.cvtc.doberlander.ulearnit;

import androidx.annotation.NonNull;

public class TranslationModel {
    // Declare Variables
    private String firstLanguage;
    private String firstLanguageWord;
    private String secondLanguage;
    private String secondLanguageWord;
    private Boolean favorite;

    // Default constructor
    public TranslationModel() {
    }

    // Overloaded Constructor
    public TranslationModel(String firstLanguage, String firstLanguageWord, String secondLanguage, String secondLanguageWord, boolean favorite) {
        this.firstLanguage = firstLanguage;
        this.firstLanguageWord = firstLanguageWord;
        this.secondLanguage = secondLanguage;
        this.secondLanguageWord = secondLanguageWord;
        this.favorite = favorite;
    }


    // Getters and Setters;
    public String getFirstLanguage() {
        return firstLanguage;
    }

    public void setFirstLanguage(String firstLanguage) {
        this.firstLanguage = firstLanguage;
    }

    public String getFirstLanguageWord() {
        return firstLanguageWord;
    }

    public void setFirstLanguageWord(String firstLanguageWord) {
        this.firstLanguageWord = firstLanguageWord;
    }

    public String getSecondLanguage() {
        return secondLanguage;
    }

    public void setSecondLanguage(String secondLanguage) {
        this.secondLanguage = secondLanguage;
    }

    public String getSecondLanguageWord() {
        return secondLanguageWord;
    }

    public void setSecondLanguageWord(String secondLanguageWord) {
        this.secondLanguageWord = secondLanguageWord;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @NonNull
    @Override
    public String toString() {
        return ("\nFirst Language: " + this.getFirstLanguage() +
                "\nFirst Language Word: " + this.getFirstLanguageWord() +
                "\nSecond Language: " + this.getSecondLanguage() +
                "\nSecond Language Word: " + this.getSecondLanguageWord() +
                "\nFavorite: " + this.getFavorite()) + "\n";
    }
}
