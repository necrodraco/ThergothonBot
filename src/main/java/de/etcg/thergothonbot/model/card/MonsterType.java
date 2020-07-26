package de.etcg.thergothonbot.model.card;

public enum MonsterType{
    AQUA("Aqua", 1),
    DINOSAUR("Dinosaur", 2),
    THUNDER("Thunder", 3),
    DRAGON("Dragon", 4),
    FAIRY("Fairy", 5),
    ROCK("Rock", 6),
    FISH("Fish", 7),
    WINGED_BEAST("Winged Beast", 8),
    SPELLCASTER("spellcaster", 9),
    INSECT("Insect", 10),
    WARRIOR("Warrior", 11),
    MACHINE("Machine", 12),
    PLANT("Plant", 13),
    PSYCHIC("Psychic", 14),
    PYRO("Pyro", 15),
    REPTILE("Reptile", 16),
    SEA_SERPENT("Sea Serpent", 17),
    BEAST("Beast", 18),
    BEAST_WARRIOR("Beast Warrior", 19),
    FIEND("Fiend", 20),
    ZOMBIE("Zombie", 21),
    GOD("God", 22),
    WYRM("Wyrm", 23),
    CYBERSE("Cyberse: ", 24)
    ;

    private String name; 
    private int type; 

    private MonsterType(String name, int type){
        this.name = name; 
        this.type = type; 
    }

    public static MonsterType getMonsterType(int type){
        switch(type){
            case 1: return MonsterType.AQUA;
            case 2: return MonsterType.DINOSAUR;
            case 3: return MonsterType.THUNDER;
            case 4: return MonsterType.DRAGON;
            case 5: return MonsterType.FAIRY;
            case 6: return MonsterType.ROCK;
            case 7: return MonsterType.FISH;
            case 8: return MonsterType.WINGED_BEAST;
            case 9: return MonsterType.SPELLCASTER;
            case 10: return MonsterType.INSECT;
            case 11: return MonsterType.WARRIOR;
            case 12: return MonsterType.MACHINE;
            case 13: return MonsterType.PLANT;
            case 14: return MonsterType.PSYCHIC;
            case 15: return MonsterType.PYRO;
            case 16: return MonsterType.REPTILE;
            case 17: return MonsterType.SEA_SERPENT;
            case 18: return MonsterType.BEAST;
            case 19: return MonsterType.BEAST_WARRIOR;
            case 20: return MonsterType.FIEND;
            case 21: return MonsterType.ZOMBIE;
            case 22: return MonsterType.GOD;
            case 23: return MonsterType.WYRM;
            case 24: return MonsterType.CYBERSE;
        }
        return null;
    }

    public static MonsterType getMonsterType(String type){
        if(type.contains("aqua")){
            return MonsterType.AQUA;
        }else if(type.contains("dinosaur")){
            return MonsterType.DINOSAUR;
        }else if(type.contains("thunder")){
            return MonsterType.THUNDER;
        }else if(type.contains("dragon")){
            return MonsterType.DRAGON;
        }else if(type.contains("fairy")){
            return MonsterType.FAIRY;
        }else if(type.contains("rock")){
            return MonsterType.ROCK;
        }else if(type.contains("fish")){
            return MonsterType.FISH;
        }else if(type.contains("winged")){
            return MonsterType.WINGED_BEAST;
        }else if(type.contains("spellcaster")){
            return MonsterType.SPELLCASTER;
        }else if(type.contains("insect")){
            return MonsterType.INSECT;
        }else if(type.contains("warrior")){
            if(type.contains("beast")){
                return MonsterType.BEAST_WARRIOR;
            }else{
                return MonsterType.WARRIOR;
            }
        }else if(type.contains("machine")){
            return MonsterType.MACHINE;
        }else if(type.contains("plant")){
            return MonsterType.PLANT;
        }else if(type.contains("psychic")){
            return MonsterType.PSYCHIC;
        }else if(type.contains("pyro")){
            return MonsterType.PYRO;
        }else if(type.contains("reptile")){
            return MonsterType.REPTILE;
        }else if(type.contains("serpent")){
            return MonsterType.SEA_SERPENT;
        }else if(type.contains("beast")){
            return MonsterType.BEAST;
        }else if(type.contains("fiend")){
            return MonsterType.FIEND;
        }else if(type.contains("zombie")){
            return MonsterType.ZOMBIE;
        }else if(type.contains("god")){
            return MonsterType.GOD;
        }else if(type.contains("wyrm")){
            return MonsterType.WYRM;
        }else if(type.contains("cyberse")){
            return MonsterType.CYBERSE;
        }
        return null; 
    }

    public int getType() {
       return this.type;
    }

    @Override
    public String toString(){
        return this.name;
    }
}