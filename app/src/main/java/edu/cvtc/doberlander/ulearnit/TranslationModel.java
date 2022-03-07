package edu.cvtc.doberlander.ulearnit;

import androidx.annotation.NonNull;

public class TranslationModel {
    // Declare Variables
    private String category;
    private String firstLanguage;
    private String firstLanguageWord;
    private String secondLanguage;
    private String secondLanguageWord;
    private int favorite;

    // Default constructor
    public TranslationModel() {
    }

    // Overloaded Constructor
    public TranslationModel(String category, String firstLanguage, String firstLanguageWord, String secondLanguage, String secondLanguageWord, int favorite) {
        this.category = category;
        this.firstLanguage = firstLanguage;
        this.firstLanguageWord = firstLanguageWord;
        this.secondLanguage = secondLanguage;
        this.secondLanguageWord = secondLanguageWord;
        this.favorite = favorite;
    }


    // Getters and Setters;
    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

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

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    @NonNull
    @Override
    public String toString() {
        return ("\nCategory: " + this.getCategory() +
                "\nFirst Language: " + this.getFirstLanguage() +
                "\nFirst Language Word: " + this.getFirstLanguageWord() +
                "\nSecond Language: " + this.getSecondLanguage() +
                "\nSecond Language Word: " + this.getSecondLanguageWord() +
                "\nFavorite: " + this.getFavorite()) + "\n";
    }
}
