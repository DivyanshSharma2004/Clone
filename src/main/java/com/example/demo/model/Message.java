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

import java.text.SimpleDateFormat;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Message {

    private String sender_name;
    private long timestamp_ms;
    private String content;
    private boolean is_geoblocked_for_viewer;
    private Share share;
    private AudioFile[] audio_files;
    private AudioFile[] photos;
    private AudioFile[] videos;
    private Long call_duration;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    private String date;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    private String hour;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    private String minute;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    private String dateTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    HashSet<String> attributeList = new HashSet<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    private String foldername;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Can be read but not set by users
    private int messageIndex;

    //method to add attributes to the message
    public void addAttributeList(HashSet<String> attributeList) {
        this.attributeList = attributeList;
    }

    //primairly as of now used to add emojis to attribute list for safety purposes call after adding the attribute list 
    public void addAttribute(String attribute){
        attributeList.add(attribute);
    }
    //check if message object contains attributes
    public boolean areAttributesPresent(HashSet<String> desiredAttributeSet,HashSet<String> unDesiredAttributeSet) {
        //if attributes wanted is empty hmm acc yeah return
        if(desiredAttributeSet.isEmpty()&&attributeList.isEmpty()){
            return true;
        } else if (desiredAttributeSet.isEmpty()) {
            //for when you want messages that don't have any attributes but might change that with a specific value?
            return false;
        }
        // Iterate through each element in the desiredAttributeSet
        for (String attribute : desiredAttributeSet) {
            // Check if the current element is not in the HashSet
            if (!attributeList.contains(attribute)) {
                return false; // Return false if any element is not found in the HashSet
            }
        }
        // Iterate through each element in the unDesiredAttributeSet
        for (String attribute : unDesiredAttributeSet) {
            // Check if the current element is not in the HashSet
            if (attributeList.contains(attribute)) {
                return false; // Return false if any undesired element is found in the HashSet
            }
        }
        // All elements are found in the HashSet
        return true;
    }


    //write method that returns boolean if laugh is detected in the text.
    static List<String> laughList = Arrays.asList(
           "haha","hehe","hahe","lol","lmao","thats funny","that's funny","thats hilarious ","that's hilarious","\\uD83D\\uDE02","\\uD83E\\uDD23","\\uD83D\\uDE2D","\\uD83D\\uDE06"
    );

    //return true or false in conversation where it checks if it contains laugh etc.
    public boolean containsLaugh(){
        return containsExactWord(laughList);
    }

    public boolean containsExactWord(  List<String> words) {
        for (String word : words) {
            if (content.contains(word)) {
                return true;
            }
        }
        return false;
    }


    //a method that gets fed an arraylist to see if the text it got fed is in the cotnent
    public void setTimestamp_ms(long value) {
        this.timestamp_ms = value;
        setDateHourMinute();
    }


    public int getContentLength(){ return content.length();}

    public boolean getIsGeoblockedForViewer() { return is_geoblocked_for_viewer; }

    private void setDateHourMinute() {
        Date date = new Date(this.timestamp_ms);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");

        this.date = dateFormat.format(date);
        this.hour = hourFormat.format(date);
        this.minute = minuteFormat.format(date);
    }
}



