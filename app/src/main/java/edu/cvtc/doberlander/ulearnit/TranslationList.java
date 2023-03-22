package edu.cvtc.doberlander.ulearnit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class TranslationList {

    // PreDefined Translation Lists
    // The lists are used when filling a new database with the preset translations

    // NOTE: Adding a list here will also need to be called from DBWorker
    private static final String[] English_Greetings_ToTagalog = {"Good Morning", "Good Afternoon", "Good Night", "How are you?"};
    private static final String[] Tagalog_Greetings = {"Magandang Umaga", "Magandang Hapon", "Magandang Gabi", "Kumusta po kayo?"};
    private static final String[] EngToTag_Greetings_EntryTypes = {"Phrase", "Phrase", "Phrase", "Phrase"};

    private static final String[] English_Numbers_ToTagalog = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private static final String[] Tagalog_Numbers = {"Isa", "Dalawa", "Tatlo", "Apat", "Lima", "Anim", "Pito", "Walo", "Siyam", "Sampu", "Labingisa", "Labingdalawa"};
    private static final String[] EngToTag_Numbers_EntryTypes = {"Number", "Number", "Number", "Number", "Number", "Number", "Number", "Number", "Number", "Number", "Number", "Number"};

    private static final String[] English_Food_ToTagalog = {"Eat", "Food", "Rice", "Hungry", "Fish", "Chicken", "Pork"};
    private static final String[] Tagalog_Food = {"Kumain", "Pagkain", "Kanin", "Gutom", "Isda", "Manok", "Baboy"};
    private static final String[] EngToTag_Food_EntryTypes = {"Verb", "Noun", "Noun", "Adjective", "Noun", "Noun", "Noun"};

    private static final String[] English_People_ToTagalog = {"Family", "Mother", "Father", "Brother", "Sister", "Friend"};
    private static final String[] Tagalog_People = {"Pamilya", "Nanay", "Tatay", "Kuya", "Ate", "Kaibigan"};
    private static final String[] EngToTag_People_EntryTypes = {"Noun", "Noun", "Noun", "Noun", "Noun", "Noun"};

    private static final String[] English_Relationship_ToTagalog = {"I Love You"};
    private static final String[] Tagalog_Relationship = {"Mahal Kita"};
    private static final String[] EngToTag_Relationship_EntryTypes = {"Phrase"};

    private static final String[] English_General_ToTagalog = {"More"};
    private static final String[] Tagalog_General = {"Higit Pa"};
    private static final String[] EngToTag_General_EntryTypes = {"Determiner"};

    private static final String[] English_Theocratic_ToTagalog = {"God", "Jehovah", "Jesus Christ"};
    private static final String[] Tagalog_Theocratic = {"Diyos", "Jehova", "Jesu-Kristo"};
    private static final String[] EngToTag_Theocratic_EntryTypes = {"Noun", "Noun", "Noun"};

    // The lists are used when filling a new database with the preset translations
    private static final String[] English_Greetings_ToKorean = {"Hello", "How are you?"};
    private static final String[] Korean_Greetings = {"안녕하세요", "어떻게 지내세요?"};
    private static final String[] EngToKor_Greetings_EntryType = {"Noun", "Phrase"};

    private static final String[] English_Numbers_ToKorean = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "20", "21", "30", "40", "50", "60", "70", "80",
            "90", "100",
    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "20", "21", "30", "40", "50", "60", "70", "80",
            "90", "100", "1000", "10,000", "100,000", "1,000,000", "10,000,000", "100,000,000", "1,000,000,000", "1,000,000,000,000" };
    private static final String[] Korean_Numbers = {"일", "이", "삼", "사", "오", "육", "칠", "팔", "구", "십",
            "십일", "십이", "이십", "이십일", "삼십", "사십", "오십", "육십", "칠십", "팔십",
            "구십", "백",
    "하나", "둘", "셋", "넷", "다섯", "여섯", "일곱", "여덟", "아홉", "열",
            "열 하나", "열둘", "스물", "스물 하나", "서른", "마흔", "쉰", "예순하나", "일흔", "여든",
            "아흔", "백", "천", "만", "십만", "백만", "천만", "일억", "십억", "일조"
    };
    private static final String[] EngToKor_Numbers_EntryType = {"Sino", "Sino", "Sino", "Sino", "Sino", "Sino", "Sino", "Sino", "Sino", "Sino",
            "Sino", "Sino", "Sino", "Sino", "Sino", "Sino", "Sino", "Sino", "Sino", "Sino",
            "Sino", "Sino",
        "Native", "Native", "Native", "Native", "Native", "Native", "Native", "Native", "Native", "Native",
            "Native", "Native", "Native", "Native", "Native", "Native", "Native", "Native", "Native", "Native",
            "Native", "Native", "Native", "Native", "Native", "Native", "Native", "Native", "Native", "Native"};

    private static final String[] English_Food_ToKorean = {"Eat", "Food", "Rice", "Hungry", "Fish", "Chicken", "Pork", "Beef"};
    private static final String[] Korean_Food = {"먹다", "음식", "쌀", "배고픈", "생선", "닭고기", "돼지고기", "쇠고기"};
    private final String[] EngToKor_Food_EntryType = {"Verb", "Noun", "Noun", "Adjective", "Noun", "Noun", "Noun", "Noun"};

    private static final String[] English_People_ToKorean = {"Father", "Friend"};
    private static final String[] Korean_People = {"아버지", "친구"};
    private static final String[] EngToKor_People_EntryType = {"Noun", "Noun"};

    private static final String[] English_Relationship_ToKorean = {"I Love You", "I Miss You"};
    private static final String[] Korean_Relationship = {"사랑해", "보고 싶다"};
    private static final String[] EngToKor_Relationship_EntryType = {"Phrase", "Phrase"};

    private static final String[] English_General_ToKorean = {"More", "Life", "Problems/Hardships", "Research/Study"};
    private static final String[] Korean_General = {"더", "생명", "문제", "연구"};
    private static final String[] EngToKor_General_EntryType = {"Determiner", "Noun", "Noun", "Noun"};

    private static final String[] English_Theocratic_ToKorean = {"God", "Jehovah", "Jesus Christ", "Faith", "Congregation", "Prayer", "Bible", "God's Kingdom", "Everlasting Life", "Prayer", "Satan", "Devil"};
    private static final String[] Korean_Theocratic = {"하나님", "여호와", "예수 그리스도", "믿음", "회중", "기도", "성경", "하느님의 왕국", "영원한 생명", "기도", "사탄", "마귀"};
    private static final String[] EngToKor_Theocratic_EntryType = {"Noun", "Noun", "Noun", "Noun", "Noun", "Noun", "Noun", "Noun", "Noun", "Noun", "Noun", "Noun"};

    // Create public variable that is used to add translation objects as favorites - MUST BE STATIC
    public static final LinkedList<TranslationModel> Favorites_List = new LinkedList<>();

    // Create the Translation list of translation objects
    private final LinkedList<TranslationModel> translationList = new LinkedList<>();

    // Greetings List
    public LinkedList<TranslationModel> GetTranslations(String language, String category) {
        // Set translation languages for initial db creation
        String firstLang = "English";
        String secondLang = "Korean";

        String[] firstLangEntries = new String[0];
        String[] secondLangEntries = new String[0];
        String[] entryTypes = new String[0];
        String[] notes = new String[0];
        String[] tags = new String[0];

        // Only get the listings from the specified language
        if (language.equals("Tagalog")) {
            secondLang = "Tagalog";

            // Fill the translation list with the appropriate category
            switch (category) {
                case "Greetings":
                    // Create the Greetings list of translation objects
                    firstLangEntries = English_Greetings_ToTagalog;
                    secondLangEntries = Tagalog_Greetings;
                    entryTypes = EngToTag_Greetings_EntryTypes;
                    break;
                case "Numbers":
                    firstLangEntries = English_Numbers_ToTagalog;
                    secondLangEntries = Tagalog_Numbers;
                    entryTypes = EngToTag_Numbers_EntryTypes;
                    break;
                case "Food":
                    firstLangEntries = English_Food_ToTagalog;
                    secondLangEntries = Tagalog_Food;
                    entryTypes = EngToTag_Food_EntryTypes;
                    break;
                case "People":
                    firstLangEntries = English_People_ToTagalog;
                    secondLangEntries = Tagalog_People;
                    entryTypes = EngToTag_People_EntryTypes;
                    break;
                case "Relationship":
                    firstLangEntries = English_Relationship_ToTagalog;
                    secondLangEntries = Tagalog_Relationship;
                    entryTypes = EngToTag_Relationship_EntryTypes;
                    break;
                case "General":
                    firstLangEntries = English_General_ToTagalog;
                    secondLangEntries = Tagalog_General;
                    entryTypes = EngToTag_General_EntryTypes;
                    break;
                case "Theocratic":
                    firstLangEntries = English_Theocratic_ToTagalog;
                    secondLangEntries = Tagalog_Theocratic;
                    entryTypes = EngToTag_Theocratic_EntryTypes;
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
                    firstLangEntries = English_Greetings_ToKorean;
                    secondLangEntries = Korean_Greetings;
                    entryTypes = EngToKor_Greetings_EntryType;
                    break;
                case "Numbers":
                    firstLangEntries = English_Numbers_ToKorean;
                    secondLangEntries = Korean_Numbers;
                    entryTypes = EngToKor_Numbers_EntryType;
                    break;
                case "Food":
                    firstLangEntries = English_Food_ToKorean;
                    secondLangEntries = Korean_Food;
                    entryTypes = EngToKor_Food_EntryType;
                    break;
                case "People":
                    firstLangEntries = English_People_ToKorean;
                    secondLangEntries = Korean_People;
                    entryTypes = EngToKor_People_EntryType;
                    break;
                case "Relationship":
                    firstLangEntries = English_Relationship_ToKorean;
                    secondLangEntries = Korean_Relationship;
                    entryTypes = EngToKor_Relationship_EntryType;
                    break;
                case "General":
                    firstLangEntries = English_General_ToKorean;
                    secondLangEntries = Korean_General;
                    entryTypes = EngToKor_General_EntryType;
                    break;
                case "Theocratic":
                    firstLangEntries = English_Theocratic_ToKorean;
                    secondLangEntries = Korean_Theocratic;
                    entryTypes = EngToKor_Theocratic_EntryType;
                    break;
                case "Favorites":
                    // Populate the list with the selected favorites
                    translationList.addAll(Favorites_List);

                    // Return the favorites list
                    return translationList;
                default:
            }
        }

        // Create List (This would be the initial list input into a fresh db)
        for (int i = 0; i < firstLangEntries.length; i++) {
            // Create translation entry object
            TranslationModel translation = new TranslationModel();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault());
            // Populate the translation object
            translation.setCategory(category);
            translation.setSubCategory("");
            translation.setFirstLanguage(firstLang);
            translation.setFirstLanguageEntry(firstLangEntries[i]);
            translation.setFirstLanguageEntryRomanized("");
            translation.setFirstLanguageExample("");
            translation.setSecondLanguage(secondLang);
            translation.setSecondLanguageEntry(secondLangEntries[i]);
            translation.setSecondLanguageEntryRomanized("");
            translation.setSecondLanguageExample("");
            translation.setEntryType(entryTypes[i]);
            translation.setTense("");
            translation.setIsPlural(false);
            translation.setGender("N/A");
            translation.setFormality("");
            translation.setPercentLearned(0);
            translation.setPercentLearnedModifiedDate(sdf.format(new Date()));
            translation.setMemorized(false);
            translation.setNotes("");
            translation.setImage("");
            translation.setAudio("");
            translation.setUserAudio("");
            translation.setUserAudioModifiedDate(sdf.format(new Date()));
            translation.setFavorite(0);
            translation.setTags("");
            translation.setModifiedDate(sdf.format(new Date()));
            translation.setOnQuickList(false);
            translation.setArchived(false);

            // add the translation to the greetings list
            translationList.add(translation);
        }

        // Return the list of translations from the submitted category.
        return translationList;
    }
}
