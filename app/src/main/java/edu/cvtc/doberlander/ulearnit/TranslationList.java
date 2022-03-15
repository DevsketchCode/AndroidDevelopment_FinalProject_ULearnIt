package edu.cvtc.doberlander.ulearnit;

import java.util.LinkedList;

public class TranslationList {

    // PreDefined Translation Lists
    // The lists are used when filling a new database with the preset translations
    private static final String[] English_Greetings = {"Good Morning", "Good Afternoon", "Good Night", "How are you?"};
    private static final String[] Tagalog_Greetings = {"Magandang Umaga", "Magandang Hapon", "Magandang Gabi", "Kumusta po kayo?"};

    private static final String[] English_Numbers = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    private static final String[] Tagalog_Numbers = {"Isa", "Dalawa", "Tatlo", "Apat", "Lima", "Anim", "Pito", "Walo", "Siyam", "Sampu"};

    // Create public variable that is used to add translation objects as favorites - MUST BE STATIC
    public static final LinkedList<TranslationModel> Favorites_List = new LinkedList<>();

    // Create the Translation list of translation objects
    private final LinkedList<TranslationModel> translationList = new LinkedList<>();

    // Greetings List
    public LinkedList<TranslationModel> GetTranslations(String category) {
        // Set translation languages
        String firstLang = "English";
        String secondLang = "Tagalog";

        String[] firstLangWords = new String[0];
        String[] secondLangWords = new String[0];


        // Fill the translation list with the appropriate category
        switch (category) {
            case "Greetings":
                // Create the Greetings list of translation objects
                firstLangWords = English_Greetings;
                secondLangWords = Tagalog_Greetings;
                break;
            case "Numbers":
                firstLangWords = English_Numbers;
                secondLangWords = Tagalog_Numbers;
                break;
            case "Favorites":
                // Populate the list with the selected favorites
                translationList.addAll(Favorites_List);

                // Return the favorites list
                return translationList;
            default:
        }

        // Create List
        for (int i = 0; i < firstLangWords.length; i++) {
            // Create translation entry object
            TranslationModel translation = new TranslationModel();
            // Populate the translation object
            translation.setCategory(category);
            translation.setFirstLanguage(firstLang);
            translation.setFirstLanguageWord(firstLangWords[i]);
            translation.setSecondLanguage(secondLang);
            translation.setSecondLanguageWord(secondLangWords[i]);
            translation.setFavorite(0);

            // add the translation to the greetings list
            translationList.add(translation);
        }

        // Return the list of translations from the submitted category.
        return translationList;
    }
}
