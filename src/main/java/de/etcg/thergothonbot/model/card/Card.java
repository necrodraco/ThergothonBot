package de.etcg.thergothonbot.model.card;

import org.json.simple.JSONObject;

public class Card{
    private String setId; //e.g. LEDD von LEDD-ENA00
    private String nrId; //e.g. A00 von LEDD-ENA00
    
    private int etcgSetId; //Enthält die Id, welche in der CSE für die setId verwendet wird.
    
    private String engName; 
    private String gerName; 
    private String engText; 
    private String gerText; 
    private String gba = ""; //8-stelliger Kartencode, mit führenden Nullen deshalb string. Außerdem besitzen Skillcards keine GBA

    private RarityType rarity; 

    private CardType type; 

    public Card(String id, int etcgSetId){
        setId(id); 
        this.etcgSetId = etcgSetId; 
    }

    //Copy-Constructor
    protected Card(Card card){
        this.nrId = card.nrId; 
        this.setId = card.setId; 
        this.etcgSetId = card.etcgSetId; 
        this.engName = card.engName;  
        this.gerName = card.gerName;  
        this.engText = card.engText;  
        this.gerText = card.gerText;  
        this.gba = card.gba; 
        this.rarity = card.rarity;  
        this.type = card.type;  
    }

    private void setId(String id){
        String[] ids = id.split("-EN|-DE");
        this.setId = ids[0];
        this.nrId = ids[1]; 
    }

    public String getId(){
        return this.setId + "-EN" + this.nrId; 
    }

    public int getEtcgId(){
        return this.etcgSetId; 
    }

    public String getNrId(){
        return this.nrId; 
    }

    public void setEngName(String engName){
        this.engName = engName; 
    }

    public String getEngName(){
        return this.engName; 
    }

    public void setGerName(String gerName){
        this.gerName = gerName; 
    }

    public String getGerName(){
        return this.gerName; 
    }

    public void setGerText(String gerText){
        if(gerText != null){
            //Wenn der Text einen doppelten/n-ten Linefeed hat auf einen Linefeed reduzieren
            gerText = gerText.replaceAll("[\r\n]{2,}", "\n");
            //Wenn der Text mit Linefeed startet oder endet, entfernen
            gerText = gerText.replaceAll("^[\r\n]{1,}|[\r\n]{1,}$", "");
        } 
        this.gerText = gerText; 
    }

    public String getGerText(){
        return this.gerText; 
    }

    public void setEngText(String engText){
        if(engText != null){
            //Wenn der Text einen doppelten/n-ten Linefeed hat auf einen Linefeed reduzieren
            engText = engText.replaceAll("[\r\n]{2,}", "\n");
            //Wenn der Text mit Linefeed startet oder endet, entfernen
            engText = engText.replaceAll("^[\r\n]{1,}|[\r\n]{1,}$", "");
        }
        this.engText = engText; 
    }

    public String getEngText(){
        return this.engText; 
    }

    public void setGBA(String gba){
        this.gba = gba; 
    }

    public String getGBA(){
        return this.gba; 
    }

    public void setRarityType(RarityType rarity){
        this.rarity = rarity; 
    }

    public RarityType getRarityType(){
        return this.rarity; 
    }

    public void setType(CardType type){
        this.type = type; 
    }

    public CardType getType(){
        return this.type; 
    }

    public JSONObject toJSON(){
        JSONObject card = new JSONObject();
        card.put("id", this.getId());
        card.put("etcgId", this.getEtcgId());
        card.put("gerName", this.getGerName());
        card.put("gerText", this.getGerText());
        card.put("engName", this.getEngName());
        card.put("engText", this.getEngText());
        card.put("gba", this.getGBA());
        card.put("rarity", this.getRarityType().getType());
        card.put("CardType", this.getType().getType());
        //card.put("", );
        return card; 
    }

    public boolean isReprint(){
        return this.getType().getType() < 0; 
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Wird als neue Karte eingetragen: \n");
        sb.append("id: " + this.getId() + "\n"); 
        sb.append("deutscher Kartenname: " + this.getGerName() + "\n"); 
        sb.append("deutscher EffektText: " + this.getGerText() + "\n"); 
        sb.append("englischer Kartenname: " + this.getEngName() + "\n"); 
        sb.append("englischer EffektText: " + this.getEngText() + "\n"); 
        sb.append("GBA: " + this.getGBA() + "\n"); 
        sb.append("Rarität: " + this.getRarityType() + "\n"); 
        sb.append("Attribut: " + this.getType() + "\n"); 
        return sb.toString();
    }

    public String toStringAsReprint(){
        StringBuilder sb = new StringBuilder("Wird als Reprint eingetragen: \n");
        sb.append("id: " + this.getId() + "\n"); 
        sb.append("englischer Kartenname: " + this.getEngName() + "\n"); 
        sb.append("Rarität: " + this.getRarityType() + "\n"); 
        return sb.toString(); 
    }
}