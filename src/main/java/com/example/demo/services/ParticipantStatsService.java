package com.example.demo.services;

import com.example.demo.model.ParticipantStats;
import com.example.demo.model.Conversation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Service
public class ParticipantStatsService {
    //first load in the arraylist full of conversations.
    //in the controller if 1 conversation simply run the get conversations arraylist and then pass it on to the method here.

    //method takes in an arraylist of conversations, outputs? should i use generic to hold both values? to hold a list of hmmm should i even return that? yah sure why not you can use a sort of one for the classs and if user runs the retrieve then yeah, pros: performance
    //then what of the other people if the user wants to run the count english words fro everyone?
    //for each participant create a what? generic arraylist? holds int count and a words list. say hasset? sure hashset works. oh wait we needed instances the word appeared as well so i guess use the hashmap so you can count. and have 2 seperate maps and when you want the count simply run size on the hashmap.
    //1 hahsmap for the real words and the other for not real words. also load the word the dictionary word not in a hashmap but as a hashset. as O(1)
    //desearalise or hardcode?
    //create a new json strucutre. so have it so that you have alphabet on the out most? talk to chatgpt if thats the quicketest yes ti is. or maybe do it according to the wway the words are split as for example letter e words might exist mroe than x words yk. as in words that satat with x.
    // then after alophabet i could just have an arraylist of word objects.
    /*
    * word obj:
    * sTRING WORD, int count, arraylist of data time objects epoch, is it necessary? can be for plotting. yeah sure why not plus if i wanna send i can just send the values not the whole obj ygm.
    * //so alphabet entity has char and has arraylist of word obj
    * */

    /**
     * Calculates and merges the laugh counts for all participants in a list of conversations.
     *
     * @param conversations List of conversations to process.
     * @return A map where the key is the participant's name and the value is the laugh count and other statistics.
     */
    public Map<String, ParticipantStats> participantsConversationsLaughCount(ArrayList<Conversation> conversations) {
        Map<String, ParticipantStats> mergedMap = new HashMap<>();
        for (Conversation conversation : conversations) {
            //mergedMap = mergeParticipantStats(mergedMap, conversation.participantsLaughCount());
        }
        return mergedMap;
    }
    /**
     * Merges two maps of ParticipantStats. If a participant exists in both maps, their stats are combined.
     *
     * @param map1 The first map of participant stats.
     * @param map2 The second map of participant stats to merge.
     * @return A merged map with combined stats.
     */
    public static Map<String, ParticipantStats> mergeParticipantStats(Map<String, ParticipantStats> map1, Map<String, ParticipantStats> map2) {
        Map<String, ParticipantStats> mergedMap = new HashMap<>(map1);

        for (Map.Entry<String, ParticipantStats> entry : map2.entrySet()) {
            String participantName = entry.getKey();
            ParticipantStats statsToMerge = entry.getValue();

            // Merge stats for the same participant
            mergedMap.compute(participantName, (name, existingStats) -> {
                if (existingStats == null) {
                    return new ParticipantStats(name); // Create a new entry if it doesn't exist
                } else {
                    // Combine the stats
                    existingStats.addLaugh(statsToMerge.getLaughCount());
                    existingStats.addMessages(statsToMerge.getTotalMessages());
                    return existingStats;
                }
            });
        }

        return mergedMap;
    }
}
