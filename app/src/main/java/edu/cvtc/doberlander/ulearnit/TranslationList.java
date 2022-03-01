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

    // Greetings List
    public LinkedList<TranslationModel> GetGreetings() {
        // Create the Greetings list of translation objects
        LinkedList<TranslationModel> greetings = new LinkedList<>();

        // Set translation languages
        String firstLang = "English";
        String secondLang = "Tagalog";

        // Create Greetings List
        for (int i = 0; i < English_Greetings.length; i++) {
            // Create translation entry object
            TranslationModel translation = new TranslationModel();
            // Populate the translation object
            translation.setFirstLanguage(firstLang);
            translation.setFirstLanguageWord(English_Greetings[i]);
            translation.setSecondLanguage(secondLang);
            translation.setSecondLanguageWord(Tagalog_Greetings[i]);
            translation.setFavorite(false);

            // add the translation to the greetings list
            greetings.add(translation);
        }
        return greetings;
    }


}
