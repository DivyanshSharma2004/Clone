package com.example.demo.enteties;

public class ParticipantStats {
    String name;
    int laughCount;
    int totalMessages;

    public ParticipantStats(String name) {
        this.name = name;
        laughCount = 0;
        totalMessages = 0;
    }

    public void addLaugh(){
        laughCount +=1;
    }
    public void addLaugh(int i){
        laughCount +=i;
    }
    public void addMessages(){
        laughCount +=1;
    }
    public void addMessages(int i){
        laughCount +=i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLaughCount() {
        return laughCount;
    }

    public void setLaughCount(int laughCount) {
        this.laughCount = laughCount;
    }

    public int getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(int totalMessages) {
        this.totalMessages = totalMessages;
    }
}
