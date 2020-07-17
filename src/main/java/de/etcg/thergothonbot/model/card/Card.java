package de.etcg.thergothonbot.model.card;

import org.json.simple.JSONObject;

public class Card{
    private String nrId; //e.g. A00 von LEDD-ENA00
    private String setId; //e.g. LEDD von LEDD-ENA00

    private int etcgSetId; //Enthält die Id, welche in der CSE für die setId verwendet wird.
    
    private String engName; 
    private String gerName; 
    private String engText; 
    private String gerText; 
    private String gba; //8-stelliger Kartencode, mit führenden Nullen deshalb string

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

    public String getGerId(){
        return this.setId + "-DE" + this.nrId; 
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
        this.gerText = gerText; 
    }

    public String getGerText(){
        return this.gerText; 
    }

    public void setEngText(String engText){
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
        card.put("rarity", this.rarity.getType());
        card.put("CardType", this.type.getType());
        //card.put("", );
        return card; 
    }
}