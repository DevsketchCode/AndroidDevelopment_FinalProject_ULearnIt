package edu.cvtc.doberlander.ulearnit;

import java.util.LinkedList;

public class TranslationList {

    // PreDefined Translation Lists
    // The lists are used when filling a new database with the preset translations
    private static final String[] English_Greetings_ToTagalog = {"Good Morning", "Good Afternoon", "Good Night", "How are you?"};
    private static final String[] Tagalog_Greetings = {"Magandang Umaga", "Magandang Hapon", "Magandang Gabi", "Kumusta po kayo?"};

    private static final String[] English_Numbers_ToTagalog = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    private static final String[] Tagalog_Numbers = {"Isa", "Dalawa", "Tatlo", "Apat", "Lima", "Anim", "Pito", "Walo", "Siyam", "Sampu"};

    private static final String[] English_Food_ToTagalog = {"Eat", "Food", "Rice", "Hungry", "Fish", "Chicken", "Pork"};
    private static final String[] Tagalog_Food = {"Kumain", "Pagkain", "Kanin", "Gutom", "Isda", "Manok", "Baboy"};

    private static final String[] English_People_ToTagalog = {"Family", "Mother", "Father", "Brother", "Sister", "Friend"};
    private static final String[] Tagalog_People = {"Pamilya", "Nanay", "Tatay", "Kuya", "Ate", "Kaibigan"};

    // The lists are used when filling a new database with the preset translations
    private static final String[] English_Greetings_ToKorean = {"Hello", "How are you?"};
    private static final String[] Korean_Greetings = {"안녕하세요", "어떻게 지내세요?"};

    private static final String[] English_Numbers_ToKorean = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    private static final String[] Korean_Numbers = {"일 / 하나", "이 / 둘", "삼 / 셋", "사 / 넷", "오 / 다섯", "육 / 여섯", "칠 / 일곱", "팔 / 여덟", "구 / 아홉", "십 / 열"};

    private static final String[] English_Food_ToKorean = {};
    private static final String[] Korean_Food = {};

    private static final String[] English_People_ToKorean = {};
    private static final String[] Korean_People = {};

    // Create public variable that is used to add translation objects as favorites - MUST BE STATIC
    public static final LinkedList<TranslationModel> Favorites_List = new LinkedList<>();

    // Create the Translation list of translation objects
    private final LinkedList<TranslationModel> translationList = new LinkedList<>();

    // Greetings List
    public LinkedList<TranslationModel> GetTranslations(String language, String category) {
        // Set translation languages for initial db creation
        String firstLang = "English";
        String secondLang = "Tagalog";

        String[] firstLangWords = new String[0];
        String[] secondLangWords = new String[0];

        // Only get the listings from the specified language
        if (language.equals("Tagalog")) {
            secondLang = "Tagalog";

            // Fill the translation list with the appropriate category
            switch (category) {
                case "Greetings":
                    // Create the Greetings list of translation objects
                    firstLangWords = English_Greetings_ToTagalog;
                    secondLangWords = Tagalog_Greetings;
                    break;
                case "Numbers":
                    firstLangWords = English_Numbers_ToTagalog;
                    secondLangWords = Tagalog_Numbers;
                    break;
                case "Food":
                    firstLangWords = English_Food_ToTagalog;
                    secondLangWords = Tagalog_Food;
                    break;
                case "People":
                    firstLangWords = English_People_ToTagalog;
                    secondLangWords = Tagalog_People;
                    break;
                case "Favorites":
                    // Populate the list with the selected favorites
                    translationList.addAll(Favorites_List);

                    // Return the favorites list
                    return translationList;
                default:
            }
        } else if (language.equals("Korean")) {
            secondLang = "Korean";

            // Fill the translation list with the appropriate category
            switch (category) {
                case "Greetings":
                    // Create the Greetings list of translation objects
                    firstLangWords = English_Greetings_ToKorean;
                    secondLangWords = Korean_Greetings;
                    break;
                case "Numbers":
                    firstLangWords = English_Numbers_ToKorean;
                    secondLangWords = Korean_Numbers;
                    break;
                case "Food":
                    firstLangWords = English_Food_ToKorean;
                    secondLangWords = Korean_Food;
                    break;
                case "People":
                    firstLangWords = English_People_ToKorean;
                    secondLangWords = Korean_People;
                    break;
                case "Favorites":
                    // Populate the list with the selected favorites
                    translationList.addAll(Favorites_List);

                    // Return the favorites list
                    return translationList;
                default:
            }
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
