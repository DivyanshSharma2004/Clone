/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.enteties;

import java.util.ArrayList;

/**
 *
 * @author divme
 */
public class MessagesOrganisedByAttributes {
    ArrayList<String> list = new ArrayList<>(); 
    String content ="";
    
    public MessagesOrganisedByAttributes(String content){
        this.content = content;
    }
    
    public void addAttribute(String Attribute){
        list.add(Attribute);
    }
    
    public boolean checkIfNoAttributes(){
        if(!list.isEmpty()){
            return false;
        }
        return true;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public String getContent() {
        return content;
    }
    
}
