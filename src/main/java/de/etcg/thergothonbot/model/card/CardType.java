package de.etcg.thergothonbot.model.card;

public enum CardType{
    MONSTER_EARTH("earth", 1), 
    MONSTER_FIRE("fire", 2), 
    MONSTER_DARK("dark", 3), 
    MONSTER_LIGHT("light", 4), 
    MONSTER_WATER("water", 5), 
    MONSTER_WIND("wind", 6), 
    MONSTER_GOD("god", 16), 
    SPELL_NORMAL("normal spell", 15), 
    SPELL_EQUIP("equip spell", 7), 
    SPELL_CONTINUOUS("continuous spell", 8), 
    SPELL_RITUAL("ritual spell", 9), 
    SPELL_QUICKPLAY("quick-play spell", 10), 
    SPELL_FIELD("fieldspell", 11),
    TRAP_NORMAL("normal trap", 14),
    TRAP_CONTINUOUS("continuous trap", 13),
    TRAP_COUNTER("counter trap", 12),
    SKILL("skill card", 17);

    private String name; 
    private int type; 

    private CardType(String name, int type){
        this.name = name; 
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
            default: return CardType.MONSTER_EARTH; 
        }
    }

    public static CardType getCardType(String type){
        if(type.contains("monster")){
            if(type.contains("light")){
                return CardType.MONSTER_LIGHT;
            }else if(type.contains("dark")){
                return CardType.MONSTER_DARK;
            }else if(type.contains("fire")){
                return CardType.MONSTER_FIRE;
            }else if(type.contains("water")){
                return CardType.MONSTER_WATER;
            }else if(type.contains("earth")){
                return CardType.MONSTER_EARTH;
            }else if(type.contains("wind")){
                return CardType.MONSTER_WIND;
            }else{
                return CardType.MONSTER_GOD;
            }
        }else if(type.contains("spell")){
            if(type.contains("continuous")){
                return CardType.SPELL_CONTINUOUS;
            }else if(type.contains("equip")){
                return CardType.SPELL_EQUIP;
            }else if(type.contains("ritual")){
                return CardType.SPELL_RITUAL;
            }else if(type.contains("quick-play")){
                return CardType.SPELL_QUICKPLAY;
            }else if(type.contains("field")){
                return CardType.SPELL_FIELD; 
            }else{
                return CardType.SPELL_NORMAL;
            }
        }else if(type.contains("trap")){
            if(type.contains("continuous")){
                return CardType.TRAP_CONTINUOUS;
            }else if(type.contains("counter")){
                return CardType.TRAP_COUNTER;
            }else{
                return CardType.TRAP_NORMAL;
            }
        }else if(type.contains("skill")){
            return CardType.SKILL; 
        }
        return CardType.MONSTER_EARTH; 
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

    @Override
    public String toString(){
        return this.name;
    }
}