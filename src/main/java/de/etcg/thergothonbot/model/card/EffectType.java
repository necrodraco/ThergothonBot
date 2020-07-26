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
        if(type.contains("effect")) list.add(EffectType.EFFECT);
        if(type.contains("fusion")) list.add(EffectType.FUSION);
        if(type.contains("ritual")) list.add(EffectType.RITUAL);
        if(type.contains("spirit")) list.add(EffectType.SPIRIT);
        if(type.contains("toon")) list.add(EffectType.TOON);
        if(type.contains("union")) list.add(EffectType.UNION);
        if(type.contains("gemini")) list.add(EffectType.GEMINI);
        if(type.contains("tuner")) list.add(EffectType.TUNER);
        if(type.contains("synchro")) list.add(EffectType.SYNCHRO);
        if(type.contains("xyz")) list.add(EffectType.XYZ);
        if(type.contains("pendulum")) list.add(EffectType.PENDULUM);
        if(type.contains("link")) list.add(EffectType.LINK);
        return list; 
    }

    public String getType() {
       return this.type;
    }

    public static String[] listOfEffectTypes(){
        String[] listofEffectTypes = {
            EffectType.EFFECT.toString(), 
            EffectType.FUSION.toString(), 
            EffectType.RITUAL.toString(),
            EffectType.SPIRIT.toString(), 
            EffectType.TOON.toString(), 
            EffectType.UNION.toString(),
            EffectType.GEMINI.toString(), 
            EffectType.TUNER.toString(), 
            EffectType.SYNCHRO.toString(),
            EffectType.XYZ.toString(), 
            EffectType.PENDULUM.toString(), 
            //EffectType.PENDULUM.toString(), //Pendulum nicht verarbeiten, da entsprechende Routine Pendulum manuell behandelt
            EffectType.LINK.toString()
        };
        return  listofEffectTypes; 
    }

    @Override
    public String toString(){
        return this.type;
    }
}