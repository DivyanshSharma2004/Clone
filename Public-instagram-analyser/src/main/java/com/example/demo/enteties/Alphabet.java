package com.example.demo.enteties;

import com.example.demo.model.Participant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Alphabet {
    //you could create a hashmap for each person
    //have a sort of person to hashmap.
    //string:
    //i forget that i dont need to send over all the hashmaps just the ones the user requests.
    // should i have it soi that i only send out 1 hashmap or one yeah one.
    // so  a english words hashmap and an english waste hashmap.



    //hashmap that has char to arraylist of letter obj
    // i wonder can i use hashset????

    //method to add char
    //method to add word onto char
    static String jsonData;
    //arraylist to hold all the participants, for each participant you have 1 subsequent hashmap that maps characters to their own hasmap that contains string word to a word obj, a word obj contains all the dayTimes the word has appeared for graphing purposes.
    HashMap<String,HashMap<Character, HashMap<String, Word>>> realEnglishWords = new HashMap<>();
    HashMap<String,HashMap<Character, HashMap<String, Word>>> fakeEnglishWords = new HashMap<>();
    String inputFilePath = "com//example//demo//JsonFiles//OrganisedDictsHashSet";
    HashSet<String> wordsSet;
    public Alphabet(Participant[] participants){
        if (jsonData == null) {
            try {
                jsonData = new String(Files.readAllBytes(Paths.get(inputFilePath)));
                ObjectMapper objectMapper = new ObjectMapper();
                // Deserializing JSON into HashSet<String>
                 wordsSet = objectMapper.readValue(jsonData, new TypeReference<HashSet<String>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method for creating hashmaps inside arraylists for each partcipant
    public void createParticipantHashMaps(Participant[] participants){
        for(Participant i : participants){
            String name = i.getName();
            // Create a new HashMap for each participant
            HashMap<Character, HashMap<String, Word>> participantsMap = new HashMap<>();
            // Add the participant's map to the lists
            realEnglishWords.put(name,participantsMap);
            fakeEnglishWords.put(name,participantsMap);
        }
    }

    //written here is the logic of writting down a word but not the logic of writting down the word top the correct corresponding hashmap
    // Add a word to the map
    public void addWord(String name,String word, long epochTime){
        char ch = Character.toLowerCase(word.charAt(0));  // gets first letter of word
        if(realEnglishWords.get(name).get(ch).get(word)!=null){//returns null if doesnt exist in the real english word hashmap
            realEnglishWords.get(name).get(ch).get(word).addDateTime(epochTime);
        }else if(fakeEnglishWords.get(name).get(ch).get(word)!=null){//returns null if it doesnt exist in the fake hashmap
            fakeEnglishWords.get(name).get(ch).get(word).addDateTime(epochTime);
        }else if(wordsSet.contains(word)){//if the dictionary contains the word then add it to the real english word hashmap and if it doesnt fianlly add it to the fake
            Word wordObj = new Word(word,epochTime);
            realEnglishWords.get(name).get(ch).put(word,wordObj);
        }else{
            Word wordObj = new Word(word,epochTime);
            fakeEnglishWords.get(name).get(ch).put(word,wordObj);
        }
    }
   //no hmmm its creating the data time and stuff but um to save size we might just be intreasted in sending back the hashmap of words and the numbercount no date times.
    //so simply gets hashmap loops through it gets the counts and sends back a char:word:int
    //returns 1 real words map by person //TO DO: look and see if thyme leaf might be better
//    public HashMap<Character, HashMap<String, Word>> getRealWordsByPerson(String name) {
//        realEnglishWords.get(name);
//        return realEnglishWords.get(name);
//    }
    public HashMap<Character, HashMap<String, Integer>> getRealWordsByPerson(String name) {
        // Retrieve the realEnglishWords map for the given person
        HashMap<Character, HashMap<String, Word>> wordsByPerson = realEnglishWords.get(name);

        // Create a new HashMap to store the transformed data
        HashMap<Character, HashMap<String, Integer>> result = new HashMap<>();

        if (wordsByPerson != null) {
            // Loop through each character in the outer HashMap
            //entryset to allow you to go through it with 1 varible instead of having to do values and keys seperate
            for (Map.Entry<Character, HashMap<String, Word>> entry : wordsByPerson.entrySet()) {
                Character letter = entry.getKey();
                HashMap<String, Word> wordsMap = entry.getValue();

                // Create a new inner HashMap for the counts
                HashMap<String, Integer> countMap = new HashMap<>();

                // Loop through each word in the inner HashMap
                for (Map.Entry<String, Word> wordEntry : wordsMap.entrySet()) {
                    String word = wordEntry.getKey();
                    Word wordObj = wordEntry.getValue();

                    // Get the count of the word and store it in the new inner HashMap
                    int count = wordObj.getCount();
                    countMap.put(word, count);
                }

                // Add the inner HashMap to the result under the corresponding character
                result.put(letter, countMap);
            }
        }
        // Return the final result
        return result;
    }
    //realEnglishWords.get() = gets hashmap for person
    //realEnglishWords.get(name).get() = gets hashmap for character
    //realEnglishWords.get(name).get('a').get(word) = gets word obj
    public Word getDateTimesForWord(String name, String word) {
        //would have to  search both hashmaps.
        word.toLowerCase();
        if(realEnglishWords.get(name).get(word.charAt(0)).get(word)!=null){
           return realEnglishWords.get(name).get(word.charAt(0)).get(word);
        }else if(fakeEnglishWords.get(name).get(word.charAt(0)).get(word)!=null){
            return fakeEnglishWords.get(name).get(word.charAt(0)).get(word);
        }else{
            return(new Word());
        }
    }

    //returns all real words map //TO DO: look and see if thyme leaf might be better
    public  HashMap<String,HashMap<Character, HashMap<String, Word>>> getAllRealWords(String word) {
        return realEnglishWords;
    }

    //returns one fake words map //TO DO: look and see if thyme leaf might be better
    public HashMap<Character, HashMap<String, Word>> getFakeWordsByPerson(String name) {
        return fakeEnglishWords.get(name);
    }
    //returns all fake words //TO DO: look and see if thyme leaf might be better
    public  HashMap<String,HashMap<Character, HashMap<String, Word>>> getAllFakeWords(String word) {
        return fakeEnglishWords;
    }

}
//for this function i expect the gui to look like
