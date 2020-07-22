package de.etcg.thergothonbot.model.card;

import java.util.List; 
import java.util.ArrayList; 

public enum EffectType{
    EFFECT("effect"), FUSION("fusion"), RITUAL("ritual"),
    SPIRIT("spirit"), TOON("toon"), UNION("union"),
    GEMINI("gemini"), TUNER("empfaenger"), SYNCHRO("synchro"),
    XYZ("xyz"), PENDULUM("pendulum"), LINK("link")
    ;

    private String type; 

    private EffectType(String type){
        this.type = type; 
    }

    public static List<EffectType> getEffectTypes(String type){
        List<EffectType> list = new ArrayList<EffectType>();
        if(type.contains("EFFECT")) list.add(EffectType.EFFECT);
        if(type.contains("FUSION")) list.add(EffectType.FUSION);
        if(type.contains("RITUAL")) list.add(EffectType.RITUAL);
        if(type.contains("SPIRIT")) list.add(EffectType.SPIRIT);
        if(type.contains("TOON")) list.add(EffectType.TOON);
        if(type.contains("UNION")) list.add(EffectType.UNION);
        if(type.contains("GEMINI")) list.add(EffectType.GEMINI);
        if(type.contains("TUNER")) list.add(EffectType.TUNER);
        if(type.contains("SYNCHRO")) list.add(EffectType.SYNCHRO);
        if(type.contains("XYZ")) list.add(EffectType.XYZ);
        if(type.contains("PENDULUM")) list.add(EffectType.PENDULUM);
        if(type.contains("LINK")) list.add(EffectType.LINK);
        return list; 
    }

    public String getType() {
       return this.type;
    }

    public static String[] listOfEffectTypes(){
        String[] listofEffectTypes = {
            "effect", "fusion", "ritual",
            "spirit", "toon", "union",
            "gemini", "empfaenger", "synchro",
            "xyz", "link"
            //"PENDULUM", //Pendulum nicht verarbeiten, da entsprechende Routine Pendulum manuell behandelt
        };
        return  listofEffectTypes; 
    }
}