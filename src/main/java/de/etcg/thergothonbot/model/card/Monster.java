package de.etcg.thergothonbot.model.card; 

import org.json.simple.JSONObject;

import java.util.List; 
import java.util.ArrayList; 

public class Monster extends Card{
    private MonsterType monsterType; 
    private List<EffectType> effectTypes; 
    private int level; 
    private String atk; //ATK und DEF weil es ja ? ATK/DEF gibt...
    private String def = ""; 
    private String pendGerText;
    private String pendEngText; 
    private int pendScale = -1;//bei pendScale < 0 => kein Pendulum 
    private int linkMarkers; //Achtung, link rating bedient sich der "binary logic"
    /*Erklaerung: 
        00000001;00000010;00000100
        00001000;        ;00010000
        00100000;01000000;10000000
        Einfach die gesetzten Stellen aufaddieren und man hat so alle Link Ratings
        z.b.: Link 2 mit Links-Oben(00000001) + Links(00001000) = 00001001
    */

    public Monster(Card card){
        super(card);
    }

    public Monster(Card card, String types, String atk_def, String level, String pendEngText, String pendGerText, String pendScale, String linkMarkers){
        //Achtung, types ist ein simpler String der den Kartentyp(XYZ,Effect, etc.) 
        //als auch die Monstertypen(Dragon, Winged Beast, ...)
        //enthalten
        super(card);
        this.setType(CardType.getCardType(types));
        this.setMonsterType(MonsterType.getMonsterType(types));
        this.setEffectTypes(EffectType.getEffectTypes(types));
        this.setATKDEF(atk_def); 
        if(level != null) this.setLevel(Integer.parseInt(level)); 
        if(pendScale != null) this.setEngPendText(pendEngText);
        if(pendScale != null) this.setGerPendText(pendGerText);
        if(pendScale != null) this.setPendScale(Integer.parseInt(pendScale));
        if(linkMarkers != null) this.setLinkMarkers(linkMarkers);
    }

    public void setMonsterType(MonsterType monsterType){
        this.monsterType = monsterType; 
    }

    public MonsterType getMonsterType(){
        return this.monsterType; 
    }

    public void setEffectTypes(List<EffectType> effectTypes){
        this.effectTypes = effectTypes; 
    }

    public List<EffectType> getEffectTypes(){
        return this.effectTypes; 
    }

    public void setLevel(int level){
        this.level = level; 
    }

    public int getLevel(){
        return this.level;
    }

    public void setATKDEF(String atk_def){
        //System.out.println("ATKDEF: " + atk_def);
        String[] atk_def_list = atk_def.split(" / ");
        this.setAtk(atk_def_list[0]);
        if(atk_def_list.length > 1){
            //Wenn ? DEF, dann blind übernehmen
            if(atk_def_list[1].equals("?")){
                setDef("?");
            }else{
                //Sonst testen wir zuvor ob es sich womöglich um ein Link Rating handelt
                int def_or_link = Integer.parseInt(atk_def_list[1]);
                if(def_or_link > 99 || def_or_link == 0)
                    setDef(atk_def_list[1]);
            }

            
        }
    }

    public void setAtk(String atk){
        this.atk = atk; 
    }

    public String getAtk(){
        return this.atk;
    }

    public void setDef(String def){
        this.def = def; 
    }

    public String getDef(){
        return this.def;
    }

    public void setEngPendText(String pendEngText){
        if(pendEngText != null){
            //Wenn der Text einen doppelten/n-ten Linefeed hat auf einen Linefeed reduzieren
            pendEngText = pendEngText.replaceAll("[\r\n]{2,}", "\n");
            //Wenn der Text mit Linefeed startet oder endet, entfernen
            pendEngText = pendEngText.replaceAll("^[\r\n]{1,}|[\r\n]{1,}$", "");
        }
        this.pendEngText = pendEngText; 
    }

    public String getEngPendText(){
        return this.pendEngText; 
    }

    public void setGerPendText(String pendGerText){
        if(pendGerText != null){
            //Wenn der Text einen doppelten/n-ten Linefeed hat auf einen Linefeed reduzieren
            pendGerText = pendGerText.replaceAll("[\r\n]{2,}", "\n");
            //Wenn der Text mit Linefeed startet oder endet, entfernen
            pendGerText = pendGerText.replaceAll("^[\r\n]{1,}|[\r\n]{1,}$", "");
        }
        this.pendGerText = pendGerText; 
    }

    public String getGerPendText(){
        return this.pendGerText; 
    }

    public void setPendScale(int pendScale){
        this.pendScale = pendScale; 
    }

    public int getPendScale(){
        return this.pendScale; 
    }

    public void setLinkMarkers(int linkMarkers){
        this.linkMarkers = linkMarkers; 
    }

    public void setLinkMarkers(String linkMarkers){
        if(linkMarkers == null) return; 
        int value = 0; 
        String[] markers = linkMarkers.split(", ");
        for(String marker : markers){
            if(marker.equals("Top-Left")){ 
                value += 1; 
            }else if(marker.equals("Top")){ 
                value += 10; 
            }else if(marker.equals("Top-Right")){ 
                value += 100; 
            }else if(marker.equals("Left")){ 
                value += 1000; 
            }else if(marker.equals("Right")){ 
                value += 10000; 
            }else if(marker.equals("Bottom-Left")){ 
                value += 100000; 
            }else if(marker.equals("Bottom")){ 
                value += 1000000; 
            }else if(marker.equals("Bottom-Right")){ 
                value += 10000000;
            }
        }
        this.setLinkMarkers(value);

    }

    public int getLinkMarkers(){
        return this.linkMarkers; 
    }

    @Override
    public JSONObject toJSON(){
        JSONObject card = super.toJSON();
        card.put("MonsterType", this.getMonsterType().getType());
        StringBuilder effectTypes = new StringBuilder("");
        for(EffectType effect : this.getEffectTypes()){
            effectTypes.append(effect.getType());
            effectTypes.append(" ");
        }
        card.put("EffectTypes", effectTypes.toString());
        card.put("level", this.getLevel());
        card.put("atk", this.getAtk());
        card.put("def", this.getDef());
        card.put("engPendText", this.getEngPendText());
        card.put("gerPendText", this.getGerPendText());
        card.put("pendScale", this.getPendScale());//bei pendScale < 0 => kein Pendulum 
        card.put("linkMarkers", this.getLinkMarkers());
        //card.put("", );
        return card; 
    }
}