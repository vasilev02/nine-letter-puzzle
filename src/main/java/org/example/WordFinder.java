package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * WordFinder is a class which is used to find all 9 letters words which after reducing them with single character are
 * also valid until we end with either I or A letters.
 * <br>
 * <a href="">Link to GitHub project</a>
 * @author Valentin Vasilev
 * @version 1.0
 */
public class WordFinder {

    private static final String URL = "https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt";

    /**
     * Main method that triggers the word loading and filtering.
     * @param args Console arguments.
     */
    public static void main(String[] args) {
        Set<String> wordList = loadWordsFromURL(URL);

        long startTime = System.currentTimeMillis();

        Set<String> nineLetterWords = new HashSet<>();

        for (String word : wordList) {
            if (word.length() == 9 && (word.contains("I") || word.contains("A"))) {
                nineLetterWords.add(word);
            }
        }

        Set<String> validNineLetterWords = new HashSet<>();
        for (String word : nineLetterWords) {
            if (validateWord(word, wordList)) {
                validNineLetterWords.add(word);
            }
        }

        System.out.println("Total words count: " + validNineLetterWords.size());

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Elapsed time: " + elapsedTime + " milliseconds");
    }

    /**
     * Method is used to load all words from the given url.
     * @param url The url from where we are going to load the words.
     * @return Set of strings representing the loaded words.
     */
    private static Set<String> loadWordsFromURL(String url) {
        Set<String> wordList = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordList.add(line.trim());
            }
        } catch (IOException e) {
            throw new IllegalStateException("There was problem with the provided data source: " + e.getMessage(), e);
        }
        return wordList;
    }

    /**
     * Recursive method that checks whether a word is valid according to the given conditions.
     * @param word The word we are going to validate.
     * @param wordList Set of words that holds all english words.
     * @return True if the word match the criteria, false otherwise.
     */
    private static boolean validateWord(String word, Set<String> wordList) {
        for (int i = 0; i < word.length(); i++) {
            String shortenedWord = word.substring(0, i) + word.substring(i + 1);

            if (shortenedWord.equals("I") || shortenedWord.equals("A")) {
                return true;
            }
            if (wordList.contains(shortenedWord) && validateWord(shortenedWord, wordList)) {
                return true;
            }
        }
        return false;
    }
}

