package com.example.covid19shahajjo.models;

import com.example.covid19shahajjo.utils.Enums;

public class Area {
    public String Id;
    public String Name;

    public Area(){

    }

    @Override
    public String toString(){
        return Id+": "+Name;
    }
}
