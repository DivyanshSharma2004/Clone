package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Attributes {
    @JsonProperty("attributes")
    HashMap<String, HashSet<String>> attributesMap = new HashMap<>();
    HashMap<String,String> emojiMap = new HashMap<String, String>();
    private final ObjectMapper objectMapper = new ObjectMapper();


    // Method to deserialize JSON into the correct structure
    @PostConstruct
    public void init() {
        try {

            // Use ClassPathResource to safely load file inside JAR
            ClassPathResource resource = new ClassPathResource("JsonFiles/attributesList.json");
            InputStream inputStream = resource.getInputStream();

            // Read JSON as a Map
            var jsonData = objectMapper.readValue(inputStream, HashMap.class);
            // Extract data into respective maps
            attributesMap = objectMapper.convertValue(jsonData.get("attributes"),
                    objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, HashSet.class));

            emojiMap = objectMapper.convertValue(jsonData.get("emojis"),
                    objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, String.class));

            System.out.println("✅ Attributes JSON successfully loaded into HashMaps!");
            System.out.println("✅ attributesMAP: "+ attributesMap);
            System.out.println("✅ attributesMAP: "+ emojiMap);
        
        } catch (Exception e) {
            System.err.println("Error reading attributesList.json: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //in this class add the logic for finding and return arraylist by messages containg attributes// takes in ap[arm of the hashmap in service 

    /*
    * maybe we can ddo the design procvess so that 1 attrbutes object contains all the messages object you know.
    * how would that work? participant names and the rest.
    * */



    //you can have the attributes in this way.
    //1.attribute hashmap contains string participant name: arraylist message objects. Pros: when looking for messages with specific attribute this is pretty fast
    //2. option is to just have an arraylist attribute containg all message objects that contain that attribute.
    //

    //create method that create a hashmpap for weach participant
    //the hashmap sjhould be persn name: [hashmap: attribute String, message arraylist containsg messages you know]
    HashMap<String, HashMap<String,ArrayList<Message>>> map = new HashMap<>();
    //participant name, attribute name, messageObject


    //how will this class be claled? controller> service> runs the add attributes to the message object but after that i need it to add it to you know what.
    //maybe right i can create a conversations class that contains you know what along with other sutff like you know the arraylist of attributes the hashmap.
    //or i mean ciontains the attributes object that will then be prompted. maybe i can create the object for
    //hmm add the attrivutes into the conversation object, maybe consider creating the conversations one yk.
    //or i can simply create the hashmap in the you know the service.
    //so attributes hashmap? no attribute object for each conversation use a hashmap. add the attribute to it once ? o maybe use the get attribute see if attribute object null if attribute object null then run the create command and then run the get copmmand.
    //
    public Attributes(){

    }
    //maybe create a class that has arraylist that contains the conversation object covnersatuion, so far we have it as a bean.
    public void createHashMapsForParticipants(Participant[] participants){
        for(Participant participant : participants){
            participant.getName();
        }
    }

    //filler words hashset that the regex tokenize method removes. FIXME: fix for when you want to add questions as an attribute, remove words like which from this list
    private static final HashSet<String> FILLER_WORDS = new HashSet<>(Arrays.asList(
            "the", "is", "and", "or", "but", "a", "an", "of", "in", "on", "at", "for", "with", "to", "as", "it", "this", "that", "by", "are", "be", "was", "were", "am", "i", "you", "he", "she", "we", "they", "has", "had", "having", "do", "does", "did", "doing", "how", "where", "when", "who", "what", "which", "whoever", "whichever", "why", "because", "which", "all", "any", "some", "none", "each", "few", "many", "much", "more", "less", "most", "least"
    ));

    private static final Pattern EMOJI_REGEX = Pattern.compile("[\\p{So}\\u203C-\\u3299\\uD83C-\\uD83E]");
    private static final Pattern UNICODE_ESCAPE = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");

    // Decode Unicode escapes and check for emojis
    public static boolean containsEmojisInJsonString(String jsonString) {
        // Quick scan before decoding to avoid unnecessary work
        if (jsonString == null){System.out.println("no emoji found for this sentence: "+jsonString);return false;};
        if (!jsonString.contains("\\u")){System.out.println("no emoji found for this sentence: "+jsonString);return false;};

        // Decode Unicode escapes only if needed
        String decoded = decodeUnicode(jsonString);
        System.out.println("emoji found for this sentence: "+jsonString);
        return EMOJI_REGEX.matcher(decoded).find();
    }

    // Efficiently decode Unicode escape sequences
    private static String decodeUnicode(String input) {
        Matcher matcher = UNICODE_ESCAPE.matcher(input);
        StringBuffer decoded = new StringBuffer();

        while (matcher.find()) {
            int codePoint = Integer.parseInt(matcher.group(1), 16);
            matcher.appendReplacement(decoded, Character.toString((char) codePoint));
        }
        matcher.appendTail(decoded);
        return decoded.toString();
    }

    /**
     * Adds attributes to a given message based on its content.
     * This method processes the content of the message, tokenizes it, and checks each token
     * against predefined attributes in an attribute map. If any tokens match an attribute,
     * the attribute is added to the message. The resulting attributes are then added to the
     * message and also associated with the conversation.
     *
     * @param message The message object to which the attributes will be added.
     * @param conversation The conversation object where the message will be associated with its attributes.
     *
     * @throws NullPointerException if the message or conversation is null.
     *
     * @see Message#addAttributeList(HashSet)
     * @see Conversation#addMessageToAttributesMap(Message, HashSet)
     */
    public void addAttribute (Message message, Conversation conversation) {
        //get message content
        String content = message.getContent();
        //hashset that will be attached to the message object at the end
        HashSet<String> attributesHashSet = new HashSet<>();
        //if message content is not empty else skip the attributes processing
        if(message.getContent()!=null) {
            //remove the filler words and get it back as a list
            List<String> tokenizedList = tokenizeAndRemoveFillers(content);
            //loop through tokenized list and then on all the hashset from the hashmap use the contains
            //and if contains get the hashmap string key and add it to the message using message

            //loop through tokenized list
            for (String word : tokenizedList) {
                //loop through each key and value in the attributes map
                for (Map.Entry<String, HashSet<String>> entry : attributesMap.entrySet()) {
                    //get the attribute name
                    String attributeName = entry.getKey();
                    //the hashset for the corresponding attribute
                    HashSet<String> AttributeSet = entry.getValue();
                    //checks if the word exists in the attributeSet
                    if (AttributeSet.contains(word)) {
                        //if it does add the attribute into the messages attribute map
                        attributesHashSet.add(attributeName);
                    }
                }
            }
        }
        //add emojis
        if(containsEmojis(message.getContent())){
            attributesHashSet.add("emojis");
            System.out.println("added emojis to message object: "+message.getContent());
            System.out.println("added emojis to message object: "+message);
        }
        //add the attributes to the message object
        message.addAttributeList(attributesHashSet);
        //System.out.print("attributes added to message: " + attributesHashSet);
        //add the message object in the attributes map in the conversation object
        conversation.addMessageToAttributesMap(message, attributesHashSet);
    }

    //if message already contains attribute dont add it lol use hashset for it not arraylist.
    //Altered to keep emojis
    //Altered to keep emojis
    //Altered to keep words like i'll and don't instead of splitting it up like i ll don t
    //altered to dog. -> dog
    // Better regex to capture words & emojis separately
    private static final Pattern TOKENIZER = Pattern.compile("[\\p{L}\\p{N}'-]+|[\\p{So}\\p{Sc}\\p{Sk}\\x{1F300}-\\x{1FAD6}]");

    public static List<String> tokenizeAndRemoveFillers(String sentence) {
        List<String> filteredWords = new ArrayList<>();
        Matcher matcher = TOKENIZER.matcher(sentence);

        while (matcher.find()) {
            String word = matcher.group();
            if (!FILLER_WORDS.contains(word.toLowerCase())) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    public static boolean containsEmojis(String text) {
        if(text==null){return false;}
        if(EMOJI_REGEX.matcher(text).find()){
            return true;
        }else{
            System.out.println("this message did not have an emoji: "+text);
            return false;
        }
    }

    //TODO: add feature for when you want to have attachment sent as a graph or calls etc or reel sent. ykwim?

    //fixme: this one uses regex to check for emojis doesnt include :)
//    public static boolean containsEmoji(Message message) {
//        if (message == null || message.getContent() == null) {
//            return false;
//        }
//
//        String sentence = message.getContent();
//
//        // Regular expression to match emoji characters
//        String emojiRegex = "[\uD83C-\uDBFF\uDC00-\uDFFF]+";
//
//        Pattern pattern = Pattern.compile(emojiRegex);
//        Matcher matcher = pattern.matcher(sentence);
//
//        return matcher.find();
//    }

//    public boolean containsEmoji(String word) {
//        return emojiMap.containsKey(word);
//    }
    //for testing
    // Method to check if the string contains an emoji
//    public static boolean containsEmoji(String input) {
//        char[] chars = input.toCharArray();
//
//        for (int index = 0; index < chars.length - 1; index++) {
//            char high = chars[index];
//            char low = chars[index + 1];
//
//            // Check if the chars form a surrogate pair
//            if (Character.isHighSurrogate(high) && Character.isLowSurrogate(low)) {
//                return true; // Emoji detected, return true
//            }
//        }
//        return false; // No emoji detected
//    }

}
