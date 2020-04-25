package com.example.covid19shahajjo.models;

public class ContactsInfo {
    public String UNO;
    public String Police;
    public String FireService;
    public String CivilSurgeon;

    @Override
    public String toString(){
        return String.format("UNO: %s\nCivilSurgeon: %s\nPolice: ", UNO,CivilSurgeon,Police);
    }
}
