package de.etcg.thergothonbot.model.card;

public enum MonsterType{
    AQUA(1),
    DINOSAUR(2),
    THUNDER(3),
    DRAGON(4),
    FAIRY(5),
    ROCK(6),
    FISH(7),
    WINGED_BEAST(8),
    SPELLCASTER(9),
    INSECT(10),
    WARRIOR(11),
    MACHINE(12),
    PLANT(13),
    PSYCHIC(14),
    PYRO(15),
    REPTILE(16),
    SEA_SERPENT(17),
    BEAST(18),
    BEAST_WARRIOR(19),
    FIEND(20),
    ZOMBIE(21),
    GOD(22),
    WYRM(23),
    CYBERSE(24)
    ;

    private int type; 

    private MonsterType(int type){
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
        if(type.contains("AQUA")){
            return MonsterType.AQUA;
        }else if(type.contains("DINOSAUR")){
            return MonsterType.DINOSAUR;
        }else if(type.contains("THUNDER")){
            return MonsterType.THUNDER;
        }else if(type.contains("DRAGON")){
            return MonsterType.DRAGON;
        }else if(type.contains("FAIRY")){
            return MonsterType.FAIRY;
        }else if(type.contains("ROCK")){
            return MonsterType.ROCK;
        }else if(type.contains("FISH")){
            return MonsterType.FISH;
        }else if(type.contains("WINGED")){
            return MonsterType.WINGED_BEAST;
        }else if(type.contains("SPELLCASTER")){
            return MonsterType.SPELLCASTER;
        }else if(type.contains("INSECT")){
            return MonsterType.INSECT;
        }else if(type.contains("WARRIOR")){
            if(type.contains("BEAST")){
                return MonsterType.BEAST_WARRIOR;
            }else{
                return MonsterType.WARRIOR;
            }
        }else if(type.contains("MACHINE")){
            return MonsterType.MACHINE;
        }else if(type.contains("PLANT")){
            return MonsterType.PLANT;
        }else if(type.contains("PSYCHIC")){
            return MonsterType.PSYCHIC;
        }else if(type.contains("PYRO")){
            return MonsterType.PYRO;
        }else if(type.contains("REPTILE")){
            return MonsterType.REPTILE;
        }else if(type.contains("SERPENT")){
            return MonsterType.SEA_SERPENT;
        }else if(type.contains("BEAST")){
            return MonsterType.BEAST;
        }else if(type.contains("FIEND")){
            return MonsterType.FIEND;
        }else if(type.contains("ZOMBIE")){
            return MonsterType.ZOMBIE;
        }else if(type.contains("GOD")){
            return MonsterType.GOD;
        }else if(type.contains("WYRM")){
            return MonsterType.WYRM;
        }else if(type.contains("CYBERSE")){
            return MonsterType.CYBERSE;
        }
        return null; 
    }

    public int getType() {
       return this.type;
    }
}