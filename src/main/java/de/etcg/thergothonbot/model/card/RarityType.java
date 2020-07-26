package de.etcg.thergothonbot.model.card;

public enum RarityType{
    SECRET_RARE("SECRET RARE", 1), 
    ULTRA_RARE("ULTRA RARE", 2), 
    COMMON("COMMON", 3), 
    SUPER_RARE("SUPER RARE", 4), 
    RARE("RARE", 5), 
    PARALLEL_RARE("PARALLEL RARE", 6), 
    GHOST_RARE("GHOST RARE", 7), 
    GOLD_RARE("GOLD RARE", 8), 
    ULTIMATE_RARE("ULTIMATE RARE", 9), 
    STARFOIL("STARFOIL", 10), 
    MOSAIC_RARE("MOSAIC RARE", 11), 
    GOLD_SECRET_RARE("GOLD SECRET RARE", 12), 
    SHATTERFOIL("SHATTERFOIL", 13), 
    PLATINUM_SECRET_RARE("PLATINUM SECRET RARE", 14), 
    PLATINUM_RARE("PLATINUM RARE", 15)
    ;

    private String name; 
    private int type; 

    private RarityType(String name, int type){
        this.name = name; 
        this.type = type; 
    }

    public static RarityType getRarityType(int type){
        switch(type){
            case 1 : return RarityType.SECRET_RARE;  
            case 2 : return RarityType.ULTRA_RARE;  
            case 3 : return RarityType.COMMON;  
            case 4 : return RarityType.SUPER_RARE;  
            case 5 : return RarityType.RARE;  
            case 6 : return RarityType.PARALLEL_RARE;  
            case 7 : return RarityType.GHOST_RARE;  
            case 8 : return RarityType.GOLD_RARE;  
            case 9 : return RarityType.ULTIMATE_RARE;  
            case 10 : return RarityType.STARFOIL;  
            case 11 : return RarityType.MOSAIC_RARE;  
            case 12 : return RarityType.GOLD_SECRET_RARE;  
            case 13 : return RarityType.SHATTERFOIL;  
            case 14 : return RarityType.PLATINUM_SECRET_RARE;  
            case 15 : return RarityType.PLATINUM_RARE; 
            default: return RarityType.COMMON; 
        }
    }

    public static RarityType getRarityType(String type){
        if(type.contains("GOLD")){
            if(type.contains("SECRET")){
                return RarityType.GOLD_SECRET_RARE;
            }else{
                return RarityType.GOLD_RARE;
            } 
        }else if(type.contains("PLATINUM")){
            if(type.contains("SECRET")){
                return RarityType.PLATINUM_SECRET_RARE;
            }else{
                return RarityType.PLATINUM_RARE;
            }
        }else if(type.contains("SECRET")){
            return RarityType.SECRET_RARE; 
        }else if(type.contains("ULTIMATE")){
            return RarityType.ULTIMATE_RARE; 
        }else if(type.contains("GHOST")){
            return RarityType.GHOST_RARE; 
        }else if(type.contains("ULTRA")){
            return RarityType.ULTRA_RARE; 
        }else if(type.contains("SUPER")){
            return RarityType.SUPER_RARE; 
        }else if(type.contains("COMMON")){
            return RarityType.COMMON; 
        }else if(type.contains("PARALLEL")){
            return RarityType.PARALLEL_RARE; 
        }else if(type.contains("STARFOIL")){
            return RarityType.STARFOIL; 
        }else if(type.contains("MOSAIC")){
            return RarityType.MOSAIC_RARE; 
        }else if(type.contains("SHATTERFOIL")){
            return RarityType.SHATTERFOIL; 
        }else{//Default Rare
            return RarityType.RARE;
        }
    }

    public int getType() {
       return this.type;
    }

    @Override
    public String toString(){
        return this.name;
    }
}