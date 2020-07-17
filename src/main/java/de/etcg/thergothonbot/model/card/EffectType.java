package de.etcg.thergothonbot.model.card;

import java.util.List; 
import java.util.ArrayList; 

public enum EffectType{
    EFFECT("EFFECT"), FUSION("FUSION"), RITUAL("RITUAL"),
    SPIRIT("SPIRIT"), TOON("TOON"), UNION("UNION"),
    GEMINI("GEMINI"), TUNER("TUNER"), SYNCHRO("SYNCHRO"),
    XYZ("XYZ"), PENDULUM("PENDULUM"), LINK("LINK")
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
}