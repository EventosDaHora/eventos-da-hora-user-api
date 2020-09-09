package br.com.eventosdahora.api.util;

public class StringUtil {

    public static String lowerCase(String phrase) {
        if(phrase == null) {
            return null;
        }

        if(phrase.isEmpty()){
            return phrase;
        }

        return phrase.toLowerCase();
    }

    public static String capitalCase(String phrase) {
        String returnPhrase = "";

        if(phrase == null) {
            return null;
        }

        if(phrase.isEmpty()){
            return phrase;
        }

        String[] words = phrase.replaceAll("\\s+", " ").trim().split(" ");
        for(String w: words){
            if(w.length() <= 2){
                returnPhrase += w + " ";
            }else {
                returnPhrase += w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase();
                returnPhrase += " ";
            }
        }

        return returnPhrase.trim();
    }
}
