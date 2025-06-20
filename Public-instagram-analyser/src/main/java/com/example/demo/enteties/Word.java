package com.example.demo.enteties;

import lombok.Getter;

import java.util.ArrayList;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Word {
    @Getter
    String word;
    int count;
    @Getter
    ArrayList<String> dateTimeList;

    public Word(String word, long dataTime){
        this.word = word;
        dateTimeList = new ArrayList<>();
        addDateTime(dataTime);
    }
    public Word() {

    }

    public void addDateTime(long time){
        //turn time from epoch into dd-mm-yyyy
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = dateTime.format(formatter);
        dateTimeList.add(formattedDate);

    }
    //method that retuns how many apperences for a word
    public int getCount(){
        return dateTimeList.size();
    }

}
