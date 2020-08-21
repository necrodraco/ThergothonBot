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
    private List<LinkArrow> linkArrows; 

    public Monster(Card card){
        super(card);
    }

    public Monster(Card card, String types, String atk_def, String level, String pendEngText, String pendGerText, String pendScale, String linkArrows){
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
        if(linkArrows != null){
            this.setLinkArrows(LinkArrow.getLinkArrows(linkArrows));
        }else{
            this.setLinkArrows(new ArrayList<LinkArrow>());
        }
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
        String[] atk_def_list = atk_def.split(" / ");
        this.setAtk(atk_def_list[0]);
        if(atk_def_list.length > 1){
            //Wenn ? DEF, dann blind übernehmen
            if(atk_def_list[1].equals("?")){
                setDef("?");
            }else if(atk_def_list[1].equals("X000")){
                setDef("X000");
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

    public void setLinkArrows(List<LinkArrow> linkArrows){
        this.linkArrows = linkArrows; 
    }

    public List<LinkArrow> getLinkArrows(){
        return this.linkArrows; 
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
        StringBuilder linkArrows = new StringBuilder("");
        for(LinkArrow arrows : this.getLinkArrows()){
            linkArrows.append(arrows.getType());
            linkArrows.append(", ");
        }
        card.put("linkArrows", linkArrows.toString());
        //card.put("", );
        return card; 
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(super.toString());
        if(!this.isReprint()){
            sb.append("MonsterType: " + this.getMonsterType() + "\n");
            sb.append("EffectTypen: " );
            for(EffectType effect : this.getEffectTypes())
                sb.append("" + effect + " ");
            sb.append("\n");
            sb.append("Level: " + this.getLevel() + "\n");
            sb.append("atk: " + this.getAtk() + "\n");
            sb.append("def: " + this.getDef() + "\n");
            if(this.getPendScale() >= 0){
            sb.append("deutscher Pendel-Effekt: " + this.getGerPendText() + "\n");
            sb.append("englischer Pendel-Effekt: " + this.getEngPendText() + "\n");
            sb.append("Pendel-Skala: " + this.getPendScale()); 
            }
            if(this.getLinkArrows().size() > 0){
                sb.append("linkArrows: ");
                for(LinkArrow arrows : this.getLinkArrows()){
                    sb.append("" + arrows + ", ");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}