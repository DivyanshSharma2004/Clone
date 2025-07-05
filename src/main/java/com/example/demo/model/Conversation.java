/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  com.example.demo.model;

/**
 *
 * @author divme
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "conversation")
@Data
public class Conversation {

    private Participant[] participants;
    private Message[] messages;
    private String title;
    private boolean is_still_participant;
    private String thread_path;
    private Object[] magic_words;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    private String folderName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    private Boolean boolAttributesProcessed = false;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    private Boolean boolStatsProcessed = false;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    private HashMap<String, HashSet<Message>> attributesMessageMap;//MAP TO STORE MESSAGES DIVIDED BY ATTRIBUTES.
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    //adding a category no attributes for messages that dont contain attributes and //todo: add emojis as an attribute
    private String[] AttributeKeys = {
            "emojis", "contextual", "pharmacologyTerms", "optometryTerms", "physicsTerms", "bakingTerms", "aiTerms", "obstetricsAndGynecologyTerms", "fantasyTerms", "agricultureTerms", "comedyTerms", "geneticsTerms", "specialEducationTerms", "dataScienceTerms", "museumStudiesTerms", "immunologyTerms", "wearableTechnologyTerms", "games", "machineLearningTerms", "lawTerms", "parentingTerms", "realEstateTerms", "selfImprovementTerms", "newsTerms", "nursingTerms", "fishingTerms", "microbiologyTerms", "cookingTerms", "adventureTerms", "spaceExplorationTerms", "cybersecurityTerms", "socialMedia", "orthopedicsTerms", "campingTerms", "toxicologyTerms", "productivityTerms", "hikingTerms", "pharmacyTerms", "historyTerms", "cloudComputingTerms", "householdTerms", "paleontologyTerms", "scienceFictionTerms", "fashionTerms", "arTerms", "gossipTerms", "architectureTerms", "businessTerms", "teacherTrainingTerms", "colors", "documentaryTerms", "mathTerms", "psychologyTerms", "anatomyTerms", "economyTerms", "spaceTerms", "horrorTerms", "geologyTerms", "dancingTerms", "vrTerms", "adultEducationTerms", "stockMarketTerms", "forestryTerms", "educationTerms", "philosophyTerms", "chemistryTerms", "ecommerceTerms", "literacyTerms", "clothingTerms", "mythologyTerms", "podcastTerms", "photographyTerms", "people", "educationalAssessmentTerms", "wildlifeTerms", "weatherConditions", "renewableEnergyTerms", "places", "theaterTerms", "oceanographyTerms", "pathologyTerms", "travelTerms", "entertainmentTerms", "mindfulnessTerms", "archaeologyTerms", "gardeningTerms", "fisheriesTerms", "attributes", "basicQuestions", "gamesTerms", "relationshipTerms", "feelings", "artTerms", "environmentTerms", "oncologyTerms", "libraryScienceTerms", "theologyTerms", "musicTerms", "sleepTerms", "curriculumDevelopmentTerms", "bodyParts", "skiingTerms", "religionTerms", "videographyTerms", "psychiatryTerms", "beautyTerms", "natureTerms", "animalTerms", "friendTerms", "astronomyTerms", "nutritionTerms", "technologyTerms", "socialMediaTerms", "locationTerms", "marketingTerms", "neurologyTerms", "languageLearningTerms", "transportationTerms", "biomedicalEngineering", "surgeryTerms", "academicResearchTerms", "workTerms", "mobileTechnologyTerms", "shortResponses", "sportTerms", "gerontologyTerms", "bankingTerms", "publicHealthTerms", "climateChangeTerms", "innovationTerms", "petTerms", "blockchainTerms", "sociologyTerms", "psychotherapyTerms", "cultureTerms", "environmentalScienceTerms", "dramaTerms", "educationalPsychologyTerms", "ethicsTerms", "schoolSafetyTerms", "veterinaryMedicineTerms", "educationalTechnologyTerms", "socialWorkTerms", "earlyChildhoodEducationTerms", "politicsTerms", "educationPolicyTerms", "romanceTerms", "biotechnologyTerms", "languageTerms", "financeTerms", "carrerTerms", "distanceLearningTerms", "holidayTerms", "cryptocurrencyTerms", "scienceTerms", "anthropologyTerms", "higherEducationTerms", "emergencyMedicineTerms", "vehicleTerms", "biologyTerms", "boatingTerms", "plantTerms", "crimeTerms", "craftsTerms", "schoolAdministrationTerms", "shoppingTerms", "insuranceTerms", "healthTerms", "foodTerms", "iotTerms", "mysteryTerms", "fitnessTerms", "neuroscienceTerms", "roboticsTerms", "meteorologyTerms", "cardiologyTerms", "pediatricsTerms", "literatureTerms", "molecularBiologyTerms", "hobbyTerms", "exercisesTerms", "sustainabilityTerms", "time", "surfingTerms", "dentistryTerms", "physiologyTerms"
    };
    /**
     * Retrieves the statistics of attributes and their associated message counts.
     *
     * This method iterates through the `attributesMessageMap`, which is assumed to contain
     * a mapping of attribute names (String) to lists of `Message` objects (ArrayList<Message>).
     * It calculates the number of messages associated with each attribute and stores the result
     * in a new `HashMap`, where the key is the attribute name and the value is the count of messages.
     *
     * @return A `HashMap<String, Integer>` containing the attribute names as keys and their
     *         respective message counts as values.
     */
    public HashMap<String,Integer> getAttributeStats(){
        HashMap<String,Integer> attributeStatsMap= new HashMap<>();
        for (Map.Entry<String, HashSet<Message>> entry : attributesMessageMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue().size();
            attributeStatsMap.put(key,value);
        }
        return attributeStatsMap;
    }
    /**
     * Retrieves the statistics of attributes and their associated message counts.
     *
     * This method iterates through the `attributesMessageMap`, which is assumed to contain
     * a mapping of attribute names (String) to lists of `Message` objects (ArrayList<Message>).
     * It calculates the number of messages associated with each attribute and stores the result
     * in a new `HashMap`, where the key is the attribute name and the value is the count of messages.
     *
     * @return A `HashMap<String, Integer>` containing the attribute names as keys and their
     *         respective message counts as values.
     */
    public HashMap<String, Double> getAttributePercentageStats() {
        HashMap<String, Double> attributeStatsMap = new HashMap<>();
        // Ensure messages.length is valid before the loop
        if (messages == null || messages.length == 0) {
            return attributeStatsMap; // Return an empty map if no messages
        }
        // Store messages.length outside the loop to avoid redundant access
        double totalMessages = messages.length;

        for (Map.Entry<String, HashSet<Message>> entry : attributesMessageMap.entrySet()) {
            String key = entry.getKey();
            int messageCount = entry.getValue().size(); // Renamed to be more descriptive
            double percentage = 0.0;
            if (messageCount != 0) {
                percentage = (messageCount / totalMessages)*100;  // Avoid repeated division
            }
            attributeStatsMap.put(key, percentage);
        }
        return attributeStatsMap;
    }
    /**
     * Adds a Message object to the attribute lists in the attributesMessageMap.
     *
     * This method iterates through each attribute in the provided HashSet of strings
     * and adds the given Message object to the corresponding list of messages in the
     * attributesMessageMap. If the attribute already exists in the map, the message
     * will be overwritten but its using reference instead of value so nothing much will be overwritten
     *
     * @param message The Message object to be added to the map's lists.
     * @param attributeSet The set of attributes to which the message should be added.
     *        Each attribute corresponds to a key in the attributesMessageMap.
     *
     * @throws NullPointerException If either the attributesMessageMap or any list
     *         in the map is null, or if attributeSet is null.
     */
    public void addMessageToAttributesMap(Message message, HashSet<String> attributeSet) {
        if (attributeSet != null) {
            for (String attribute : attributeSet) {
                // Ensure the attribute has a valid set to avoid NPE
                attributesMessageMap.putIfAbsent(attribute, new HashSet<>());
                attributesMessageMap.get(attribute).add(message);
            }
        } else {
            // Ensure the "noAttribute" key has a valid set
            attributesMessageMap.putIfAbsent("noAttribute", new HashSet<>());
            attributesMessageMap.get("noAttribute").add(message);
        }
    }
    /**
     * initializes the attributesMessageMap in conversation object, called in ConversationStatsService in the processAttributes method in ConversationStatsService
     *
     * This method checks if attributesMessageMap is initialized if not adds the keys
     * from AttributeKeys and creates an empty Message arraylist with it for each key
     */
    public void initializeAttributesMap(){
        if(attributesMessageMap==null){
            attributesMessageMap = new HashMap<>(); // Initialize the map if it's null
            for (String key : AttributeKeys) {
                attributesMessageMap.put(key, new HashSet<Message>());
            }
        }
    }

    /**
     * initializes the attributesMessageMap in conversation object, called in ConversationStatsService in the processAttributes method in ConversationStatsService
     *
     * This method checks if attributesMessageMap is initialized if not adds the keys
     * from AttributeKeys and creates an empty Message arraylist with it for each key
     */
    public ArrayList<Message> getMessagesByAttributes(HashSet<String> desiredAttributeSet,HashSet<String> unDesiredAttributeSet){
        ArrayList<Message> messageArrayList = new ArrayList<>();
        String smallestKey = null;
        int smallestSize = Integer.MAX_VALUE;
        // Iterate through the provided desiredAttributeSet (the keys to check)
        for (String attribute : desiredAttributeSet) {
            // Check if the attribute exists in the attributesMessageMap
            if (attributesMessageMap.containsKey(attribute)) {
                // Get the number of messages for that attribute (size of associated HashSet)
                int value = attributesMessageMap.get(attribute).size();
                // Check if this attribute has the smallest value
                if (value < smallestSize) {
                    smallestSize = value;
                    smallestKey = attribute;
                }
            }
        }
            HashSet<Message> smallestAttributeSet = attributesMessageMap.get(smallestKey);
            for (Message message : smallestAttributeSet) {
                //check if the message contains all the attributes
                if(message.areAttributesPresent(desiredAttributeSet,unDesiredAttributeSet)){
                    messageArrayList.add(message);
                }
            }
            return messageArrayList;
    }//TODO: for the giggles throw custom made error which just throws and error on the front end of the client
}

