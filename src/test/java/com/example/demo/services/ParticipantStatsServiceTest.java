package com.example.demo.services;

import com.example.demo.enteties.ParticipantStats;

import java.util.HashMap;
import java.util.Map;

public class ParticipantStatsServiceTest {

    /**
     * Merges two maps of ParticipantStats. If a participant exists in both maps, their stats are combined.
     *
     * @param map1 The first map of participant stats.
     * @param map2 The second map of participant stats to merge.
     * @return A merged map with combined stats.
     */


//    public static Map<String, ParticipantStats> mergeParticipantStats(Map<String, ParticipantStats> map1, Map<String, ParticipantStats> map2) {
//        Map<String, ParticipantStats> mergedMap = new HashMap<>(map1);
//
//        for (Map.Entry<String, ParticipantStats> entry : map2.entrySet()) {
//            String participantName = entry.getKey();
//            ParticipantStats statsToMerge = entry.getValue();
//
//            // Merge stats for the same participant
//            mergedMap.compute(participantName, (name, existingStats) -> {
//                if (existingStats == null) {
//                    return new ParticipantStats(name); // Create a new entry if it doesn't exist
//                } else {
//                    // Combine the stats
//                    existingStats.addLaugh(statsToMerge.getLaughCount());
//                    existingStats.addMessages(statsToMerge.getTotalMessages());
//                    return existingStats;
//                }
//            });
//        }
//
//        return mergedMap;
//    }
}
