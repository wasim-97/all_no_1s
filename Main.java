package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        try {
            long startTime = System.nanoTime();
            File myObj = new File("src/main/resources/moby.txt");
            Scanner myReader = new Scanner(myObj);
            StringBuilder data = new StringBuilder();
            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine());
            }
            myReader.close();
            Set<String> wordsToBeIgnored = new HashSet<>(Arrays.asList(
                    // Articles
                    "a", "an", "the",

                    // Prepositions
                    "about", "above", "across", "after", "against", "along", "among", "around", "as", "at",
                    "before", "behind", "below", "beneath", "beside", "between", "beyond", "but", "by",
                    "despite", "down", "during", "except", "for", "from", "in", "inside", "into", "like",
                    "near", "of", "off", "on", "onto", "out", "outside", "over", "past", "since", "through",
                    "throughout", "till", "to", "toward", "under", "underneath", "until", "up", "upon",
                    "with", "within", "without",

                    // Pronouns
                    "i", "me", "my", "mine", "myself",
                    "you", "your", "yours", "yourself", "yourselves",
                    "he", "him", "his", "himself",
                    "she", "her", "hers", "herself",
                    "it", "its", "itself",
                    "we", "us", "our", "ours", "ourselves",
                    "they", "them", "their", "theirs", "themselves",

                    // Conjunctions
                    "and", "or", "but", "so", "for", "nor", "yet", "although", "because", "since", "unless",
                    "while", "whereas", "whether", "either", "neither", "though", "if", "once", "until", "when",
                    "whenever", "where", "wherever", "as", "than", "that",

                    // Auxiliary/Helping Verbs
                    "is", "am", "are", "was", "were", "be", "being", "been",
                    "have", "has", "had", "having",
                    "do", "does", "did", "doing",

                    // Modal Verbs
                    "can", "could", "shall", "should", "will", "would", "may", "might", "must", "ought",

                    // Common Adverbs & Miscellaneous Words
                    "about", "also", "again", "almost", "already", "always", "any", "anyway", "anywhere",
                    "both", "each", "either", "every", "everyone", "everything", "everywhere", "few",
                    "just", "more", "most", "much", "neither", "never", "not", "now", "only", "other",
                    "same", "some", "sometimes", "somewhere", "such", "than", "then", "there", "therefore",
                    "these", "those", "thus", "very", "well", "where", "whether", "which", "while", "who",
                    "whom", "whose", "why", "will", "with", "within", "without"
            ));

            List<String> words = Arrays.stream(data.toString().toLowerCase().split("\\s+"))
                    .map(word -> word.replaceAll("[^a-z]", "")) // Keep letters
                    .map(word -> word.replaceAll("'s$", "")) // Remove possessive 's
                    .filter(word -> !word.isEmpty() && !wordsToBeIgnored.contains(word))
                    .collect(Collectors.toList());

            Map<String, Long> countOfCharacters = words.stream()
                    .collect(Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()));
            System.out.println("Total word count - "+ words.size());
            System.out.println("Top 5 most frequent words with counts - "+ countOfCharacters.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(5).collect(Collectors.toList()));
            System.out.println("Top 50 alphabetically sorted list of all unique words - "+ words.stream().distinct().sorted().limit(50).collect(Collectors.toList()));
            long endTime = System.nanoTime();
            long durationInSeconds = (endTime - startTime) / 1_000_000_000;
            System.out.println("Processing Time: " + durationInSeconds + "s");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}