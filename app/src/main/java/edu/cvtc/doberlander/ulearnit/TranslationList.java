package edu.cvtc.doberlander.ulearnit;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TranslationList {
    private static final String TAG = "CategoryActivity";

    // PreDefined Translation Lists
    private static final String[] English_Greetings = {"Good Morning", "Good Afternoon", "Good Night", "How are you?"};
    private static final String[] Tagalog_Greetings = {"Magandang Umaga", "Magandang Hapon", "Magandang Gabi", "Kumusta po kayo?"};

    private static final String[] English_Numbers = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    private static final String[] Tagalog_Numbers = {"Isa", "Dalawa", "Tatlo", "Apat", "Lima", "Anim", "Pito", "Walo", "Siyam", "Sampu"};

    // Greetings List
    public LinkedList<TranslationModel> GetTranslations(String category) {
        // Set translation languages
        String firstLang = "English";
        String secondLang = "Tagalog";

        String[] firstLangWords = new String[0];
        String[] secondLangWords = new String[0];

        // Create the Translation list of translation objects
        LinkedList<TranslationModel> translationList = new LinkedList<>();

        // Fill the translation list with the appropriate category
        switch(category) {
            case "Greetings":
                // Create the Greetings list of translation objects
                firstLangWords = English_Greetings;
                secondLangWords = Tagalog_Greetings;
                break;
            case "Numbers":
                firstLangWords = English_Numbers;
                secondLangWords = Tagalog_Numbers;
                break;
            default:
                break;
        }

        // Create List
        for (int i = 0; i < firstLangWords.length; i++) {
            // Create translation entry object
            TranslationModel translation = new TranslationModel();
            // Populate the translation object
            translation.setFirstLanguage(firstLang);
            translation.setFirstLanguageWord(firstLangWords[i]);
            translation.setSecondLanguage(secondLang);
            translation.setSecondLanguageWord(secondLangWords[i]);
            translation.setFavorite(false);

            // add the translation to the greetings list
            translationList.add(translation);
        }

        // Return the list of translations from the submitted category.
        return translationList;


    }


}
