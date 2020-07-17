package de.etcg.thergothonbot.model.card;

public enum CardType{
    REPRINT(-1),//Keine spezifische ID, alle anderen IDs basieren auf den Value-Wert von Karten-Typ in ygo_card_add
    MONSTER_EARTH(1), 
    MONSTER_FIRE(2), 
    MONSTER_DARK(3), 
    MONSTER_LIGHT(4), 
    MONSTER_WATER(5), 
    MONSTER_WIND(6), 
    MONSTER_GOD(16), 
    SPELL_NORMAL(15), 
    SPELL_EQUIP(7), 
    SPELL_CONTINUOUS(8), 
    SPELL_RITUAL(9), 
    SPELL_QUICKPLAY(10), 
    SPELL_FIELD(11),
    TRAP_NORMAL(14),
    TRAP_CONTINUOUS(13),
    TRAP_COUNTER(12),
    SKILL(17);

    private int type; 

    private CardType(int type){
        this.type = type; 
    }

    public static CardType getCardType(int type){
        switch(type){
            case 1: return CardType.MONSTER_EARTH;
            case 2: return CardType.MONSTER_FIRE;
            case 3: return CardType.MONSTER_DARK;
            case 4: return CardType.MONSTER_LIGHT;
            case 5: return CardType.MONSTER_WATER;
            case 6: return CardType.MONSTER_WIND;
            case 16: return CardType.MONSTER_GOD;
            case 15: return CardType.SPELL_NORMAL; 
            case 7: return CardType.SPELL_EQUIP;
            case 8: return CardType.SPELL_CONTINUOUS;
            case 9: return CardType.SPELL_RITUAL;
            case 10: return CardType.SPELL_QUICKPLAY;
            case 11: return CardType.SPELL_FIELD;
            case 14: return CardType.TRAP_NORMAL;
            case 13: return CardType.TRAP_CONTINUOUS;
            case 12: return CardType.TRAP_COUNTER;
            case 17: return CardType.SKILL;
            default: return CardType.REPRINT; 
        }
    }

    public static CardType getCardType(String type){
        if(type.contains("MONSTER")){
            if(type.contains("LIGHT")){
                return CardType.MONSTER_LIGHT;
            }else if(type.contains("DARK")){
                return CardType.MONSTER_DARK;
            }else if(type.contains("FIRE")){
                return CardType.MONSTER_FIRE;
            }else if(type.contains("WATER")){
                return CardType.MONSTER_WATER;
            }else if(type.contains("EARTH")){
                return CardType.MONSTER_EARTH;
            }else if(type.contains("WIND")){
                return CardType.MONSTER_WIND;
            }else{
                return CardType.MONSTER_GOD;
            }
        }else if(type.contains("SPELL")){
            if(type.contains("CONTINUOUS")){
                return CardType.SPELL_CONTINUOUS;
            }else if(type.contains("EQUIP")){
                return CardType.SPELL_EQUIP;
            }else if(type.contains("RITUAL")){
                return CardType.SPELL_RITUAL;
            }else if(type.contains("QUICKPLAY")){
                return CardType.SPELL_QUICKPLAY;
            }else if(type.contains("FIELD")){
                return CardType.SPELL_FIELD; 
            }else{
                return CardType.SPELL_NORMAL;
            }
        }else if(type.contains("TRAP")){
            if(type.contains("CONTINUOUS")){
                return CardType.TRAP_CONTINUOUS;
            }else if(type.contains("COUNTER")){
                return CardType.TRAP_COUNTER;
            }else{
                return CardType.TRAP_NORMAL;
            }
        }else if(type.contains("SKILL")){
            return CardType.SKILL; 
        }
        return CardType.REPRINT; 
    }

    public int getType() {
       return this.type;
    }

    public boolean isMonster(){
        return this.type == 16 
            || (
                this.type >= 1 
                && this.type <= 6
            ); 
    }
}