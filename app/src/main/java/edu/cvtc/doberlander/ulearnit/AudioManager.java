package edu.cvtc.doberlander.ulearnit;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

public class AudioManager {
    TextToSpeech tts;
    private boolean isSpeaking = false;

    public void textToSpeech(Context context, final String language, final String text, UtteranceProgressListener listener) {
        // The below portion breaks it instead of adding a delay.
//        if (isSpeaking) {
//            // The text-to-speech engine is currently speaking, delay the next attempt to run TTS
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    textToSpeech(context, language, text, listener);
//                }
//            }, 1000);
//            return;
//        }

        // Initialize TextToSpeech object
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                int result = 0;
                String lang = language;

                if (status == TextToSpeech.SUCCESS) {

                    if (!lang.equals("")) {
                        lang = lang.toUpperCase();
                    } else {
                        result = TextToSpeech.LANG_MISSING_DATA;
                    }

                    switch (lang) {
                        case "CHINESE_SIMPLIFIED":
                            result = tts.setLanguage(Locale.SIMPLIFIED_CHINESE);
                            break;
                        case "ENGLISH":
                            result = tts.setLanguage(Locale.ENGLISH); // change the language here
                            break;
                        case "FRENCH":
                            result = tts.setLanguage(Locale.FRENCH);
                            break;
                        case "GERMAN":
                            result = tts.setLanguage(Locale.GERMAN);
                            break;
                        case "HMONG":
                            result = tts.setLanguage(new Locale("hmn", "LAO"));
                            break;
                        case "ILOCANO":
                            result = tts.setLanguage(new Locale("fil", "PH"));
                            break;
                        case "JAPANESE":
                            result = tts.setLanguage(Locale.JAPANESE);
                            break;
                        case "KOREAN":
                            result = tts.setLanguage(Locale.KOREAN);
                            break;
                        case "SPANISH":
                            result = tts.setLanguage(new Locale("es", "ES"));
                            break;
                        case "TAGALOG":
                            result = tts.setLanguage(new Locale("fil", "PH"));
                            break;

                        default:
                            // If the language is not recognized, use the default locale
                            tts.setLanguage(Locale.getDefault());
                            break;
                    }

                    // If the language is not supported or the language data is not available,
                    // display an error message
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(context, "Language not supported", Toast.LENGTH_SHORT).show();
                    } else {
                        // Speak the text
                        tts.setOnUtteranceProgressListener(listener);

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "messageID");
                        tts.speak(text, TextToSpeech.QUEUE_ADD, params);
                    }
                } else {
                    Toast.makeText(context, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void isSpeakingFlag() {
        isSpeaking = true;
    }
    public void resetSpeakingFlag() {
        isSpeaking = false;
    }
}

